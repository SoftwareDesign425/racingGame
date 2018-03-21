import javafx.scene.paint.Color;//Joe Deleted unneeded import Node
import javafx.scene.shape.Circle;

/* Michael Swann
 * This class contains data for each individial race stop.
 * Integers X and Y store coordinate data, which is used by other classes to calculate location and distance information.
 * The name of the stop is stored in a String.
 */

/* Changed by Ana Gorohovschi
* Stops now provide a visual representation of themselves.
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
  
  public String toString()
  {
      return name + ": " + x + ", " + y;
  }

    public Circle getStopView()
    {
        Circle c = new Circle();
        
        c.setStroke(Color.BLUE.brighter());
        c.setFill(Color.TRANSPARENT);
        c.setCenterX(x);
        c.setCenterY(y);
        
        c.setRadius(15.0f); 
        
        return c;
    }
}