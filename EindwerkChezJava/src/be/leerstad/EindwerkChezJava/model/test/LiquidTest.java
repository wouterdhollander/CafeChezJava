package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.model.Liquid;


/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class LiquidTest {
	private Liquid l1;
	private Liquid l2;
	private Liquid l3;
	private static final float PRECISION = 0.01F;

   
   @Before
   public void init() {
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0);   
		l3 = new Liquid(1, "Cola", 2.0); 
   }

	@Test
	public void testLiquid() {
		assertEquals(2, l1.getId(),1);
	}

	@Test
	public void testGetId() {
		assertEquals(1, l1.getId(), PRECISION);;
	}

	@Test
	public void testGetLiquidName() {
		assertEquals("Cola",l1.getLiquidName());
	}

	@Test
	public void testGetPrice() {
		 assertEquals(2,l1.getPrice(),PRECISION);
	}
	
	@Test
	public void testHashCode() {

		assertEquals(l1.hashCode(), l1.hashCode());
		assertEquals(l3.hashCode(), l1.hashCode());
		assertNotEquals(l3.hashCode(), l2.hashCode());
	}
	@Test
	public void testEqualsObject() {
		// Rule 1: reflexive
		assertTrue(l1.equals(l1));
		// Rule 2: transitive
		assertTrue(l3.equals(l1));
		// Rule 3: symmetric
		assertTrue(l3.equals(l1));
		
		// Rule 4: null
		assertFalse(l1.equals(null));
		// Rule 5: hashcode
		assertEquals(l1.hashCode(), l3.hashCode());
		
		
		assertFalse(l2.equals(l1));
		
		assertFalse(l1.equals("a string"));
		
	}
	@Test 
	public void testToString() {
		assertEquals("Cola(2.00€)", l1.toString());
	}

}
