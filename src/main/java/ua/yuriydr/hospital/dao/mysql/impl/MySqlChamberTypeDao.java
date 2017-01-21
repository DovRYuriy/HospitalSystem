package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.ChamberTypeDao;
import ua.yuriydr.hospital.model.ChamberType;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlChamberTypeDao implements ChamberTypeDao {

    private static final Logger logger = Logger.getLogger(MySqlChamberTypeDao.class);

    private static volatile MySqlChamberTypeDao mySqlChamberTypeDao;

    private static final String FIND_ALL = "SELECT * FROM hospital.chamber_type";
    private static final String FIND_CHAMBER_TYPE_BY_ID = "SELECT * FROM hospital.chamber_type WHERE id_chamber = ?";
    private static final String FIND_CHAMBER_TYPE_BY_NAME = "SELECT * FROM hospital.chamber_type WHERE chamber_name = ?";
    private static final String DELETE_CHAMMER_TYPE = "DELETE FROM hospital.chamber_type WHERE id_chamber = ?";
    private static final String INSERT_CHAMBER_TYPE = "INSERT INTO hospital.chamber_type(chamber_name) VALUES(?)";

    private static final int COLUMN_ID_CHAMBER_TYPE = 1;
    private static final int COLUMN_CHAMBER_NAME = 2;

    private PreparedStatement statement;
    private Connection connection;

    private MySqlChamberTypeDao() {

    }

    public static MySqlChamberTypeDao getInstance() {
        MySqlChamberTypeDao localInstance = mySqlChamberTypeDao;
        if (localInstance == null) {
            synchronized (MySqlChamberTypeDao.class) {
                localInstance = mySqlChamberTypeDao;
                if (localInstance == null) {
                    mySqlChamberTypeDao = localInstance = new MySqlChamberTypeDao();
                }
            }
        }
        logger.debug(mySqlChamberTypeDao);
        return localInstance;
    }

    private ChamberType createChamberType(ResultSet rs) {
        ChamberType chamberType = new ChamberType();
        try {
            chamberType.setIdChamberType(rs.getLong(COLUMN_ID_CHAMBER_TYPE));
            chamberType.setChamberName(rs.getString(COLUMN_CHAMBER_NAME));
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to create chamber type " + e);
        }
        return chamberType;
    }

    @Override
    public List<ChamberType> findAll() {
        logger.debug("Try to get all chamber types");

        ResultSet rs = null;
        connection = DatabaseManager.getConnection();
        List<ChamberType> chamberTypes = new ArrayList<>();

        try {
            statement = connection.prepareStatement(FIND_ALL);
            rs = statement.executeQuery();
            while (rs.next()) {
                chamberTypes.add(createChamberType(rs));
            }
            logger.debug("Chamber types were found successfully " + chamberTypes);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find all chamber types " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return chamberTypes;
    }

    @Override
    public ChamberType findChamberTypeById(Long id) {
        logger.debug("Try to find chamber type by id: " + id);

        ChamberType chamberType = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_CHAMBER_TYPE_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                chamberType = createChamberType(rs);
                logger.debug("Chamber type was found successfully " + chamberType);
            } else {
                logger.debug("Chamber type was not found by this id: " + id);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find chamber type by id: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }

        return chamberType;
    }

    @Override
    public ChamberType findChamberTypeByName(String name) {
        logger.debug("Try to find chamber type by name: " + name);

        ChamberType chamberType = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_CHAMBER_TYPE_BY_NAME);
            statement.setString(1, name);
            rs = statement.executeQuery();

            if (rs.next()) {
                chamberType = createChamberType(rs);
                logger.debug("Chamber type was found successfully " + chamberType);
            } else {
                logger.debug("Chamber type was not found by this chamber name: " + name);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find chamber type by name: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return chamberType;
    }

    @Override
    public boolean deleteChamberType(ChamberType chamberType) {
        logger.debug("Try to delete chamber type " + chamberType);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(DELETE_CHAMMER_TYPE);
            statement.setLong(1, chamberType.getIdChamberType());

            if (statement.executeUpdate() > 0) {
                logger.debug("The chamber type deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete chamber type: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("The chamber type was not deleted " + chamberType);
        return false;
    }

    @Override
    public boolean insertChamberType(ChamberType chamberType) {
        logger.debug("Try to insert new chamber type " + chamberType);

        statement = null;
        connection = DatabaseManager.getConnection();
        ResultSet rs;

        try {
            statement = connection.prepareStatement(INSERT_CHAMBER_TYPE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, chamberType.getChamberName());
            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    chamberType.setIdChamberType(rs.getLong(COLUMN_ID_CHAMBER_TYPE));
                    logger.debug("Chamber type added successfully " + chamberType);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to insert chamber type: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("The chamber type was not inserted " + chamberType);
        return false;
    }
}
