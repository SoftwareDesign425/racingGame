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
    private GameObject car, top, bottom, left, right,
                            topi, bottomi, lefti, righti;
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
    private boolean to, b, l, r, toi, bi, li, ri;
    
    
    public Animation() {
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
        
        top.draw(g, Color.BLACK, false);
        bottom.draw(g, Color.BLACK, false);
        left.draw(g, Color.BLACK, false);
        right.draw(g, Color.BLACK, false);
        
        topi.draw(g, Color.BLACK, false);
        bottomi.draw(g, Color.BLACK, false);
        lefti.draw(g, Color.BLACK, false);
        righti.draw(g, Color.BLACK, false);
        
        car.draw(g, Color.GREEN, false);
        
        to = car.isTouching(top);
        b = car.isTouching(bottom);
        l = car.isTouching(left);
        r = car.isTouching(right);
        //Add inner rectangle boolean vals
        toi = car.isTouching(topi);
        bi = car.isTouching(bottomi);
        li = car.isTouching(lefti);
        ri = car.isTouching(righti);
        
        g.setStroke(Color.BLACK);
        
        //Testing for collsion:)
//        g.strokeText(to ? "Colliding (top)" : "Not Colliding (top)", 100, 100);
//        g.strokeText(b ? "Colliding (bottom)" : "Not Colliding (bottom)", 100, 150);
//        g.strokeText(l ? "Colliding (left)" : "Not Colliding (left)", 100, 200);
//        g.strokeText(r ? "Colliding (right)" : "Not Colliding (right)", 100, 250);
//        
//        g.strokeText(toi ? "Colliding (toi)" : "Not Colliding (toi)", 100, 300);
//        g.strokeText(bi ? "Colliding (toi)" : "Not Colliding (toi)", 100, 350);
//        g.strokeText(li ? "Colliding (toi)" : "Not Colliding (toi)", 100, 400);
//        g.strokeText(ri ? "Colliding (toi)" : "Not Colliding (toi)", 100, 450);
    }
    
    public void buildTrack(){
        //Outer Rectangle
        top = new GameObject(0,0,500,1);//Top boundary
        bottom = new GameObject(0,499,499,1);//Bottom boundary
        left = new GameObject(0,0,1,500);//Left boundary
        right = new GameObject(499,1,1,499);//Right boundary
        //Inner Rectangle
        topi = new GameObject(100,100,300,1);
        bottomi = new GameObject(100,400,300,1);
        lefti = new GameObject(100,100,1,300);
        righti = new GameObject(400,100,1,300);
    }
    //Inner Rectangle dimensions 100,100,300,300
    public void buildCars(){//new Cars will be initialized here:)
        car = new GameObject(50,50,25,25);
    }
    
    //Outer boundary accessors
    public GameObject getTop(){
        return top;
    }
    
    public GameObject getBottom(){
        return bottom;
    }
    
    public GameObject getLeft(){
        return left;
    }
    
    public GameObject getRight(){
        return right;
    }
    
    public boolean stopW(){
        return (!to && !bi);
    }
    
    public boolean stopA(){
        return (!l && !ri);
    }
    
    public boolean stopS(){
        return (!b && !toi);
    }
    
    public boolean stopD(){
        return (!r && !li);
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
