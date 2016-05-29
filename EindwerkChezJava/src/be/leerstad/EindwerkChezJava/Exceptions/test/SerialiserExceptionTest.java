package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.SerialiserException;

public class SerialiserExceptionTest {

	@Test
	public void testSerialiserException() {
		SerialiserException ex = new SerialiserException();
		assertEquals("Error With Serialiser", ex.getMessage());
	}

	@Test
	public void testSerialiserExceptionString() {
		SerialiserException ex = new SerialiserException("specal error serializer");
		assertEquals("specal error serializer", ex.getMessage());
	}

}
