package ua.yuriydr.hospital.dao;


import ua.yuriydr.hospital.dao.mysql.impl.MySqlPrescriptionDao;
import ua.yuriydr.hospital.entity.Prescription;

import java.util.List;

/**
 * Represents methods to manage prescriptions in the hospital.
 *
 * @see MySqlPrescriptionDao
 */
public interface PrescriptionDao {

    /**
     * Returns a list of prescriptions.
     *
     * @return a list of prescriptions.
     */
    List<Prescription> findAll();

    /**
     * Returns prescription by his id.
     *
     * @param id prescription id
     * @return prescription.
     */
    Prescription findPrescriptionById(Long id);

    /**
     * Update prescription.
     *
     * @param prescription that need to change.
     * @return true if prescription successfully updated.
     */
    boolean updatePrescription(Prescription prescription);

    /**
     * Delete prescription.
     *
     * @param prescription that need to delete.
     * @return true if prescription successfully deleted.
     */
    boolean deletePrescription(Prescription prescription);

    /**
     * Add prescription.
     *
     * @param prescription that need to add.
     * @return true if prescription successfully added.
     */
    boolean insertPrescription(Prescription prescription);

}
