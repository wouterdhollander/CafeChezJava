package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
