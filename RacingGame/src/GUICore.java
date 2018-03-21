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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/* Changed by Ana Gorohovschi
 * There should be only one Venue object
 * Added drawing of the Stops
 */

public class GUICore extends Application{
  
  
  private Animation a;
  private String inputString;
  private Alert helpAlert;
  private Alert winAlert;
  private boolean winner;
  private Pane corePane, animPane;
  
  public GUICore(){//Joe - eliminated unnessacary attribute:)
    inputString = "Kansas.txt"; // Initialize this String to our "base" venue
  }  

  public void start(Stage stage){
    
//******************* Set-Up *******************//
    
    // Main body
    Venue coreVenue = new Venue();
    coreVenue.load(inputString);
    a = new Animation(coreVenue);
    corePane = new Pane();
    corePane.setPrefSize(650,500); // Allotting a 150x500 space for our menu
    
    // Alerts
    helpAlert = new Alert(AlertType.INFORMATION, 
            "Begin/Restart Race - restarts the current race\n\n"
                    + "Play/Pause Race - plays or pauses the current race\n\n"
                    + "Select Race - chooses a new map with new cars and speeds\n\n");
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
    fileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event){
        fileSelection(); // Not implemented yet
      }
    });
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
    
    animPane = new Pane(); // This is a 500x500 Pane
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
    
//******************* Animation Finalization *******************//
    
    a.init_Cars();//initializes cars color and car name color and adds them to ArrayLists that will return to animPane
    a.buildPaths();//Builds the paths for each car
    a.buildTransitions();//Builds the transitions for each car
    a.playTransitions();//plays the transition for each car
    
    
    animPane.getChildren().addAll(a.buildRoad());//builds the road(track for the animation)
    
    animPane.getChildren().addAll(coreVenue.stopsView());//Returns the stops animation for track
    
    animPane.getChildren().addAll(a.init_StopNames());//Returns the stop names to the animPane
    animPane.getChildren().addAll(a.getCarAnim());//Returns the cars to the animPane
    animPane.getChildren().addAll(a.getCarNames());//Returns the car names to the animPane
    animPane.getChildren().add(a.getWinner());//Returns the winner to the screen
    
    animPane.addEventHandler(GameOverEvent.gameOver, event -> showScoreBoard());
  }
  
  //Ana Gorohovschi
  //Display the scoreboard in a pop-up window
  private void showScoreBoard()
  {
    final Stage scoreBoardStage = new Stage();
    GridPane scoreBoard = new GridPane();
    
    scoreBoard.setPadding(new Insets(10, 10, 10, 10)); 

    scoreBoard.setVgap(5); 
    scoreBoard.setHgap(5);       

    scoreBoard.setAlignment(Pos.CENTER);
    
    Venue v = a.getVenue();
    
    double trackLength = v.trackLength();
    
    scoreBoard.add(new Text("Car:"), 0, 0);
    scoreBoard.add(new Text("Path:"), 1, 0);
    scoreBoard.add(new Text("Speed (mph):"), 2, 0);
    scoreBoard.add(new Text("Total time (s):"), 3, 0);
    
    int i=1;
    for(Car c : v.getCars())
    {
        scoreBoard.add(new Text(c.getName()), 0, i);
        TextFlow path = new TextFlow();
        path.setLineSpacing(5.0);

        for(int j=c.getEndStop(), k=0; k<v.getStops().size(); k++, j++)
        {
            if(j == v.getStops().size())
                j = 0;
            
            Text stopName = new Text(v.getStops().get(j).getName() + ", ");
            path.getChildren().add(stopName);
        }
        
        scoreBoard.add(path, 1, i);
        
        scoreBoard.add(new Text(
                String.format("%.2f", c.getSpeed()) + " mph"), 
                2, i);
        
        double time = trackLength * (200 - c.getSpeed());
        time /= 60;
        
        scoreBoard.add(new Text(String.format("%.2f", time) + " s"), 3, i);
        
        i++;
    }
    
    Scene scoreBoardScene = new Scene(scoreBoard);
    scoreBoardStage.setScene(scoreBoardScene);
    scoreBoardStage.show();      
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
          inputString = "Kansas.txt";
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
    filePane.getChildren().addAll(button1, button2, button3, button4,  selectButton, cancelButton);
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