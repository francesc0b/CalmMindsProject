package main.Model;

import java.util.Date;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Counseler extends Person {

    private int clientID;
    private Date hireDate;
    private int yearsOfExp;

    public Counseler(int SSN, String fName, String mName, String lName, String sex, String address, String cellPhoneNum, String housePhoneNum) {
        super(SSN, fName, mName, lName, sex, address, cellPhoneNum, housePhoneNum);
    }

    public int getClientID(){
        return clientID;
    }

    public void setClientID(int clientID){
        this.clientID = clientID;
    }

    public Date getHireDate(){
        return hireDate;
    }

    public void setHireDate(Date hireDate){
        this.hireDate = hireDate;
    }

    public int getYearsOfExp(){
        return yearsOfExp;
    }

    public void setYearsOfExp(int yearsOfExp){
        this.yearsOfExp = yearsOfExp;
    }
}
