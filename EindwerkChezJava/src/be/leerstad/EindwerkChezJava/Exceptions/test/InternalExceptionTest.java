package be.leerstad.EindwerkChezJava.Exceptions.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.InternalException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class InternalExceptionTest {

	@Test
	public void testInternalException() {
		InternalException ex = new InternalException();
		assertEquals("An internal error occurred!" + "\n" + "Please report this so we can fix this", ex.getMessage());
	}

	@Test
	public void testInternalExceptionString() {
		InternalException ex = new InternalException("special error internal");
		assertEquals("special error internal", ex.getMessage());
	}

	@Test
	public void testGetMessage() {
		InternalException ex = new InternalException("special error");
		assertEquals("special error", ex.getMessage());
	}

	@Test
	public void testToString() {
		InternalException ex = new InternalException("special error");
		assertEquals("special error", ex.toString());
	}

}
