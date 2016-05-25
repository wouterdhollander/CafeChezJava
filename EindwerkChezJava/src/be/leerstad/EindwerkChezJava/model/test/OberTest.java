package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.model.*;

public class OberTest
{
	private Ober ober1;
	private Ober ober2;
	private Ober ober3;
	@Before
	public void setUp() throws Exception {
		ober1 = new Ober(1, "D'hollander", "Wouter", "password");
		ober2 = new Ober(2, "Demuynck", "Fleur", "password");
		ober3 = new Ober(1, "Dhollander", "Wouter", "password");


	}

	@Test
	public void testEqualsObject() {
		// Rule 1: reflexive
		assertTrue(ober1.equals(ober1));
		// Rule 2: transitive
		assertTrue(ober3.equals(ober1));
		// Rule 3: symmetric
		assertTrue(ober3.equals(ober1));
		
		// Rule 4: null
		assertFalse(ober1.equals(null));
		// Rule 5: hashcode
		assertEquals(ober1.hashCode(), ober3.hashCode());
		
		
		assertFalse(ober2.equals(ober1));
		
		assertFalse(ober1.equals("a string"));
		
	}
	@Test
	public void testHashCode() {

		assertEquals(ober1.hashCode(), ober1.hashCode());
		assertEquals(ober3.hashCode(), ober1.hashCode());
		assertNotEquals(ober3.hashCode(),ober2.hashCode());
	}

	@Test
	public void testOber() {
		assertEquals(1, ober1.getId());
		assertEquals(2, ober2.getId());
	}
	@Test
	public void testToString() {
		assertEquals("Ober: D'hollander Wouter", ober1.toString());;
	}

	//@Test
	public void testGetTables() {
		fail("Not yet implemented");
	}


	@Test
	public void testGetId() {
		assertEquals(1, ober1.getId());
	}


	@Test
	public void testGetLastName() {
		assertEquals("D'hollander", ober1.getLastName());
	}

	@Test
	public void testGetFirstName() {
		assertEquals("Wouter", ober1.getFirstName());
		assertEquals("Fleur", ober2.getFirstName());
	}

	//@Test
	public void testGetPassword() {
		fail("Not yet implemented");
	}

}
