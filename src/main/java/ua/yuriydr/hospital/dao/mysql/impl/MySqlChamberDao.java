package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.ChamberDao;
import ua.yuriydr.hospital.model.Chamber;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlChamberDao implements ChamberDao {

    private static final Logger logger = Logger.getLogger(MySqlChamberDao.class);

    private static volatile MySqlChamberDao mySqlChamberDao;

    private static final String FIND_ALL_CHAMBER = "SELECT * FROM hospital.chamber";
    private static final String FIND_CHAMBER_BY_ID = "SELECT * FROM hospital.chamber WHERE id_chamber = ?";
    private static final String UPDATE_CHAMBER = "UPDATE hospital.chamber set max_count = ?, number = ? WHERE id_chamber = ?";
    private static final String DELETE_CHAMBER = "DELETE FROM hospital.chamber WHERE id_chamber = ?";
    private static final String INSERT_CHAMBER = "INSERT INTO hospital.chamber(max_count, number) VALUES(?, ?)";

    private static final int COLUMN_ID_CHAMBER = 1;
    private static final int COLUMN_MAX_COUNT = 2;
    private static final int COLUMN_NUMBER = 3;

    private PreparedStatement statement;
    private Connection connection;

    private MySqlChamberDao() {

    }

    public static MySqlChamberDao getInstance() {
        MySqlChamberDao localInstance = mySqlChamberDao;
        if (localInstance == null) {
            synchronized (MySqlChamberDao.class) {
                localInstance = mySqlChamberDao;
                if (localInstance == null) {
                    mySqlChamberDao = localInstance = new MySqlChamberDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private Chamber createChamber(ResultSet rs) {
        Chamber chamber = new Chamber();
        try {
            chamber.setIdChamber(rs.getLong(COLUMN_ID_CHAMBER));
            chamber.setMaxCount(rs.getLong(COLUMN_MAX_COUNT));
            chamber.setNumber(rs.getLong(COLUMN_NUMBER));
            chamber.setPatients(MySqlPersonDao.getInstance().findAllInSpecificChamber(chamber.getIdChamber()));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return chamber;
    }

    @Override
    public List<Chamber> findAll() {
        logger.debug("Try to find all chambers");

        ResultSet rs = null;
        connection = DatabaseManager.getConnection();
        List<Chamber> chambers = new ArrayList<>();

        try {
            statement = connection.prepareStatement(FIND_ALL_CHAMBER);
            rs = statement.executeQuery();

            while (rs.next()) {
                chambers.add(createChamber(rs));
            }
            logger.debug("All chamber were found " + chambers);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find all chambers: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return chambers;
    }

    @Override
    public Chamber findChamberById(Long id) {
        logger.debug("Try to find chamber by id: " + id);

        Chamber chamber = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_CHAMBER_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                chamber = createChamber(rs);
                logger.debug("Chamber was found successfully " + chamber);
            } else {
                logger.debug("Chamber was not found by this id: " + id);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find chamber by id: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return chamber;
    }

    @Override
    public boolean updateChamber(Chamber chamber) {
        logger.debug("Try to update chamber " + chamber);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(UPDATE_CHAMBER);
            statement.setLong(1, chamber.getMaxCount());
            statement.setLong(2, chamber.getNumber());
            statement.setLong(3, chamber.getIdChamber());

            if (statement.executeUpdate() > 0) {
                logger.debug("Chamber was updated successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update chamber: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Chamber was not updated " + chamber);
        return false;
    }

    @Override
    public boolean deleteChamber(Chamber chamber) {
        logger.debug("Try to delete chamber " + chamber);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(DELETE_CHAMBER);
            statement.setLong(1, chamber.getIdChamber());
            if (statement.executeUpdate() > 0) {
                logger.debug("Chamber was deleted successfully ");
                return true;
            }

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete chamber: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Chamber was not deleted " + chamber);
        return false;
    }

    @Override
    public boolean insertChamber(Chamber chamber) {
        logger.debug("Try to insert new chamber " + chamber);

        ResultSet rs;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(INSERT_CHAMBER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, chamber.getMaxCount());
            statement.setLong(2, chamber.getNumber());

            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    chamber.setIdChamber(rs.getLong(COLUMN_ID_CHAMBER));
                    logger.debug("Chamber added successfully " + chamber);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.debug("SQLException thrown when try to insert new chamber: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Chamber was not inserted " + chamber);
        return false;
    }

}
