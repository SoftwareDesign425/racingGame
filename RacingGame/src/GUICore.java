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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
// Class Compatibility
import javafx.scene.Parent;

public class GUICore extends Application{
  
  public void start(Stage stage){
    
//******************* Set-Up *******************//
    
    Venue coreVenue = new Venue();
    coreVenue.load("inputFile.txt");
    Animation coreAnim = new Animation(coreVenue);
    Pane corePane = new Pane();
    corePane.setPrefSize(800,500); // Allotting a 300x500 space for our menu
    

    // Add buttons
    //gp.add(guiButtons, 0,0);
    // Add animation
    //gp.add(animation, 0,0);
//******************* Animation *******************//
    
    Pane animPane = coreAnim.createContent(); // This is a 500x500 Pane
    corePane.getChildren().add(animPane);
    animPane.relocate(0,0); // Move animation pane to top left corner
//******************* Display *******************//
    
    Scene menu = new Scene(corePane);
    //Animation animator = new Animation(p);
    stage.setTitle("Project 3 - Racing Game");       
    stage.setScene(menu); 
    stage.setResizable(true);    
    stage.sizeToScene();
    stage.show();
    
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}