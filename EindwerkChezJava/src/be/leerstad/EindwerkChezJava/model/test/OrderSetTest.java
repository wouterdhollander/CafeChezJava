package be.leerstad.EindwerkChezJava.model.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class OrderSetTest {
	private static final float PRECISION = 0.01F;
	private Liquid l1;
	private Liquid l2;
	private Order o1;
	private Order o1_2;
	private Order o2;
	private Order o3;
	private Ober ober1;
	private Ober ober2;
	private OrderSet orders;
	@Before
	public void setUp() throws QuantityToLowException, QuantityZeroException {
		ober1 = new Ober(1, "Peters", "Wout","password");
		ober2 = new Ober(2, "Segers", "Nathalie", "password");
		//cafe.setActiveOber(ober1);
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0); 

		o1 = new Order(l1, 2, ober1);
		o1_2 = new Order(l1, 3, ober1);
		o2 = new Order(l2, 2, ober1);
		o3 = new Order(l1, 1, ober2);

		orders = new OrderSet();
	}
	
	@Test
	public void testCalculateOrders() {
		orders.add(o1);
		assertEquals(4, orders.calcutateOrders(),PRECISION);
		orders.add(o1);
		assertEquals(8, orders.calcutateOrders(),PRECISION);
		orders.add(o2);
		assertEquals(14, orders.calcutateOrders(),PRECISION);
		
	}

	@Test
	public void testPrintOutPayment() {
		assertEquals("geen bestellingen!", orders.printOutPayment());
		orders.add(o1);
		orders.add(o1);
		orders.add(o2);
		String expected = "Tafel besteld door Ober: Peters Wout" + "\n" + "4 x Cola(2.00€) = 8.00€" + "\n" + "2 x Bier(3.00€) = 6.00€"+ "\n" + "totaal (€) = 14.00";
		
		assertEquals(expected, orders.printOutPayment());	
	}
	
	@Test
	public void testAdd() {
		assertTrue(orders.add(o1));
		assertEquals(1, orders.size());
		assertTrue(orders.add(o1_2));
		assertEquals(1, orders.size());
		
		//assertEquals(5, orders.get(0).getQuantity());
		assertTrue(orders.add(o2));
		assertEquals(2, orders.size());
		assertTrue(orders.add(o3));
		assertEquals(3, orders.size());//zelfde drank andere ober
		assertFalse(orders.add(null));
		
	}
	@Test
	public void testRemove() {
		orders.add(o1);
		orders.add(o1_2);
		assertFalse(orders.remove(o2));
		assertFalse(orders.remove(null));
		assertFalse(orders.remove(l1));
		assertTrue(orders.remove(o1_2));
		//orders.add(o1);
		assertFalse(orders.remove(o1_2)); //men kan niet meer verwijderen dan wat erin zit.
	}
	
	@Test
	public void testAddAll() {
		List<Order> ordersarraylist= new ArrayList<>();
		ordersarraylist.add(o1);
		ordersarraylist.add(o1_2);
		
		orders.addAll(ordersarraylist);
		assertEquals(1, orders.size());
		ordersarraylist.add(null);
		ordersarraylist.add(o2);
		ordersarraylist.add(o2);
		orders.addAll(ordersarraylist);
		assertEquals(2, orders.size());
		ordersarraylist.add(o1);
		orders.addAll(ordersarraylist);
		assertEquals(2, orders.size());
		//assertEquals(5, orders.get(0).getQuantity());
	}
	
	@Test
	public void testRemoveAll() {
		List<Order> ordersarraylist= new ArrayList<>();
		ordersarraylist.add(o1);
		ordersarraylist.add(o1_2);
		orders.add(o1);
		orders.add(o1_2);
		orders.add(o1);
		orders.add(o1_2);
		assertTrue(orders.removeAll(ordersarraylist));
		assertEquals(1, orders.size());
		ordersarraylist.add(o1);
		ordersarraylist.add(o1_2);
		ordersarraylist.add(o1);
		ordersarraylist.add(o1_2);
		assertFalse(orders.removeAll(ordersarraylist));
		assertEquals(1, orders.size());

		List<Order> ordersarraylist2= new ArrayList<>();//gemengd

		ordersarraylist2.add(o1);
		ordersarraylist2.add(o1_2);
		ordersarraylist2.add(o2);
		OrderSet orders2 = new OrderSet();
		orders2.add(o1);
		orders2.add(o1_2);
		assertFalse(orders.removeAll(ordersarraylist2));
		
		//assertEquals(5, orders.get(0).getQuantity());
	}

}
