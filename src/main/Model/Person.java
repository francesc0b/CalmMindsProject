package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Person {
   private int SSN;
    private String firstName;
    private String middleInit;
    private String lastName;
    private String sex;
    private String address;
    private String housePhoneNum;
    private String cellPhoneNum;
    private Date dateOfBirth;

    Person(){

    }

    public Person(int SSN,String fName,String middleInit, String lName,String sex, String address, String cellPhoneNum, String housePhoneNum){
        this.SSN = SSN;
        this.firstName = fName;
        this.middleInit = middleInit;
        this.lastName = lName;
        this.sex = sex;
        this.address = address;
        this.cellPhoneNum = cellPhoneNum;
        this.housePhoneNum = housePhoneNum;
    }


    public int getSSN(){
        return SSN;
    }
    public void setSSN(int SSN){
        this.SSN = SSN;
    }


    public Date getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }


    public String getMiddleInit(){
        return middleInit;
    }
    public void setMiddleInit(String middleInit){
        this.middleInit = middleInit;
    }


    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }


    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex = sex;
    }


    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }


    public String getCellPhoneNum(){
        return cellPhoneNum;
    }

    public void setCellPhoneNum(String cellPhoneNum){
        this.cellPhoneNum = cellPhoneNum;
    }


    public String getHousePhoneNum(){
        return housePhoneNum;
    }

    public void setHousePhoneNum(String housePhoneNum){
        this.housePhoneNum = housePhoneNum;
    }


}
