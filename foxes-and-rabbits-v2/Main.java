/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    }
}
