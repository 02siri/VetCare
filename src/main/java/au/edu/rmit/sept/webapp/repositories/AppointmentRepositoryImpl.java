package au.edu.rmit.sept.webapp.repositories;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import au.edu.rmit.sept.webapp.models.Appointment;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository{
    private final String TABLE_NAME = "appointments";
    private final DataSource ds;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AppointmentRepositoryImpl(DataSource ds){
        this.ds = ds;
    }


    @Override
    public void setup() throws SQLException {
        try(Connection connection = ds.getConnection();
            Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "apptNum VARCHAR(50),"
                    + "type VARCHAR(50),"
                    + "date DATE,"
                    + "time TIME,"
                    + "clinic VARCHAR(255),"
                    + "doctor VARCHAR(255),"
                    + "createdAt TIMESTAMP,"
                    + "PRIMARY KEY(apptNum))";
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public void insert(Appointment appt){
        if(isTimeSlotBooked(appt.getClinic(),appt.getDoctor(),appt.getDate(),appt.getTime())){
            throw new RuntimeException("Time Slot already booked");
        }
        String sql = "INSERT INTO " + TABLE_NAME+ " (apptNum, type, date, time, clinic, doctor, createdAt) VALUES(?,?,?,?,?,?,?)";

        try(Connection connection = ds.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, appt.getApptNum());
            statement.setString(2,appt.getType());
            statement.setDate(3, Date.valueOf(appt.getDate()));

            String timeStr = appt.getTime().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            LocalTime localTime = LocalTime.parse(timeStr,formatter);

            statement.setTime(4,Time.valueOf(localTime));
            statement.setString(5, appt.getClinic());
            statement.setString(6,appt.getDoctor());
            statement.setTimestamp(7, Timestamp.valueOf(appt.getCreatedAt()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Appointment mapToAppt(ResultSet rs) throws SQLException {
        Appointment appt = new Appointment();
        appt.setApptNum(rs.getString("apptNum"));
        appt.setType(rs.getString("type"));
        appt.setDate(String.valueOf(rs.getDate("date").toLocalDate()));
        appt.setTime(String.valueOf(rs.getTime("time").toLocalTime()));
        appt.setClinic(rs.getString("clinic"));
        appt.setDoctor(rs.getString("doctor"));
        return appt;
    }

    @Override
    public Appointment findApptNo(String apptNo) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE apptNum = ?";
        try(Connection connection = ds.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,apptNo);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                return mapToAppt(rs);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Appointment appt) {
    String sql = "UPDATE " + TABLE_NAME + " SET date = ?, time = ?, clinic = ? , doctor = ? WHERE apptNum = ?";
        try(Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setDate(1, Date.valueOf(appt.getDate()));

            String timeStr = appt.getTime().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            LocalTime localTime = LocalTime.parse(timeStr, formatter);

            statement.setTime(2, Time.valueOf(localTime));
            statement.setString(3, appt.getClinic());
            statement.setString(4,appt.getDoctor());
            statement.setString(5,appt.getApptNum());

            statement.executeUpdate();
        }catch (SQLException e){
            System.err.println("SQL Error " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancel(Appointment appt) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE apptNum = ?";
        try(Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,appt.getApptNum());
            statement.executeUpdate();

        }catch (SQLException e){
            System.err.println("SQL Error " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isTimeSlotBooked(String clinic, String doctor, String date, String time) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE clinic = ? AND doctor = ? AND date = ? AND time =?";
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clinic);
            statement.setString(2, doctor);

            statement.setDate(3, Date.valueOf(date));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
            LocalTime localTime = LocalTime.parse(time.trim(), formatter);

            Time sqlTime = Time.valueOf(localTime);
            statement.setTime(4, sqlTime);


            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean isCancellationLate(Appointment appt) {
        String sql = "SELECT createdAt FROM " + TABLE_NAME + " WHERE apptNum = ?";

        try(Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,appt.getApptNum());
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                    LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                    return LocalDateTime.now().isAfter(createdAt.minusHours(24));
                }
            }
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }



}

