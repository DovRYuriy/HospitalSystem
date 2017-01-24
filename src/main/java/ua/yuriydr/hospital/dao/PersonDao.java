package ua.yuriydr.hospital.dao;

import ua.yuriydr.hospital.dao.mysql.impl.MySqlPersonDao;
import ua.yuriydr.hospital.model.Person;

import java.util.List;

/**
 * Represents methods to manage persons in the hospital.
 *
 * @see MySqlPersonDao
 */
public interface PersonDao {

    /**
     * Returns a list of persons.
     *
     * @return a list of persons.
     */
    List<Person> findAll();

    /**
     * Return a list of persons by their role.
     *
     * @return a list of persons by their role.
     */
    List<Person> findAllByRole(String role);

    /**
     * Return a list of healthy persons.
     *
     * @return a list of healthy persons.
     */
    List<Person> findAllHealthyPatients();

    /**
     * Returns a list of persons by their role.
     *
     * @param idChamber chamber's id.
     * @return a list of persons in chamber.
     */
    List<Person> findAllInSpecificChamber(Long idChamber);

    /**
     * Returns person by his id.
     *
     * @param id person's id.
     * @return Person.
     */
    Person findPersonById(Long id);

    /**
     * Update person.
     *
     * @param person that need to change.
     * @return true if person successfully updated.
     */
    boolean updatePerson(Person person);

    /**
     * Delete person.
     *
     * @param person that need to delete.
     * @return true if person successfully deleted.
     */
    boolean deletePerson(Person person);

    /**
     * Add new person.
     *
     * @param person that need to add.
     * @return true if diagnosis successfully updated.
     */
    boolean insertPerson(Person person);

    /**
     * Checks that person is in system.
     *
     * @param email    unique email for every person.
     * @param password person's password in system.
     * @return true if person is in system.
     */
    Person login(String email, String password);

    /**
     * Checks that email is in system.
     *
     * @param email that need to check.
     * @return true if email exists.
     */
    boolean checkEmail(String email);

    /**
     * Changes person's password.
     *
     * @param person whose password you want to change.
     * @return true if password was successfully changed.
     */
    boolean changePersonPassword(Person person);

    /**
     * Discharge patient from hospital
     *
     * @param patient to discharge.
     * @return boolean to discharge.
     */
    boolean updateChamber(Person patient);


}
