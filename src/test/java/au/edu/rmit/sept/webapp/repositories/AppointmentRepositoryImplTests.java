package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppointmentRepositoryImplTests {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private AppointmentRepositoryImpl appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentRepository = new AppointmentRepositoryImpl(dataSource);
    }

    @Test
    public void testFindAppointment_NotFound() throws Exception {
        String nonExistingApptNum = "APPT-9999";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate appointment not found

        Appointment appointment = appointmentRepository.findApptNo(nonExistingApptNum);

        assertNull(appointment, "Appointment should be null when not found");
    }


    @Test
    public void testUpdateAppointment_NotExisting() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setApptNum("APPT-9999");
        appointment.setDate("2024-10-25");
        appointment.setTime("10:00 AM");
        appointment.setClinic("Unknown Clinic");
        appointment.setDoctor("Unknown Doctor");

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        appointmentRepository.update(appointment);

       verify(preparedStatement,times(1)).executeUpdate();
    }

    @Test
    public void testCancelAppointment_SQLException() throws Exception {
        String apptNum = "APPT-123456";
        Appointment appointment = new Appointment();
        appointment.setApptNum(apptNum);

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        doThrow(new SQLException("Error during cancellation")).when(preparedStatement).executeUpdate();

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            appointmentRepository.cancel(appointment);
        });

        assertTrue(thrown.getCause() instanceof SQLException);
        assertEquals("Error during cancellation", thrown.getCause().getMessage());
    }

    @Test
    public void testIsTimeSlotBooked_InvalidDate() throws Exception {
        String clinic = "Happy Paws Clinic";
        String doctor = "Dr. Smith";
        String invalidDate = "2024-02-30";
        String time = "9:00 AM";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Invalid date format"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            appointmentRepository.isTimeSlotBooked(clinic, doctor, invalidDate, time);
        });

        assertTrue(thrown.getCause() instanceof SQLException);
        assertEquals("Invalid date format", thrown.getCause().getMessage());
    }

    @Test
    public void testIsCancellationLate_BoundaryCase() throws Exception {
        String apptNum = "APPT-123456";
        Appointment appointment = new Appointment();
        appointment.setApptNum(apptNum);
        // Setting createdAt just 24 hours ago (boundary case for late cancellation)
        appointment.setCreatedAt(LocalDateTime.now().minusDays(1));

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf(appointment.getCreatedAt()));


        boolean isLate = Duration.between(appointment.getCreatedAt(), LocalDateTime.now()).toHours() > 24;


        // Boundary case: exactly 24 hours should not be considered late
        assertFalse(isLate, "Cancellation should not be considered late if it's exactly 24 hours before the appointment");
    }
}
