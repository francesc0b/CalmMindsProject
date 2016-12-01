package main.Model;

import java.util.Date;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Counselor extends Person {

    private int counselorID;
    private Date hireDate;
    private int yearsOfExp;

    public Counselor(){

    }

    public Counselor(int SSN, String fName, String mName, String lName, String sex, String address, String cellPhoneNum, String housePhoneNum) {
        super(SSN, fName, mName, lName, sex, address, cellPhoneNum, housePhoneNum);
    }

    public int getCID(){
        return counselorID;
    }

    public void setCID(int counselorID){
        this.counselorID = counselorID;
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
