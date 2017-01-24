package ua.yuriydr.hospital.dao.mysql.impl;

import org.apache.log4j.Logger;
import ua.yuriydr.hospital.dao.PersonDiagnosisDao;
import ua.yuriydr.hospital.model.Diagnosis;
import ua.yuriydr.hospital.model.Person;
import ua.yuriydr.hospital.model.PersonDiagnosis;
import ua.yuriydr.hospital.model.Prescription;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPersonDiagnosisDao implements PersonDiagnosisDao {

    private static final Logger logger = Logger.getLogger(MySqlPersonDiagnosisDao.class);

    private static volatile MySqlPersonDiagnosisDao mySqlPrescriptionDao;

    private static final String FIND_ALL_PERSON_DIAGNOSIS_BY_PATIENT_ID = "SELECT pd.id_patient, pd.id_staff, pd.id_diagnosis, d.diagnosis_name, d.description, " +
            "pd.id_prescription, p.drugs, p.procedure, p.operation, pd.date, pd.discharge_date " +
            "FROM hospital.person_diagnosis pd " +
            "LEFT JOIN hospital.diagnosis d ON (pd.id_diagnosis=d.id_diagnosis) " +
            "LEFT JOIN hospital.prescription p ON (pd.id_prescription=p.id_prescription)" +
            "WHERE pd.id_patient = ?";

    private static final String FIND_ALL_PERSON_DIAGNOSIS_BY_PATIENT_STAFF_ID = "SELECT pd.id_patient, pd.id_staff, pd.id_diagnosis, d.diagnosis_name, d.description, " +
            "pd.id_prescription, p.drugs, p.procedure, p.operation, pd.date, pd.discharge_date " +
            "FROM hospital.person_diagnosis pd " +
            "LEFT JOIN hospital.diagnosis d ON (pd.id_diagnosis=d.id_diagnosis) " +
            "LEFT JOIN hospital.prescription p ON (pd.id_prescription=p.id_prescription)" +
            "WHERE pd.id_patient = ? AND pd.id_staff = ?";

    private static final String FIND_ALL_PERSON_DIAGNOSIS_BY_STAFF_ID = "SELECT pd.id_patient, pd.id_staff, pd.id_diagnosis, d.diagnosis_name, d.description, " +
            "pd.id_prescription, p.drugs, p.procedure, p.operation, pd.date, pd.discharge_date " +
            "FROM hospital.person_diagnosis pd " +
            "LEFT JOIN hospital.diagnosis d ON (pd.id_diagnosis=d.id_diagnosis) " +
            "LEFT JOIN hospital.prescription p ON (pd.id_prescription=p.id_prescription) " +
            "WHERE pd.id_staff = ? ";

    private static final String FIND_ALL_OPEN_PERSON_DIAGNOSIS_BY_STAFF_ID = "SELECT pd.id_patient, pd.id_staff, pd.id_diagnosis, d.diagnosis_name, d.description, " +
            "pd.id_prescription, p.drugs, p.procedure, p.operation, pd.date, pd.discharge_date " +
            "FROM hospital.person_diagnosis pd " +
            "LEFT JOIN hospital.diagnosis d ON (pd.id_diagnosis=d.id_diagnosis) " +
            "LEFT JOIN hospital.prescription p ON (pd.id_prescription=p.id_prescription) " +
            "WHERE pd.id_staff = ? " +
            "AND pd.discharge_date IS NULL";

    private static final String FIND_ALL_PERSON_DIAGNOSIS = "SELECT pd.id_patient, pd.id_staff, pd.id_diagnosis, d.diagnosis_name, d.description, " +
            "pd.id_prescription, p.drugs, p.procedure, p.operation, pd.date, pd.discharge_date " +
            "FROM hospital.person_diagnosis pd " +
            "LEFT JOIN hospital.diagnosis d ON (pd.id_diagnosis=d.id_diagnosis) " +
            "LEFT JOIN hospital.prescription p ON (pd.id_prescription=p.id_prescription) " +
            "WHERE pd.discharge_date IS NULL";

    private static final String UPDATE_PERSON_DIAGNOSIS = "UPDATE hospital.person_diagnosis SET date = ?, discharge_date = ?" +
            "WHERE (id_patient = ? && id_staff = ? &&" + " id_diagnosis = ? && id_prescription = ?)";

    private static final String DELETE_PERSON_DIAGNOSIS = "DELETE FROM hospital.person_diagnosis WHERE (id_patient = ? && id_staff = ? &&" +
            " id_diagnosis = ? && id_prescription = ?)";

    private static final String INSERT_PERSON_DIAGNOSIS = "INSERT INTO hospital.person_diagnosis(id_patient, id_staff, " +
            "id_diagnosis, id_prescription, date, discharge_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final int COLUMN_ID_PATIENT = 1;
    private static final int COLUMN_ID_STAFF = 2;
    private static final int COLUMN_ID_DIAGNOSIS = 3;
    private static final int COLUMN_DIAGNOSIS_NAME = 4;
    private static final int COLUMN_DIAGNOSIS_DESCRIPTION = 5;
    private static final int COLUMN_ID_PRESCRIPTION = 6;
    private static final int COLUMN_PRESCRIPTION_DRUGS = 7;
    private static final int COLUMN_PRESCRIPTION_PROCEDURE = 8;
    private static final int COLUMN_PRESCRIPTION_OPERATION = 9;
    private static final int COLUMN_DATE = 10;
    private static final int COLUMN_DISCHARGE_DATE = 11;

    private PreparedStatement statement;
    private Connection connection;


    public static MySqlPersonDiagnosisDao getInstance() {
        MySqlPersonDiagnosisDao localInstance = mySqlPrescriptionDao;
        if (localInstance == null) {
            synchronized (MySqlPersonDiagnosisDao.class) {
                localInstance = mySqlPrescriptionDao;
                if (localInstance == null) {
                    mySqlPrescriptionDao = localInstance = new MySqlPersonDiagnosisDao();
                }
            }
        }
        logger.debug(localInstance);
        return localInstance;
    }

    private PersonDiagnosis createPatientDiagnosis(ResultSet rs) {
        PersonDiagnosis personDiagnosis = new PersonDiagnosis();
        try {
            Person patient = new Person();
            patient.setIdPerson(rs.getLong(COLUMN_ID_PATIENT));
            personDiagnosis.setPatient(patient);

            Person doc = new Person();
            doc.setIdPerson(rs.getLong(COLUMN_ID_STAFF));
            personDiagnosis.setDoctor(doc);

            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setIdDiagnosis(rs.getLong(COLUMN_ID_DIAGNOSIS));
            diagnosis.setName(rs.getString(COLUMN_DIAGNOSIS_NAME));
            diagnosis.setDescription(rs.getString(COLUMN_DIAGNOSIS_DESCRIPTION));
            personDiagnosis.setDiagnosis(diagnosis);

            Prescription prescription = new Prescription();
            prescription.setIdPrescription(rs.getLong(COLUMN_ID_PRESCRIPTION));
            prescription.setDrugs(rs.getString(COLUMN_PRESCRIPTION_DRUGS));
            prescription.setProcedure(rs.getString(COLUMN_PRESCRIPTION_PROCEDURE));
            prescription.setOperation(rs.getString(COLUMN_PRESCRIPTION_OPERATION));
            personDiagnosis.setPrescription(prescription);

            personDiagnosis.setDate(rs.getTimestamp(COLUMN_DATE));
            personDiagnosis.setDischargeDate(rs.getTimestamp(COLUMN_DISCHARGE_DATE));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personDiagnosis;
    }

    @Override
    public List<PersonDiagnosis> findAllByPatientId(Long idPatient) {
        logger.debug("Try to find all person diagnoses by his id " + idPatient);
        return findAll(FIND_ALL_PERSON_DIAGNOSIS_BY_PATIENT_ID, idPatient);
    }

    @Override
    public List<PersonDiagnosis> findAllByStaffId(Long idStaff) {
        logger.debug("Try to find all person diagnosis by staff id " + idStaff);
        return findAll(FIND_ALL_PERSON_DIAGNOSIS_BY_STAFF_ID, idStaff);
    }

    @Override
    public List<PersonDiagnosis> findAllOpenByStaffId(Long idStaff) {
        logger.debug("Try to find all open person diagnosis by staff id " + idStaff);
        return findAll(FIND_ALL_OPEN_PERSON_DIAGNOSIS_BY_STAFF_ID, idStaff);
    }

    @Override
    public List<PersonDiagnosis> findAllByPatientAndDoctorId(Long idPatient, Long idDoctor) {
        ResultSet rs = null;
        List<PersonDiagnosis> personDiagnoses = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(FIND_ALL_PERSON_DIAGNOSIS_BY_PATIENT_STAFF_ID);
            statement.setLong(1, idPatient);
            statement.setLong(2, idDoctor);
            rs = statement.executeQuery();
            while (rs.next()) {
                personDiagnoses.add(createPatientDiagnosis(rs));
            }
            logger.debug("All person diagnoses was found " + personDiagnoses);
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find person diagnoses: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return personDiagnoses;
    }

    @Override
    public List<PersonDiagnosis> findAllForNurse() {
        logger.debug("Try to find all person diagnoses for nurse");
        return findAll(FIND_ALL_PERSON_DIAGNOSIS, null);
    }

    private List<PersonDiagnosis> findAll(String query, Long id) {
        ResultSet rs = null;
        List<PersonDiagnosis> personDiagnoses = new ArrayList<>();
        connection = DatabaseManager.getConnection();

        try {
            statement = connection.prepareStatement(query);
            if (id != null) {
                statement.setLong(1, id);
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                personDiagnoses.add(createPatientDiagnosis(rs));
            }
            logger.debug("All person diagnoses was found " + personDiagnoses);
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to find person diagnoses: " + e);
        } finally {
            DatabaseManager.closeAll(connection, statement, rs);
        }
        return personDiagnoses;
    }

    @Override
    public boolean updatePatientDiagnosis(PersonDiagnosis personDiagnosis) {
        logger.debug("Try to update person diagnosis " + personDiagnosis);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PERSON_DIAGNOSIS);
            statement.setTimestamp(1, personDiagnosis.getDate());
            statement.setTimestamp(2, personDiagnosis.getDischargeDate());
            statement.setLong(3, personDiagnosis.getPatient().getIdPerson());
            statement.setLong(4, personDiagnosis.getDoctor().getIdPerson());
            statement.setLong(5, personDiagnosis.getDiagnosis().getIdDiagnosis());
            statement.setLong(6, personDiagnosis.getPrescription().getIdPrescription());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person diagnosis was updated successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to update person diagnosis " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }
        logger.debug("Person diagnosis was not updated " + personDiagnosis);
        return false;
    }

    @Override
    public boolean deletePatientDiagnosis(PersonDiagnosis personDiagnosis) {
        logger.debug("Try to delete patient diagnosis " + personDiagnosis);

        statement = null;
        connection = DatabaseManager.getConnection();
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(DELETE_PERSON_DIAGNOSIS);
            statement.setLong(1, personDiagnosis.getPatient().getIdPerson());
            statement.setLong(2, personDiagnosis.getDoctor().getIdPerson());
            statement.setLong(3, personDiagnosis.getDiagnosis().getIdDiagnosis());
            statement.setLong(4, personDiagnosis.getPrescription().getIdPrescription());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person diagnosis was deleted successfully");
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException thrown when try to delete person diagnosis: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }

        logger.debug("Person diagnosis was not deleted " + personDiagnosis);
        return false;
    }

    @Override
    public boolean insertPatientDiagnosis(PersonDiagnosis personDiagnosis) {
        logger.debug("Try to insert new person diagnosis " + personDiagnosis);

        statement = null;
        connection = DatabaseManager.getConnection();

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(INSERT_PERSON_DIAGNOSIS, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, personDiagnosis.getPatient().getIdPerson());
            statement.setLong(2, personDiagnosis.getDoctor().getIdPerson());
            statement.setLong(3, personDiagnosis.getDiagnosis().getIdDiagnosis());
            statement.setLong(4, personDiagnosis.getPrescription().getIdPrescription());
            statement.setTimestamp(5, personDiagnosis.getDate());
            statement.setTimestamp(6, personDiagnosis.getDischargeDate());
            if (statement.executeUpdate() > 0) {
                logger.debug("Person diagnosis added successfully " + personDiagnosis);
                connection.commit();
                return true;
            }

        } catch (SQLException e) {
            logger.error("SQLException thrown when try to insert patient diagnosis: " + e);
            rollback(connection);
        } finally {
            DatabaseManager.closeAll(connection, statement, null);
        }
        logger.debug("Person diagnosis was not inserted " + personDiagnosis);
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
