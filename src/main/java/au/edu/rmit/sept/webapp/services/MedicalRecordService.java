package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.MedicalRecord;

public interface MedicalRecordService {
    void insertRecord(MedicalRecord record);
    MedicalRecord findRecordByTitle(String title);
    void updateRecord(MedicalRecord record);
    void deleteRecord(MedicalRecord record);
}
