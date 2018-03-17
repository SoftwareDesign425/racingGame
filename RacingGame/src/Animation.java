/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package AnimationPractice;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Joseph Mitchell
 */

//Calling FileReader twice somewhere :> 

public class Animation {
    private Rectangle r1,r2,r3;
    private Path path1,path2,path3,
            namePath1, namePath2, namePath3;
    private String pOrder1, pOrder2, pOrder3;
    private Text winner;
    private PathTransition p1,p2,p3,cName1,cName2,cName3;
    private ParallelTransition pt1, pt2, pt3;
    private Venue v;
    private ArrayList<Integer> x,y;
    private ArrayList<Double> speed;
    private ArrayList<String> s,n;
    private ArrayList<Text> t,n_t;
    private ArrayList<Car> cars;
    private boolean gameOver;
    
    public Animation(){
        v = new Venue();
        r1 = new Rectangle(50,50,15,15);
        r1.setFill(Color.BLUE);
        r2 = new Rectangle(50,50,15,15);
        r2.setFill(Color.RED);
        r3 = new Rectangle(50,50,15,15);
        r3.setFill(Color.BLUEVIOLET);
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
        speed = new ArrayList<Double>();
        s = new ArrayList<String>();
        n = new ArrayList<String>();
        t = new ArrayList<Text>();
        n_t = new ArrayList<Text>();
        cars = new ArrayList<Car>();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        namePath1 = new Path();
        namePath2 = new Path();
        namePath3 = new Path();
        pOrder1 = "";
        pOrder2 = "";
        pOrder3 = "";
        p1 = new PathTransition();
        p2 = new PathTransition();
        p3 = new PathTransition();
        pt1 = new ParallelTransition();
        pt2 = new ParallelTransition();
        pt3 = new ParallelTransition();
        cName1 = new PathTransition();
        cName2 = new PathTransition();
        cName3 = new PathTransition();
        winner = new Text();
        gameOver = false;
    }
    
    public void captureXvalues(){
        x = v.getXvalues();
    }
    
    public void captureYvalues(){
        y = v.getYvalues();
    }
    
    public void captureStopNames(){
        s = v.getStopNames();
    }
    
    public void captureCarNames(){
        n = v.getCarNames();
    }
    
    public void captureCars(){
        cars = v.getCars();
        System.out.println(cars);
    }
    
    public ArrayList<Text> init_StopNames(){
        for(int i = 0; i < s.size(); i++){
            t.add(new Text(s.get(i)));
            t.get(i).relocate(x.get(i), y.get(i));
        }
        return t;
    }
    
    public ArrayList<Text> init_CarNames(){
        for(int i = 0; i < n.size(); i++){
            n_t.add(new Text(n.get(i)));
        }
        n_t.get(0).setFill(Color.BLUE);
        n_t.get(1).setFill(Color.RED);
        n_t.get(2).setFill(Color.BLUEVIOLET);
        return n_t;
    }
    
    public void setCarsSpeed(){
        Random rand = new Random();
        double speed = 0;
        for(Car c : cars){
            speed = (v.distanceT(c) - 200.0) + (v.distanceT(c)-(v.distanceT(c) - 200.0))*rand.nextDouble();
                    
            c.setSpeed(speed);
        }
    }
    
    public void moveCars(){
        Random rand = new Random();
        int choice = rand.nextInt(x.size());
        int choice2 = rand.nextInt(x.size());
        int choice3 = rand.nextInt(x.size());
        path1.getElements().add(new MoveTo(x.get(choice), y.get(choice)));//Randomize start and loop through
        path2.getElements().add(new MoveTo(x.get(choice2), y.get(choice2)));
        path3.getElements().add(new MoveTo(x.get(choice3), y.get(choice3)));
        namePath1.getElements().add(new MoveTo(x.get(choice), y.get(choice)-15));
        namePath2.getElements().add(new MoveTo(x.get(choice2), y.get(choice2)-30));
        namePath3.getElements().add(new MoveTo(x.get(choice3), y.get(choice3)-45));
        
        System.out.println("BLUE: " + s.get(choice) + "\nRED: " + s.get(choice2) + "\nBLUEVIOLET: " + s.get(choice3));
        
        int track = choice + 1, track2 = choice2 + 1, track3 = choice3 + 1;
        for(int i = 0; i < x.size(); i++){
            if(track >= x.size()){
                track = 0;
            }
            if(track2 >= x.size()){
                track2 = 0;
            }
            if(track3 >= x.size()){
                track3 = 0;
            }
            pOrder1 += track;
            pOrder2 += track2;
            pOrder3 += track3;
            
            track++;
            track2++;
            track3++;
        }
        
        for(int i = 0; i < pOrder1.length(); i++){
            String i_s = "", i_s2 = "", i_s3 = "";
            i_s += pOrder1.charAt(i);
            i_s2 += pOrder2.charAt(i);
            i_s3 += pOrder3.charAt(i);
            int index = Integer.parseInt(i_s), 
                    index2 = Integer.parseInt(i_s2), index3 = Integer.parseInt(i_s3);
            path1.getElements().add(new LineTo(x.get((index)), y.get(index)));
            namePath1.getElements().add(new LineTo(x.get(index), y.get(index)-15));
            path2.getElements().add(new LineTo(x.get(index2), y.get(index2)));
            namePath2.getElements().add(new LineTo(x.get(index2), y.get(index2)-30));
            System.out.println("Index3: " + index3);
            path3.getElements().add(new LineTo(x.get(index3), y.get(index3)));
            namePath3.getElements().add(new LineTo(x.get(index3), y.get(index3)-45));
        }
    }
    
    public void setPath(Node a, Path p, PathTransition pt, double t){
        pt.setNode(a);
        pt.setPath(p);
        pt.setDuration(Duration.seconds(t));
        pt.setAutoReverse(false);
        pt.setCycleCount(1);
    }
    
    public void buildPath1(){
        setPath(r1,path1,p1,v.distanceT(cars.get(0))/cars.get(0).getSpeed());//Adding speed:)
        p1.setOnFinished(event -> {
            if(!gameOver){
                gameOver = true;
                System.out.println("BLUE WINS");
                winner.setText(n.get(0));
                System.out.println("Winner Name: " + n.get(0));
                winner.setStroke(Color.BLUE);
                winner.relocate(250,250);
            }
            gameOver = true;
        });
    }
    
    public void buildCarNamePath1(){
        setPath(n_t.get(0),namePath1,cName1,v.distanceT(cars.get(0))/cars.get(0).getSpeed());
    }
    
    public ParallelTransition getSimotaneous1(){
        Timeline t1 = new Timeline();
        buildPath1();
        buildCarNamePath1();
        pt1.getChildren().add(p1);
        pt1.getChildren().add(cName1);
        pt1.getChildren().add(t1);
        return pt1;
    }
    
    public void buildPath2(){
        setPath(r2,path2,p2,v.distanceT(cars.get(1))/cars.get(1).getSpeed());
        p2.setOnFinished(event -> {
            if(!gameOver){
                gameOver = true;
                System.out.println("RED WINS");
                winner.setText(n.get(1));
                System.out.println("Winner Name: " + n.get(1));
                winner.setStroke(Color.RED);
                winner.relocate(250, 250);
            }
            gameOver = true;
        });
    }
    
    public void buildCarNamePath2(){
        setPath(n_t.get(1),namePath2,cName2,v.distanceT(cars.get(1))/cars.get(1).getSpeed());
    }
    
    public ParallelTransition getSimotaneous2(){
        Timeline t1 = new Timeline();
        buildPath2();
        buildCarNamePath2();
        pt2.getChildren().add(p2);
        pt2.getChildren().add(cName2);
        pt2.getChildren().add(t1);
        return pt2;
    }
    
    public void buildPath3(){
        setPath(r3, path3, p3, v.distanceT(cars.get(2))/cars.get(2).getSpeed());
        p3.setOnFinished(event -> {
            if(!gameOver){
                gameOver = true;
                System.out.println("BLUEVIOLET WINS");
                winner.setText(n.get(2));
                System.out.println("Winner Name: " + n.get(2));
                winner.setStroke(Color.BLUEVIOLET);
                winner.relocate(250, 250);
            }
            gameOver = true;
        });
    }
    
    public void buildCarNamePath3(){
        setPath(n_t.get(2), namePath3, cName3, v.distanceT(cars.get(2))/cars.get(2).getSpeed());
    }
    
    public ParallelTransition getSimotaneous3(){
        Timeline t = new Timeline();
        buildPath3();
        buildCarNamePath3();
        pt3.getChildren().add(p3);
        pt3.getChildren().add(cName3);
        pt3.getChildren().add(t);
        return pt3;
    }
    
    public Rectangle getCar1(){
        return r1;
    }
    
    public Rectangle getCar2(){
        return r2;
    }
    
    public Rectangle getCar3(){
        return r3;
    }
    
    public Text getCar1Name(){
        return n_t.get(0);
    }
    
    public Text getCar2Name(){
        return n_t.get(1);
    }
    
    public Text getCar3Name(){
        return n_t.get(2);
    }
    
    public Venue getVenue(){
        return v;
    }
    
    public boolean gameOver(){
        return gameOver;
    }
    
    public ParallelTransition getPT1(){
      return pt1;
    }
    
    public ParallelTransition getPT2(){
      return pt2;
    }
    
    public ParallelTransition getPT3(){
      return pt3;
    }
    
    public Text getWinner(){//Returns the winner of the race:)
        return winner;
    }
}
