package main.Model;

import java.sql.Date;

/**
 * Created by blakejoynes on 12/6/16.
 */
public class Session {
    private int sessionID;
    private String sessionType;
    private String therapyType;
    private String leadCounselorName;
   
    private Date sessionDate;


    public Session(){

    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getTherapyType() {
        return therapyType;
    }

    public void setTherapyType(String therapyType) {
        this.therapyType = therapyType;
    }

    public String getLeadCounselorName() {
        return leadCounselorName;
    }

    public void setLeadCounselorName(String leadCounselorName) {
        this.leadCounselorName = leadCounselorName;
    }


    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }


}
