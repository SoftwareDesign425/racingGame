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
    private final ArrayList<Integer> xValues;
    private final ArrayList<Integer> yValues;
    private final ArrayList<String> stopNames;
    private final ArrayList<String> carNames;
    
    public Venue(){
        cars = new ArrayList<Car>();
        stops = new ArrayList<Stop>();
        xValues = new ArrayList<Integer>();
        yValues = new ArrayList<Integer>();
        stopNames = new ArrayList<String>();
        carNames = new ArrayList<String>();
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
                        xValues.add(x);
                        yValues.add(y);
                        stopNames.add(name);
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
                        carNames.add(name);
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
        return xValues;
    }
    
    public ArrayList<Integer> getYvalues(){
        return yValues;
    }
    
    public ArrayList<String> getStopNames(){
        return stopNames;
    }
    
    public ArrayList<String> getCarNames(){
        return carNames;
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