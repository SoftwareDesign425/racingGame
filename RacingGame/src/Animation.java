//import java.util.Random;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.scene.Parent;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;//Leaving color for next step:)
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

//ALL COMMENTED OUT CODE WAS ORIGINALLY IMPLEMENTED FOR USER'S CAR:)


public class Animation{
//    private GameObject car, car2,
//            top, bottom, left, right,
//            topi, bottomi, lefti, righti;    
//    private Rectangle path1, path2, path3;
//    private Random pos;
//    private int        randPos1x, randPos2x, randPos3x,
//            randPos1y, randPos2y, randPos3y, xOry1, xOry2, xOry3;
//    private boolean to, b, l, r, toi, bi, li, ri;
//    private GraphicsContext g;
//    private int rate;
    
    //    public void pathSetup(Rectangle car, Rectangle path, int pos, int rand1,
//                            int rand2, int choice, double rate, Color carColor){
//        car = new Rectangle(pos,pos,pos,pos);
//        car.setFill(carColor);
//        path = new Rectangle(pos,pos,510 - ((pos/15)*40),510 -((pos/15)*40));
//        path.setFill(Color.TRANSPARENT);
//        PathTransition pl = new PathTransition();
//        pl.setNode(car);
//        pl.setPath(path(pos, rand1, rand2, choice));
//        pl.setDuration(Duration.seconds(rate*20));
//        pl.setAutoReverse(false);
//        pl.setCycleCount(1);
//        pl.play();
//    }
    
//    public Path path(int x, int rx, int ry, int a){
//        Path p = new Path();
//        
//        if(a == 0){
//            p.getElements().add(new MoveTo(rx,ry));
//            p.getElements().add(new LineTo(x,x));//Move Up
//            p.getElements().add(new LineTo(500 - x, x));//Move Right
//            p.getElements().add(new LineTo(500 - x, 500 - x));//Move Down
//            p.getElements().add(new LineTo(x,500 - x));//Move Left
//            p.getElements().add(new LineTo(rx,ry));
//            gameOver();
//        }else if(a == 1){
//            p.getElements().add(new MoveTo(rx,ry));
//            p.getElements().add(new LineTo(500 - x, x));
//            p.getElements().add(new LineTo(500 - x, 500 - x));
//            p.getElements().add(new LineTo(x, 500 - x));
//            p.getElements().add(new LineTo(x,x));
//            p.getElements().add(new LineTo(rx,ry));
//            gameOver();
//        }else if(a == 2){
//            p.getElements().add(new MoveTo(rx,ry));
//            p.getElements().add(new LineTo(500 - x, 500 - x));//
//            p.getElements().add(new LineTo(x, 500 - x));//
//            p.getElements().add(new LineTo(x, x));//
//            p.getElements().add(new LineTo(500 - x,x));//
//            p.getElements().add(new LineTo(rx,ry));
//            gameOver();
//        }else if(a == 3){
//            p.getElements().add(new MoveTo(rx,ry));
//            p.getElements().add(new LineTo(x, 500 - x));//
//            p.getElements().add(new LineTo(x, x));//
//            p.getElements().add(new LineTo(500 - x, x));//
//            p.getElements().add(new LineTo(500 - x,500 - x));
//            p.getElements().add(new LineTo(rx,ry));
//            gameOver();
//        }
//        
//        return p;
//    }
    
//    public void gameOver(){
//        Text t = new Text("Game Over");
//        t.setX(250);
//        t.setY(250);
//        double time = (double)sec;
//        if(Double.compare(time,trackRate1*20) > 0  || 
//                Double.compare(time,trackRate2*20) > 0 || Double.compare(time,trackRate3*20) > 0){
//            root.getChildren().add(t);
//        }
//    }
    
    //Outer boundary accessors
//    public GameObject getTop(){
//        return top;
//    }
//    
//    public GameObject getBottom(){
//        return bottom;
//    }
//    
//    public GameObject getLeft(){
//        return left;
//    }
//    
//    public GameObject getRight(){
//        return right;
//    }
//    
//    public boolean stopW(){
//        return (!to && !bi);
//    }
//    
//    public boolean stopA(){
//        return (!l && !ri);
//    }
//    
//    public boolean stopS(){
//        return (!b && !toi);
//    }
//    
//    public boolean stopD(){
//        return (!r && !li);
//    }
//    public GameObject getCar(){
//        return car;
//    }
//    public void setRate(int a){
//        rate = a;
//    }
//    
//    public int getRate(){
//        return rate;
//    }
    private Rectangle r1,r2,r3;
    private Path p;
    private Pane root;
    private Rectangle carPath1, carPath2, carPath3;
    private Path pat1, pat2, pat3;
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
    private Venue v;
    private int sec = 0; 
    private ArrayList<Integer> x, y;
    private final double trackRate1, trackRate2, trackRate3;
    private volatile boolean shutdown = false;

    
    
    public Animation() {
//        path1 = new Rectangle();
//        path2 = new Rectangle();
//        path3 = new Rectangle();
//        pos = new Random();        
//        xOry1 = pos.nextInt(4);
//        xOry2 = pos.nextInt(4);
//        xOry3 = pos.nextInt(4);
//        randPos1x = (15 + pos.nextInt(470));
//        randPos2x = (35 + pos.nextInt(430));
//        randPos3x = (55 + pos.nextInt(390));
//        randPos1y = (15 + pos.nextInt(455));
//        randPos2y = (35 + pos.nextInt(415));
//        randPos3y = (55 + pos.nextInt(375));        
//        rate = 1;

        carPath1 = new Rectangle();
        carPath2 = new Rectangle();
        carPath3 = new Rectangle();
        trackRate1 = 1.0;
        trackRate2 = 1.085;
        trackRate3 = 1.07;

        v = new Venue();
        p = new Path();
        r1 = new Rectangle(50,50,15,15);
        r1.setFill(Color.BLUE);
        r2 = new Rectangle(50,50,15,15);
        r2.setFill(Color.RED);
        r3 = new Rectangle(50,50,15,15);
        r3.setFill(Color.BLUEVIOLET);
    }
    
    public Parent createContent(){
        root = new Pane();
        
        root.setMaxHeight(500);
        root.setMaxWidth(500);
//        Canvas canvas = new Canvas(500,500);
//        g = canvas.getGraphicsContext2D();
        
//        root.getChildren().add(canvas);
        
        buildPaths();
        buildCars();
        buildTrack();
        
//        render();
        
        return root;
    }
    
//    public void render(){
////        g.clearRect(0,0,500,500);
//        
////        top.draw(g, Color.BLACK, false);
////        bottom.draw(g, Color.BLACK, false);
////        left.draw(g, Color.BLACK, false);
////        right.draw(g, Color.BLACK, false);
////        
////        topi.draw(g, Color.BLACK, false);
////        bottomi.draw(g, Color.BLACK, false);
////        lefti.draw(g, Color.BLACK, false);
////        righti.draw(g, Color.BLACK, false);
////        
////        car.draw(g, Color.GREEN, false);  
//        
////        to = car.isTouching(top);
////        b = car.isTouching(bottom);
////        l = car.isTouching(left);
////        r = car.isTouching(right);
////        //Add inner rectangle boolean vals
////        toi = car.isTouching(topi);
////        bi = car.isTouching(bottomi);
////        li = car.isTouching(lefti);
////        ri = car.isTouching(righti);
//        
////        g.setStroke(Color.BLACK);
//    }
    
    public void buildTrack(){
        //Outer Rectangle
//        top = new GameObject(0,0,500,1);//Top boundary
//        bottom = new GameObject(0,499,499,1);//Bottom boundary
//        left = new GameObject(0,0,1,500);//Left boundary
//        right = new GameObject(499,1,1,499);//Right boundary
//        //Inner Rectangle
//        topi = new GameObject(100,100,300,1);
//        bottomi = new GameObject(100,400,300,1);//Modify this
//        lefti = new GameObject(100,100,1,300);
//        righti = new GameObject(400,100,1,300);//Modify this
//        pat1 = v.getPath();
//        for(int i = 0; i < x.size(); i++){
//            pat1.getElements().add(new LineTo(x.get(i), y.get(i)));
//        }
        
    }
    //Inner Rectangle dimensions 100,100,300,300
    public void buildCars(){//new Cars will be initialized here:)
//        car = new GameObject(70,70,15,15);
//        root.getChildren().add(path1);
//        root.getChildren().add(path2);
//        root.getChildren().add(path3);

        root.getChildren().add(carPath1);
        root.getChildren().add(carPath2);
        root.getChildren().add(carPath3);
    }
    
//    public void choosePathPosition(){
////        xOry1 =2;xOry2=2;xOry3=2;
////        if(xOry1 == 0){//Left side of track is positioned
////            randPos1x = 15;
////        }else if(xOry1 == 1){//Top side of track is positioned
////            randPos1y = 15;
////        }else if(xOry1 == 2){//Right side of track is positioned
////            randPos1x = 485;
////        }else if(xOry1 == 3){//Bottom side of track is positioned
////            randPos1y = 485;
////        }
////        
////        if(xOry2 == 0){//Left side of track is positioned
////            randPos2x = 35;
////        }else if(xOry2 == 1){//Top side of track is positioned
////            randPos2y = 35;
////        }else if(xOry2 == 2){//Right side of track is positioned
////            randPos2x = 470;
////        }else if(xOry2 == 3){//Bottom side of track is positioned
////            randPos3y = 470;
////        }
////        
////        if(xOry3 == 0){//Left side of track is positioned
////            randPos3x = 55;
////        }else if(xOry3 == 1){//Top side of track is positioned
////            randPos3y = 55;
////        }else if(xOry3 == 2){//Right side of track is positioned
////            randPos3x = 455;
////        }else if(xOry3 == 3){//Bottom side of track is positioned
////            randPos3y = 455;
////        }
//    }
    
    public void buildPaths(){
//        choosePathPosition();
        //p.getElements().add(new HLineTo(10));
//        pathSetup(carPath1, path1, 15, randPos1x, randPos1y, xOry1, trackRate1,
//                    Color.BLUE);
//        path1 = new Rectangle(15,15,470,470);//and this to change the size and 
//        path1.setFill(Color.TRANSPARENT);//path of the Transition car
//        pl.setPath(path(15, randPos1x, randPos1y, xOry1));
//        path2 = new Rectangle(35,35,430,430);
//        path2.setFill(Color.TRANSPARENT);
//        p2.setPath(path(35, randPos2x, randPos2y, xOry2));
//        path3 = new Rectangle(55,55,390,390);
//        path3.setFill(Color.TRANSPARENT);
//        p3.setPath(path(55, randPos3x, randPos3y, xOry3));
//        carPath1 = new Rectangle(15,15,15,15);//change this 
//        carPath1.setFill(Color.BLUE);
//        carPath2 = new Rectangle(35,35,15,15);
//        carPath2.setFill(Color.RED);
//        carPath3 = new Rectangle(55,55,15,15);
//        carPath3.setFill(Color.AQUAMARINE);
        
        carPath1 = new Rectangle(15,15,15,15);//change this 
        carPath1.setFill(Color.TRANSPARENT);

        PathTransition pl = new PathTransition();
        pl.setNode(carPath1);
        pl.setPath(pat1);
        //Add new path right here
        pl.setDuration(Duration.seconds(trackRate1*20));
        pl.setAutoReverse(false);
        pl.setCycleCount(1);
        pl.play();
        
        Venue v = new Venue();
        //int[] a = v.getXvalues();
        
//        for(int i = 0; i < a.length; i++){
//            System.out.println("X Values: " + a[i]);
//        }
        
        carPath2 = new Rectangle(35,35,15,15);
        carPath2.setFill(Color.TRANSPARENT);

        PathTransition p2 = new PathTransition();
        p2.setNode(carPath2);
        //Add new path right here
        p2.setDuration(Duration.seconds(trackRate2 * 20));
        p2.setAutoReverse(false);
        p2.setCycleCount(1);
        p2.playFromStart();
        
        carPath3 = new Rectangle(55,55,15,15);
        carPath3.setFill(Color.TRANSPARENT);

        PathTransition p3 = new PathTransition();
        p3.setNode(carPath3);
        //Add new path right here
        p3.setDuration(Duration.seconds(trackRate3 * 20));
        p3.setAutoReverse(false);
        p3.setCycleCount(1);
        p3.playFromStart();
    }
    
    public void shutdown(){
        shutdown = true;
    }
     
    public Timer getTimer(){
        return ti;
    }
    
    public TimerTask getTimerTask(){
        return task;
    }
    
    public void addLines(){
        p.getElements().add(new LineTo(250,400));
    }
    
    public Rectangle getCar1(){
        return r1;
    }
    
    public Rectangle getCar2(){
        return r2;
    }
    
    public Rectangle getCar3(){
        return r3;
    }
    
    public Venue getVenue(){
        return v;
    }
}
