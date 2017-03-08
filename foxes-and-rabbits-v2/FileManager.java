import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class FileManager {
    
    private PrintWriter writer;
    private PrintWriter rabbitWriter;
    private PrintWriter foxWriter;
    private PrintWriter wolfWriter;
    
    FileManager()
    {
        try {
            writer = new PrintWriter("log-file.csv", "UTF-8");
            rabbitWriter = new PrintWriter("rabbit-log-file.csv", "UTF-8");
            foxWriter = new PrintWriter("fox-log-file.csv", "UTF-8");
            wolfWriter = new PrintWriter("wolf-log.file.csv", "UTF-8");
        } catch (IOException ex) {
            System.out.println("An error occurred when creating a new file :'(");
        }
    }

    public void createFile(String listAges) {
        try{
            System.out.println("Writing to file...");
            PrintWriter writerR = new PrintWriter("the-file-name.csv", "UTF-8");
            writerR.println(listAges);
            /*writer.println("The first line");
            writer.println("The second line");*/
            writerR.close();
            System.out.println("Done writing to file.");
        } catch (IOException e) {
            System.out.println("An error occurred when creating a new file :'(");
        }
    }
    
    public void writeToFile(String text)
    {
        System.out.println("Writing to file...");
        writer.println(text);
        writer.close();
        System.out.println("Done writing to file.");
    }
    
    public void writeToRabbit(String rabbitText)
    {
        System.out.println("Writing to rabbit file...");
        rabbitWriter.println(rabbitText);
        rabbitWriter.close();
        System.out.println("Done writing to file.");
    }
    
    public void writeToFox(String foxText)
    {
        System.out.println("Writing to fox file...");
        foxWriter.println(foxText);
        foxWriter.close();
        System.out.println("Done writing to file.");
    }
    
    public void writeToWolf(String wolfText)
    {
        System.out.println("Writing to wolf file...");
        wolfWriter.println(wolfText);
        wolfWriter.close();
        System.out.println("Done writing to file.");
    }
}
