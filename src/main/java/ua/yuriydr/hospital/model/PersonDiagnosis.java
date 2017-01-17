package ua.yuriydr.hospital.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entity for hospital.person_diagnosis table.
 */
public class PersonDiagnosis implements Serializable {

    private Person patient;
    private Diagnosis diagnosis;
    private Person doctor;
    private Prescription prescription;
    private Timestamp date;
    private Timestamp dischargeDate;

    public PersonDiagnosis() {
    }

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Person patient) {
        this.patient = patient;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Person getDoctor() {
        return doctor;
    }

    public void setDoctor(Person doctor) {
        this.doctor = doctor;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Timestamp dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDiagnosis that = (PersonDiagnosis) o;

        if (patient != null ? !patient.equals(that.patient) : that.patient != null) return false;
        if (diagnosis != null ? !diagnosis.equals(that.diagnosis) : that.diagnosis != null) return false;
        if (doctor != null ? !doctor.equals(that.doctor) : that.doctor != null) return false;
        if (prescription != null ? !prescription.equals(that.prescription) : that.prescription != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return dischargeDate != null ? dischargeDate.equals(that.dischargeDate) : that.dischargeDate == null;
    }

    @Override
    public int hashCode() {
        int result = patient != null ? patient.hashCode() : 0;
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        result = 31 * result + (doctor != null ? doctor.hashCode() : 0);
        result = 31 * result + (prescription != null ? prescription.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (dischargeDate != null ? dischargeDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDiagnosisService{" +
                "patient=" + patient +
                ", diagnosis=" + diagnosis +
                ", doctor=" + doctor +
                ", prescription=" + prescription +
                ", date=" + date +
                ", dischargeDate=" + dischargeDate +
                '}';
    }
}
