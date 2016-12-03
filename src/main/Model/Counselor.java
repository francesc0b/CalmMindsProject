package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Counselor extends Person {

    private int counselorID;
    private Date hireDate;
    private int yearsOfExp;
private String degreeType;
private String degreeLevel;
private String specialization;
private String availability;
    public Counselor(){

    }

    public Counselor(int SSN, String fName, String mName, String lName, String sex, String address, String cellPhoneNum, String housePhoneNum) {
        super(SSN, fName, mName, lName, sex, address, cellPhoneNum, housePhoneNum);
    }

    public int getCounselorID(){
        return counselorID;
    }

    public void setCounselorID(int counselorID){
        this.counselorID = counselorID;
    }

    public String getDegreeType(){
        return degreeType;
    }

    public void setDegreeType(String degreeType){
        this.degreeType = degreeType;
    }
    public String getDegreeLevel(){
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel){
        this.degreeLevel = degreeLevel;
    }
    public String getSpecialization(){
        return specialization;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public String getAvailability(){
        return availability;
    }

    public void setAvailability(String availability){
        this.availability = availability;
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
