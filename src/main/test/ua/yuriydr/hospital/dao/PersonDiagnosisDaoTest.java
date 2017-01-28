package ua.yuriydr.hospital.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.entity.Diagnosis;
import ua.yuriydr.hospital.entity.Person;
import ua.yuriydr.hospital.entity.PersonDiagnosis;
import ua.yuriydr.hospital.entity.Prescription;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class PersonDiagnosisDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testInsertPersonDiagnosis() throws SQLException{

        PowerMockito.mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        PersonDiagnosis personDiagnosis = new PersonDiagnosis();
        personDiagnosis.setPatient(new Person());
        personDiagnosis.getPatient().setIdPerson(1L);

        personDiagnosis.setDoctor(new Person());
        personDiagnosis.getDoctor().setIdPerson(1L);

        personDiagnosis.setPrescription(new Prescription());
        personDiagnosis.getPrescription().setIdPrescription(1L);

        personDiagnosis.setDiagnosis(new Diagnosis());
        personDiagnosis.getDiagnosis().setIdDiagnosis(1L);

        personDiagnosis.setDate(Timestamp.valueOf(LocalDateTime.now()));
        personDiagnosis.setDischargeDate(Timestamp.valueOf(LocalDateTime.now()));

        PersonDiagnosisDao personDiagnosisDao = MySqlDaoFactory.getPersonDiagnosisDao();
        personDiagnosisDao.insertPatientDiagnosis(personDiagnosis);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(1)).setLong(1, personDiagnosis.getPatient().getIdPerson());
        verify(preparedStatement, times(1)).setLong(2, personDiagnosis.getDoctor().getIdPerson());
        verify(preparedStatement, times(1)).setLong(3, personDiagnosis.getDiagnosis().getIdDiagnosis());
        verify(preparedStatement, times(1)).setLong(4, personDiagnosis.getPrescription().getIdPrescription());
        verify(preparedStatement, times(1)).setTimestamp(5, personDiagnosis.getDate());
        verify(preparedStatement, times(1)).setTimestamp(6, personDiagnosis.getDischargeDate());

        assertEquals(verify(preparedStatement).executeUpdate(), 0);

    }

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testFindAll() throws SQLException{
        List<PersonDiagnosis> expectedResult = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedResult.add(EntitiesGenerator.createPersonDiagnosis(i));
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
                return expectedResult.get(i++).getPatient().getIdPerson();
            }
        });

        when(resultSet.getLong(2)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDoctor().getIdPerson();
            }
        });

        when(resultSet.getLong(3)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDiagnosis().getIdDiagnosis();
            }
        });

        when(resultSet.getString(4)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDiagnosis().getName();
            }
        });

        when(resultSet.getString(5)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDiagnosis().getDescription();
            }
        });

        when(resultSet.getLong(6)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPrescription().getIdPrescription();
            }
        });

        when(resultSet.getString(7)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPrescription().getDrugs();
            }
        });

        when(resultSet.getString(8)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPrescription().getProcedure();
            }
        });

        when(resultSet.getString(9)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getPrescription().getOperation();
            }
        });

        when(resultSet.getTimestamp(10)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDate();
            }
        });

        when(resultSet.getTimestamp(11)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getDischargeDate();
            }
        });

        PersonDiagnosisDao personDiagnosisDao = MySqlDaoFactory.getPersonDiagnosisDao();
        List<PersonDiagnosis> actualResult = personDiagnosisDao.findAllForNurse();
        for (int i = 0; i < actualResult.size(); i++) {
            actualResult.get(i).setPatient(EntitiesGenerator.createPerson(i));
            actualResult.get(i).setDoctor(EntitiesGenerator.createPerson(i));
        }

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(5)).next();

        assertEquals(expectedResult, actualResult);
    }

}
