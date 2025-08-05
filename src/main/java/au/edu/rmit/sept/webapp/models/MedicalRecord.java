package au.edu.rmit.sept.webapp.models;

public class MedicalRecord {

    private String title;
    private String healthCondition;
    private String veterinarian;
    private String notes;
    private String vaccinationType;
    private String vaccinationDate;
    private String dueDate;

    // Constructor
    public MedicalRecord(String title, String healthCondition, String veterinarian, String notes,
                         String vaccinationType, String vaccinationDate, String dueDate) {
        this.title = title;
        this.healthCondition = healthCondition;
        this.veterinarian = veterinarian;
        this.notes = notes;
        this.vaccinationType = vaccinationType;
        this.vaccinationDate = vaccinationDate;
        this.dueDate = dueDate;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getHealthCondition() { return healthCondition; }
    public void setHealthCondition(String healthCondition) { this.healthCondition = healthCondition; }
    public String getVeterinarian() { return veterinarian; }
    public void setVeterinarian(String veterinarian) { this.veterinarian = veterinarian; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getVaccinationType() { return vaccinationType; }
    public void setVaccinationType(String vaccinationType) { this.vaccinationType = vaccinationType; }
    public String getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(String vaccinationDate) { this.vaccinationDate = vaccinationDate; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}