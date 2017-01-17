package ua.yuriydr.hospital.service.factory.impl;

import ua.yuriydr.hospital.dao.PersonDao;
import ua.yuriydr.hospital.dao.PersonDiagnosisDao;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.model.PersonDiagnosis;
import ua.yuriydr.hospital.service.PersonDiagnosisService;

import java.util.List;

public class PersonDiagnosisServiceImpl implements PersonDiagnosisService {

    private static volatile PersonDiagnosisServiceImpl personDiagnosisService;

    private static PersonDao personDao = MySqlDaoFactory.getPersonDao();
    private static PersonDiagnosisDao personDiagnosisDao = MySqlDaoFactory.getPersonDiagnosisDao();

    private PersonDiagnosisServiceImpl() {

    }

    public static PersonDiagnosisServiceImpl getInstance() {
        PersonDiagnosisServiceImpl localInstance = personDiagnosisService;
        if (localInstance == null) {
            synchronized (PersonDiagnosisServiceImpl.class) {
                localInstance = personDiagnosisService;
                if (localInstance == null) {
                    personDiagnosisService = localInstance = new PersonDiagnosisServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<PersonDiagnosis> findAllByPatientId(Long idPatient) {
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllByPatientId(idPatient);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(personDao.findPersonById(idPatient));
            personDiagnosis.setDoctor(personDao.findPersonById(personDiagnosis.getDoctor().getIdPerson()));
        }
        return personDiagnoses;
    }

    @Override
    public boolean insertPatientDiagnosis(PersonDiagnosis personDiagnosis) {
        return personDiagnosisDao.insertPatientDiagnosis(personDiagnosis);
    }

    @Override
    public List<PersonDiagnosis> findAllByStaffId(Long idStaff) {
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllByStaffId(idStaff);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(personDao.findPersonById(personDiagnosis.getPatient().getIdPerson()));
            personDiagnosis.setDoctor(personDao.findPersonById(idStaff));
        }
        return personDiagnoses;

    }

    @Override
    public boolean deletePatientDiagnosis(PersonDiagnosis personDiagnosis) {
        return personDiagnosisDao.deletePatientDiagnosis(personDiagnosis);
    }

    @Override
    public List<PersonDiagnosis> findAllForNurse() {
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllForNurse();
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(personDao.findPersonById(personDiagnosis.getPatient().getIdPerson()));
            personDiagnosis.setDoctor(personDao.findPersonById(personDiagnosis.getDoctor().getIdPerson()));
        }
        return personDiagnoses;
    }

    @Override
    public boolean updatePatientDiagnosis(PersonDiagnosis personDiagnosis) {
        return personDiagnosisDao.updatePatientDiagnosis(personDiagnosis);
    }

}
