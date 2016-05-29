package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
