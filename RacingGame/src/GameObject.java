
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
Joseph Mitchell

This class will be responcible for handling each game objects positioning with 
the following charactoristics:
- x(int) - holds the current x position of the object
- y(int) - holds the current y position of the object
- w(int) - width of the object
- h(int) - hieght of the object

methods:
- GameObject() [no return type]- no arg constructor [initializes all attributes to 5]
- GameObject(*with all attributes) [no return type]- initializes the Rectangle the the 
         given position, width, and height
- draw(GraphicsContext g, Color c, boolean s) [void]- draws as well as colors
         to the given rectangle with boolean s stating whether or not 
         Color is for stroke or fill
- car()[Car] - used for detecting the cars position relative to another object
- isTouching(GameObject o) [boolean]- uses Car's isTouching method to determine
         whether the two GameObjects collided.


Purpose:
To declutter the Animation class and be able to freely manipulate each
game objects position on the screen
*/


public class GameObject{
    
    public int x, y, w, h;
    
    public GameObject(){ x = 5; y = 5; w = 5; h = 5;}
    public GameObject(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void draw(GraphicsContext g, Color c, boolean s){
        
        if(s){
            g.setStroke(c);
            g.strokeRect(x, y, w, h);
        }else{
            g.setFill(c);
            g.fillRect(x, y, w, h);
        }
    }
    
    public Car car(){
        return new Car(x, x+w, y, y+h);
    }
    public boolean isTouching(GameObject other){
        return car().isTouching(other.car());
    }
    
    
}
