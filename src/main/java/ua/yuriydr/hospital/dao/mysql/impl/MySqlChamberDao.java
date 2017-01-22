package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.ChamberDao;
import ua.yuriydr.hospital.model.Chamber;
import ua.yuriydr.hospital.model.ChamberType;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlChamberDao implements ChamberDao {

    private static final Logger logger = Logger.getLogger(MySqlChamberDao.class);

    private static volatile MySqlChamberDao mySqlChamberDao;

    private static final String FIND_ALL_CHAMBER = "SELECT DISTINCT ch.id_chamber, ch.max_count, ch.number, ch.fk_ch_type, t.chamber_name " +
            "FROM hospital.chamber ch " +
            "LEFT JOIN hospital.chamber_type t ON (ch.fk_ch_type = t.id_chamber)";

    private static final String FIND_ALL_CHABERS_BY_TYPE = "SELECT DISTINCT ch.id_chamber, ch.max_count, ch.number, ch.fk_ch_type, t.chamber_name " +
            "FROM hospital.chamber ch " +
            "LEFT JOIN hospital.chamber_type t ON (ch.fk_ch_type = t.id_chamber) " +
            "WHERE t.chamber_name = ?";

    private static final String FIND_CHAMBER_BY_ID = "SELECT ch.id_chamber, ch.max_count, ch.number, ch.fk_ch_type, t.chamber_name " +
            "FROM hospital.chamber ch " +
            "LEFT JOIN hospital.chamber_type t ON (ch.fk_ch_type = t.id_chamber) " +
            "WHERE ch.id_chamber = ?";
    private static final String UPDATE_CHAMBER = "UPDATE hospital.chamber SET max_count = ?, number = ? WHERE id_chamber = ?";
    private static final String DELETE_CHAMBER = "DELETE FROM hospital.chamber WHERE id_chamber = ?";
    private static final String INSERT_CHAMBER = "INSERT INTO hospital.chamber(max_count, number, fk_ch_type) VALUES(?, ?, ?)";

    private static final int COLUMN_ID_CHAMBER = 1;
    private static final int COLUMN_MAX_COUNT = 2;
    private static final int COLUMN_NUMBER = 3;
    private static final int COLUMN_FK_CHAMBER_TYPE = 4;
    private static final int COLUMN_CHAMBER_TYPE_NAME = 5;


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
            ChamberType chamberType = new ChamberType();
            chamberType.setIdChamberType(rs.getLong(COLUMN_FK_CHAMBER_TYPE));
            chamberType.setChamberName(rs.getString(COLUMN_CHAMBER_TYPE_NAME));
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
    public List<Chamber> findAllByType(String type) {
        logger.debug("Try to find all chambers by type " + type);

        ResultSet rs = null;
        statement = null;
        List<Chamber> chambers = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ALL_CHABERS_BY_TYPE);
            statement.setString(1, type);
            rs = statement.executeQuery();
            while (rs.next()) {
                chambers.add(createChamber(rs));
            }
            logger.debug("Chambers were found " + chambers);

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
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_CHAMBER);
            statement.setLong(1, chamber.getMaxCount());
            statement.setLong(2, chamber.getNumber());
            statement.setLong(3, chamber.getIdChamber());

            if (statement.executeUpdate() > 0) {
                logger.debug("Chamber was updated successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update chamber: " + e);
            rollback(connection);
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
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_CHAMBER);
            statement.setLong(1, chamber.getIdChamber());
            if (statement.executeUpdate() > 0) {
                logger.debug("Chamber was deleted successfully ");
                connection.commit();
                return true;
            }

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete chamber: " + e);
            rollback(connection);
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
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_CHAMBER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, chamber.getMaxCount());
            statement.setLong(2, chamber.getNumber());
            statement.setLong(3, chamber.getChamberType().getIdChamberType());

            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    chamber.setIdChamber(rs.getLong(COLUMN_ID_CHAMBER));
                    logger.debug("Chamber added successfully " + chamber);
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.debug("SQLException thrown when try to insert new chamber: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Chamber was not inserted " + chamber);
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
