package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Rashmi Alawani
 * @version 5/21
 * Hospital class that deals with Patient circular buffer and printing methods
 */

public class Hospital {
    final int initialMedicine=150;
    private int patientCured;
    private int patientDeceased;
    private Patient room[];
    private ArrayList<String> list;
    private Doctor doctor;

    public static void main(String[] args)throws FileNotFoundException {
        Hospital h = new Hospital();
    }

    public Hospital() throws FileNotFoundException
    {

        //give doctor initial amount of medicine and set all variables to 0
        doctor = new Doctor(initialMedicine);
        patientCured = 0;
        patientDeceased = 0;
        room = new Patient[10]; //10 patients at any given time
        Scanner scan = new Scanner(new File("patient.txt"));
        System.out.println("Welcome to Wokanda Hospital");
        System.out.println("Health of Current Patients:");

        list = new ArrayList<>();
        while (scan.hasNextLine()){
            list.add(scan.nextLine());
        }


        int count=0;
        populateRooms();

        Scanner scan2 = new Scanner(System.in);

        String input = "";
        while(input.equals("")){
            while (doctor.hasMedicine()>0){
                int leastHealth = room[0].getHealth();
                String leastName=room[0].getName();
                int leastRoom = room[0].getRoom();
                int r=0;
                for (int i= 0; i < room.length; i++) {
                    //find out which patient is in worst condition
                    if (room[i].getHealth()< leastHealth) {
                        leastHealth = room[i].getHealth();
                        leastName=room[i].getName();
                        leastRoom = room[i].getRoom();
                        r = i;
                    }
                }
                if (count==0){
                    displayStatus(leastRoom);
                }
                else if (count==1){
                    doctor.seeNextPatient(room[r]);
                    decrementHealth(r);
                    checkOnPatients();
                    displayStatus(leastRoom);
                }
                else{
                    displayStatus(leastRoom);
                    doctor.seeNextPatient(room[r]);
                    decrementHealth(r);
                    checkOnPatients();
                }

                if (input.equalsIgnoreCase("q")){
                    System.out.println("Doctor has quit! Here is the exit status: ");
                    printExitStatus();
                    break;
                }
                count++;
                input = scan2.nextLine();
            }

            if (doctor.hasMedicine()<=0){
                printExitStatus();
                break;
            }
        }

    }

    public void displayStatus(int roomNumber){
        int dosesLeft = doctor.hasMedicine();
        System.out.println("The patient in the following room needs medicine: " + roomNumber);
        System.out.println("Current patient status before doctor gives medicine to current room: ");
        System.out.printf("%20s %15s %10s", "Patient", "Age", "Health");
        System.out.println(" ");
        for (int i = 0; i<room.length; i++){
            System.out.printf("%20s %15s %10s", room[i].getName(), room[i].getAge(), room[i].getHealth() + "\n");
        }

        System.out.println("Currently in room: " + roomNumber + " with " + dosesLeft + " doeses left.");
        System.out.println("Press Enter to continue, Q to quit");
    }

    /**
     * different exit status depending on if you quit halfway or if you actually lose the game
     */
    public void printExitStatus(){
        if (doctor.hasMedicine()>0){
            System.out.println("Our health status:");
            System.out.println("Patients cured: " + patientCured);
            System.out.println("Patients deceased: "  + patientDeceased);
        }
        if (doctor.hasMedicine()<=0){
            System.out.println("No medicine left, all remaining patients dead.");
            System.out.println("Patients cured: " + patientCured);
            System.out.println("Patients deceased: "  + (patientDeceased+10));

        }
    }

    /**
     * decrease helath of patients that arent visited yet
     * @param index
     */
    public void decrementHealth(int index){
        for (int i=0; i<room.length; i++){
            if (i !=index){
                room[i].getWorse();
            }
        }
    }

    /**
     * fills array by reading the next people from the list
     * @throws FileNotFoundException
     */
    public void populateRooms() throws FileNotFoundException{ //only for the first 10
        for (int i=0; i<10; i++){
            room[i] = new Patient(list.get(i), 100+i);
        }
        int count = 0; //need count to keep track of how many you remove

        /**
         * I used an iterator that i learned in oop to remove the patients
         * https://www.w3schools.com/java/java_iterator.asp
         */

        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String string = it.next();
            if (count>=0 && count<10){
                it.remove();
            }
            count++;
        }

    }

    public void checkOnPatients() throws FileNotFoundException
    {
        boolean checkCures = false;
        System.out.println("Hospital departures:");

        for (int i=0; i<room.length; i++) {
            if (room[i].getHealth()>=80) {
                System.out.println(room[i].getName() + " has been cured!");
                room[i] = new Patient(list.get(0),100+i);
                list.remove(0);
                patientCured++;
                checkCures = true;
            }
            if (room[i].getHealth()<=0) {
                System.out.println(room[i].getName() + " has died.");
                //populateRooms();
                room[i] = new Patient(list.get(0),100+i);
                list.remove(0);

                patientDeceased++;
                checkCures = true;
            }
        }
        if (checkCures == false){
            System.out.println("No one departed the hospital");
        }
    }


}
