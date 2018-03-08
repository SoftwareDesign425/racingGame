import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/*
Joseph Mitchell

This class handles animations like, tracks, cars, ect.

This class has the following attributes:


track(Image) - this is the image of the track(may later be collection)
wid(int)- This is the width of the image
heigh(int) - This is the height of the image
    *wid and heigh are used to resize the scene
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
public class Animation extends Scene{
    
    private Image track;
    private int wid, heigh;
    private Timer t = new Timer(true);
    
    private TimerTask task = new TimerTask() {    
        public void run(){
            if(!shutdown){
                sec++;
                System.out.println("Seconds: " + sec);
            }
        }
    };;
    private int sec = 0;
    private volatile boolean shutdown = false;
    
    
    public Animation(Parent root1) {
        super(root1, 500, 500);
        track = new Image("https://docs.telerik.com/devtools/winforms/telerik-presentation-framework/shapes/images/donut-shape001.png");
        wid = 0;heigh = 0;//initialize width and height of game to 0.
        ImageView iv = new ImageView();
        iv.setImage(track);
        iv.setSmooth(true);
        setGameDim(iv);
    }
    
    public void setGameDim(ImageView i){
        String info = "", width = "", height = "";
        info += i.boundsInParentProperty();
        width = info.substring(info.indexOf("width:"), info.indexOf(",",info.indexOf("width:")));
        height = info.substring(info.indexOf("height:"), info.indexOf(",",info.indexOf("height:")));
        
        width = width.substring(width.indexOf(":")+1, width.indexOf(".", width.indexOf(":")));
        height = height.substring(height.indexOf(":")+1, height.indexOf(".", height.indexOf(":")));
        
        wid = Integer.parseInt(width);
        heigh = Integer.parseInt(height);
        
        System.out.println("Width: " + wid + "\nHeight: " + heigh);
        
        ((Group)getRoot()).getChildren().add(i);
    }
    
    public void shutdown(){
        shutdown = true;
    }
    
    public Timer getTimer(){
        return t;
    }
    
    public TimerTask getTimerTask(){
        return task;
    }
    
    public int getGameWidth(){
        return wid;
    }
    
    public int getGameHeight(){
        return heigh;
    }
}
