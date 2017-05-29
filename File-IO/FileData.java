/**
 * Elam, Jonathan 
 * Personal Code Library
 * File IO - Generic 
 */

import java.io.IOException;

public class FileData {
    
    public static void main(String[] args) throws IOException {
        
        String file_name = "sourceText.txt";
        String file_name_program = "appendText.txt";
        
        try {
            ReadFile file = new ReadFile(file_name);
            String[] everyLine = file.openFile();
            
            int i;
            for ( i=0; i < everyLine.length; i++ ) {
                System.out.println( everyLine[ i ] ) ;
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }  
        
        /*
        
        Write to file
        Seperate 
        
        */

        WriteFile data = new WriteFile(file_name_program, true);
        String n = "<Place Text Here>" ;
        data.writeToFile(n);
  
    }
}
