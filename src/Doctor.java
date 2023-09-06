package src;

/**
 * @author Rashmi Alawani
 * @version 5/21
 * Doctor class that deals with giving patients health
 */

public class Doctor {
    private int medicine; //doses remaining after each visit
    public Doctor(int medicine)
    {
        this.medicine = medicine;
    }

    public void seeNextPatient(Patient p)
    {
        int neededAmount = (100-p.getHealth()) / 10;
        if(medicine >=neededAmount){
            p.getBetter(neededAmount*8);
            medicine -= neededAmount;
        }
        else{
            medicine=0;
        }
    }
    public int hasMedicine(){
        return medicine;
    }
}
