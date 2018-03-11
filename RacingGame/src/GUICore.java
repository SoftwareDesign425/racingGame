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

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
  }  
    
  public void start(Stage stage){
    Animation a = new Animation();//Joe for Animation
    a.getTimer().scheduleAtFixedRate(a.getTimerTask(), 1000, 1000);//Joe for 
    //Animation
//******************* GridPane *******************//
    //GridPane gp = new GridPane();
    Button b = new Button("This is a button");//Testing purposes
    GridPane gp = ((GridPane)a.createContent());//Joe for Animation
    gp.getChildren().add(b);
    // Add buttons
    //gp.add(guiButtons, 0,0);
    // Add animation
    //gp.add(animation, 0,0);
    
//******************* Display *******************//
    Scene scene = new Scene(gp); 
    scene.setOnKeyPressed(e -> {//Joe for Animation
            if(e.getCode() == KeyCode.W){//Move forward
                if(a.stopW()){
                    isWin = false;
                    (a.getCar()).y -=5;
                }else{
                    isWin = true;
                }
            }else if(e.getCode() == KeyCode.S){//Move backward
                if(a.stopS()){
                    isSin = false;
                    (a.getCar()).y +=5;
                }else{
                    isSin = true;
                }
            }else if(e.getCode() == KeyCode.A){//Move left
                if(a.stopA()){
                    isAin = false;
                    (a.getCar()).x -= 5;
                }else{
                    isAin = true;
                }
            }else if(e.getCode() == KeyCode.D){//Move right
                if(a.stopD()){
                    isDin = false;
                    (a.getCar()).x +=5;
                }else{
                    isDin = true;
                }
            }
            a.render();
    });
    stage.setTitle("Project 3 - Racing Game");       
    stage.setScene(scene); 
    stage.setResizable(true);    
    stage.sizeToScene();
    stage.show();
    
  }
  
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