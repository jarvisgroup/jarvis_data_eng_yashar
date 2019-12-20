package ca.jrvs.apps.grep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp  extends JavaGrepImp{

    public static void main(String[] args){
        if(args.length !=3){
            throw new IllegalArgumentException("Usage: regex rootPath outfile");
        }
        JavaGrepLambdaImp javaGreplambdaImp = new JavaGrepLambdaImp();
        javaGreplambdaImp.setRegex(args[0]);
        javaGreplambdaImp.setRootPath(args[1]);
        javaGreplambdaImp.setOutFile(args[2]);

        try{
            javaGreplambdaImp.process();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //Traverse a given directory and return all files
    @Override
    public List<File> listFiles(String rootDir){
        List<File> files = new ArrayList<File>();
        try {
            files = Files.walk(Paths.get(rootDir)).map(Path::toFile).filter(File::isFile).collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return files;
    }

    // read a file and return all the lines
    @Override
    public List<String> readLines(File inoutFile){
        // pre-condition: check if inoutfile is a normal file
        List<String> all_lines= new ArrayList<String>();
        try{
            BufferedReader buffer_reader = new BufferedReader(new FileReader(inoutFile));
            all_lines = buffer_reader.lines().collect(Collectors.toList());
        }catch (FileNotFoundException e) {
            e.fillInStackTrace();
        }
        return all_lines;
    }


}


