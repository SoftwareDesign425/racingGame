/* Michael Swann
 * This class contains the basic core of the GUI
 * It will display the main window, as well as navigation/menu buttons
 * It will NOT perform animating, but will contain the animation class
 */

/* TO-DO:
 * ADD BUTTONS
 * ADD PLUG FOR ANIMATION CLASS
 * ADD FILE I/O
 */

// Default javaFX imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
// Layout management
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;
  private Scene scene;
  private Animation a;
  
  private Venue v = new Venue();
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
  }  
  
  
  public void start(Stage stage){
    v.load("inputFile.txt");
    a = new Animation(v);//Joe for Animation
    a.setRate(25);
    a.getTimer().scheduleAtFixedRate(a.getTimerTask(), 1000, 1000);//Joe for
    //Animation
//******************* GridPane *******************//
    //GridPane gp = new GridPane();
    Button b = new Button("This is a button");//Testing purposes
    Pane gp = ((Pane)a.createContent());//Joe for Animation
    b.setTranslateX(300);
    b.setTranslateY(250);
    gp.getChildren().add(b);
    // Add buttons
    //gp.add(guiButtons, 0,0);
    // Add animation
    //gp.add(animation, 0,0);
    
//******************* Display *******************//
    scene = new Scene(gp); 
    scene.setOnKeyPressed(e -> {//Joe for Animation(Have speed be based by time!)
        for(int i = 0; i < a.getRate(); i++){
            if(e.getCode() == KeyCode.W){//Move forward
                if(a.stopW()){
                    isWin = false;
                    (a.getCar()).y -=1;//value could make the car go outside the boundary!
                }else{                 //have this be 1, and change the repaint time 
                    isWin = true;      //be the speed factor:)
                }
            }else if(e.getCode() == KeyCode.S){//Move backward
                if(a.stopS()){
                    isSin = false;
                    (a.getCar()).y +=1;
                }else{
                    isSin = true;
                }
            }else if(e.getCode() == KeyCode.A){//Move left
                if(a.stopA()){
                    isAin = false;
                    (a.getCar()).x -=1;
                }else{
                    isAin = true;
                }
            }else if(e.getCode() == KeyCode.D){//Move right
                if(a.stopD()){
                    isDin = false;
                    (a.getCar()).x +=1;
                }else{
                    isDin = true;
                }
            }
            a.render();
        }
    });
    stage.setTitle("Project 3 - Racing Game");       
    stage.setScene(scene); 
    stage.setResizable(true);    
    stage.sizeToScene();
    stage.show();
    
  }
  
//  public void keyCommands(Scene scene, Animation a){
//      
//  }
  
  public boolean inContactTop(Animation a){
      return (a.getCar()).isTouching(a.getTop());
  }
  
  public boolean inContactBottom(Animation a){
      return (a.getCar()).isTouching(a.getBottom());
  }
  
  public boolean inContactLeft(Animation a){
      return (a.getCar()).isTouching(a.getLeft());
  }
  
  public boolean inContactRight(Animation a){
      return (a.getCar()).isTouching(a.getRight());
  }
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}