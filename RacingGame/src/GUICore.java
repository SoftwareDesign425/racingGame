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
import javafx.stage.Modality;
// Menu things (buttons, mouse input, etc)
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent; 
import javafx.event.EventHandler;
// Alerts/Pop-Ups
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// Class Compatibility/ Misc.
import javafx.animation.ParallelTransition;
import javafx.animation.Animation.Status;

/* Changed by Ana Gorohovschi
* There should be only one Venue object
* Added drawing of the Stops
*/

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;
  private Scene scene;
  private Animation a;
  private String inputString;
  private Alert helpAlert;
  private Alert winAlert;
  private boolean winner;
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
      winner = false;
  }  
  
  
  public void start(Stage stage){

//******************* Set-Up *******************//
    
    // Main body
    inputString = "inputFile.txt"; // Initialize this String
    Venue coreVenue = new Venue();
    coreVenue.load(inputString);
    a = new Animation(coreVenue);
    Pane corePane = new Pane();
    corePane.setPrefSize(650,500); // Allotting a 150x500 space for our menu
    
    // Alerts
    helpAlert = new Alert(AlertType.INFORMATION, "Help information will go here once created.");
    helpAlert.setHeaderText("Game Help");
    //winAlert = new Alert(AlertType.CONFIRMATION, "The winner is " + a.getWinner().getText()); //Commented out the stuff I'm absolutely know doesn't work now
    
//******************* Menu *******************//
    
    // Start button
    Button startButton = new Button("Begin/Reset Race");
    startButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        for(ParallelTransition i : a.getPA()){
          i.playFromStart();
        }
      }
    }));
    startButton.relocate(525, 25);
    
    // Reset button
    Button resetButton = new Button("Pause Race");
    resetButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        System.out.println(a.getStatus());
        if(a.getStatus().equals(Status.RUNNING)){
          resetButton.setText("Play Race");
          for(ParallelTransition i : a.getPA()){
            i.pause();
          }
        }
        else{
          resetButton.setText("Pause Race");
          for(ParallelTransition i : a.getPA()){
            if(!i.getStatus().equals(Status.STOPPED)){
              i.play();
            }
          }
        }
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
        a.init_Cars();
        a.buildPaths();
        a.buildTransitions();
        a.playTransitions();

        animPane.getChildren().addAll(a.buildRoad());
        
        animPane.getChildren().addAll(coreVenue.stopsView());
        
        animPane.getChildren().addAll(a.init_StopNames());
        animPane.getChildren().addAll(a.getCarAnim());
        animPane.getChildren().addAll(a.getCarNames());
        
        a.playTransitions();
        
  }
  
  
  // File selection
  public void fileSelection(){
    
    // Buttons
    //
    
    // Create a new pop-up window to display this
    final Stage fileStage = new Stage();
    fileStage.initModality(Modality.APPLICATION_MODAL); // This window blocks access to the main window until closed
    Pane filePane = new Pane();
    Scene fileScene = new Scene(filePane, 300, 200);
    fileStage.setScene(fileScene);
    fileStage.show();
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}