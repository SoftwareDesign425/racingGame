//package AnimationPractice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Circle;


/**
 * Changed by Ana Gorohovschi
 * Venue class loads data from file
 * Computes total track length
 * Provides a list of visual representation of the Stops
 */
public class Venue {
    private final ArrayList<Car> cars;
    private final ArrayList<Stop> stops;
    
    private double distance = 0.0f;
    
    public Venue(){
        cars = new ArrayList<Car>();
        stops = new ArrayList<Stop>();
    }
    
    public void load(String fileName)
    {
        String line = null;
        BufferedReader buffereReader;
        try {
            buffereReader = new BufferedReader(new FileReader(fileName));

            while ((line = buffereReader.readLine()) != null) {
                

                if (line.matches("/xstops:")) {
                    while (((line = buffereReader.readLine()) != null)
                            && !line.matches("Place:")) {

                        if(line.isEmpty())
                            break;
                        
                        String coords = line.substring("Place:".length());
                        String [] location = coords.split(",");
                        int x = Integer.parseInt(location[0].trim()); 
                        int y = Integer.parseInt(location[1].trim());
                        
                        String name = buffereReader.readLine();
                        
                        name = name.substring("Name:".length()).trim();

                        Stop s = new Stop(name, x, y);
                        stops.add(s);
                    }
                }
                    
                if (line.matches("/xcars:")) {
                    while ((line = buffereReader.readLine()) != null
                            && !line.matches("Name:")) {
                        
                        if(line.isEmpty())
                            break;
                        
                        String name = line.substring("Name:".length());
                        Car c = new Car(name);
                        
                        cars.add(c);    
                    }
                }
            }
            buffereReader.close(); //close file
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }        
        
        for(int i=0; i<stops.size() - 1; i++)
        {
            distance += dist(stops.get(i), stops.get(i+1));
        }
        
        distance += dist(stops.get(0), stops.get(stops.size()-1));
    }
    
    private double dist(Stop a, Stop b)
    {
        double distanceX = (a.getX() - b.getX());
        double distanceY = (a.getY() - b.getY());
        return Math.sqrt((distanceX*distanceX)+(distanceY*distanceY));
    }
    
    public double trackLength(){//Joe to calculate total distance of the track:)
                              //total track length is independent of any given car
        return distance;
    }
    
    public ArrayList<Car> getCars(){
        return cars;
    }
    
    public ArrayList<Stop> getStops(){
        return stops;
    }

    public ArrayList<Circle> stopsView()
    {
        ArrayList<Circle> retValue = new ArrayList<Circle>();

        for(Stop s : stops)
        {
            retValue.add(s.getStopView());
        }

        return retValue;
    }
}