package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 12/1/16.
 */
public class Schedule extends Session {
    private int scheduleID;
    private String availability;
    private String violation;
    private String clientName;


    public Schedule(){

    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public String getViolation(){
        return violation;
    }
    
    public void setViolation(String violation){
        this.violation = violation;
    }
    
    public String getClientName(){
        return clientName;
    }
    
    public void setClientName(String clientName){
        this.clientName = clientName;
    }
}
