package ua.yuriydr.hospital.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.entity.Role;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class PersonDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testInsertUser() throws SQLException {

        PowerMockito.mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        Person person = new Person();
        person.setName("Name");
        person.setSurname("Surname");
        person.setPhone("1234567890");
        person.setEmail("Email");
        person.setPassword("Password");
        person.setBirthday(Date.valueOf(LocalDate.now()));
        person.setIdChamber(1L);
        person.setRole(new Role());
        person.getRole().setIdRole(1L);

        PersonDao personDao = MySqlDaoFactory.getPersonDao();
        assertTrue(personDao.insertPerson(person));

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(1)).setString(1, person.getName());
        verify(preparedStatement, times(1)).setString(2, person.getSurname());
        verify(preparedStatement, times(1)).setDate(3, person.getBirthday());
        verify(preparedStatement, times(1)).setString(4, person.getPhone());
        verify(preparedStatement, times(1)).setString(5, person.getEmail());
        verify(preparedStatement, times(1)).setString(6, person.getPassword());
        verify(preparedStatement, times(1)).setLong(7, person.getIdChamber());
        verify(preparedStatement, times(1)).setLong(8, person.getRole().getIdRole());

        verify(preparedStatement).executeUpdate();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(person.getIdPerson(), new Long(0));
    }

    @PrepareForTest(DatabaseManager.class)
    @Test(expected = NullPointerException.class)
    public void testNullInsert() {
        PowerMockito.mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        PersonDao personDao = MySqlDaoFactory.getPersonDao();
        assertTrue(personDao.insertPerson(null));
    }

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testFindAll() throws SQLException {
        List<Person> expectedResult = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            expectedResult.add(EntitiesGenerator.createPerson(i));
        }

        PowerMockito.mockStatic(DatabaseManager.class);

        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        when(resultSet.getLong(1)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getIdPerson();
            }
        });

        when(resultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getName();
            }
        });

        when(resultSet.getString(3)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getSurname();
            }
        });

        when(resultSet.getDate(4)).thenAnswer(new Answer<Date>() {
            int i = 0;

            @Override
            public Date answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getBirthday();
            }
        });

        when(resultSet.getString(5)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPhone();
            }
        });

        when(resultSet.getString(6)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getEmail();
            }
        });

        when(resultSet.getString(7)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPassword();
            }
        });

        when(resultSet.getLong(8)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getRole().getIdRole();
            }
        });

        when(resultSet.getString(9)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getRole().getName();
            }
        });

        when(resultSet.getLong(10)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getIdChamber();
            }
        });


        PersonDao personDao = MySqlDaoFactory.getPersonDao();
        List<Person> actualResult = personDao.findAll();

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(5)).next();

        assertEquals(actualResult, expectedResult);
    }

}
