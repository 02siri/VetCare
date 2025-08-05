package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordRepositoryImplTests {

    private MedicalRecordRepositoryImpl medicalRecordRepository;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        medicalRecordRepository = new MedicalRecordRepositoryImpl(dataSource);
    }

    @Test
    void testSetup() throws Exception {
        // Arrange
        when(connection.createStatement()).thenReturn(statement);

        // Act
        medicalRecordRepository.setup();

        // Assert
        verify(statement, times(1)).executeUpdate(anyString());
        verify(connection, times(1)).close();
    }

    @Test
    void testInsert() throws Exception {
        // Arrange
        MedicalRecord record = new MedicalRecord(
                "Test Title", "Healthy", "Dr. Smith", "Some notes", "Rabies", "2024-08-17", "2024-12-16");
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Act
        medicalRecordRepository.insert(record);

        // Assert
        verify(preparedStatement, times(1)).setString(1, "Test Title");
        verify(preparedStatement, times(1)).setString(2, "Healthy");
        verify(preparedStatement, times(1)).setString(3, "Dr. Smith");
        verify(preparedStatement, times(1)).setString(4, "Some notes");
        verify(preparedStatement, times(1)).setString(5, "Rabies");
        verify(preparedStatement, times(1)).setDate(6, Date.valueOf("2024-08-17"));
        verify(preparedStatement, times(1)).setDate(7, Date.valueOf("2024-12-16"));
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).close();
    }

    @Test
    void testFindRecordByTitle_RecordFound() throws Exception {
        // Arrange
        String title = "Test Title";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("title")).thenReturn(title);
        when(resultSet.getString("healthCondition")).thenReturn("Healthy");
        when(resultSet.getString("veterinarian")).thenReturn("Dr. Smith");
        when(resultSet.getString("notes")).thenReturn("Some notes");
        when(resultSet.getString("vaccinationType")).thenReturn("Rabies");
        when(resultSet.getString("vaccinationDate")).thenReturn("2024-08-17");
        when(resultSet.getString("dueDate")).thenReturn("2024-12-16");

        // Act
        MedicalRecord result = medicalRecordRepository.findRecordByTitle(title);

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        assertEquals("Healthy", result.getHealthCondition());
        assertEquals("Dr. Smith", result.getVeterinarian());
        verify(connection, times(1)).close();
    }

    @Test
    void testFindRecordByTitle_RecordNotFound() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no result found

        // Act
        MedicalRecord result = medicalRecordRepository.findRecordByTitle("Nonexistent Title");

        // Assert
        assertNull(result);
        verify(connection, times(1)).close();
    }

    @Test
    void testUpdate() throws Exception {
        // Arrange
        MedicalRecord record = new MedicalRecord(
                "Test Title", "Sick", "Dr. Adams", "Updated notes", "Flu", "2024-09-01", "2024-12-31");
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Act
        medicalRecordRepository.update(record);

        // Assert
        verify(preparedStatement, times(1)).setString(1, "Sick");
        verify(preparedStatement, times(1)).setString(2, "Dr. Adams");
        verify(preparedStatement, times(1)).setString(3, "Updated notes");
        verify(preparedStatement, times(1)).setString(4, "Flu");
        verify(preparedStatement, times(1)).setDate(5, Date.valueOf("2024-09-01"));
        verify(preparedStatement, times(1)).setDate(6, Date.valueOf("2024-12-31"));
        verify(preparedStatement, times(1)).setString(7, "Test Title");
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).close();
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        MedicalRecord record = new MedicalRecord(
                "Test Title", "Healthy", "Dr. Smith", "Some notes", "Rabies", "2024-08-17", "2024-12-16");
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Act
        medicalRecordRepository.delete(record);

        // Assert
        verify(preparedStatement, times(1)).setString(1, "Test Title");
        verify(preparedStatement, times(1)).executeUpdate();
        verify(connection, times(1)).close();
    }
}
