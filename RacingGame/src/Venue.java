//package AnimationPractice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.shape.Path;


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
    private final Path pat1;
    private final Path pat2;
    private final Path pat3;
    
    public Venue(){
        cars = new ArrayList<Car>();
        stops = new ArrayList<Stop>();
        xValues = new ArrayList<Integer>();
        yValues = new ArrayList<Integer>();
        stopNames = new ArrayList<String>();
        pat1 = new Path();
        pat2 = new Path();
        pat3 = new Path();
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
        System.out.println(xValues.size());
        for(Integer i : xValues){
            System.out.println(i);
        }
    }
    
    public Path getPath1(){
        return pat1;
    }
    
    public Path getPath2(){
        return pat2;
    }
    
    public Path getPath3(){
        return pat3;
    }
    
    public ArrayList<Integer> getXvalues(){
        ArrayList<Integer> clone = new ArrayList<Integer>(xValues);
        for(Integer i : xValues){
            clone.add(new Integer(i));
        }
        return clone;
    }
    
    public ArrayList<Integer> getYvalues(){
        ArrayList<Integer> clone = new ArrayList<Integer>(yValues);
        for(Integer i : yValues){
            clone.add(new Integer(i));
        }
        return clone;
    }
}