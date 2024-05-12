package org.example;

import expressionsParser.CommandLineParser;
import expressionsParser.FileParser;
import logicalExpressions.Expression;

public class Main {
    public static void main(String[] args) {
        var parser = new FileParser("C:\\matfyz\\vyrokynajavu.txt");

        parser.loadInput();
        var parsed = parser.getExpressions();
        for (Expression expression : parsed){
            System.out.println(expression);
        }
    }
}