/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package AnimationPractice;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.PathTransition;
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
    private Path path1,path2,path3;
    private PathTransition p1,p2,p3;
    private Venue v;
    private ArrayList<Integer> x,y;
    private ArrayList<String> s;
    private ArrayList<Text> t;
    
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
        s = new ArrayList<String>();
        t = new ArrayList<Text>();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        p1 = new PathTransition();
        p2 = new PathTransition();
        p3 = new PathTransition();
    }
    
    public Animation(Color c1, Color c2, Color c3){
        v = new Venue();
        r1 = new Rectangle(50,50,15,15);
        r1.setFill(c1);
        r2 = new Rectangle(50,50,15,15);
        r2.setFill(c2);
        r3 = new Rectangle(50,50,15,15);
        r3.setFill(c3);
        x = new ArrayList<Integer>();
        y = new ArrayList<Integer>();
        s = new ArrayList<String>();
        t = new ArrayList<Text>();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();
        p1 = new PathTransition();
        p2 = new PathTransition();
        p3 = new PathTransition();
    }
    
    public void captureXvalues(){
        x = v.getXvalues();
//        for(int i = 0; i < x.size(); i++){
//            System.out.println(i + " " + x.get(i));
//        }
    }
    
    public void captureYvalues(){
        y = v.getYvalues();
//        for(int i = 0; i < y.size(); i++){
//            System.out.println(i + " " + y.get(i));
//        }
    }
    
    public void captureStopNames(){
        s = v.getStopNames();
        for(String s : s){
            System.out.println(s);
        }
    }
    
    public ArrayList<Text> init_StopNames(){
        for(int i = 0; i < s.size(); i++){
            t.add(new Text(s.get(i)));
            t.get(i).relocate(x.get(i), y.get(i));
        }
        System.out.println(t);
        return t;
    }
    
    public void moveCars(){
//        captureXvalues();
//        captureYvalues();
        Random rand = new Random();
        System.out.println(x.size());
        int choice = rand.nextInt(x.size());
        int choice2 = rand.nextInt(x.size());
        int choice3 = rand.nextInt(x.size());
        System.out.println("Choice1: " + choice);
        System.out.println("Choice2: " + choice2);
        System.out.println("Choice3: " + choice3);
        path1.getElements().add(new MoveTo(x.get(choice), y.get(choice)));//Randomize start and loop through
        path2.getElements().add(new MoveTo(x.get(choice2), y.get(choice2)));
        path3.getElements().add(new MoveTo(x.get(choice3), y.get(choice3)));
        if(choice == (x.size()-1)){
            choice = 0;
        }
        int counter = 0;
        for(int i = 0; i < x.size(); i++){
            counter++;
            if(counter != x.size()){
                path1.getElements().add(new LineTo(x.get(i), y.get(i)));
                path2.getElements().add(new LineTo(x.get(i), y.get(i)));
                path3.getElements().add(new LineTo(x.get(i), y.get(i)));
                if(i == x.size()-1){
                    i = 0;
                }
            }else{
                path1.getElements().add(new LineTo(x.get(choice), y.get(choice)));
                path2.getElements().add(new LineTo(x.get(choice2), y.get(choice2)));
                path3.getElements().add(new LineTo(x.get(choice3), y.get(choice3)));
                if(counter == x.size()){
                    break;
                }
            }
        }
//        counter = 1;
//        for(int i = 0; i < x.size(); i++){
//            if(choice2 + i >= x.size()){choice2 = 0;}
//            path2.getElements().add(new LineTo(x.get(choice2+i), y.get(choice2+i)));//This is the next stop after start location
//            if(counter == x.size()){
//                path2.getElements().add(new LineTo(x.get(choice2), y.get(choice2)));
//                break;
//            }
//            counter++;
//        }
//        counter = 1;
//        for(int i = 0; i < x.size(); i++){
//            if(choice3 + i >= x.size()){choice3 = 0;}
//            path3.getElements().add(new LineTo(x.get(choice3+i), y.get(choice3+i)));//This is the next stop after start location
//            if(counter == x.size()){
//                path1.getElements().add(new LineTo(x.get(choice3), y.get(choice3)));
//                break;
//            }
//            counter++;
//        }
    }
    
    public PathTransition getPath1(){
        p1.setNode(r1);
        p1.setPath(path1);
        p1.setDuration(Duration.seconds(20));
        p1.setAutoReverse(false);
        p1.setCycleCount(1);
        p1.setOnFinished(event -> {
        // after transition is finished continue with next one
            System.out.println("Car1 has won!");
        });
        return p1;
    }
    
    public PathTransition getPath2(){
        p2.setNode(r2);
        p2.setPath(path2);
        p2.setDuration(Duration.seconds(5));
        p2.setAutoReverse(false);
        p2.setCycleCount(1);
        p2.setOnFinished(event -> {
        // after transition is finished continue with next one
            System.out.println("Car2 has won!");
        });
        return p2;
    }
    
    public PathTransition getPath3(){
        p3.setNode(r3);
        p3.setPath(path3);
        p3.setDuration(Duration.seconds(15));
        p3.setAutoReverse(false);
        p3.setCycleCount(1);
        p3.setOnFinished(event -> {
        // after transition is finished continue with next one
            System.out.println("Car3 has won!");
        });
        return p3;
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
    
    public Venue getVenue(){
        return v;
    }
}
