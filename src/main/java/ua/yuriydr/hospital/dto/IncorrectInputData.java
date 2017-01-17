package ua.yuriydr.hospital.dto;

/**
 * This class represents data transfer object for incorrect data
 * from a registration form.
 */
public class IncorrectInputData {

    private String incorrectName;
    private String incorrectSurname;
    private String incorrectPhone;
    private String incorrectEmail;
    private String incorrectDiagnosis;
    private String incorrectDate;

    private IncorrectInputData() {

    }

    public String getIncorrectName() {
        return incorrectName;
    }

    public String getIncorrectSurname() {
        return incorrectSurname;
    }

    public String getIncorrectPhone() {
        return incorrectPhone;
    }

    public String getIncorrectEmail() {
        return incorrectEmail;
    }

    public String getIncorrectDiagnosis() {
        return incorrectDiagnosis;
    }

    public String getIncorrectDate() {
        return incorrectDate;
    }

    public static Builder newBuilder(String attribute) {
        return new IncorrectInputData().new Builder(attribute);
    }

    /**
     * Class that building dto object.
     */
    public class Builder {
        private final String attribute;

        private Builder(String attribute) {
            this.attribute = attribute;
        }

        public Builder setIncorrectNameAttribute() {
            IncorrectInputData.this.incorrectName = attribute;
            return this;
        }

        public Builder setIncorrectSurnameAttribute() {
            IncorrectInputData.this.incorrectSurname = attribute;
            return this;
        }

        public Builder setIncorrectPhoneAttribute() {
            IncorrectInputData.this.incorrectPhone = attribute;
            return this;
        }

        public Builder setIncorrectEmailAttribute() {
            IncorrectInputData.this.incorrectEmail = attribute;
            return this;
        }

        public Builder setIncorrectDiagnosisAttribute() {
            IncorrectInputData.this.incorrectDiagnosis = attribute;
            return this;
        }

        public Builder setIncorrectDateAttribute() {
            IncorrectInputData.this.incorrectDate = attribute;
            return this;
        }

        public IncorrectInputData build() {
            return IncorrectInputData.this;
        }
    }

}
