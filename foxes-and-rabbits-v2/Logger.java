import java.util.ArrayList;

/**
 * Created by KristofferMartinsen, ThomasSTodal and VinhKTran on 2/26/2017.
 */
public class Logger {

    //private final ArrayList ages;
    //private final ArrayList step;
    private final ArrayList<String> deadActors;
    private final ArrayList<String> deadRabbits;
    private final ArrayList<String> deadFoxes;
    private final ArrayList<String> deadWolves;
    private FileManager fileManager;


    public Logger() {
        //ages = new ArrayList<>();
        //step = new ArrayList<>();
        deadActors = new ArrayList<>();
        deadRabbits = new ArrayList<>();
        deadFoxes = new ArrayList<>();
        deadWolves = new ArrayList<>();
        fileManager = new FileManager();
    }
   
    public void newDeadActor(String newDead)
    {
        deadActors.add(newDead);
        if(newDead.toLowerCase().contains("rabbit")) {
            deadRabbits.add(newDead);
        }
        if(newDead.toLowerCase().contains("fox")) {
            deadFoxes.add(newDead);
        }
        if(newDead.toLowerCase().contains("wolf")) {
            deadWolves.add(newDead);
        }
    }

    public void iterateOverRabbitList()
    {
        String finalString = "";
        //for(ArrayList<String> list1 : deadRabbits) {
            for(String string : deadRabbits) {
                // Make one line for the file
                finalString += string;
                finalString += "\n";
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
        //}
        fileManager.writeToRabbit(finalString);
    }

    public void iterateOverFoxList()
    {
        String finalString = "";
        //for(ArrayList<String> list1 : deadFoxes) {
            for(String string : deadFoxes) {
                // Make one line for the file
                finalString += string;
                finalString += "\n";
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
        //}
        fileManager.writeToFox(finalString);
    }

    public void iterateOverWolfList()
    {
        String finalString = "";
        //for(ArrayList<String> list1 : deadWolves) {
            for(String string : deadWolves) {
                // Make one line for the file
                finalString += string;
                finalString += "\n";
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
        //}
        fileManager.writeToWolf(finalString);
    }
}
