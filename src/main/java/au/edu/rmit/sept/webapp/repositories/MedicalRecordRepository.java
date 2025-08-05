package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.MedicalRecord;

import java.sql.SQLException;

public interface MedicalRecordRepository {
    void setup() throws SQLException;
    void insert(MedicalRecord record);
    MedicalRecord findRecordByTitle(String title);
    void update(MedicalRecord record);
    void delete(MedicalRecord record);
}
