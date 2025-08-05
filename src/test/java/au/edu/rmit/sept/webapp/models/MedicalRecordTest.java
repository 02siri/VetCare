package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MedicalRecordTest {

    @Test
    
    // Test case 1: Test handling missing mandatory fields (e.g., title, health condition)
    public void testMissingMandatoryFields() {
        // Create a medical record with missing (empty string) mandatory fields
        MedicalRecord record = new MedicalRecord("", "", "Dr. Smith", "No issues", "Rabies", "2023-01-01", "2024-01-01");
    
        // Assert that the mandatory fields are empty
        assertEquals("", record.getTitle());
        assertEquals("", record.getHealthCondition());
    
    }
    
    @Test
    // Test case 2: Test handling null values
    public void testNullValuesInMedicalRecord() {
        // Create a medical record with null values
        MedicalRecord record = new MedicalRecord(null, null, null, null, null, null, null);

        // Assert that all values are null
        assertEquals(null, record.getTitle());
        assertEquals(null, record.getHealthCondition());
        assertEquals(null, record.getVeterinarian());
        assertEquals(null, record.getNotes());
        assertEquals(null, record.getVaccinationType());
        assertEquals(null, record.getVaccinationDate());
        assertEquals(null, record.getDueDate());

        // Set new null values and check again
        record.setTitle(null);
        record.setHealthCondition(null);
        record.setVeterinarian(null);
        record.setNotes(null);
        record.setVaccinationType(null);
        record.setVaccinationDate(null);
        record.setDueDate(null);

        // Assert again that all values are null
        assertEquals(null, record.getTitle());
        assertEquals(null, record.getHealthCondition());
        assertEquals(null, record.getVeterinarian());
        assertEquals(null, record.getNotes());
        assertEquals(null, record.getVaccinationType());
        assertEquals(null, record.getVaccinationDate());
        assertEquals(null, record.getDueDate());
    }
    @Test
    // Test case 3: Check the behavior when due date is before vaccination date (without validation)
    public void testDueDateBeforeVaccinationDate() {
        // Create a medical record where the due date is before the vaccination date
        MedicalRecord record = new MedicalRecord("Record 1", "Healthy", "Dr. Smith", "No issues", "Rabies", "2023-01-01", "2022-12-01");
    
        // Since we are not expecting an exception, just assert the values are set correctly
        assertEquals("2023-01-01", record.getVaccinationDate()); // Vaccination date
        assertEquals("2022-12-01", record.getDueDate());         // Due date (even if it is illogical)
    }
        
    @Test
    // Test case 4: Test creation of a medical record with valid inputs
    public void testMedicalRecordCreation() {
        // Create a medical record with valid inputs
        MedicalRecord record = new MedicalRecord("Record 1", "Healthy", "Dr. Smith", "No issues", "Rabies", "2023-01-01", "2024-01-01");

        // Assert that the values are correctly set
        assertEquals("Record 1", record.getTitle());
        assertEquals("Healthy", record.getHealthCondition());
        assertEquals("Dr. Smith", record.getVeterinarian());
        assertEquals("No issues", record.getNotes());
        assertEquals("Rabies", record.getVaccinationType());
        assertEquals("2023-01-01", record.getVaccinationDate());
        assertEquals("2024-01-01", record.getDueDate());
    }
}
