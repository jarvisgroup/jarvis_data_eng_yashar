package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.io.util.List;
import java.util.List;

public interface JavaGrep {
    /*Top level search workflow
      throw IOException
    */
    void process() throws IOException;

    /*
    Traverse a given directory and return all files
    @param rootDir input directory
    @return files under rootDir
     */
    List<File> listFiles(String rootDir);

    /*
     * read a line and return all the lines
     * explain FileReader, BufferReader, and char encoding
     * @param inoutFile file to be read
     * @return lines
     * @throws IllegalArgumentException if a given inputFile is not a file
     */
    List<String> readLines(File inoutFile);

    /*
     *check if a line contains the regex pattern
     * @param line inout string
     * @return true if there is a match
     */
    boolean containsPattern(String line);

    /*
     *Write lines to a file
     * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
     * @param lines matches line
     * throws IOException if write failed
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);

}
