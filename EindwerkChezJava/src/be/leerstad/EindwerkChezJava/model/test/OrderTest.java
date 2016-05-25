package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.Exceptions.TableNotAllowedException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;

public class OrderTest {

	private static final float PRECISION = 0.01F;
	private Liquid l1;
	private Liquid l2;
	private Order o1;
	private Order o2;
	private Order o3;
	private Ober ober1;
	@Before
	public void setUp() throws Exception {
		ober1 = new Ober(1, "D'hollander", "Wouter", "password");
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0);
		o1 = new Order(l1, 2, ober1);
		o2 = new Order(l2, 2, ober1);
		o3 = new Order(l1, 2, ober1);
	}



	@Test
	public void testOrder() {
		assertEquals(2, o1.getQuantity(),PRECISION);
	}
	
	@Test
	public void testGetOber()
	{
		assertTrue(o1.getOber().equals(ober1));
	}
	@Test
	public void testGetPrice() {
		assertEquals(4, o1.getPrice(),PRECISION);
		assertEquals(6, o2.getPrice(),PRECISION);
	}

	@Test 
	public void testToString() {
		assertEquals("2 x Cola(2.0€) = 4.0€", o1.toString());
	}

	@Test
	public void testGetLiquid() {
		assertTrue(o1.getLiquid().equals(l1));
		assertFalse(o2.getLiquid().equals(l1));
	}

	@Test
	public void testGetQuantity() {
		assertEquals(2, o1.getQuantity());
	}

	@Test
	public void testSetQuantity() throws QuantityToLowException, QuantityZeroException {

		o1.setQuantity(4);

		assertEquals(4, o1.getQuantity());
	}

	@Test (expected = QuantityToLowException.class)
	public void testSetQuantityInvallidQuantityLow() throws QuantityToLowException {
		try {
			o1.setQuantity(-2);
		} catch (QuantityZeroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(4, o1.getQuantity());
	}
	
	@Test (expected = QuantityZeroException.class)
	public void testSetQuantityInvallidQuantityZero() throws QuantityZeroException, QuantityToLowException {

			o1.setQuantity(0);

		assertEquals(4, o1.getQuantity());
	}
	
	@Test
	public void testGetDate() {
		LocalDate now = LocalDate.now();
		
		assertEquals(now, o1.getDate());
	}

	@Test
	public void testEqualsObject() {
		// Rule 1: reflexive
		assertTrue(o1.equals(o1));
		// Rule 2: transitive
		assertTrue(o3.equals(o1));
		// Rule 3: symmetric
		assertTrue(o3.equals(o1));
		
		// Rule 4: null
		assertFalse(o1.equals(null));
		// Rule 5: hashcode
		assertEquals(o1.hashCode(), o3.hashCode());
		
		
		assertFalse(o2.equals(o1));
		
		assertFalse(o1.equals("a string"));
		
	}
	@Test
	public void testHashCode() {

		assertEquals(o1.hashCode(), o1.hashCode());
		assertEquals(o3.hashCode(), o1.hashCode());
		assertNotEquals(o3.hashCode(), o2.hashCode());
	}

}
