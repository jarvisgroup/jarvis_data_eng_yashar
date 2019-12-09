## Introduction 
The `Grep` App is a simply Java app that searches for a text pattern recursively in a given directory,
and output matched lines to a file. 

We use Maven for project management for `Grep` App, and a few methods are override by using Lambda and 
Stream APIs for practice purpose. 

## Usage
This App takes three arguments:
1. `regex` : the regex pattern that need to be searched for.
2. `rootPath` : the path to the directory that will be searched recursively, including all the child directories.
3. `outFile` : a file that stores all the matched lines from the path directory. 

Simple Example: 
```
  .*hello.* /home/user /tmp/result.txt
  /* a regex pattern hello will be searched through directory
   * ./home/user and matched line will be stored into /tmp/result.txt
  */
```


## Pseudocode 
`process` method is for high level workflow purpose.
```
matchedline [] 
for file in listfile(rootDir)
    for line in readLines(file)
        if containPattern(line)
            matchedline.add(line)
writetoFile(matchedline)
``` 
## Performance Issue
Since we are using `BufferReader` to read each line for every single file to check for 
Regex pattern, when the File size is big (50GB as mentioned), every single line from the 50 GB file will be stored into
the memory which will cause a memory problem. 

## Improvements 
1. A simple UI : Having a simple user interface will better help the user to visualize the regex and matched-lines, can use a simple Java GUI 
framework to achieve this. 
2. Detailed Output File : Currently, only the matched_lines are being stored into the output file, the path to that matched_line
should be printed along with the line itself to improve the readability.
3. Time Constrain : There should be a time constrain when the app is searching through the directory, for example, if the time that searching through a file is
relatively longer that searching other file, the program should abort the file and move on to the next one. Longer search time means the file is too large. 