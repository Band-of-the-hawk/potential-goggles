import java.util.ArrayList;

/**
 * Created by KristofferMartinsen, ThomasSTodal and VinhKTran on 2/26/2017.
 */
public class Logger {

    private final ArrayList<String> deadActors;
    private final ArrayList<String> deadRabbits;
    private final ArrayList<String> deadFoxes;
    private final ArrayList<String> deadWolves;
    private final ArrayList<String> population;
    private final FileManager fileManager;


    public Logger() {
        deadActors = new ArrayList<>();
        deadRabbits = new ArrayList<>();
        deadFoxes = new ArrayList<>();
        deadWolves = new ArrayList<>();
        population = new ArrayList<>();
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
    
    public void populationSize(String population)
    {
        this.population.add(population);
    }

    public void iterateOverRabbitList()
    {
        String finalString = "";
        for(String string : deadRabbits) {
            // Make one line for the file
            finalString += string;
            finalString += "\n";
        }
        fileManager.writeToRabbit(finalString);
    }

    public void iterateOverFoxList()
    {
        String finalString = "";
        for(String string : deadFoxes) {
            // Make one line for the file
            finalString += string;
            finalString += "\n";
        }
        fileManager.writeToFox(finalString);
    }

    public void iterateOverWolfList()
    {
        String finalString = "";
        for(String string : deadWolves) {
            // Make one line for the file
            finalString += string;
            finalString += "\n";
        }
        fileManager.writeToWolf(finalString);
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
