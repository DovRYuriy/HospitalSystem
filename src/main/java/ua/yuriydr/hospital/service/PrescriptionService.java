package ua.yuriydr.hospital.service;

import ua.yuriydr.hospital.entity.Prescription;

import java.util.List;

/**
 * Service layer for working with {@link ua.yuriydr.hospital.dao.PrescriptionDao}
 */
public interface PrescriptionService {

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
