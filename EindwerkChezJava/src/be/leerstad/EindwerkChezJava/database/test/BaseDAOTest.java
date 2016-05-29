package be.leerstad.EindwerkChezJava.database.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.database.BaseDAO;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
