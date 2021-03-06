package ua.yuriydr.hospital.service;

import ua.yuriydr.hospital.entity.Diagnosis;

import java.util.List;

/**
 * Service layer for working with {@link ua.yuriydr.hospital.dao.DiagnosisDao}
 */
public interface DiagnosisService {

    /**
     * Returns a list of diagnoses.
     *
     * @return a list of diagnoses.
     */
    List<Diagnosis> findAll();

    /**
     * Returns diagnosis by id.
     *
     * @param id diagnosis.
     * @return diagnosis.
     */
    Diagnosis findDiagnosisById(Long id);

    /**
     * Update diagnosis description.
     *
     * @param diagnosis that need to change.
     * @return true if diagnosis successfully updated.
     */
    boolean updateDiagnosis(Diagnosis diagnosis);

    /**
     * Delete diagnosis.
     *
     * @param diagnosis that need to delete.
     * @return true if diagnosis successfully deleted.
     */
    boolean deleteDiagnosis(Diagnosis diagnosis);

    /**
     * Add new diagnosis.
     *
     * @param diagnosis that need to add.
     * @return true if diagnosis successfully added.
     */
    boolean insertDiagnosis(Diagnosis diagnosis);

}
