
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 *
 * @author
 */
public class Venue {
    private ArrayList<Car> cars = new ArrayList<Car>();
    private ArrayList<Stop> stops= new ArrayList<Stop>();
    
    public Venue()
    {
        
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
        
        for(Stop s : stops)
            System.out.println(s.toString());
        
        for(Car c : cars)
            System.out.println(c.toString());        
    }
    
    public static void main(String [] args)
    {
        Venue test = new Venue();
        test.testLoading();
    }
}
