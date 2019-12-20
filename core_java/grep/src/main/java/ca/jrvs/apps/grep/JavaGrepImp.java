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
    private String root_path;
    private String outfile;


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
            List<String> matched_line = new ArrayList<String>();
            for(File file : listFiles(this.root_path)){
                for( String line : readLines(file) ){
                    if(containsPattern(line)){
                        matched_line.add(line);
                    }
                }
            }
            writeToFile(matched_line);

    }

    //Traverse a given directory and return all files
    @Override
    public List<File> listFiles(String rootDir){
        // initialize the output List
        List<File> all_files= new ArrayList<File>();
        File root = new File(rootDir);
        // listing all child file
        File[] child = root.listFiles();
        for( File i : child){
            // check if child is sub-directory
            if(i.isDirectory()){
                List<File> child_dir = listFiles(i.getAbsolutePath());
                all_files.addAll(child_dir);
            }else{
                all_files.add(i);
            }
        }
        return all_files;
    }

    // read a file and return all the lines
    @Override
    public List<String> readLines(File inoutFile){
        // pre-condition: check if inoutfile is a normal file
        if(inoutFile.isFile() == false){
            throw new IllegalArgumentException("No such file exists");
        }
        List<String> all_lines = new ArrayList<String>();
        try{
            BufferedReader buffer_reader = new BufferedReader(new FileReader(inoutFile));
            String line = buffer_reader.readLine();
            while(line != null){
                all_lines.add(line);
                line = buffer_reader.readLine();
            }
        // handle FileReader()
        }catch (FileNotFoundException e){
            e.fillInStackTrace();
        // handle readLine()
        }catch (IOException e){
            e.fillInStackTrace();
        }
        return all_lines;
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
        BufferedWriter buffer_writer = new BufferedWriter(new FileWriter(this.getOutFile()));
        for (String line : lines){
            buffer_writer.write(line);
            buffer_writer.newLine();
        }
        buffer_writer.close();
    }

    // getters and setters for 3 arguments
    @Override
    public String getRootPath(){
        return root_path;
    }
    // getters and setters for 3 var
    @Override
    public void setRootPath(String rootPath){
        this.root_path = rootPath;
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
        return outfile;
    }
    @Override
    public void setOutFile(String outFile){
        this.outfile = outFile;
    }

}
