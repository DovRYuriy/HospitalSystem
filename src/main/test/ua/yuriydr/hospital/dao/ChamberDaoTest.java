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
import ua.yuriydr.hospital.entity.Chamber;
import ua.yuriydr.hospital.entity.ChamberType;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ChamberDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testInsertChamber() throws SQLException{

        PowerMockito.mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        Chamber chamber = new Chamber();
        chamber.setMaxCount(1L);
        chamber.setNumber(1L);
        chamber.setChamberType(new ChamberType());
        chamber.getChamberType().setIdChamberType(1L);

        ChamberDao chamberDao = MySqlDaoFactory.getChamberDao();
        assertTrue(chamberDao.insertChamber(chamber));

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(1)).setLong(1, chamber.getMaxCount());
        verify(preparedStatement, times(1)).setLong(2, chamber.getNumber());
        verify(preparedStatement, times(1)).setLong(3, chamber.getChamberType().getIdChamberType());

        verify(preparedStatement).executeUpdate();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(chamber.getIdChamber(), new Long(0));

    }

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void findAll() throws SQLException{

        List<Chamber> expectedResult = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expectedResult.add(EntitiesGenerator.createChamber(i));
        }

        PowerMockito.mockStatic(DatabaseManager.class);

        PowerMockito.when(DatabaseManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        when(resultSet.getLong(1)).then(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getIdChamber();
            }
        });

        when(resultSet.getLong(2)).then(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getMaxCount();
            }
        });

        when(resultSet.getLong(3)).then(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getNumber();
            }
        });

        when(resultSet.getLong(4)).then(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getChamberType().getIdChamberType();
            }
        });

        when(resultSet.getString(5)).then(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getChamberType().getChamberName();
            }
        });

        ChamberDao chamberDao = MySqlDaoFactory.getChamberDao();
        List<Chamber> actualResult = chamberDao.findAll();

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(5)).next();

        assertEquals(actualResult, expectedResult);

    }

}
