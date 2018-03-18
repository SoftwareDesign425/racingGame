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
import javafx.animation.Animation.Status;

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
      a = new Animation();
      winner = false;
  }  
  
  
  public void start(Stage stage){

//******************* Set-Up *******************//
    
    // Main body
    inputString = "../inputFile.txt"; // Initialize this String
    Venue coreVenue = new Venue();
    coreVenue.load(inputString);
    Animation coreAnim = new Animation();
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
//        winner = false;
//        a.getPT1().playFromStart();
//        a.getPT2().playFromStart();
//        a.getPT3().playFromStart();
      }
    }));
    startButton.relocate(525, 25);
    
    // Reset button
    Button resetButton = new Button("Pause Race");
    resetButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
//        if(a.getPT1().getStatus().toString().equals("PAUSED") || a.getPT2().getStatus().toString().equals("PAUSED") || a.getPT3().getStatus().toString().equals("PAUSED")){ // If any are paused, unpause
//          resetButton.setText("Pause Race");
//          if(!a.getPT1().getStatus().toString().equals("STOPPED")){ // Don't attempt to play animation if it's already finished
//            a.getPT1().play();
//          }
//          if(!a.getPT2().getStatus().toString().equals("STOPPED")){
//            a.getPT2().play();
//          }
//          if(!a.getPT3().getStatus().toString().equals("STOPPED")){
//            a.getPT3().play();
//          }
//        }
//        else{ // If none are paused, pause
//          resetButton.setText("Play Race");
//          a.getPT1().pause();
//          a.getPT2().pause();
//          a.getPT3().pause();
//        }
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
    
        a.getVenue().testLoading();
        
        a.init_Cars();
        a.buildPaths();
        a.buildTransitions();
        a.playTransitions();
        
        animPane.getChildren().addAll(a.init_StopNames());
        animPane.getChildren().addAll(a.getCarAnim());
        animPane.getChildren().addAll(a.getCarNames());
        
        
        a.playTransitions();
        
  }
  
  
  // File selection
  public void fileSelection(){
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}