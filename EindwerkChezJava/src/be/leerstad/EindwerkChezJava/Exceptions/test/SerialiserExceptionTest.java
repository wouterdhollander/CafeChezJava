package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.SerialiserException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
