package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.MedicalRecord;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {
    private final String TABLE_NAME = "medical_records";
    private final DataSource ds;

    public MedicalRecordRepositoryImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void setup() throws SQLException {
        try (Connection connection = ds.getConnection();
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "title VARCHAR(255),"
                    + "healthCondition VARCHAR(255),"
                    + "veterinarian VARCHAR(255),"
                    + "notes TEXT,"
                    + "vaccinationType VARCHAR(255),"
                    + "vaccinationDate DATE,"
                    + "dueDate DATE,"
                    + "PRIMARY KEY (title))";
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public void insert(MedicalRecord record) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, healthCondition, veterinarian, notes, vaccinationType, vaccinationDate, dueDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, record.getTitle());
            statement.setString(2, record.getHealthCondition());
            statement.setString(3, record.getVeterinarian());
            statement.setString(4, record.getNotes());
            statement.setString(5, record.getVaccinationType());
            statement.setDate(6, Date.valueOf(record.getVaccinationDate()));
            statement.setDate(7, Date.valueOf(record.getDueDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MedicalRecord findRecordByTitle(String title) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE title = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapToMedicalRecord(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private MedicalRecord mapToMedicalRecord(ResultSet rs) throws SQLException {
        return new MedicalRecord(
                rs.getString("title"),
                rs.getString("healthCondition"),
                rs.getString("veterinarian"),
                rs.getString("notes"),
                rs.getString("vaccinationType"),
                rs.getString("vaccinationDate"),
                rs.getString("dueDate")
        );
    }

    @Override
    public void update(MedicalRecord record) {
        String sql = "UPDATE " + TABLE_NAME + " SET healthCondition = ?, veterinarian = ?, notes = ?, vaccinationType = ?, vaccinationDate = ?, dueDate = ? WHERE title = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, record.getHealthCondition());
            statement.setString(2, record.getVeterinarian());
            statement.setString(3, record.getNotes());
            statement.setString(4, record.getVaccinationType());
            statement.setDate(5, Date.valueOf(record.getVaccinationDate()));
            statement.setDate(6, Date.valueOf(record.getDueDate()));
            statement.setString(7, record.getTitle());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MedicalRecord record) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE title = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, record.getTitle());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
