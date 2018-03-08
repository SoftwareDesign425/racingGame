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

Purpose:
To split the gui so that the gui is not too long.
*/
public class Animation extends Scene{
    
    private Image track;
    private int wid, heigh;
    
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
    
    public int getGameWidth(){
        return wid;
    }
    
    public int getGameHeight(){
        return heigh;
    }
}
