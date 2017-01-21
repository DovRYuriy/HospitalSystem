package ua.yuriydr.hospital.dao.mysql;

import ua.yuriydr.hospital.dao.*;
import ua.yuriydr.hospital.dao.mysql.impl.*;

/**
 * Represents static methods to return all dao implementations.
 */
public class MySqlDaoFactory {

    private MySqlDaoFactory() {

    }

    /**
     * Returns PersonDao object.
     *
     * @return PersonDao implementation object.
     * @see MySqlPersonDao
     */
    public static PersonDao getPersonDao() {
        return MySqlPersonDao.getInstance();
    }

    /**
     * Returns PersonDiagnosisDao object.
     *
     * @return PersonDiagnosisDao implementation object.
     * @see MySqlPersonDiagnosisDao
     */
    public static PersonDiagnosisDao getPersonDiagnosisDao() {
        return MySqlPersonDiagnosisDao.getInstance();
    }

    /**
     * Returns Diagnosis object.
     *
     * @return DiagnosisDao implementation object.
     * @see MySqlDiagnosisDao
     */
    public static DiagnosisDao getDiagnosisDao() {
        return MySqlDiagnosisDao.getInstance();
    }

    /**
     * Returns PrescriptionDao object.
     *
     * @return PrescriptionDao implementation object.
     * @see MySqlPrescriptionDao
     */
    public static PrescriptionDao getPrescriptionDao() {
        return MySqlPrescriptionDao.getInstance();
    }

    /**
     * Returns RoleDao object.
     *
     * @return RoleDao implementation object.
     * @see MySqlRoleDao
     */
    public static RoleDao getRoleDao() {
        return MySqlRoleDao.getInstance();
    }

    /**
     * Returns ChamberDao object.
     *
     * @return ChamberDao implementation object.
     * @see MySqlChamberDao
     */
    public static ChamberDao getChamberDao() {
        return MySqlChamberDao.getInstance();
    }

    /**
     * Returns ChamberTypeDao object.
     *
     * @return ChamberTypeDao implementation object.
     * @see MySqlChamberTypeDao
     */
    public static ChamberTypeDao getChamberTypeDao() {
        return MySqlChamberTypeDao.getInstance();
    }

}
