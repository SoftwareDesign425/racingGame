import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;//Leaving color for next step:)
/*
Joseph Mitchell

This class handles animations like, tracks, cars, ect.

This class has the following attributes:


t(Timer) - This creates a new timer that is daemon.
    (meaning the thread will terminate when program terminates)
task(TimerTask) - This will schedule the task for the timer(keep score for race)
sec(int) - This is the actual timer counter.
    (counts the number of seconds the race is currently on)
shutdown(volatile boolean) - This boolean will track whether or not the user terminates the program. 
    (So the Timer thread will terminate)


Purpose:
To split the gui so that the gui is not too long.
*/
public class Animation{
    
    private GraphicsContext g;
    private GameObject to, ti, car;
    private final Timer t = new Timer(true);
    
    private final TimerTask task = new TimerTask() {    
        @Override
        public void run(){
            if(!shutdown){
                sec++;
                System.out.println("Seconds: " + sec);
            }
        }
    };;
    private int sec = 0;
    private volatile boolean shutdown = false;
    private boolean outerTrack, innerTrack;
    
    
    public Animation() {
        outerTrack = false;
        innerTrack = false;
    }
    
    public Parent createContent(){
        GridPane root = new GridPane();
        
        Canvas canvas = new Canvas(500,500);
        g = canvas.getGraphicsContext2D();
        
        root.getChildren().add(canvas);
        
        buildCars();
        buildTrack();
        
        render();
        
        return root;
    }
    
    public void render(){
        g.clearRect(0,0,500,500);
        
        to.draw(g, Color.TRANSPARENT, true);
        ti.draw(g, Color.BLUE, false);
        car.draw(g, Color.GREEN, false);
        
        outerTrack = car.isTouching(to);
        innerTrack = car.isTouching(ti);
        
        g.setStroke(Color.BLACK);
        
        g.strokeText(!outerTrack ? "Colliding (to)" : "Not Colliding (to)", 100, 50);
        g.strokeText(innerTrack ? "Colliding (ti)" : "Not Colliding (ti)", 100, 100);
    }
    
    public void buildTrack(){
        to = new GameObject(30,30,440,440);
        ti = new GameObject(100,100,300,300);
    }
    
    public void buildCars(){
        car = new GameObject(50,50,25,25);
    }
    
    public GameObject getOuterTrack(){
        return to;
    }
    
    public GameObject getInnerTrack(){
        return ti;
    }
    
    public void shutdown(){
        shutdown = true;
    }
     
    public GameObject getCar(){
        return car;
    }
    
    public Timer getTimer(){
        return t;
    }
    
    public TimerTask getTimerTask(){
        return task;
    }
}
