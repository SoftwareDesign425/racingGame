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
t (ArrayList<Text>) - will hold the names of stops in Text objects and send to GUICore
n_t (ArrayList<Text>) - will hold the names of cars in Text objects and sent to GUICore
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
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/*
* Changed by Ana Gorohovschi
* Removed the construction of the Venue object.
* That comes from the GUICore
* Total distance of the track is independent of any given car
* Changed the visual styles of the tracks and Stop's names
*/

public class Animation {
    private ArrayList<Rectangle> carAnim;//Only sixteen attributes:)
    private ArrayList<Path> carPaths;
    private ArrayList<Path> namePaths;
    private ArrayList<PathTransition> p;
    private ArrayList<PathTransition> np;
    private ArrayList<ParallelTransition> pa;
    private ArrayList<Text> t,n_t;
    private Text winner;
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
    
    public Animation(Venue v){
        carAnim = new ArrayList<Rectangle>();
        carPaths = new ArrayList<Path>();
        namePaths = new ArrayList<Path>();
        p = new ArrayList<PathTransition>();
        np = new ArrayList<PathTransition>();
        pa = new ArrayList<ParallelTransition>();
        t = new ArrayList<Text>();
        n_t = new ArrayList<Text>();
        winner = new Text();
        this.v = v;
        endOfRace = false;
        pOrder1 = "";
        time = new Timer(true);
        time.scheduleAtFixedRate(task, 1000,1000);
        sec = 0;
    }
    
    public ArrayList<Text> init_StopNames(){           
        ArrayList<Stop> stops = v.getStops();
        
        Text txt = null;
        
        for(int i = 0; i < stops.size(); i++){
            txt = new Text(stops.get(i).getName());
            txt.setTextAlignment(TextAlignment.CENTER);
            txt.setX(stops.get(i).getX() - txt.getLayoutBounds().getWidth()/2);
            txt.setY(stops.get(i).getY());
            txt.setTextOrigin(VPos.BOTTOM);
            
            t.add(txt); //Creates a new Text object with for each stop name
        }
        
        return t;//returns ArrayList to the Pane in GUICore
    }
    
    public ArrayList<Line> buildRoad(){//Builds a visible road to see the paths of the cars easier
        ArrayList<Stop> stops = v.getStops();
        ArrayList<Line> line = new ArrayList<Line>();
        for(int i = 0; i < stops.size(); i++){//This will show the track as a line for the mapping out of where to build the track
            Line newLine = null;
            if(i != stops.size()-1){
                newLine = new Line(stops.get(i).getX(), stops.get(i).getY(), stops.get(i+1).getX(), stops.get(i+1).getY());
            }else{
                newLine = new Line(stops.get(0).getX(), stops.get(0).getY(), stops.get(i).getX(), stops.get(i).getY());
            }
            
            newLine.setStrokeWidth(25);
            newLine.setStroke(Color.rgb(125, 125, 125));
            newLine.setStrokeLineCap(StrokeLineCap.ROUND);            
            
            line.add(newLine);
        }
        return line;
    }
    
    public ArrayList<Text> init_Cars(){   
        ArrayList<Car> cars = v.getCars();
        for(int i = 0; i < cars.size(); i++){
            n_t.add(new Text(cars.get(i).toString()));//Adds each car name to a text object
            double r = Math.random();//Red value as a value passed to Co
            double g = Math.random();
            double b = Math.random();
            n_t.get(i).setFill(Color.color(r, g, b));//Fills text object with a random color
            Rectangle re = new Rectangle(50,50,15,15);//Creates new car
            re.setFill(Color.color(r, g, b));//Gives new car a random color
            carAnim.add(re);
        }
        return n_t;
    }
    
    public void buildPaths(){                                                      
        int counter = 1;
        ArrayList<Car> cars = v.getCars();
        for(int i = 0; i < cars.size(); i++){
            Path p = new Path();//creates new path for the car
            Path n = new Path();//creates new path for the name of the car
            moveCar(p, n, counter);//adds the path to each car
            counter++;//loops through giving each car a path
        }
    }
    
    public void moveCar(Path cp, Path np, int counter){
        Random rand = new Random();
        ArrayList<Stop> stops = v.getStops();
        int choice = rand.nextInt(stops.size());//random integer from 0 to stop size - 1
        cp.getElements().add(new MoveTo(stops.get(choice).getX(), stops.get(choice).getY()));
        np.getElements().add(new MoveTo(stops.get(choice).getX(), stops.get(choice).getY()- (15*counter)));//Places each car at their initial starting point
        System.out.println("Car " + counter + " started at: " + stops.get(choice));
        int track = choice+1;//starting postition + 1
        for(int i = 0; i < stops.size(); i++){
            if(track >= stops.size()){//if the track is out of bounds of the arrayList then reset track to zero
                track = 0;
            }
            pOrder1 += track;//reset this back to "" after each call
            track++;
        }
        for(int i = 0; i < pOrder1.length(); i++){//pOrder now holds all of the stops to make it go full circle(start and end at the same stop hitting all stops)
            String i_s = "";
            i_s += pOrder1.charAt(i);//Takes each position's index
            int index = Integer.parseInt(i_s);//casts to Integer
            cp.getElements().add(new LineTo(stops.get((index)).getX(), stops.get(index).getY()));//adds the position's path to the car path
            np.getElements().add(new LineTo(stops.get(index).getX(), stops.get(index).getY()-(15*counter)));//and name path (y-namePath is -15pxl.)[Careful how many cars you add, because the car names could go off screen]
        }
        pOrder1 = "";//reset pOrder1 for the next race
        carPaths.add(cp);//adds the carPaths to ArrayList
        namePaths.add(np);//adds the namePaths to ArrayList
    }
    
    public void buildTransitions(){          
        ArrayList<Car> cars = v.getCars();
        for(int i = 0; i < carPaths.size(); i++){//Builds the pathTransition for each carPath and namePath
            PathTransition pathC = new PathTransition();//new car PathTransition
            PathTransition pathN = new PathTransition();//new name PathTransition
            p.add(setPath(carAnim.get(i),carPaths.get(i),pathC,v.trackLength()/cars.get(i).getSpeed()));//Takes the car,carPath,car PathTransition,Adding speed:)[passes to setPath method]
            np.add(setPath(n_t.get(i),namePaths.get(i),pathN,v.trackLength()/cars.get(i).getSpeed()));//Takes the car name,carNamePath,car name PathTransition,Adding speed:)[passes to setPath method]
            p.get(i).statusProperty().addListener(new ChangeListener<Status>() {//will determine if the animations are still running, using ChangeListener, Status, and ObservableValue classes
                @Override
                public void changed(ObservableValue<? extends Status> observableValue,
                                Status oldValue, Status newValue) {
                    if(newValue==Status.RUNNING){
                        winner.setVisible(false);
                    }else{
                        winner.setVisible(true);
                        if(!endOfRace)
                          if(checkDuration(p)>=0){System.out.println("The winner is: " + cars.get(checkDuration(p)).toString() );
                          winner.setText(cars.get(checkDuration(p)).toString());
                          winner.relocate(250,20);
                          winner.setFont(Font.font("Serif", 20));
                          winner.setVisible(true);
                          };//This is the winner:)(index of checkDuration(p))
                    }
                }
            });
        }
    }
    
    public PathTransition setPath(Node a, Path p, PathTransition pt, double t){
        pt.setNode(a);//adds the passed Node to the passed PathTransition
        pt.setPath(p);//adds the path to the passed PathTransition
        pt.setDuration(Duration.seconds(t));//adds the passed duration(this case (TotalDistance/RandomSpeed))
        pt.setAutoReverse(false);//Only one lap
        pt.setCycleCount(1);//Only one lap
        return pt;//returns the set Path back to the given ArrayList in buildTranistions method:)
    }
    
    public int checkDuration(ArrayList<PathTransition> p){//checks to see if the current duration matches the timer
        boolean b = false;
        for(int n = 0; n < p.size(); n++){
            String i = ""; 
            i+=(p.get(n).getDuration());
            int j = Integer.parseInt(i.substring(0,i.indexOf(".")));
            j/=1000;//Converts Duration to an integer number of seconds
            if(j == sec){
                endOfRace = true;//if it does the the winner will be caught in buildTransitions method
                return n;
            }
        }
        return -1;
    }
    
    public void playTransitions(){                                                  
        for(int i = 0; i < p.size(); i++){
            Timeline t1 = new Timeline();//builds new Timeline object
            ParallelTransition a = new ParallelTransition();//builds new ParallelTransition
            pa.add(setParallelTransition(a,p.get(i),np.get(i),t1));//Adds new ParallelTransition to ArrayList using setParallelTransition method below
        }
        for(int i = 0; i < pa.size(); i++){
            pa.get(i).playFromStart();//plays each ParallelTransition from start
        }
    }
    
    public ParallelTransition setParallelTransition(ParallelTransition a, PathTransition p, PathTransition np, Timeline t){
        a.getChildren().add(p);//adds the cars PathTransition to the passed ParallelTransition
        a.getChildren().add(np);//adds the car name PathTransition to the passed ParallelTransition 
        a.getChildren().add(t);//adds a new Timeline object to each ParallelTransition
        return a;//returns the set ParallelTransition
    }
    
    //Accessor for ParallelTransition array
    public ArrayList<ParallelTransition> getPA(){
      return pa;
    }
    
    //Special Accessor for status
    public Status getStatus(){
      for(ParallelTransition i : pa){
        if(i.getStatus() == Status.RUNNING){ // If ANY are running, this is applicable
          return Status.RUNNING;
        }
        if(i.getStatus() == Status.PAUSED){ // If ANY are paused, they all must be
          return Status.PAUSED;
        }
      }
      return Status.STOPPED; // If none are running or paused, all must be stopped
    }
    
    public Text getWinner(){
        return winner;
    }
    
    //Accessors for new ArrayLists
    public ArrayList<Rectangle> getCarAnim(){                                       
        return carAnim;
    }
    
    public ArrayList<Text> getCarNames(){                                           
        return n_t;
    }
}
