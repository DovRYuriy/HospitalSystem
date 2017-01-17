package ua.yuriydr.hospital.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for hospital.chamber table.
 */
public class Chamber {

    private Long idChamber;
    private Long maxCount;
    private Long number;

    private List<Person> patients;

    public Chamber() {
        patients = new ArrayList<>();
    }

    public Long getIdChamber() {
        return idChamber;
    }

    public void setIdChamber(Long idChamber) {
        this.idChamber = idChamber;
    }

    public Long getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Long maxCount) {
        this.maxCount = maxCount;
    }

    public List<Person> getPatients() {
        return patients;
    }

    public void setPatients(List<Person> patients) {
        this.patients = patients;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chamber chamber = (Chamber) o;

        if (idChamber != null ? !idChamber.equals(chamber.idChamber) : chamber.idChamber != null) return false;
        if (maxCount != null ? !maxCount.equals(chamber.maxCount) : chamber.maxCount != null) return false;
        if (number != null ? !number.equals(chamber.number) : chamber.number != null) return false;
        return patients != null ? patients.equals(chamber.patients) : chamber.patients == null;
    }

    @Override
    public int hashCode() {
        int result = idChamber != null ? idChamber.hashCode() : 0;
        result = 31 * result + (maxCount != null ? maxCount.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (patients != null ? patients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chamber{" +
                "idChamber=" + idChamber +
                ", maxCount=" + maxCount +
                ", number=" + number +
                ", patients=" + patients +
                '}';
    }
}
