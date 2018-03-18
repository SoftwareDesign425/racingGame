/*
Joseph Mitchell

This class is for creating animations(preparing stop names to be added to Pane,
                                      animating cars, ect.)
and has the following attributes:

carAnim (ArrayList<Rectangle>) - These are the animation for all of the cars
carPaths (ArrayList<Path>) - This holds the Paths for the cars
namePaths (ArrayList<Path>) - This holds the Paths for the names
//These next three are responcible for making sure each car goes through all stops
//and returns to their starting position.
pOrder1 (String) - carries every stop index starting from (starting stop index + 1) for each car(is reset).
//These are where each Stop point is added to each PathTransition
p (ArrayList<PathTransition>) -  holds PathTransitions for all cars
np (ArrayList<PathTransition>) - holds PathTransitions for all names
//These PathTransition's will be paired (car-name) to ParallelTransitions
pa (ArrayList<ParallelTransition>) - holds ParallelTransitions for all cars && car names
v (Venue) - an instance of venue for file reading(game info grabbing).
x (ArrayList<Integer>) - holds the x coordinates of all stops for track.
y (ArrayList<Integer>) - holds the y coordinates of all stops for track.
s (ArrayList<String>) - holds the names of the stops
n (ArrayList<String>) - holds the names of the cars
t (ArrayList<Text>) - will hold the names of stops in Text objects and send to GUICore
n_t (ArrayList<Text>) - will hold the names of cars in Text objects and sent to GUICore
cars (ArrayList<Car>) - will capture all cars from inputFile.txt and initialize random speed.
endOfRace (boolean) - tests whether the game is over based on each cars PathTransition Duration with (sec below).
sec (int) - The tracking of the total time for Duration(tested with each PathTransition)
time (Timer) - used to create a timer.(Will be scheduled for seconds)
task (TimerTask) - used to create a specified task for the Timer(keep track of time in sec)

With the following methods:

Accessors:
getCarAnim() [ArrayList<Rectangle>] - returns to the main GUICore class to add to Pane
getCarNames() [ArrayList<Text>] - returns to the main GUICore class to add to Pane
getVenue() [Venue] - returns the venue object to call the load method to read the inputFile.txt

Initialization Methods:
animation() [none] - initializes all private attributes of class Animation
captureAllValues() [void] - initializes all values from the inputFile.txt
init_StopNames() [ArrayList<Text>] - initializes all stop names into ArrayList of Text objects and returns to main GUICore class's animPane
init_Cars() [ArrayList<Text>] - initializes all car names into ArrayList of Text objects and returns to main GUICore class's animPane
                                    *Also adds random colors with to each car:)
build_Paths() [void] - builds the paths for each car using the moveCar method
moveCar(Path c, Path n, int c) [void] - takes two new Paths, a carPath and namePath, 
    initializes their starting location {MoveTo} and specifies path {LineTo} 
buildTransitions() [void] - builds the PathTransitions for each car
checkDuration(PathTranisiton) - this method checks to see if any of the cars PathTransitions' Duration equal sec(Duration was converted to seconds)
playTransitions() [void] - this method initializes the ArrayList of ParallelTransitions with each cars PathTransition and each cars' name PathTransition
                                then it plays each of them from start.(method call is made to GUICore)
setParallelTransition(ParalellTransition a, PathTransition p, PathTransition np, Timeline t) [ParallelTransition p] - 
                                initializes the new ParallelTransition and returns it
setPath(Node a, Path p, PathTransition pt, double t) [PathTransition p] - sets the Path of a given PathTransition when passed any Node, Path, and double(duration)
*/

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation.Status;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Animation {
    //New ArrayLists
    private ArrayList<Rectangle> carAnim;//Only twenty-one attributes
    private ArrayList<Path> carPaths;
    private ArrayList<Path> namePaths;
    private ArrayList<PathTransition> p;
    private ArrayList<PathTransition> np;
    private ArrayList<ParallelTransition> pa;
    private ArrayList<Integer> x,y;
    private ArrayList<String> s,n;
    private ArrayList<Text> t,n_t;
    private ArrayList<Car> cars;
    private Venue v;
    private boolean endOfRace;
    private String pOrder1;
    private int sec;
    private final Timer time;
    private final TimerTask task = new TimerTask() {    
        @Override
        public void run(){
            sec++;
        }
    };
    
    
    public Animation(){
        //Intialization of new ArrayLists
        carAnim = new ArrayList<Rectangle>();//Have buildCars randomly color cars
        carPaths = new ArrayList<Path>();
        namePaths = new ArrayList<Path>();
        p = new ArrayList<PathTransition>();
        np = new ArrayList<PathTransition>();
        pa = new ArrayList<ParallelTransition>();
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
        s = new ArrayList<String>();
        n = new ArrayList<String>();
        t = new ArrayList<Text>();
        n_t = new ArrayList<Text>();
        cars = new ArrayList<Car>();
        v = new Venue();
        endOfRace = false;
        pOrder1 = "";
        time = new Timer(true);
        time.scheduleAtFixedRate(task, 1000,1000);
        sec = 0;
    }
    
    public void captureAllValues(){                                               //Called this Method
        x = v.getXvalues();
        y = v.getYvalues();
        s = v.getStopNames();
        n = v.getCarNames();
        cars = v.getCars();
    }
    
    public ArrayList<Text> init_StopNames(){                                      //Called this Method
        for(int i = 0; i < s.size(); i++){
            t.add(new Text(s.get(i)));
            t.get(i).relocate(x.get(i), y.get(i));
        }
        return t;
    }
    
    public ArrayList<Text> init_Cars(){                                           //Called this Method
        for(int i = 0; i < n.size(); i++){
            n_t.add(new Text(n.get(i)));
            double r = Math.random();
            double g = Math.random();
            double b = Math.random();
            n_t.get(i).setFill(Color.color(r, g, b));
            Rectangle re = new Rectangle(50,50,15,15);
            re.setFill(Color.color(r, g, b));
            carAnim.add(re);
        }
        return n_t;
    }
    
    public void buildPaths(){                                                      //Called this method
        int counter = 1;
        for(int i = 0; i < cars.size(); i++){
            Path p = new Path();
            Path n = new Path();
            moveCar(p, n, counter);
            counter++;
        }
    }
    
    public void moveCar(Path cp, Path np, int counter){
        Random rand = new Random();
        int choice = rand.nextInt(x.size());
        cp.getElements().add(new MoveTo(x.get(choice), y.get(choice)));
        np.getElements().add(new MoveTo(x.get(choice), y.get(choice)- (15*counter)));//Places each car at their initial starting point
        System.out.println("Car " + counter + " started at: " + s.get(choice));
        int track = choice+1;
        for(int i = 0; i < x.size(); i++){
            if(track >= x.size()){
                track = 0;
            }
            pOrder1 += track;//reset this back to "" after each call
            track++;
        }
        for(int i = 0; i < pOrder1.length(); i++){
            String i_s = "", i_s2 = "", i_s3 = "";
            i_s += pOrder1.charAt(i);
            int index = Integer.parseInt(i_s);
            cp.getElements().add(new LineTo(x.get((index)), y.get(index)));
            np.getElements().add(new LineTo(x.get(index), y.get(index)-(15*counter)));
        }
        pOrder1 = "";
        carPaths.add(cp);
        namePaths.add(np);
    }
    
    public void buildTransitions(){//This is not playTransitions                   //Called this method
        for(int i = 0; i < carPaths.size(); i++){
            PathTransition pathC = new PathTransition();
            PathTransition pathN = new PathTransition();
            p.add(setPath(carAnim.get(i),carPaths.get(i),pathC,v.distanceT(cars.get(i))/cars.get(i).getSpeed()));//Adding speed:)
            np.add(setPath(n_t.get(i),namePaths.get(i),pathN,v.distanceT(cars.get(i))/cars.get(i).getSpeed())); 
            p.get(i).statusProperty().addListener(new ChangeListener<Status>() {
                @Override
                public void changed(ObservableValue<? extends Status> observableValue,
                                Status oldValue, Status newValue) {
                    if(newValue==Status.RUNNING){
                    }else{
                        if(!endOfRace)
                          if(checkDuration(p)>=0){System.out.println("The winner is: " + n.get(checkDuration(p)) );};//This is the winner:)(index of checkDuration(p))
                    }
                }
            });
        }
    }
    
    public int checkDuration(ArrayList<PathTransition> p){
        boolean b = false;
        for(int n = 0; n < p.size(); n++){
            String i = ""; 
            i+=(p.get(n).getDuration());
            int j = Integer.parseInt(i.substring(0,i.indexOf(".")));
            j/=1000;
            if(j == sec){
                endOfRace = true;
                return n;
            }
        }
        return -1;
    }
    
    public void playTransitions(){                                                  //Called this method
        for(int i = 0; i < p.size(); i++){
            Timeline t1 = new Timeline();
            ParallelTransition a = new ParallelTransition();
            pa.add(setParallelTransition(a,p.get(i),np.get(i),t1));
        }
        for(int i = 0; i < pa.size(); i++){
            pa.get(i).playFromStart();
        }
    }
    
    public ParallelTransition setParallelTransition(ParallelTransition a, PathTransition p, PathTransition np, Timeline t){
        a.getChildren().add(p);
        a.getChildren().add(np);
        a.getChildren().add(t);
        return a;
    }
    
    public PathTransition setPath(Node a, Path p, PathTransition pt, double t){
        pt.setNode(a);
        pt.setPath(p);
        pt.setDuration(Duration.seconds(t));
        pt.setAutoReverse(false);
        pt.setCycleCount(1);
        return pt;
    }
    
    //Accessors for new ArrayLists
    public ArrayList<Rectangle> getCarAnim(){                                       //Calls this method
        return carAnim;
    }
    
    public ArrayList<Text> getCarNames(){                                           //Call this method
        return n_t;
    }

    public Venue getVenue(){
        return v;
    }
}
