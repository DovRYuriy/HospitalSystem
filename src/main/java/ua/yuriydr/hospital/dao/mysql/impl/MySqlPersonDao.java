package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.PersonDao;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.model.Role;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPersonDao implements PersonDao {

    private static final Logger logger = Logger.getLogger(MySqlPersonDao.class);

    private static volatile MySqlPersonDao mySqlPersonDao;

    private static final String FIND_ALL = "SELECT DISTINCT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)";

    private static final String FIND_ALL_BY_ROLE = "SELECT DISTINCT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)" +
            "WHERE r.role_name = ?";

    private static final String FIND_ALL_HEALTHY_PATIENTS = "SELECT DISTINCT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)" +
            "WHERE p.fk_chamber IS NULL ";

    private static final String FIND_PERSON_BY_ID = "SELECT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)" +
            "WHERE p.id_person=?";

    private static final String FIND_PERSONS_IN_CHAMBER = "SELECT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)" +
            "WHERE p.fk_chamber = ?";

    private static final String FIND_PERSON_BY_EMAIL = "SELECT p.id_person, p.name, p.surname, p.birthday, p.phone, p.email, p.password, p.fk_role, r.role_name, p.fk_chamber " +
            "FROM hospital.person p " +
            "LEFT JOIN hospital.role r ON(p.fk_role = r.id_role)" +
            "WHERE p.email = ?";

    private static final String UPDATE_CHAMBER = "UPDATE hospital.person SET fk_chamber = ? WHERE id_person = ?";

    private static final String UPDATE_PERSON = "UPDATE hospital.person SET name = ?, surname = ?, " +
            "birthday = ?, phone = ?, email = ? WHERE id_person = ?";
    private static final String UPDATE_PERSON_PASSWORD = "UPDATE hospital.person SET password = ? WHERE id_person = ?";
    private static final String DELETE_PERSON = "DELETE FROM hospital.person WHERE id_person = ?";
    private static final String INSERT_PERSON = "INSERT INTO hospital.person(name, surname, birthday, " +
            "phone, email, password, fk_role, fk_chamber) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

    private static final int COLUMN_ID_PERSON = 1;
    private static final int COLUMN_NAME = 2;
    private static final int COLUMN_SURNAME = 3;
    private static final int COLUMN_BIRTHDAY = 4;
    private static final int COLUMN_PHONE = 5;
    private static final int COLUMN_EMAIL = 6;
    private static final int COLUMN_PASSWORD = 7;
    private static final int COLUMN_ID_ROLE = 8;
    private static final int COLUMN_ROLE_NAME = 9;
    private static final int COLUMN_ID_CHAMBER = 10;

    private PreparedStatement statement;
    private Connection connection;


    private MySqlPersonDao() {

    }

    public static MySqlPersonDao getInstance() {
        MySqlPersonDao localInstance = mySqlPersonDao;
        if (localInstance == null) {
            synchronized (MySqlPersonDao.class) {
                localInstance = mySqlPersonDao;
                if (localInstance == null) {
                    mySqlPersonDao = localInstance = new MySqlPersonDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private Person createPerson(ResultSet rs) {
        Person person = new Person();
        try {
            person.setIdPerson(rs.getLong(COLUMN_ID_PERSON));
            person.setName(rs.getString(COLUMN_NAME));
            person.setSurname(rs.getString(COLUMN_SURNAME));
            person.setBirthday(rs.getDate(COLUMN_BIRTHDAY));
            person.setPhone(rs.getString(COLUMN_PHONE));
            person.setEmail(rs.getString(COLUMN_EMAIL));
            person.setPassword(rs.getString(COLUMN_PASSWORD));
            Role role = new Role();
            role.setIdRole(rs.getLong(COLUMN_ID_ROLE));
            role.setName(rs.getString(COLUMN_ROLE_NAME));
            person.setRole(role);
            person.setIdChamber(rs.getLong(COLUMN_ID_CHAMBER));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        logger.debug("Try to find all persons");

        ResultSet rs = null;
        statement = null;
        List<Person> persons = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ALL);
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(createPerson(rs));
            }
            logger.debug("Persons were found " + persons);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find all persons: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return persons;
    }

    @Override
    public List<Person> findAllByRole(String role) {
        logger.debug("Try to find all persons by role " + role);

        ResultSet rs = null;
        statement = null;
        List<Person> persons = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ALL_BY_ROLE);
            statement.setString(1, role);
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(createPerson(rs));
            }
            logger.debug("Persons were found " + persons);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find all persons: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return persons;
    }

    @Override
    public List<Person> findAllHealthyPatients() {
        logger.debug("Try to find all healthy patients");

        ResultSet rs = null;
        statement = null;
        List<Person> persons = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ALL_HEALTHY_PATIENTS);
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(createPerson(rs));
            }
            logger.debug("Persons were found " + persons);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find healthy persons: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return persons;
    }

    @Override
    public List<Person> findAllInSpecificChamber(Long idChamber) {
        logger.debug("Try to find persons in chamber: " + idChamber);

        ResultSet rs = null;
        statement = null;
        List<Person> persons = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_PERSONS_IN_CHAMBER);
            statement.setLong(1, idChamber);
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(createPerson(rs));
            }
            logger.debug("Persons in chamber were found " + persons);

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find persons in chamber: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return persons;
    }

    @Override
    public Person findPersonById(Long id) {
        logger.debug("Try to find person by id: " + id);

        Person person = null;
        ResultSet rs = null;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_PERSON_BY_ID);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                person = createPerson(rs);
                logger.debug("Person was found successfully " + person);
            } else {
                logger.debug("Person was not found by this id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return person;
    }

    @Override
    public boolean updatePerson(Person person) {
        logger.debug("Try to update person " + person);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PERSON);
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setDate(3, person.getBirthday());
            statement.setString(4, person.getPhone());
            statement.setString(5, person.getEmail());
            statement.setLong(6, person.getIdPerson());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person was updated successfully ");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update person: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Person was not updated " + person);
        return false;
    }

    @Override
    public boolean deletePerson(Person person) {
        logger.debug("Try to delete person " + person);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_PERSON);
            statement.setLong(1, person.getIdPerson());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person was deleted successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete person: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Person was not deleted " + person);
        return false;
    }

    @Override
    public boolean insertPerson(Person person) {
        logger.debug("Try to insert new person " + person);

        ResultSet rs;
        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setString(2, person.getSurname());
            statement.setDate(3, person.getBirthday());
            statement.setString(4, person.getPhone());
            statement.setString(5, person.getEmail());
            statement.setString(6, person.getPassword());
            statement.setLong(7, person.getRole().getIdRole());
            if (person.getIdChamber() == null) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setLong(8, person.getIdChamber());
            }
            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    person.setIdPerson(rs.getLong(COLUMN_ID_PERSON));
                    logger.debug("Person added successfully " + person);
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error("SQLIntegrityConstraintViolationException thrown when try to insert person " + e);
            rollback(connection);
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to insert person " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Person was not inserted " + person);
        return false;
    }

    @Override
    public Person login(String email, String password) {
        logger.debug("Try to find person with this credentials: " + email + " " + password);

        Person person = null;
        statement = null;
        connection = DatabaseManager.getConnection();
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(FIND_PERSON_BY_EMAIL);
            statement.setString(1, email);
            rs = statement.executeQuery();

            if ((rs.next()) && rs.getString(COLUMN_EMAIL).equals(email)
                    && rs.getString(COLUMN_PASSWORD).equals(password)) {
                person = createPerson(rs);
                logger.debug("Person was found successfully");
            } else {
                logger.debug("Person was not found with this credentials: " + email + " " + password);
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find person: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return person;
    }

    @Override
    public boolean checkEmail(String email) {
        logger.debug("Try to find email: " + email);

        statement = null;
        connection = DatabaseManager.getConnection();
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(FIND_PERSON_BY_EMAIL);
            statement.setString(1, email);
            rs = statement.executeQuery();
            if (rs.next()) {
                logger.debug("Email was found ");
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find email: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        logger.debug("Email was not found " + email);
        return false;
    }

    @Override
    public boolean changePersonPassword(Person person) {
        logger.debug("Try to change password: " + person);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PERSON_PASSWORD);
            statement.setString(1, person.getPassword());
            statement.setLong(2, person.getIdPerson());
            if (statement.executeUpdate() > 0) {
                logger.debug("Successfully updated person password");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to change person password: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }
        logger.debug("Password was not changed");
        return false;
    }

    @Override
    public boolean updateChamber(Person patient) {
        logger.debug("Try to discharge patient " + patient);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_CHAMBER);
            Long id = patient.getIdChamber();
            if (id == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setLong(1, id);
            }
            statement.setLong(2, patient.getIdPerson());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person was discharged successfully ");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to discharge patient: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Patient was not discharged " + patient);
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
