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
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
// Layout management
import javafx.scene.layout.GridPane;

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;

  public GUICore(){
      isWin = false; isAin = false; 
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
            if(e.getCode() == KeyCode.W){
                
//                if(inContactOuter(a) && isA == false
//                        && isD == false && isS == false){
//                    isW = true;
//                }else{
//                    if(!inContactOuter(a)){
//                        isW = false;
//                    }
//                    (a.getCar()).y -=5;
//                }
                
                
                if(inContactInner(a) && isAin == false 
                        && isDin == false && isSin == false){
                    isWin = true;
                }else{
                    if(!inContactInner(a)){
                        isWin = false;
                    }
                    (a.getCar()).y -=5;
                }
            }else if(e.getCode() == KeyCode.S){
                
                
//                if(inContactOuter(a) && isA == false
//                        && isD == false && isW == false){
//                    isS = true;
//                }else{
//                    if(!inContactOuter(a)){
//                        isS = false;
//                    }
//                    (a.getCar()).y +=5;
//                }
                
                
                
                if(inContactInner(a) && isAin == false
                        && isDin == false && isWin == false){
                    isSin = true;
                }else{
                    if(!inContactInner(a)){
                        isSin = false;
                    }
                    (a.getCar()).y +=5;
                }
            }else if(e.getCode() == KeyCode.A){
                
                
//                if(inContactOuter(a) && isW == false
//                        && isS == false && isD == false){
//                    isA = true;
//                }else{
//                    if(!inContactOuter(a)){
//                        isA = false;
//                    }
//                    (a.getCar()).x -= 5;
//                }
                
                
                if(inContactInner(a) && isWin == false
                        && isSin == false && isDin == false){
                    isAin = true;
                }else{
                    if(!inContactInner(a)){
                        isAin = false;
                    }
                    (a.getCar()).x -= 5;
                }
            }else if(e.getCode() == KeyCode.D){
                
//                if(inContactOuter(a) && isW == false
//                        && isS == false && isA == false){
//                    isD = true;
//                }else{
//                    if(!inContactOuter(a)){
//                        isD = false;
//                    }
//                    (a.getCar()).x +=5;
//                }
                
                
                if(inContactInner(a) && isWin == false
                        && isSin == false && isAin == false){
                    isDin = true;
                }else{
                    if(!inContactInner(a)){
                        isDin = false;
                    }
                    (a.getCar()).x +=5;
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
  
  public boolean inContactOuter(Animation a){
      return (!(a.getCar()).isTouching(a.getOuterTrack()));
  }
  
  public boolean inContactInner(Animation a){
      return (a.getCar()).isTouching(a.getInnerTrack());
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}