package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.MedicalRecord;
import au.edu.rmit.sept.webapp.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.sql.SQLException;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @PostConstruct
    public void initialize() {
        try {
            medicalRecordRepository.setup();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating medical_records table");
        }
    }

    @Override
    public void insertRecord(MedicalRecord record) {
        medicalRecordRepository.insert(record);
    }

    @Override
    public MedicalRecord findRecordByTitle(String title) {
        return medicalRecordRepository.findRecordByTitle(title);
    }

    @Override
    public void updateRecord(MedicalRecord record) {
        medicalRecordRepository.update(record);
    }

    @Override
    public void deleteRecord(MedicalRecord record) {
        medicalRecordRepository.delete(record);
    }
}
