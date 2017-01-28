package ua.yuriydr.hospital.entity;

/**
 * Entity for hospital.prescription table.
 */
public class Prescription {

    private Long idPrescription;
    private String drugs;
    private String procedure;
    private String operation;

    public Prescription() {
    }

    public Long getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(Long idPrescription) {
        this.idPrescription = idPrescription;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescription that = (Prescription) o;

        if (idPrescription != null ? !idPrescription.equals(that.idPrescription) : that.idPrescription != null)
            return false;
        if (drugs != null ? !drugs.equals(that.drugs) : that.drugs != null) return false;
        if (procedure != null ? !procedure.equals(that.procedure) : that.procedure != null) return false;
        return operation != null ? operation.equals(that.operation) : that.operation == null;
    }

    @Override
    public int hashCode() {
        int result = idPrescription != null ? idPrescription.hashCode() : 0;
        result = 31 * result + (drugs != null ? drugs.hashCode() : 0);
        result = 31 * result + (procedure != null ? procedure.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "idPrescription=" + idPrescription +
                ", drugs='" + drugs + '\'' +
                ", procedure='" + procedure + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
