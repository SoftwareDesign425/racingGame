/* Michael Swann
 * This class contains data for calculating race times given a race path.
 * Path is stored as an array of Stop class objects - see Stop.java for details
 * The primary task of this class is to determine the time it takes to travel between stops. It stores only the time taken to get between currently specified stops, NOT total time taken to travel all stops.
 */


import java.util.Random;

/* Changed by Ana Gorohovschi
 * The total distance of a track is independent of any given car 
 * and it's a property of the Venue. Venue knows how many stops there are
 * so it can and should compute the total track distance
*/

public class Car{
  
  private String name;
  private double topSpeed;
  private int startEndIndex; // the index of the starting stop

  public Car(String n)//Joe Edited so that we can read speed from txt file
  {
      name = n;
      Random rand = new Random();
      topSpeed = 100.0 + (200.0-100.0)*rand.nextDouble();//creates random speed from 200-100 
      //(distance will be divided by this to create a random time for the duration of animation)
  }

  public double getSpeed(){
    return topSpeed;
  }
  
  public String getName()
  {
      return name;
  }
  
  public void setStartStop(int s)
  {
      startEndIndex = s;
  }
  
  public int getEndStop()
  {
      return startEndIndex;
  }

  @Override
  public String toString()
  {
      return name;
  }
}