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
// Menu things (buttons, mouse input, etc)
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;
// Alerts/Pop-Ups
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// Class Compatibility/ Misc.

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;
  private Scene scene;
  private Animation a;
  private String inputString;
  private Alert helpAlert;
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
      a = new Animation();
  }  
  
  
  public void start(Stage stage){

//******************* Set-Up *******************//
    
    // Main body
    inputString = "../inputFile.txt"; // Initialize this String
    Venue coreVenue = new Venue();
    coreVenue.load(inputString);
    Animation coreAnim = new Animation();
    Pane corePane = new Pane();
    corePane.setPrefSize(625,500); // Allotting a 125x500 space for our menu
    
    // Alerts
    helpAlert = new Alert(AlertType.INFORMATION, "Help information will go here once created.");
    helpAlert.setHeaderText("Game Help");
    
//******************* Menu *******************//
    
    // Start button
    Button startButton = new Button("Begin Race");
    startButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        // Not implemented properly yet
      }
    }));
    startButton.relocate(525, 25);
    
    // Reset button
    Button resetButton = new Button("Reset Race");
    resetButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        // No reset method exists yet
      }
    }));
    resetButton.relocate(525, 55);
    
    // Select File button
    Button fileButton = new Button("Select Race");
    fileButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        fileSelection(); // Not implemented yet
      }
    }));
    fileButton.relocate(525, 85);
    
    // Help button
     Button helpButton = new Button("Help");
    helpButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        helpAlert.show(); // Display our help alert
      }
    }));
    helpButton.relocate(525, 115);
    
    // Button Grouping
    Group buttons = new Group(startButton, resetButton, fileButton, helpButton);
    corePane.getChildren().add(buttons);
    
//******************* Animation *******************//
    
    Pane animPane = new Pane(); // This is a 500x500 Pane
    animPane.setLayoutX(500);
    animPane.setLayoutY(500);
    corePane.getChildren().add(animPane);
    animPane.relocate(0,0); // Move animation pane to top left corner
    
//******************* Display *******************//
    
    Scene menu = new Scene(corePane);
    stage.setTitle("Project 3 - Racing Game");       
    stage.setScene(menu); 
    stage.setResizable(true);    
    stage.sizeToScene();
    stage.show();
    
//******************* JOE STUFF BELOW THIS LINE *******************// 
    
    animPane.getChildren().add(a.getCar1());
    animPane.getChildren().add(a.getCar2());
    animPane.getChildren().add(a.getCar3());

    a.getVenue().testLoading();
        
    a.captureXvalues();
    a.captureYvalues();

    a.moveCars();
        
    a.getPath1().playFromStart();
    a.getPath2().playFromStart();
    a.getPath3().playFromStart();
    
    
  }
  
  
  // File selection
  public void fileSelection(){
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}