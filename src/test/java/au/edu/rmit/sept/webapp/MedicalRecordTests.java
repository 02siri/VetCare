package au.edu.rmit.sept.webapp;

import au.edu.rmit.sept.webapp.models.MedicalRecord;
import au.edu.rmit.sept.webapp.repositories.MedicalRecordRepository;
import au.edu.rmit.sept.webapp.services.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordServiceImplTests {

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitialize_SuccessfulSetup() throws SQLException {
        // No exception should be thrown if setup is successful
        doNothing().when(medicalRecordRepository).setup();
        
        assertDoesNotThrow(() -> medicalRecordService.initialize());

        verify(medicalRecordRepository, times(1)).setup();
    }

    @Test
    void testInitialize_FailedSetup() throws SQLException {
        // Simulate SQLException during setup
        doThrow(new SQLException()).when(medicalRecordRepository).setup();

        Exception exception = assertThrows(RuntimeException.class, () -> medicalRecordService.initialize());
        assertEquals("Error creating medical_records table", exception.getMessage());

        verify(medicalRecordRepository, times(1)).setup();
    }



    @Test
    void testFindRecordByTitle_RecordNotFound() {
        String title = "Nonexistent Record";

        when(medicalRecordRepository.findRecordByTitle(title)).thenReturn(null);

        MedicalRecord result = medicalRecordService.findRecordByTitle(title);

        assertNull(result);
        verify(medicalRecordRepository, times(1)).findRecordByTitle(title);
    }


}
