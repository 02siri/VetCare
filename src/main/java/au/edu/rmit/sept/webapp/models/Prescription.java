package au.edu.rmit.sept.webapp.models;

public class Prescription {

    private String prescriptionId;
    private String patientName;
    private String medication;
    private String dosage;
    private String prescribedBy;
    private String dateIssued;

    // Default constructor
    public Prescription() {
    }

    // Parameterized constructor
    public Prescription(String prescriptionId, String patientName, String medication, String dosage, String prescribedBy, String dateIssued) {
        this.prescriptionId = prescriptionId;
        this.patientName = patientName;
        this.medication = medication;
        this.dosage = dosage;
        this.prescribedBy = prescribedBy;
        this.dateIssued = dateIssued;
    }

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPrescribedBy() {
        return prescribedBy;
    }

    public void setPrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }
}
