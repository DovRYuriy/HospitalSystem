package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.DiagnosisDao;
import ua.yuriydr.hospital.model.Diagnosis;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDiagnosisDao implements DiagnosisDao {

    private static final Logger logger = Logger.getLogger(MySqlDiagnosisDao.class);

    private static volatile MySqlDiagnosisDao mySqlDiagnosisDao;

    private static final String FIND_ALL_DIAGNOSIS = "SELECT * FROM hospital.diagnosis";
    private static final String FIND_DIAGNOSIS_BY_ID = "SELECT * FROM hospital.diagnosis WHERE id_diagnosis = ?";
    private static final String UPDATE_DIAGNOSIS = "UPDATE hospital.diagnosis set description = ? WHERE id_diagnosis = ?";
    private static final String DELETE_DIAGNOSIS = "DELETE FROM hospital.diagnosis WHERE id_diagnosis = ?";
    private static final String INSERT_DIAGNOSIS = "INSERT INTO hospital.diagnosis(diagnosis_name, description) VALUES(?, ?)";

    private static final int COLUMN_ID_DIAGNOSIS = 1;
    private static final int COLUMN_DIAGNOSIS_NAME = 2;
    private static final int COLUMN_DESCRIPTION = 3;

    private PreparedStatement statement;
    private Connection connection;

    private MySqlDiagnosisDao() {

    }

    public static MySqlDiagnosisDao getInstance() {
        MySqlDiagnosisDao localInstance = mySqlDiagnosisDao;
        if (localInstance == null) {
            synchronized (MySqlDiagnosisDao.class) {
                localInstance = mySqlDiagnosisDao;
                if (localInstance == null) {
                    mySqlDiagnosisDao = localInstance = new MySqlDiagnosisDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private Diagnosis createDiagnosis(ResultSet rs) {
        Diagnosis diagnosis = new Diagnosis();
        try {
            diagnosis.setIdDiagnosis(rs.getLong(COLUMN_ID_DIAGNOSIS));
            diagnosis.setName(rs.getString(COLUMN_DIAGNOSIS_NAME));
            diagnosis.setDescription(rs.getString(COLUMN_DESCRIPTION));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return diagnosis;
    }

    @Override
    public List<Diagnosis> findAll() {
        logger.debug("Try to get all diagnosis");

        ResultSet rs = null;
        connection = DatabaseManager.getConnection();
        List<Diagnosis> allDiagnosis = new ArrayList<>();

        try {
            statement = connection.prepareStatement(FIND_ALL_DIAGNOSIS);
            rs = statement.executeQuery();
            while (rs.next()) {
                allDiagnosis.add(createDiagnosis(rs));
            }
            logger.debug("Diagnoses were found successfully " + allDiagnosis);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to get all diagnoses: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return allDiagnosis;
    }

    public Diagnosis findDiagnosisById(Long id) {
        logger.debug("Try to find diagnosis by id: " + id);

        Diagnosis diagnosis = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_DIAGNOSIS_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                diagnosis = createDiagnosis(rs);
                logger.debug("Diagnosis was found successfully " + diagnosis);
            } else {
                logger.debug("Diagnosis was not found by this id: " + id);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find diagnosis by id: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return diagnosis;
    }

    @Override
    public boolean updateDiagnosis(Diagnosis diagnosis) {
        logger.debug("Try to update diagnosis " + diagnosis);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_DIAGNOSIS);
            statement.setString(1, diagnosis.getDescription());
            statement.setLong(2, diagnosis.getIdDiagnosis());

            if (statement.executeUpdate() > 0) {
                logger.debug("Diagnosis was updated successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update diagnosis: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Diagnosis was not updated " + diagnosis);
        return false;
    }

    @Override
    public boolean deleteDiagnosis(Diagnosis diagnosis) {
        logger.debug("Try to delete diagnosis " + diagnosis);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_DIAGNOSIS);
            statement.setLong(1, diagnosis.getIdDiagnosis());
            if (statement.executeUpdate() > 0) {
                logger.debug("Diagnosis was deleted successfully ");
                connection.commit();
                return true;
            }

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete diagnosis: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Diagnosis was not deleted " + diagnosis);
        return false;
    }

    @Override
    public boolean insertDiagnosis(Diagnosis diagnosis) {
        logger.debug("Try to insert new diagnosis " + diagnosis);

        ResultSet rs;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_DIAGNOSIS, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, diagnosis.getName());
            statement.setString(2, diagnosis.getDescription());

            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    diagnosis.setIdDiagnosis(rs.getLong(COLUMN_ID_DIAGNOSIS));
                    logger.debug("Diagnosis added successfully " + diagnosis);
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.debug("SQLException thrown when try to insert new diagnosis: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Diagnosis was not inserted " + diagnosis);
        return false;
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("rollback error");
        }
    }
}
