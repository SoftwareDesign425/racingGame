/* Michael Swann
 * This class contains data for each individial race stop.
 * Integers X and Y store coordinate data, which is used by other classes to calculate location and distance information.
 * The name of the stop is stored in a String.
 */

public class Stop{
  
  private String name;
  private int x;
  private int y;
  
  public Stop(String stopName, int stopX, int stopY){
    name = stopName;
    x = stopX;
    y = stopY;
  }
  
  public void setXY(int newX, int newY){
    x = newX;
    y = newY;
  }
  public void setName(String newName){
    name = newName;
  }
  public String getXY(){
    return x + "," + y;
  }
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
  public String getName(){
    return name;
  }
  
}