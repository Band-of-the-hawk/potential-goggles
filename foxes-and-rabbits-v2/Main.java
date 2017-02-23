/**
 *
 * @author ThomasSTodal
 */
public class Main
{
    public static void main(String[] args)
    {
        Simulator simulator = new Simulator(300, 350);
        simulator.simulate(10000);
        //simulator.simulate(36500); // for final simulation
    }
}
