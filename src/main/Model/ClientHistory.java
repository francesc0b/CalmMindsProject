package main.Model;

import java.util.Date;

/**
 * Created by blakejoynes on 12/1/16.
 */
public class ClientHistory {

private  Date dateDiagnosed;
private String historyDetails;
private String previousTreatment;
private String treatmentType;
private int SSN;

public ClientHistory(){

}

    public int getSSN(){
        return SSN;
    }
    public void setSSN(int SSN){
        this.SSN = SSN;
    }


    public Date getDateDiagnosed(){

        return dateDiagnosed;
    }

    public void setDateDiagnosed(Date dateDiagnosed){this.dateDiagnosed = dateDiagnosed; }

    public String getHistoryDetails(){
        return historyDetails;
    }

    public void setHistoryDetails(String historyDetails){
        this.historyDetails = historyDetails;
    }

    public String getPreviousTreatment(){
        return previousTreatment;
    }

    public void setPreviousTreatment(String previousTreatment){
        this.previousTreatment = previousTreatment;
    }


    public String getTreatmentType(){
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType){
        this.treatmentType = treatmentType;
    }
}
