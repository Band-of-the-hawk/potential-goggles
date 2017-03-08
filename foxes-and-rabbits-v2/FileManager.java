import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class FileManager {
    private BufferedWriter rabbitWriter;
    private BufferedWriter foxWriter;
    private BufferedWriter wolfWriter;
    private BufferedWriter populationWriter;
    
    FileManager()
    {
        /*try {
            rabbitWriter = new BufferedWriter( new FileWriter("rabbit-log-file.csv", false));
            foxWriter = new BufferedWriter( new FileWriter("fox-log-file.csv", false));
            wolfWriter = new BufferedWriter( new FileWriter("wolf-log.file.csv", false));
            populationWriter = new BufferedWriter( new FileWriter("population-log-file.csv", false));
        } catch (IOException ex) {
            System.out.println("An error occurred when creating a new file :'(");
        }*/
    }

    public void writeToRabbit(String rabbitText)
    {
        try {
            System.out.println("Writing to rabbit file...");
            rabbitWriter.write(rabbitText);
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
            foxWriter.write(foxText);
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
            wolfWriter.write(wolfText);
            wolfWriter.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
    
    public void writeToPopulation(String populationText)
    {
        try {
            System.out.println("Writing to population file...");
            populationWriter.write(populationText);
            populationWriter.close();
            System.out.println("Done writing to file.");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
