import java.util.ArrayList;

/**
 * Created by KristofferMartinsen, ThomasSTodal and VinhKTran on 2/26/2017.
 */
public class Logger {

    //private final ArrayList ages;
    //private final ArrayList step;
    private final ArrayList<ArrayList<String>> deadActors;
    private final ArrayList<ArrayList<String>> deadRabbits;
    private final ArrayList<ArrayList<String>> deadFoxes;
    private final ArrayList<ArrayList<String>> deadWolves;
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
        if(newDead.get(2).toLowerCase().contains("rabbit")) {
            deadRabbits.add(newDead);
        }
        if(newDead.get(2).toLowerCase().contains("fox")) {
            deadFoxes.add(newDead);
        }
        if(newDead.get(2).toLowerCase().contains("wolf")) {
            deadWolves.add(newDead);
        }
    }

    public void iterateOverActorList()
    {
        String finalString = "";
        for(ArrayList<String> list1 : deadActors) {
            for(String string : list1) {
                // Make one line for the file
                finalString += string;
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
            finalString += "\n";
        }
        fileManager.writeToFile(finalString);
    }

    public void iterateOverRabbitList()
    {
        String finalString = "";
        for(ArrayList<String> list1 : deadRabbits) {
            for(String string : list1) {
                // Make one line for the file
                finalString += string;
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
            finalString += "\n";
        }
        fileManager.writeToRabbit(finalString);
    }

    public void iterateOverFoxList()
    {
        String finalString = "";
        for(ArrayList<String> list1 : deadFoxes) {
            for(String string : list1) {
                // Make one line for the file
                finalString += string;
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
            finalString += "\n";
        }
        fileManager.writeToFox(finalString);
    }

    public void iterateOverWolfList()
    {
        String finalString = "";
        for(ArrayList<String> list1 : deadWolves) {
            for(String string : list1) {
                // Make one line for the file
                finalString += string;
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            }
            finalString += "\n";
        }
        fileManager.writeToWolf(finalString);
    }
}
