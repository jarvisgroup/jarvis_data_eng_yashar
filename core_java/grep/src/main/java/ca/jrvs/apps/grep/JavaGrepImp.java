package ca.jrvs.apps.grep;

import java.io.*;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{

    // setting vars
    private String regex;
    private String rootPath;
    private String outFile;


    public static void main(String[] args){
        if(args.length !=3){
            throw new IllegalArgumentException("Usage: regex rootPath outfile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void process() throws IOException {
            List<String> matchedLine = new ArrayList<String>();
            for(File file : listFiles(this.rootPath)){
                for( String line : readLines(file) ){
                    if(containsPattern(line)){
                        matchedLine.add(line);
                    }
                }
            }
            writeToFile(matchedLine);

    }

    //Traverse a given directory and return all files
    @Override
    public List<File> listFiles(String rootDir){
        // initialize the output List
        List<File> allFiles= new ArrayList<File>();
        File root = new File(rootDir);
        // listing all child file
        File[] child = root.listFiles();
        for( File i : child){
            // check if child is sub-directory
            if(i.isDirectory()){
                List<File> childDir = listFiles(i.getAbsolutePath());
                all_files.addAll(childDir);
            }else{
                all_files.add(i);
            }
        }
        return allFiles;
    }

    // read a file and return all the lines
    @Override
    public List<String> readLines(File inoutFile){
        // pre-condition: check if inoutfile is a normal file
        if(inoutFile.isFile() == false){
            throw new IllegalArgumentException("No such file exists");
        }
        List<String> allLines = new ArrayList<String>();
        try{
            BufferedReader bufferReader = new BufferedReader(new FileReader(inoutFile));
            String line = bufferReader.readLine();
            while(line != null){
                all_lines.add(line);
                line = bufferReader.readLine();
            }
        // handle FileReader()
        }catch (FileNotFoundException e){
            e.fillInStackTrace();
        // handle readLine()
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return allLines;
    }

    //    check if a line contains the regex pattern
    @Override
    public boolean containsPattern(String line){
        boolean result = false;
        Pattern regex = Pattern.compile(this.getRegex());
        Matcher match = regex.matcher(line);
        if(match.find()) {
            result = true;
        }
        return result;
    }

    // Write lines to a file
    @Override
    public void writeToFile(List<String> lines) throws IOException{
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(this.getOutFile()));
        for (String line : lines){
            bufferWriter.write(line);
            bufferWriter.newLine();
        }
        bufferWriter.close();
    }

    // getters and setters for 3 vars
    @Override
    public String getRootPath(){
        return rooTPath;
    }
    
    @Override
    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
    }
    @Override
    public String getRegex(){
        return regex;
    }
    @Override
    public void setRegex(String regex){
        this.regex = regex;
    }
    @Override
    public String getOutFile(){
        return outFile;
    }
    @Override
    public void setOutFile(String outFile){
        this.outFile = outFile;
    }

}
