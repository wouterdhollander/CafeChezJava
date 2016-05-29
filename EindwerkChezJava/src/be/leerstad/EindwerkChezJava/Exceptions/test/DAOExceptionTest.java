package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class DAOExceptionTest {

	@Test
	public void testDAOException() {
		DAOException ex = new DAOException();
		assertEquals("Error With Database", ex.getMessage());
	}

	@Test
	public void testDAOExceptionString() {
		DAOException ex = new DAOException("specal error Database");
		assertEquals("specal error Database", ex.getMessage());
	}

}
