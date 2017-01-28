package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.dao.mysql.impl.MySqlPersonDiagnosisDao;
import ua.yuriydr.hospital.entity.PersonDiagnosis;

import java.util.List;

/**
 * Represents methods to manage person's diagnoses in the hospital.
 *
 * @see MySqlPersonDiagnosisDao
 */
public interface PersonDiagnosisDao {

    /**
     * Returns a list of person's diagnoses by his id.
     *
     * @param idPatient person's id in system.
     * @return a list of persons's diagnoses by person id.
     */
    List<PersonDiagnosis> findAllByPatientId(Long idPatient);

    /**
     * Returns a list of person's diagnoses by doctor's id.
     *
     * @param idStaff doctor's id in system.
     * @return a list of persons's diagnoses by person id.
     */
    List<PersonDiagnosis> findAllByStaffId(Long idStaff);

    /**
     * Returns a list of open person's diagnoses by doctor's id.
     *
     * @param idStaff doctor's id in system.
     * @return a list of persons's diagnoses by person id.
     */
    List<PersonDiagnosis> findAllOpenByStaffId(Long idStaff);

    /**
     * Returns a list of person's diagnoses by his id and doctor's id.
     *
     * @param idPatient patient's id in system.
     * @param idDoctor  doctor's id in system.
     * @return a list of persons's diagnoses by person id.
     */
    List<PersonDiagnosis> findAllByPatientAndDoctorId(Long idPatient, Long idDoctor);

    /**
     * Returns a list of person's diagnoses for nurse.
     *
     * @return a list of persons's diagnoses for nurse.
     */
    List<PersonDiagnosis> findAllForNurse();

    /**
     * Returns person diagnosis by id
     *
     * @return person diagnosis
     */
    PersonDiagnosis findPersonDiagnosis(Long idPatient, Long idStaff, Long idPrescription, Long idDiagnosis);


    /**
     * Update person's diagnosis.
     *
     * @param personDiagnosis that need to change.
     * @return true if person's diagnosis successfully updated.
     */
    boolean updatePatientDiagnosis(PersonDiagnosis personDiagnosis);

    /**
     * Delete person's diagnosis.
     *
     * @param personDiagnosis that need to delete.
     * @return true if person's diagnosis successfully deleted.
     */
    boolean deletePatientDiagnosis(PersonDiagnosis personDiagnosis);

    /**
     * Add new person's diagnosis.
     *
     * @param personDiagnosis that need to add.
     * @return true if person's diagnosis successfully added.
     */
    boolean insertPatientDiagnosis(PersonDiagnosis personDiagnosis);

}
