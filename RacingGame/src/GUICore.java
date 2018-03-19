/* Michael Swann
 * This class contains the basic core of the GUI
 * It will display the main window, as well as navigation/menu buttons
 * It will NOT perform animating, but will contain the animation class
 */

/* TO-DO:
 * MAKE MORE INPUT FILES SO THAT IT DOESN'T MESS UP THE FILE SELECTOR
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
  private Pane corePane;
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
      winner = false;
      inputString = "inputFile.txt"; // Initialize this String
  }  
  
  
  public void start(Stage stage){

//******************* Set-Up *******************//
    
    // Main body
    System.out.println(inputString);
    Venue coreVenue = new Venue();
    coreVenue.load(inputString);
    a = new Animation(coreVenue);
    corePane = new Pane();
    corePane.setPrefSize(650,500); // Allotting a 150x500 space for our menu
    
    // Alerts
    helpAlert = new Alert(AlertType.INFORMATION, "Help information will go here once created.");
    helpAlert.setHeaderText("Game Help");
    
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
    stage.setResizable(false);    
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
        animPane.getChildren().add(a.getWinner());
        
  }
  
  
  // File selection
  public void fileSelection(){
    
    for(ParallelTransition i : a.getPA()){ // Freeze race while window is open
      i.pause();
    }
    
    // Buttons
    RadioButton button1 = new RadioButton("Kansas");
    button1.relocate(100, 10);
    RadioButton button2 = new RadioButton("California");
    button2.relocate(100, 30);
    RadioButton button3 = new RadioButton("Florida");
    button3.relocate(100, 50);
    RadioButton button4 = new RadioButton("Arizona");
    button4.relocate(100, 70);
    
    ToggleGroup group = new ToggleGroup();
    button1.setToggleGroup(group);
    button2.setToggleGroup(group);
    button3.setToggleGroup(group);
    button4.setToggleGroup(group);
    
    Button selectButton = new Button("Select");
    selectButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        if(button1.isSelected()){
          inputString = "inputFile.txt"; // change this later
          corePane.getScene().getWindow().hide();
          start(new Stage());
        }
        if(button2.isSelected()){
          inputString = "California.txt";
          corePane.getScene().getWindow().hide();
          start(new Stage());
        }
        if(button3.isSelected()){
          inputString = "Florida.txt";
          corePane.getScene().getWindow().hide();
          start(new Stage());
        }
        if(button4.isSelected()){
          inputString = "Arizona.txt";
          corePane.getScene().getWindow().hide();     // Close the other window
          start(new Stage());                         // Make replacement window
        }
        else{ // Revert to cancel button functionality if nothing selected
          for(ParallelTransition i : a.getPA()){
            if(!i.getStatus().equals(Status.STOPPED)){
              i.play(); // Un-freeze now that the window is closed but no new race is selected
            }
          }
        }
        selectButton.getScene().getWindow().hide(); // Close this window        
      }
    }));
    selectButton.relocate(100, 120);
    
    Button cancelButton = new Button("Cancel");
    cancelButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        cancelButton.getScene().getWindow().hide(); // Hide (close) the window
        for(ParallelTransition i : a.getPA()){
          if(!i.getStatus().equals(Status.STOPPED)){
            i.play(); // Un-freeze now that the window is closed but no new race is selected
          }
        }
      }
    }));
    cancelButton.relocate(100, 150);
    
    // Create a new pop-up window to display this
    final Stage fileStage = new Stage();
    fileStage.initModality(Modality.APPLICATION_MODAL); // This window blocks access to the main window until closed
    Pane filePane = new Pane();
    filePane.getChildren().addAll(button1, button2, button3, button4, selectButton, cancelButton);
    Scene fileScene = new Scene(filePane, 300, 200);
    fileStage.setScene(fileScene);
    fileStage.setResizable(false);
    fileStage.show();
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}