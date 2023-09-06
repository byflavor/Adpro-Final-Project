package src;

import java.util.*;
/**
 * @author Rashmi Alawani
 * @version 5/21
 * Patient class that deals with an individual patient's health
 */

public class Patient {
    private int health; //from 1-50
    private int age; //between 10 and 90
    private int room;
    private String name;
    private Random rng;
    public Patient(String name, int room)
    {
        this.name=name;
        this.room=room;
        rng = new Random();
        health = rng.nextInt(50 - 1 + 1) + 1; //1-50
        age = rng.nextInt(90 - 10 + 1) + 10; //10-90
    }

    /**
     * Patient is cured and can leave if health >=80
     */
    public boolean cured()
    {
        if (health>=80){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Patient is dead and gives room to new patient if health<=0
     */
    public boolean died()
    {
        if (health<=0){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * Return name of patient
     */
    public String getName(){
        return name;
    }
    /**
     * Set patient name
     */
    public void setName(String s){
        name = s;
    }
    /**
     * Return age of patient
     */
    public int getAge(){
        return age;
    }
    /**
     * Set age to new age
     */
    public void setAge(int age){
        this.age=age;
    }
    /**
     * Return health of patient
     */
    public int getHealth(){
        return health;
    }
    /**
     * Set health to new health
     */
    public void setHealth(int health){
        this.health=health;
    }
    /**
     * Return room of patient
     */
    public int getRoom(){
        return room;
    }
    /**
     * Decrease health by 5
     */
    public void getWorse(){
        health -=5;
    }
    /**
     * Increase health by a random number
     */
    public void getBetter(int dosage){
        health +=dosage;
    }
}


