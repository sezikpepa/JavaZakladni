package expressionPrinters;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Saves expressions into file
 */
public class FilePrinter implements ExpressionPrinter{

    /**
     * Path to the file, where the expressions will be saved
     */
    private String _filePath;

    /**
     * Creates new FilePrinter
     * @param filePath Path of the file, where the expressions will be saved
     */
    public FilePrinter(String filePath){
        _filePath = filePath;
    }

    @Override
    public void Print(Iterable<String> expressions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(_filePath))) {
            for(String expression : expressions){
                writer.write(expression);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Cannot saved expressions to file");
        }
    }
}
