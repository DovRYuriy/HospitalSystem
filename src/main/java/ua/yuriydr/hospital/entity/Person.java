package ua.yuriydr.hospital.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for hospital.person table.
 */
public class Person {

    private Long idPerson;
    private Long idChamber;
    private Role role;
    private String name;
    private String surname;
    private Date birthday;
    private String phone;
    private String email;
    private String password;

    private List<PersonDiagnosis> personDiagnosisList;

    public Person() {
        personDiagnosisList = new ArrayList<>();
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdChamber() {
        return idChamber;
    }

    public void setIdChamber(Long idChamber) {
        this.idChamber = idChamber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonDiagnosis> getPersonDiagnosisList() {
        return personDiagnosisList;
    }

    public void setPersonDiagnosisList(List<PersonDiagnosis> personDiagnosisList) {
        this.personDiagnosisList = personDiagnosisList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (idPerson != null ? !idPerson.equals(person.idPerson) : person.idPerson != null) return false;
        if (idChamber != null ? !idChamber.equals(person.idChamber) : person.idChamber != null) return false;
        if (role != null ? !role.equals(person.role) : person.role != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (password != null ? !password.equals(person.password) : person.password != null) return false;
        return personDiagnosisList != null ? personDiagnosisList.equals(person.personDiagnosisList) : person.personDiagnosisList == null;
    }

    @Override
    public int hashCode() {
        int result = idPerson != null ? idPerson.hashCode() : 0;
        result = 31 * result + (idChamber != null ? idChamber.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (personDiagnosisList != null ? personDiagnosisList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", idChamber=" + idChamber +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", personDiagnosisList=" + personDiagnosisList +
                '}';
    }
}
