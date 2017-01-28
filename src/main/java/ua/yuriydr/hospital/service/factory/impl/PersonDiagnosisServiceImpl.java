package ua.yuriydr.hospital.service.factory.impl;

import ua.yuriydr.hospital.dao.PersonDao;
import ua.yuriydr.hospital.dao.PersonDiagnosisDao;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.entity.PersonDiagnosis;
import ua.yuriydr.hospital.service.PersonDiagnosisService;

import java.util.List;

public class PersonDiagnosisServiceImpl implements PersonDiagnosisService {

    private static volatile PersonDiagnosisServiceImpl personDiagnosisService;

    private static final PersonDao personDao = MySqlDaoFactory.getPersonDao();
    private static final PersonDiagnosisDao personDiagnosisDao = MySqlDaoFactory.getPersonDiagnosisDao();

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
        Person patient = personDao.findPersonById(idPatient);
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllByPatientId(idPatient);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(patient);
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
        Person staff = personDao.findPersonById(idStaff);
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllByStaffId(idStaff);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(personDao.findPersonById(personDiagnosis.getPatient().getIdPerson()));
            personDiagnosis.setDoctor(staff);
        }
        return personDiagnoses;
    }

    @Override
    public List<PersonDiagnosis> findAllOpenByStaffId(Long idStaff) {
        Person staff = personDao.findPersonById(idStaff);
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllOpenByStaffId(idStaff);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(personDao.findPersonById(personDiagnosis.getPatient().getIdPerson()));
            personDiagnosis.setDoctor(staff);
        }
        return personDiagnoses;
    }

    @Override
    public List<PersonDiagnosis> findAllByPatientAndDoctorId(Long idPatient, Long idDoctor) {
        Person patient = personDao.findPersonById(idPatient);
        Person doctor = personDao.findPersonById(idDoctor);
        List<PersonDiagnosis> personDiagnoses = personDiagnosisDao.findAllByPatientAndDoctorId(idPatient, idDoctor);
        for (PersonDiagnosis personDiagnosis : personDiagnoses) {
            personDiagnosis.setPatient(patient);
            personDiagnosis.setDoctor(doctor);
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
    public PersonDiagnosis findPersonDiagnosis(Long idPatient, Long idStaff, Long idPrescription, Long idDiagnosis) {
        PersonDiagnosis personDiagnosis = personDiagnosisDao.findPersonDiagnosis(idPatient, idStaff, idPrescription, idDiagnosis);
        if (personDiagnosis != null) {
            personDiagnosis.setPatient(personDao.findPersonById(idPatient));
            personDiagnosis.setDoctor(personDao.findPersonById(idStaff));
        }
        return personDiagnosis;
    }

    @Override
    public boolean updatePatientDiagnosis(PersonDiagnosis personDiagnosis) {
        return personDiagnosisDao.updatePatientDiagnosis(personDiagnosis);
    }

}
