package be.leerstad.EindwerkChezJava.database.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.database.*;

public class BaseDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetConnection() throws DAOException {
		BaseDAO baseDao = new BaseDAO();

		Connection con = baseDao.getConnection();
		assertTrue(con != null);

	}

}
