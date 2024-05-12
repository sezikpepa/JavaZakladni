package expressionsParser;

import logicalExpressions.INode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser extends BasicExpressionParser{


    private String _filePath;

    public FileParser(String filePath){
        _filePath = filePath;
    }


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
