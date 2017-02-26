import java.util.ArrayList;

/**
 * Created by Kristoffer on 2/26/2017.
 */
public class Logger {

    private ArrayList ages;
    private ArrayList step;


    public Logger() {
        ages = new ArrayList<>();
        step = new ArrayList<>();
    }

    public void addToAges(int ageToAdd) {
        this.ages.add(ageToAdd);
    }

    public void printAges() {
        for(int i = 0; i < ages.size() - 1; i++) {
            System.out.println(ages.get(i));
        }
    }

    public ArrayList<Integer> getListAsArrayList() {
        return ages;
    }
}
