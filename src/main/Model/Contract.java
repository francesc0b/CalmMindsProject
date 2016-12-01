package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 11/30/16.
 */
public class Contract {
    private int contractID;
    private Date dateTerminated;
    private Date dateStarted;

    public Contract(){

    }

    public int getContractID(){
        return contractID;
    }

    public void setContractID(int contractID){
        this.contractID = contractID;
    }

    public Date getDateTerminated(){
        return dateTerminated;
    }

    public void setDateTerminated(Date dateTerminated){
        this.dateTerminated = dateTerminated;
    }

    public Date getDateStarted(){
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted){
        this.dateStarted = dateStarted;
    }

}
