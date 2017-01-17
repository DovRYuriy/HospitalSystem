package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.RoleDao;
import ua.yuriydr.hospital.model.Role;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlRoleDao implements RoleDao {

    private static final Logger logger = Logger.getLogger(MySqlRoleDao.class);

    private static volatile MySqlRoleDao roleDao;

    private static final String FIND_ALL = "SELECT * FROM hospital.role";
    private static final String FIND_ROLE_BY_ID = "SELECT * FROM hospital.role WHERE id_role = ?";
    private static final String FIND_ROLE_BY_NAME = "SELECT * FROM hospital.role WHERE role_name = ?";
    private static final String DELETE_ROLE = "DELETE FROM hospital.role WHERE id_role = ?";
    private static final String INSERT_ROLE = "INSERT INTO hospital.role(role_name) VALUES(?)";

    private static final int COLUMN_ID_ROLE = 1;
    private static final int COLUMN_ROLE_NAME = 2;

    private PreparedStatement statement;
    private Connection connection;

    private MySqlRoleDao() {

    }

    public static MySqlRoleDao getInstance() {
        MySqlRoleDao localInstance = roleDao;
        if (localInstance == null) {
            synchronized (MySqlRoleDao.class) {
                localInstance = roleDao;
                if (localInstance == null) {
                    roleDao = localInstance = new MySqlRoleDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private Role createRole(ResultSet rs) {
        Role role = new Role();
        try {
            role.setIdRole(rs.getLong(COLUMN_ID_ROLE));
            role.setName(rs.getString(COLUMN_ROLE_NAME));
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to create role " + e);
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        logger.debug("Try to get all roles");

        ResultSet rs = null;
        connection = DatabaseManager.getConnection();
        List<Role> roles = new ArrayList<>();

        try {
            statement = connection.prepareStatement(FIND_ALL);
            rs = statement.executeQuery();
            while (rs.next()) {
                roles.add(createRole(rs));
            }
            logger.debug("Roles were found successfully " + roles);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find all roles " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return roles;
    }

    @Override
    public Role findRoleById(Long id) {
        logger.debug("Try to find role by id: " + id);

        Role role = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ROLE_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                role = createRole(rs);
                logger.debug("Role was found successfully " + role);
            } else {
                logger.debug("Role was not found by this id: " + id);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find role by id: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }

        return role;
    }

    @Override
    public Role findRoleByName(String name) {
        logger.debug("Try to find role by name: " + name);

        Role role = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ROLE_BY_NAME);
            statement.setString(1, name);
            rs = statement.executeQuery();

            if (rs.next()) {
                role = createRole(rs);
                logger.debug("Role was found successfully " + role);
            } else {
                logger.debug("Role was not found by this role_name: " + name);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find role by name: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return role;
    }

    @Override
    public boolean deleteRole(Role role) {
        logger.debug("Try to delete role " + role);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(DELETE_ROLE);
            statement.setLong(1, role.getIdRole());

            if (statement.executeUpdate() > 0) {
                logger.debug("The role deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete role: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("The role was not deleted " + role);
        return false;
    }

    @Override
    public boolean insertRole(Role role) {
        logger.debug("Try to insert new role " + role);

        statement = null;
        connection = DatabaseManager.getConnection();
        ResultSet rs;

        try {
            statement = connection.prepareStatement(INSERT_ROLE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getName());
            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    role.setIdRole(rs.getLong(COLUMN_ID_ROLE));
                    logger.debug("Role added successfully " + role);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to insert role: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("The role was not inserted " + role);
        return false;
    }

}
