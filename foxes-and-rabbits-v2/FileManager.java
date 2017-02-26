import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class FileManager {

    public void createFile(ArrayList<Integer> listAges) {
        try{
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            writer.println(listAges);
            /*writer.println("The first line");
            writer.println("The second line");*/
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred when creating a new file :'(");
        }
    }
}
