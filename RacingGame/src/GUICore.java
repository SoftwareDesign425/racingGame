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
import javafx.stage.Stage;
// Layout management
import javafx.scene.layout.GridPane;

public class GUICore extends Application{
  
  public void start(Stage stage){
    
//******************* GridPane *******************//
    GridPane gp = new GridPane();
    // Add buttons
    //gp.add(guiButtons, 0,0);
    // Add animation
    //gp.add(animation, 0,0);
    
//******************* Display *******************//
    Scene scene = new Scene(gp); 
    stage.setTitle("Project 3 - Racing Game");       
    stage.setScene(scene); 
    stage.setResizable(true);    
    stage.sizeToScene();
    stage.show();
    
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}