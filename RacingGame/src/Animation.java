import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;//Leaving color for next step:)
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;
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
    private Pane root;
    private GameObject car, car2,
            top, bottom, left, right,
            topi, bottomi, lefti, righti;
    private Rectangle path1, carPath1, path2, carPath2, path3, carPath3;
    
    private Venue venue = null;
    
    private final Timer ti = new Timer(true);
    
    private final TimerTask task = new TimerTask() {    
        @Override
        public void run(){
            if(!shutdown){
                sec++;
                System.out.println("Seconds: " + sec);
            }
        }
    };
    private Random pos;
    private int sec = 0, rate, randPos1x, randPos2x, randPos3x,
            randPos1y, randPos2y, randPos3y, xOry1, xOry2, xOry3;
    private final double trackRate1, trackRate2, trackRate3;
    private volatile boolean shutdown = false;
    private boolean to, b, l, r, toi, bi, li, ri;
    
    
    public Animation(Venue v) {
        venue = v;
        rate = 1;
        path1 = new Rectangle();
        path2 = new Rectangle();
        path3 = new Rectangle();
        carPath1 = new Rectangle();
        carPath2 = new Rectangle();
        carPath3 = new Rectangle();
        trackRate1 = 1.0;
        trackRate2 = 1.085;
        trackRate3 = 1.07;
        pos = new Random();
        
        xOry1 = pos.nextInt(4);
        xOry2 = pos.nextInt(4);
        xOry3 = pos.nextInt(4);
        
        randPos1x = (15 + pos.nextInt(470));
        randPos2x = (35 + pos.nextInt(430));
        randPos3x = (55 + pos.nextInt(390));
        
        randPos1y = (15 + pos.nextInt(455));
        randPos2y = (35 + pos.nextInt(415));
        randPos3y = (55 + pos.nextInt(375));
    }
    
    public Pane createContent(){
        root = new Pane();
        
        Canvas canvas = new Canvas(500,500);
        g = canvas.getGraphicsContext2D();
        
        root.getChildren().add(canvas);
        
        buildPaths();
        buildCars();
        buildTrack();
        
        render();
        
        return root;
    }
    
    public void render(){
        g.clearRect(0,0,500,500);
        
        venue.drawTrack(g);
        
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
        car = new GameObject(70,70,15,15);
        root.getChildren().add(carPath1);
        root.getChildren().add(path1);
        root.getChildren().add(carPath2);
        root.getChildren().add(path2);
        root.getChildren().add(carPath3);
        root.getChildren().add(path3);
    }
    
    public void choosePathPosition(){
        if(xOry1 == 0){//Left side of track is positioned
            randPos1x = 15;
        }else if(xOry1 == 1){//Top side of track is positioned
            randPos1y = 15;
        }else if(xOry1 == 2){//Right side of track is positioned
            randPos1x = 485;
        }else if(xOry1 == 3){//Bottom side of track is positioned
            randPos1y = 485;
        }
        
        if(xOry2 == 0){//Left side of track is positioned
            randPos2x = 35;
        }else if(xOry2 == 1){//Top side of track is positioned
            randPos2y = 35;
        }else if(xOry2 == 2){//Right side of track is positioned
            randPos2x = 470;
        }else if(xOry2 == 3){//Bottom side of track is positioned
            randPos3y = 455;
        }
        
        if(xOry3 == 0){//Left side of track is positioned
            randPos3x = 55;
        }else if(xOry3 == 1){//Top side of track is positioned
            randPos3y = 55;
        }else if(xOry3 == 2){//Right side of track is positioned
            randPos3x = 455;
        }else if(xOry3 == 3){//Bottom side of track is positioned
            randPos3y = 455;
        }
    }
    
    public void buildPaths(){
        
        choosePathPosition();
        
        //p.getElements().add(new HLineTo(10));
        
        carPath1 = new Rectangle(15,15,15,15);//change this 
        carPath1.setFill(Color.BLUE);
        path1 = new Rectangle(15,15,470,470);//and this to change the size and 
        path1.setFill(Color.TRANSPARENT);//path of the Transition car
        PathTransition pl = new PathTransition();
        pl.setNode(carPath2);
        pl.setPath(path1);
        pl.setDuration(Duration.seconds(25));
        pl.setAutoReverse(false);
        pl.setCycleCount(1);
        pl.playFromStart();
        
        //pl.play();
        
        carPath2 = new Rectangle(35,35,15,15);
        carPath2.setFill(Color.RED);
        path2 = new Rectangle(35,35,430,430);
        path2.setFill(Color.TRANSPARENT);
        PathTransition p2 = new PathTransition();
        p2.setNode(carPath2);
        p2.setPath(path(35, randPos2x, randPos2y, xOry2));
        p2.setDuration(Duration.seconds(trackRate2 * 20));
        p2.setAutoReverse(false);
        p2.setCycleCount(1);
        p2.playFromStart();
        
        carPath3 = new Rectangle(55,55,15,15);
        carPath3.setFill(Color.AQUAMARINE);
        path3 = new Rectangle(55,55,390,390);
        path3.setFill(Color.TRANSPARENT);
        PathTransition p3 = new PathTransition();
        p3.setNode(carPath3);
        p3.setPath(path(55, randPos3x, randPos3y, xOry3));
        p3.setDuration(Duration.seconds(trackRate3 * 20));
        p3.setAutoReverse(false);
        p3.setCycleCount(1);
        p3.playFromStart();
    }
    
    public Path path(int x, int rx, int ry, int a){
        Path p = new Path();
        
        if(a == 0){
            p.getElements().add(new MoveTo(rx,ry));
            p.getElements().add(new LineTo(rx,x));//Move Up
            p.getElements().add(new LineTo(500 - x, x));//Move Right
            p.getElements().add(new LineTo(500 - x, 500 - x));//Move Down
            p.getElements().add(new LineTo(x,500 - x));//Move Left
            p.getElements().add(new LineTo(rx,ry));
        }else if(a == 1){
            p.getElements().add(new MoveTo(rx,ry));
            p.getElements().add(new LineTo(500 - x, x));
            p.getElements().add(new LineTo(500 - x, 500 - x));
            p.getElements().add(new LineTo(x, 500 - x));
            p.getElements().add(new LineTo(x,x));
            p.getElements().add(new LineTo(rx,ry));
        }else if(a == 2){
            p.getElements().add(new MoveTo(rx,ry));
            p.getElements().add(new LineTo(500 - x, 500 - x));//
            p.getElements().add(new LineTo(x, 500 - x));//
            p.getElements().add(new LineTo(x, x));//
            p.getElements().add(new LineTo(500 - x,x));//
            p.getElements().add(new LineTo(rx,ry));
        }else if(a == 3){
            p.getElements().add(new MoveTo(rx,ry));
            p.getElements().add(new LineTo(x, 500 - x));//
            p.getElements().add(new LineTo(x, x));//
            p.getElements().add(new LineTo(500 - x, x));//
            p.getElements().add(new LineTo(500 - x,500 - x));
            p.getElements().add(new LineTo(rx,ry));
        }
        
        return p;
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
        return ti;
    }
    
    public TimerTask getTimerTask(){
        return task;
    }
    
    public void setRate(int a){
        rate = a;
    }
    
    public int getRate(){
        return rate;
    }
}
