package ua.yuriydr.hospital.entity;

/**
 * Entity for hospital.diagnosis table.
 */
public class Diagnosis {

    private Long idDiagnosis;
    private String name;
    private String description;

    public Diagnosis() {
    }

    public Long getIdDiagnosis() {
        return idDiagnosis;
    }

    public void setIdDiagnosis(Long idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Diagnosis diagnosis = (Diagnosis) o;

        if (idDiagnosis != null ? !idDiagnosis.equals(diagnosis.idDiagnosis) : diagnosis.idDiagnosis != null)
            return false;
        if (name != null ? !name.equals(diagnosis.name) : diagnosis.name != null) return false;
        return description != null ? description.equals(diagnosis.description) : diagnosis.description == null;
    }

    @Override
    public int hashCode() {
        int result = idDiagnosis != null ? idDiagnosis.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "idDiagnosis=" + idDiagnosis +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
