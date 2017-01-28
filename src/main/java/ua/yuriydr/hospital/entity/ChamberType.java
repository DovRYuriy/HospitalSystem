package ua.yuriydr.hospital.entity;


/**
 * Entity for hospital.chamber_type table.
 */
public class ChamberType {

    private Long idChamberType;
    private String chamberName;

    public ChamberType() {

    }

    public Long getIdChamberType() {
        return idChamberType;
    }

    public void setIdChamberType(Long idChamberType) {
        this.idChamberType = idChamberType;
    }

    public String getChamberName() {
        return chamberName;
    }

    public void setChamberName(String chamberName) {
        this.chamberName = chamberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChamberType that = (ChamberType) o;

        if (idChamberType != null ? !idChamberType.equals(that.idChamberType) : that.idChamberType != null)
            return false;
        return chamberName != null ? chamberName.equals(that.chamberName) : that.chamberName == null;
    }

    @Override
    public int hashCode() {
        int result = idChamberType != null ? idChamberType.hashCode() : 0;
        result = 31 * result + (chamberName != null ? chamberName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChamberType{" +
                "idChamberType=" + idChamberType +
                ", chamberName='" + chamberName + '\'' +
                '}';
    }

}
