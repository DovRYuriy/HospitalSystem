package ua.yuriydr.hospital.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.yuriydr.hospital.dao.mysql.MySqlDaoFactory;
import ua.yuriydr.hospital.entity.Diagnosis;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class DiagnosisDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    public DiagnosisDaoTest() {

    }

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testInsertDiagnosis() throws SQLException {

        PowerMockito.mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName("Diagnosis");
        diagnosis.setDescription("Description");

        DiagnosisDao diagnosisDao = MySqlDaoFactory.getDiagnosisDao();
        assertTrue(diagnosisDao.insertDiagnosis(diagnosis));

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(1)).setString(1, diagnosis.getName());
        verify(preparedStatement, times(1)).setString(2, diagnosis.getDescription());

        verify(preparedStatement).executeUpdate();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(diagnosis.getIdDiagnosis(), new Long(0));

    }

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testFindAll() throws SQLException {

        List<Diagnosis> expectedResult = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedResult.add(EntitiesGenerator.createDiagnosis(i));
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
                return expectedResult.get(i++).getIdDiagnosis();
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
                return expectedResult.get(i++).getDescription();
            }
        });

        DiagnosisDao diagnosisDao = MySqlDaoFactory.getDiagnosisDao();
        List<Diagnosis> actualResult = diagnosisDao.findAll();

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(5)).next();

        assertEquals(expectedResult, actualResult);

    }

}
