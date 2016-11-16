package main.Model;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Person {
   private int SSN;
    private String fName;
    private String mName;
    private String lName;
    private char sex;
    private String address;
    private String housePhoneNum;
    private String cellPhoneNum;

    public Person(int SSN,String fName,String mName, String lName,char sex, String address, String cellPhoneNum, String housePhoneNum){
        this.SSN = SSN;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
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


    public String getfName(){
        return fName;
    }
    public void setFname(String fName){
        this.fName = fName;
    }


    public String getmName(){
        return mName;
    }
    public void setmName(String mName){
        this.mName = mName;
    }


    public String getlName(){
        return lName;
    }
    public void setlName(String lName){
        this.lName = lName;
    }


    public char getSex(){
        return sex;
    }

    public void setSex(char sex){
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
