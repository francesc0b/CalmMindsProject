package main.Model;

import java.util.Date;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Client extends Person {
    private String insuranceName;
    private int insPolicyNum;
    private Date dateDiagnosed;;
    private String historyDetails;
    private String previousTreatment;
    private String treatmentType;

    public Client(){

     }

    public Client(int SSN, String fName, String mName, String lName, String sex, String address, String cellPhoneNum, String housePhoneNum) {
        super(SSN, fName, mName, lName, sex, address, cellPhoneNum, housePhoneNum);
    }

    public void setInsName(String insuranceName){
        this.insuranceName = insuranceName;
    }

    public String getInsName(){
        return insuranceName;
    }

    public void setInsPolicyNum(int insPolicyNum){
        this.insPolicyNum = insPolicyNum;
    }

    public int getInsPolicyNum(){
        return insPolicyNum;
    }


}
