package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.entity.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

class EntitiesGenerator {

    static Role createRole(long temp) {
        Role role = new Role();
        role.setIdRole(temp);
        role.setName("roleName " + temp);
        return role;
    }

    static Person createPerson(long temp) {
        Person person = new Person();
        person.setIdPerson(temp);
        person.setName("Name " + temp);
        person.setSurname("Surname " + temp);
        person.setPhone("1234567890 " + temp);
        person.setEmail("email " + temp);
        person.setPassword("password " + temp);
        person.setBirthday(Date.valueOf(LocalDate.now()));
        person.setIdChamber(temp);
        person.setRole(createRole(temp));
        return person;
    }

    static Diagnosis createDiagnosis(long temp) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setIdDiagnosis(temp);
        diagnosis.setName("Name " + temp);
        diagnosis.setDescription("Description " + temp);
        return diagnosis;
    }

    static Prescription createPrescription(long temp) {
        Prescription prescription = new Prescription();
        prescription.setIdPrescription(temp);
        prescription.setDrugs("Drugs " + temp);
        prescription.setProcedure("Procedure " + temp);
        prescription.setOperation("Operation " + temp);
        return prescription;
    }

    static ChamberType createChamberType(long temp) {
        ChamberType chamberType = new ChamberType();
        chamberType.setIdChamberType(temp);
        chamberType.setChamberName("Name " + temp);
        return chamberType;
    }

    static Chamber createChamber(long temp) {
        Chamber chamber = new Chamber();
        chamber.setIdChamber(temp);
        chamber.setNumber(temp);
        chamber.setMaxCount(temp);
        chamber.setChamberType(createChamberType(temp));
        return chamber;
    }

    static PersonDiagnosis createPersonDiagnosis(long temp) {
        PersonDiagnosis personDiagnosis = new PersonDiagnosis();
        personDiagnosis.setPatient(createPerson(temp));
        personDiagnosis.setDoctor(createPerson(temp));
        personDiagnosis.setPrescription(createPrescription(temp));
        personDiagnosis.setDiagnosis(createDiagnosis(temp));
        personDiagnosis.setDate(Timestamp.valueOf(LocalDateTime.now()));
        personDiagnosis.setDischargeDate(Timestamp.valueOf(LocalDateTime.now()));
        return personDiagnosis;
    }

}
