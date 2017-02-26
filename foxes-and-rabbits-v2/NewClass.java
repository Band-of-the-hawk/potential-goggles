import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Norway92
 */
public class NewClass
  {
   private final ArrayList<ArrayList<String>> deadActors;
   
   NewClass()
   {
       deadActors = new ArrayList<>();
   }
   
   public void newDeadActor(ArrayList<String> newDead)
   {
       deadActors.add(newDead);
   }
   
  }
