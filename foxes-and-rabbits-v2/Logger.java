import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by KristofferMartinsen, ThomasSTodal and VinhKTran on 2/26/2017.
 */
public class Logger extends FileManager{

    private final ArrayList<String> deadActors;
    private final ArrayList<String> deadRabbits;
    private final ArrayList<String> deadFoxes;
    private final ArrayList<String> deadWolves;
    private FileManager fileManager;
    private BufferedWriter writeFFSRabbit;
    private BufferedWriter writeFFSFox;
    private BufferedWriter writeFFSWolf;
    private final ArrayList<String> population;


    public Logger() {
        deadActors = new ArrayList<>();
        deadRabbits = new ArrayList<>();
        deadFoxes = new ArrayList<>();
        deadWolves = new ArrayList<>();
        //fileManager = new FileManager();
        population = new ArrayList<>();
        try {
            writeFFSRabbit = new BufferedWriter(new FileWriter("rabbit-log-file.csv", false));
            writeFFSFox = new BufferedWriter(new FileWriter("fox-log-file.csv", false));
            writeFFSWolf = new BufferedWriter(new FileWriter("wolf-log-file.csv", false));
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

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
    
    public void populationSize(String population)
    {
        this.population.add(population);
    }

    public void iterateOverRabbitList()
    {
        String finalString = "";
        System.out.println("Iterate over rabbit list");
        //for(ArrayList<String> list1 : deadRabbits) {
        try {
            for (String string : deadRabbits) {
                // Make one line for the file
                //finalString += string;
                //finalString += "\n";
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/

                writeFFSRabbit.write(string+"\n");

            }
            writeFFSRabbit.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        //}
        //fileManager.writeToRabbit(finalString);
    }

    public void iterateOverFoxList()
    {
        String finalString = "";
        //for(ArrayList<String> list1 : deadFoxes) {
        try {
            for(String string : deadFoxes) {
            // Make one line for the file
            writeFFSFox.write(string+"\n");
            }
            writeFFSFox.close();
                //finalString += string;
                //finalString += "\n";


                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
            } catch (IOException io) {
                System.out.println(io.getMessage());
            }
        //}
        //fileManager.writeToFox(finalString);
    }

    public void iterateOverWolfList()
    {
        String finalString = "";
        try {
            //for(ArrayList<String> list1 : deadWolves) {
            for (String string : deadWolves) {
                // Make one line for the file
                //finalString += string;
                //finalString += "\n";
                /*if(list1.indexOf(string) < (list1.size() - 1)) {
                    finalString += ",";
                }*/
                writeFFSWolf.write(string+"\n");
            }
            writeFFSWolf.close();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
        //}
        //fileManager.writeToWolf(finalString);
    }

    public void iterateOverPopulation()
    {
        String finalString = "";
        for(String string : this.population) {
            // Make one line for the file
            finalString += string;
            finalString += "\n";
        }
        fileManager.writeToPopulation(finalString);
    }
}
