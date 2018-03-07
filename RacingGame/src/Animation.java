
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
Joseph Mitchell

This class handles animations like, tracks, cars, ect.

Purpose:
To split the gui so that the gui is not too long.
*/
public class Animation extends Scene{
    
    private Scene root;
    private Group layout;
    
    public Animation(Parent root1) {
        super(root1, 2000, 2000);
        Pane p = new Pane();
        ((Group)getRoot()).getChildren().add(p);
    }
    
//    public void start(Stage primaryStage) throws Exception {
//        root = new Scene(layout, 2000, 2000);
//        primaryStage.setScene(root);
//        primaryStage.show();
//    
//    }
    
//    public static void main(String[] args){
//        launch(args);
//    }
    
}
