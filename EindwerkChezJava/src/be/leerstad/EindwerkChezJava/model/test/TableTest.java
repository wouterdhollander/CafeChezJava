package be.leerstad.EindwerkChezJava.model.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.Table;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
		System.out.println(t1.getActiveOber());
		assertTrue(new Ober().equals(t1.getActiveOber()));
		t1.getOrders().add(o1);

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

		assertEquals("Table [id=1, orders=[1 x Cola(2.00€) = 2.00€], activeOber=Ober: Peters Wout]", t1.toString());
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
