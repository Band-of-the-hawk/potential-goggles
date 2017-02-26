/**
 *
 * @author ThomasSTodal
 */
public class Main
{
    public static void main(String[] args)
    {
        //Simulator simulator = new Simulator(300, 350);
        Simulator simulator = new Simulator(120, 180);
        simulator.simulate(10000);
        simulator.printList();
        //simulator.simulate(36500); // for final simulation
    }
}
