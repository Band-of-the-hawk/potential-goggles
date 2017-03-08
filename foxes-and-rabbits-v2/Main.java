/**
 *
 * @author ThomasSTodal
 */
public class Main
{
    public static void main(String[] args)
    {
        Simulator simulator = new Simulator(300, 350); // for final simulation
        //Simulator simulator = new Simulator(120, 180);
        //simulator.simulate(1000);
        //simulator.printList();
        //simulator.doCreateFile();
        simulator.simulate(36525); // for final simulation
    }
}
