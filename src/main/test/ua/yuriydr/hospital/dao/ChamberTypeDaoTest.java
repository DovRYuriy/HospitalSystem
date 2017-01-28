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
import ua.yuriydr.hospital.entity.ChamberType;
import ua.yuriydr.hospital.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class ChamberTypeDaoTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @PrepareForTest(DatabaseManager.class)
    @Test
    public void testFindAll() throws SQLException {
        List<ChamberType> expectedResult = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            expectedResult.add(EntitiesGenerator.createChamberType(i));
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
                return expectedResult.get(i++).getIdChamberType();
            }
        });
        when(resultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedResult.get(i++).getChamberName();
            }
        });

        ChamberTypeDao chamberTypeDao = MySqlDaoFactory.getChamberTypeDao();
        List<ChamberType> actualResult = chamberTypeDao.findAll();

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(5)).next();

        assertEquals(actualResult, expectedResult);

    }

}
