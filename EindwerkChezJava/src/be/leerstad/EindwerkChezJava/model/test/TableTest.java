package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.model.*;
import be.leerstad.EindwerkChezJava.Exceptions.*;

public class TableTest {
	private static final float PRECISION = 0.01F;
	private Liquid l1;

	private Order o1;
	private Ober ober1;
	private Table t1;
	@Before
	public void setUp() throws Exception {
		l1 = new Liquid(1, "Cola", 2.00);
		ober1 = new Ober(1, "Peters", "Wout","password");
		t1 = new Table(1);
		o1 = new Order(l1, 1, ober1);
	}
	
	@Test
	public void testGetActiveOber(){
		t1.setActiveOber(ober1);

		assertTrue(ober1.equals(t1.getActiveOber()));
	}
	
	@Test
	public void testSetActiveOber(){
		t1.setActiveOber(ober1);

		assertTrue(ober1.equals(t1.getActiveOber()));
	}
	
	@Test
	public void testGetOrders() {
		Set<Order> orders = new HashSet<Order>();
		Order o1;
		try {
			o1 = new Order(l1, 1, ober1);
			t1.getOrders().add(o1);

			orders.add(o1);
			assertEquals(orders, t1.getOrders());
		} catch (QuantityToLowException | QuantityZeroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testToString()
	{
		t1.getOrders().add(o1);
		t1.setActiveOber(ober1);
		System.out.println(t1);
		assertTrue(t1.toString().equals("Table [id=1, orders=[1 x Cola(2.0€) = 2.0€], activeOber=Ober: Peters Wout]"));

	}

	@Test
	public void testGetId() {
		assertEquals(1,t1.getId());
	}
	@Test
	public void testEqualsObject() {
		Table t1 = new Table(1);
		Table t2 = new Table(2);
		Table t3 = new Table(1);
		t3.getOrders().add(o1);
		// Rule 1: reflexive
		assertTrue(t1.equals(t1));
		// Rule 2: transitive
		assertTrue(t3.equals(t1));
		// Rule 3: symmetric
		assertTrue(t3.equals(t1));
		
		// Rule 4: null
		assertFalse(t1.equals(null));
		// Rule 5: hashcode
		assertEquals(t1.hashCode(), t3.hashCode());
		
		assertEquals(1, t3.getOrders().size());	
		
		assertFalse(t2.equals(t1));
		
		assertFalse(t1.equals("a string"));
		
	}
	@Test
	public void testHashCode() {
		Table t1 = new Table(1);
		Table t2 = new Table(2);
		Table t3 = new Table(1);
		assertEquals(t1.hashCode(), t1.hashCode());
		assertEquals(t3.hashCode(), t1.hashCode());
		assertNotEquals(t3.hashCode(), t2.hashCode());
	}
}
