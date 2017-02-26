import java.util.ArrayList;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class Logger {

    private ArrayList ages;
    private ArrayList timeOfDeath;


    public Logger() {
        ArrayList ages = new ArrayList();
        ArrayList timeOfDeath = new ArrayList();
    }

    public int getAge(int age) {
        return age;
    }

    public void addToAges(int ageToAdd) {
        ages.add(ageToAdd);
    }

    public void printAges() {
        for(int i = 0; i < ages.size() - 1; i++) {
            System.out.println(ages.indexOf(i));
        }
    }

}
