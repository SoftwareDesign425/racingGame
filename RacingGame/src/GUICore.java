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
import javafx.event.ActionEvent;
// Alerts/Pop-Ups
import java.util.Optional;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// Class Compatibility/ Misc.
import javafx.scene.Parent;
import javafx.animation.PathTransition;
import javafx.util.Duration;

public class GUICore extends Application{
  
  private boolean isWin, isAin, isSin, isDin;
  private Scene scene;
  private Animation a;
  private PathTransition p1,p2,p3;
  private String inputString;
  private Alert helpAlert;
  
  public GUICore(){
      isWin = false; isAin = false; //True if colliding, False if not colliding
      isSin = false; isDin = false;
      a = new Animation();
      p1 = new PathTransition();
      p2 = new PathTransition();
      p3 = new PathTransition();
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
    
    Pane animPane = coreAnim.createContent(); // This is a 500x500 Pane
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
    
//    a = new Animation();//Joe for Animation
////    a.setRate(25);
//    a.getTimer().scheduleAtFixedRate(a.getTimerTask(), 1000, 1000);//Joe for
//    //GridPane gp = new GridPane();
//    Button b = new Button("This is a button");//Testing purposes
//    Pane gp = ((Pane)a.createContent());//Joe for Animation
//    b.setTranslateX(300);
//    b.setTranslateY(250);
//    gp.getChildren().add(b);
//    // Add buttons
//    //gp.add(guiButtons, 0,0);
//    // Add animation
//    //gp.add(animation, 0,0);
//    
//    scene = new Scene(gp, 900, 900); //Dimensions will be modified based on the max x/y value
////    scene.setOnKeyPressed(e -> {//Joe for Animation(Have speed be based by time!)
////        for(int i = 0; i < a.getRate(); i++){
////            if(e.getCode() == KeyCode.W){//Move forward
////                if(a.stopW()){
////                    isWin = false;
////                    (a.getCar()).y -=1;//value could make the car go outside the boundary!
////                }else{                 //have this be 1, and change the repaint time 
////                    isWin = true;      //be the speed factor:)
////                }
////            }else if(e.getCode() == KeyCode.S){//Move backward
////                if(a.stopS()){
////                    isSin = false;
////                    (a.getCar()).y +=1;
////                }else{
////                    isSin = true;
////                }
////            }else if(e.getCode() == KeyCode.A){//Move left
////                if(a.stopA()){
////                    isAin = false;
////                    (a.getCar()).x -=1;
////                }else{
////                    isAin = true;
////                }
////            }else if(e.getCode() == KeyCode.D){//Move right
////                if(a.stopD()){
////                    isDin = false;
////                    (a.getCar()).x +=1;
////                }else{
////                    isDin = true;
////                }
////            }
////            a.render();
////        }
////    });
//        a.addLines();
//        
//        gp.getChildren().add(a.getCar1());
//        gp.getChildren().add(a.getCar2());
//        gp.getChildren().add(a.getCar3());
//
//        
//        a.getVenue().testLoading();
//        
//        
//        //Path Transition for car1
//        p1.setNode(a.getCar1());
//        p1.setPath(a.getVenue().getPath1());
//        p1.setDuration(Duration.seconds(15));
//        p1.setAutoReverse(false);
//        p1.setCycleCount(1);
//        p1.play();
//        
//        //Path Transition for car2
//        p2.setNode(a.getCar2());
//        p2.setPath(a.getVenue().getPath2());
//        p2.setDuration(Duration.seconds(20));
//        p2.setAutoReverse(false);
//        p2.setCycleCount(1);
//        p2.play();
//        
//        //Path Transition for car3
//        p3.setNode(a.getCar3());
//        p3.setPath(a.getVenue().getPath3());
//        p3.setDuration(Duration.seconds(30));
//        p3.setAutoReverse(false);
//        p3.setCycleCount(1);
//        p3.play();
//        
//        
//    stage.setTitle("Project 3 - Racing Game");       
//    stage.setScene(scene); 
//    //stage.setSize(500,500);
//    stage.setResizable(true);    
//    stage.sizeToScene();
//    stage.show();
    
  }
  
//  public void keyCommands(Scene scene, Animation a){
//      
//  }
  
//  public boolean inContactTop(Animation a){
//      return (a.getCar()).isTouching(a.getTop());
//  }
//  
//  public boolean inContactBottom(Animation a){
//      return (a.getCar()).isTouching(a.getBottom());
//  }
//  
//  public boolean inContactLeft(Animation a){
//      return (a.getCar()).isTouching(a.getLeft());
//  }
//  
//  public boolean inContactRight(Animation a){
//      return (a.getCar()).isTouching(a.getRight());
//  }
  
  // File selection
  public void fileSelection(){
  }
  
  // Run program
  public static void main(String args[]){          
    launch(args);     
  }
  
}