/**
 * Elam, Jonathan 
 * Personal Code Library
 * ReadFile IO - Generic 
 */

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {
    
    private final String path;
    
    public ReadFile(String file_path){
        
        path = file_path; 
    }
    
    public String[] openFile() throws IOException {
        FileReader fr = new FileReader(path); // reads bytes from text file, where each byte is a single char
        String[] textData;
        try (BufferedReader textReader = new BufferedReader(fr) // reads a whole line instead of single chars
        ) {
            int numberOfLines = readLines();
            textData = new String[numberOfLines];
            int i;
            for (i=0; i < numberOfLines; i++) {
                textData[i] = textReader.readLine();
            }
        }
            return textData;
    }
    
    int readLines() throws IOException {
        
        FileReader file_to_read = new FileReader(path);
        int numberOfLines;
        try (BufferedReader bf = new BufferedReader(file_to_read)) {
            String aLine;
            numberOfLines = 0;
            while (( aLine = bf.readLine()) != null ) {
                numberOfLines++;
            }
        }
        
        return numberOfLines;
    }
    
}
