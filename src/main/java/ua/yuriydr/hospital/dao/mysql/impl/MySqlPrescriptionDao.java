package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.PrescriptionDao;
import ua.yuriydr.hospital.entity.Prescription;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPrescriptionDao implements PrescriptionDao {

    private static final Logger logger = Logger.getLogger(MySqlPrescriptionDao.class);

    private static volatile MySqlPrescriptionDao mySqlPrescriptionDao;

    private static final String FIND_ALL_PRESCRIPTIONS = "SELECT * FROM hospital.prescription";
    private static final String FIND_PRESCRIPTION_BY_ID = "SELECT * FROM hospital.prescription WHERE id_prescription = ?";
    private static final String UPDATE_PRESCRIPTION = "UPDATE hospital.prescription SET drugs = ?, `procedure` = ?, operation = ? WHERE id_prescription = ?";
    private static final String DELETE_PRESCRIPTION = "DELETE FROM hospital.prescription WHERE id_prescription = ?";
    private static final String INSERT_PRESCRIPTION = "INSERT INTO hospital.prescription(drugs, `procedure`, operation) VALUES(?, ?, ?)";

    private static final int COLUMN_ID_PRESCRIPTION = 1;
    private static final int COLUMN_DRUGS = 2;
    private static final int COLUMN_PROCEDURE = 3;
    private static final int COLUMN_OPERATION = 4;

    private PreparedStatement statement;
    private Connection connection;

    private MySqlPrescriptionDao() {

    }

    public static MySqlPrescriptionDao getInstance() {
        MySqlPrescriptionDao localInstance = mySqlPrescriptionDao;
        if (localInstance == null) {
            synchronized (MySqlPrescriptionDao.class) {
                localInstance = mySqlPrescriptionDao;
                if (localInstance == null) {
                    mySqlPrescriptionDao = localInstance = new MySqlPrescriptionDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private Prescription createPrescription(ResultSet rs) {
        Prescription prescription = new Prescription();
        try {
            prescription.setIdPrescription(rs.getLong(COLUMN_ID_PRESCRIPTION));
            prescription.setDrugs(rs.getString(COLUMN_DRUGS));
            prescription.setProcedure(rs.getString(COLUMN_PROCEDURE));
            prescription.setOperation(rs.getString(COLUMN_OPERATION));
        } catch (SQLException e) {
            logger.error("Prescription entity create error");
        }
        return prescription;
    }

    @Override
    public List<Prescription> findAll() {
        logger.debug("Try to get all prescriptions");

        ResultSet rs = null;
        connection = DatabaseManager.getConnection();
        List<Prescription> prescriptions = new ArrayList<>();

        try {
            statement = connection.prepareStatement(FIND_ALL_PRESCRIPTIONS);
            rs = statement.executeQuery();
            while (rs.next()) {
                prescriptions.add(createPrescription(rs));
            }
            logger.debug("Prescriptions were found successfully " + prescriptions);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to get all prescriptions: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return prescriptions;
    }

    @Override
    public Prescription findPrescriptionById(Long id) {
        logger.debug("Try to find prescription by id : " + id);

        Prescription prescription = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_PRESCRIPTION_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                prescription = createPrescription(rs);
                logger.debug("Prescription was found successfully " + prescription);
            } else {
                logger.debug("Prescription was not found by this id: " + id);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find prescription by id: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return prescription;
    }

    @Override
    public boolean updatePrescription(Prescription prescription) {
        logger.debug("Try to update prescription " + prescription);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PRESCRIPTION);
            statement.setString(1, prescription.getDrugs());
            statement.setString(2, prescription.getProcedure());
            statement.setString(3, prescription.getOperation());
            statement.setLong(4, prescription.getIdPrescription());
            if (statement.executeUpdate() > 0) {
                logger.debug("Prescription was updated successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update prescription: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }
        logger.debug("Prescription was not updated " + prescription);
        return false;
    }

    @Override
    public boolean deletePrescription(Prescription prescription) {
        logger.debug("Try to delete prescription " + prescription);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_PRESCRIPTION);
            statement.setLong(1, prescription.getIdPrescription());
            if (statement.executeUpdate() > 0) {
                logger.debug("Prescription was deleted successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete prescription: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }
        logger.debug("Prescription was not deleted " + prescription);
        return false;
    }

    @Override
    public boolean insertPrescription(Prescription prescription) {
        logger.debug("Try to insert new prescription " + prescription);

        statement = null;
        connection = DatabaseManager.getConnection();
        ResultSet rs;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_PRESCRIPTION, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, prescription.getDrugs());
            statement.setString(2, prescription.getProcedure());
            statement.setString(3, prescription.getOperation());
            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    prescription.setIdPrescription(rs.getLong(COLUMN_ID_PRESCRIPTION));
                    logger.debug("Prescription added successfully " + prescription);
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to insert prescription: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Prescription was not inserted " + prescription);
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
