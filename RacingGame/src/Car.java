/* Michael Swann
 * This class contains data for calculating race times given a race path.
 * Path is stored as an array of Stop class objects - see Stop.java for details
 * The primary task of this class is to determine the time it takes to travel between stops. It stores only the time taken to get between currently specified stops, NOT total time taken to travel all stops.
 */

public class Car{
  
  private String name;
  private double topSpeed;
  private double time;
  //private Stop[] path;
  private int minX, maxX, minY, maxY;
  
  //Constructor that takes the min and max dimensions of each car
  //Joe for Animation
  public Car(int minX, int maxX, int minY, int maxY){
      this.minX = minX;
      this.minY = minY;
      this.maxX = maxX;
      this.maxY = maxY;
  }
  //This will be the main method that will decide collsion
  //(Whether or not the car is touching the other object)
  //Joe for Animation collision detection:)
  public boolean isTouching(Car other){//Passes the GameObject param
      return maxX >= other.minX && minX <= other.maxX 
              && maxY >= other.minY && minY <= other.maxY;
  }
//  public boolean isTouchingOuterTrack(Car other){
//      return maxX >= other.minX && minX <= other.maxX 
//              && maxY >= other.minY && minY <= other.maxY;
//  }
  
  public Car(String n)
  {
      name = n;
      //randomize the other fields
  }
  
//  public Car(double newSpeed, Stop[] newPath){
//    topSpeed = newSpeed;
//    time = 0.0;
//    path = newPath;
//  }
  
  public void setTime(double newTime){
    time = newTime;
  }
//  public void setPath(Stop[] newPath){
//    path = newPath;
//  }
  public void setSpeed(double newSpeed){
    topSpeed = newSpeed;
  }
  public double getSpeed(){
    return topSpeed;
  }
  public double getTime(){
    return time;
  }
  
  
//  public Stop[] getPath(){
//    return path;
//  }
//  public double calcStop(Stop begin, Stop end){
//    int distanceX = (begin.getX() - end.getX());
//    int distanceY = (begin.getY() - end.getY());
//    double hypotenuse = Math.sqrt((distanceX*distanceX)+(distanceY*distanceY));
//    time = hypotenuse/topSpeed;
//    return time;
//  }
  
  @Override
  public String toString()
  {
      return name;
  }
}