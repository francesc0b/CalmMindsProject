package main.Model;

/**
 * Created by blakejoynes on 11/16/16.
 */
public class Client extends Person {
     public Client(){

     }

    public Client(int SSN, String fName, String mName, String lName, String sex, String address, String cellPhoneNum, String housePhoneNum) {
        super(SSN, fName, mName, lName, sex, address, cellPhoneNum, housePhoneNum);
    }

}
