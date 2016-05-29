package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;

public class QuantityZeroExceptionTest {

	@Test
	public void testQuantityZeroException() {
		QuantityZeroException ex = new QuantityZeroException();
		assertEquals("Quantity is zero!", ex.getMessage());
	}

	@Test
	public void testQuantityZeroExceptionString() {
		QuantityZeroException ex = new QuantityZeroException("special exception");
		assertEquals("special exception", ex.getMessage());
	}

}
