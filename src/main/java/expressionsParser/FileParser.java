package expressionsParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loads expressions from text files and parse them into tree structure
 */
public class FileParser extends BasicExpressionParser{

    /**
     * Location of the file in the disk
     */
    private String _filePath;

    /**
     * Creates new FileParser
     * @param filePath location of the file in the disk
     */
    public FileParser(String filePath){
        _filePath = filePath;
    }

    /**
     * It opens the file and then reads it by lines. Every line is then transformed into expression represented by a tree structure
     */
    public void loadInput(){
        tempExpressions.clear();
        try(BufferedReader reader = new BufferedReader(new FileReader(_filePath))){
            String readLine;
            while((readLine = reader.readLine()) != null){
                tempExpressions.add(readLine);
            }
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
            tempExpressions.clear();
        }
    }
}
