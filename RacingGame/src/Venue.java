//package AnimationPractice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 *Ana Gorohovschi
 * Venue class loads data from file
 * 
 * 
 */
public class Venue {
    private final ArrayList<Car> cars;
    private final ArrayList<Stop> stops;
    
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
        
    }
   
    public void testLoading()
    {
        load("inputFile.txt");
    }
    
    public ArrayList<Integer> getXvalues(){
        ArrayList<Integer> retValue = new ArrayList<Integer>();
        
        for(Stop s : stops)
            retValue.add(s.getX());
        
        return retValue;
    }
    
    public ArrayList<Integer> getYvalues(){
        ArrayList<Integer> retValue = new ArrayList<Integer>();
        
        for(Stop s : stops)
            retValue.add(s.getY());
        
        return retValue;
    }
    
    public ArrayList<String> getStopNames(){
        ArrayList<String> retValue = new ArrayList<String>();
        
        for(Stop s : stops)
            retValue.add(s.getName());
        
        return retValue;
    }
    
    public ArrayList<String> getCarNames(){
        ArrayList<String> retValue = new ArrayList<String>();
        
        for(Car c : cars)
            retValue.add(c.getName());
        
        return retValue;
    }
    
    public double distanceT(Car c){//Joe to calculate total distance of the track:)
        double distance = 0;
        int next = 1;
        for(int i = 0; i < stops.size()-1; i++){
            distance += c.calcStop(stops.get(i), stops.get(next));
            next++;
        }
        return distance;
    }
    
    public ArrayList<Car> getCars(){
        return cars;
    }
}