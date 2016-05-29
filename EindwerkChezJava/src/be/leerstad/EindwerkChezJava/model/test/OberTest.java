package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.ObersTableStatus;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
import be.leerstad.EindwerkChezJava.model.Table;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class OberTest
{
	private Ober ober1;
	private Ober ober2;
	private Ober ober3;
	private Table table1;
	private Order o1;
	private Order o2;
	private Liquid l1;
	private Liquid l2;
	@Before
	public void setUp() throws Exception {
		ober1 = new Ober(1, "D'hollander", "Wouter", "password");
		ober2 = new Ober(2, "Demuynck", "Fleur", "password");
		ober3 = new Ober(1, "Dhollander", "Wouter", "password");
		
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0); 
		
		o1 = new Order(l1, 2, ober1);
		o2 = new Order(l2, 2, ober1);	
		table1 = new Table(1);
	}
	@Test
	public void testTableStatus() throws QuantityToLowException
	{
		ObersTableStatus status = ober1.tableStatus(table1);
		assertEquals(ObersTableStatus.FREE, status);
		ober1.makeOrder(l1, 2, table1);
		status = ober1.tableStatus(table1);
		assertEquals(ObersTableStatus.ACTIVE, status);
		status = ober2.tableStatus(table1);
		assertEquals(ObersTableStatus.NOTALLOWED, status);
	}

	
	@Test
	public void testMakeOrder() throws QuantityToLowException
	{
		boolean isAllowed = ober1.makeOrder(l1, 2, table1);
		assertTrue(isAllowed);
		isAllowed = ober1.makeOrder(l1, 2, table1);
		assertTrue(isAllowed);
		isAllowed = ober1.makeOrder(l1, 2, table1);
		assertTrue(isAllowed);
		boolean isNotAllowed = ober2.makeOrder(l1, 2, table1);
		assertFalse(isNotAllowed);
		isNotAllowed = ober2.makeOrder(l1, 2, new Table(-5));
		assertFalse(isNotAllowed);
	}
	
	@Test
	public void testRemoveOrder() throws QuantityToLowException
	{
		ober1.makeOrder(l1, 2, table1);
		ober1.makeOrder(l1, 2, table1);
		boolean isAllowed = ober1.removeOrder(o1, table1);
		assertTrue(isAllowed);
		
		boolean isNotAllowed = ober1.removeOrder(o1, table1);
		assertTrue(isNotAllowed);
	}
	
	@Test
	public void testPayOrders() throws QuantityToLowException
	{
		OrderSet orders = new OrderSet();
		orders.add(o1);
		orders.add(o1);
		orders.add(o2);
		ober1.makeOrder(l1, 2, table1);
		ober1.makeOrder(l1, 2, table1);
		ober1.makeOrder(l2, 2, table1);

		assertEquals(orders.printOutPayment(), ober1.payOrders(table1));
		OrderSet ordersetOber1 = ober1.getPayedOrders();
		System.out.println(ordersetOber1.toString().equals(orders.toString()));
		System.out.println(orders.equals(ordersetOber1));
		assertEquals(ordersetOber1, orders);
		assertEquals(0, table1.getOrders().size());
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
