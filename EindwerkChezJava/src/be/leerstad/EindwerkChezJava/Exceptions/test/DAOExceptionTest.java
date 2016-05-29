package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;

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
