/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Joseph Mitchell
 */


public class Animation {
    private Rectangle r1,r2,r3;
    private Path path1,path2,path3;
    private PathTransition p1,p2,p3;
    private Venue v;
    private ArrayList<Integer> x,y;
    
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
    }
    
    public void captureXvalues(){
        x = v.getXvalues();
        for(int i = 0; i < x.size(); i++){
            System.out.println(i + " " + x.get(i));
        }
    }
    
    public void captureYvalues(){
        y = v.getYvalues();
        for(int i = 0; i < y.size(); i++){
            System.out.println(i + " " + y.get(i));
        }
    }
    
    public void moveCars(){
        captureXvalues();
        captureYvalues();
        Random rand = new Random();
        int choice = rand.nextInt(x.size()-1);
        int choice2 = rand.nextInt(x.size()-1);
        int choice3 = rand.nextInt(x.size()-1);
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
        
        p1.setNode(r1);
        p1.setPath(v.getPath1());
        p1.setDuration(Duration.seconds(15));
        p1.setAutoReverse(false);
        p1.setCycleCount(1);
        p1.play();
        
        //Path Transition for car2
        p2.setNode(r2);
        p2.setPath(v.getPath2());
        p2.setDuration(Duration.seconds(20));
        p2.setAutoReverse(false);
        p2.setCycleCount(1);
        p2.play();
        
        //Path Transition for car3
        p3.setNode(r3);
        p3.setPath(v.getPath3());
        p3.setDuration(Duration.seconds(30));
        p3.setAutoReverse(false);
        p3.setCycleCount(1);
        p3.play();
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
