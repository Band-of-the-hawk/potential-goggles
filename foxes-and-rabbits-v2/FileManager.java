import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class FileManager {

    private BufferedWriter writer;
    private BufferedWriter rabbitWriter;
    private BufferedWriter foxWriter;
    private BufferedWriter wolfWriter;
    
    FileManager()
    {
        try {
            writer = new BufferedWriter( new FileWriter("log-file.csv", true));
            rabbitWriter = new BufferedWriter( new FileWriter("rabbit-log-file.csv", true));
            foxWriter = new BufferedWriter( new FileWriter("fox-log-file.csv", true));
            wolfWriter = new BufferedWriter( new FileWriter("wolf-log.file.csv", true));
        } catch (IOException ex) {
            System.out.println("An error occurred when creating a new file :'(");
        }
    }
    
    public void writeToFile(String text)
    {
        try {
            System.out.println("Writing to file...");
            writer.write(text);
            writer.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void writeToRabbit(String rabbitText)
    {
        try {
            System.out.println("Writing to rabbit file...");
            rabbitWriter.append(rabbitText);
            rabbitWriter.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void writeToFox(String foxText)
    {
        try {
            System.out.println("Writing to fox file...");
            foxWriter.append(foxText);
            foxWriter.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void writeToWolf(String wolfText)
    {
        try {
            System.out.println("Writing to wolf file...");
            wolfWriter.append(wolfText);
            wolfWriter.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
