import java.util.ArrayList;

/**
 * Created by KristofferMartinsen, ThomasSTodal and VinhKTran on 2/26/2017.
 */
public class Logger {

    //private final ArrayList ages;
    //private final ArrayList step;
    private final ArrayList<ArrayList<String>> deadActors;
    private FileManager fileManager;


    public Logger() {
        //ages = new ArrayList<>();
        //step = new ArrayList<>();
        deadActors = new ArrayList<>();
        fileManager = new FileManager();
    }

    /*public void addToAges(int ageToAdd) {
        this.ages.add(ageToAdd);
    }*/

    /*public void printAges() {
        for(int i = 0; i < ages.size() - 1; i++) {
            System.out.println(ages.get(i));
        }
    }*/

    /*public ArrayList<Integer> getListAsArrayList() {
        return ages;
    }*/
   
    public void newDeadActor(ArrayList<String> newDead)
    {
        deadActors.add(newDead);
    }

    public void iterateOverList()
    {
        String finalString = "";
        for(ArrayList<String> list1 : deadActors) {
            for(String string : list1) {
                // Make one line for the file
                finalString += string;
                if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }
            }
            finalString += "\n";
        }
        fileManager.createFile(finalString);
    }
}
