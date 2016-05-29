package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.*;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;

public class QuantityToLowExceptionTest {

	@Test
	public void testQuantityToLowException() {
		QuantityToLowException ex = new QuantityToLowException();
		assertEquals("Quantity too low!", ex.getMessage());
	}

	@Test
	public void testQuantityToLowExceptionString() {
		QuantityToLowException ex = new QuantityToLowException("Special");
		assertEquals("Special", ex.getMessage());
	}

}
