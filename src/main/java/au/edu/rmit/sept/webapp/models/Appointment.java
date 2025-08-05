// src/main/java/au/edu/rmit/sept/webapp/model/Appointment.java
package au.edu.rmit.sept.webapp.models;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Appointment {
   private String type;
   private String date;
   private String time;
   private String clinic;
   private String doctor;
   private String apptNum;
   private LocalDateTime createdAt;

   public Appointment(){
       this.apptNum = createApptNum();
       this.createdAt = LocalDateTime.now();
   }
   public void setType(String type){
       this.type = type;
    }

    public String getType(){
       return type;
    }

    public void setDate(String date){
       this.date = date;
    }

    public String getDate(){
       return date;
    }

    public void setTime(String time){
       this.time = time;
    }

    public String getTime(){
       return time;
    }

    public void setClinic(String clinic){
       this.clinic = clinic;
    }

    public String getClinic(){
       return clinic;
    }

    public void setDoctor(String doctor){
       this.doctor = doctor;
    }

    public String getDoctor(){
       return doctor;
    }

    public String createApptNum(){
       Random random = new Random();
       int num = random.nextInt(90000)+100000;
       return "APPT-"+num;
    }

    public String getApptNum() {
        return apptNum;
    }

    public LocalDateTime getCreatedAt(){
       return createdAt;
    }

    public void setApptNum(String apptNum){
       this.apptNum = apptNum;
    }

    public void setCreatedAt(LocalDateTime createdAt){
       this.createdAt = createdAt;
    }

}
