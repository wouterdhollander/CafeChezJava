package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
import be.leerstad.EindwerkChezJava.database.test.DBInitialiser;
import be.leerstad.EindwerkChezJava.model.*;

public class CafeTest {
	private static final float PRECISION = 0.01F;
	private Cafe cafe;
	private Liquid l1;
	private Liquid l2;
	private Order o1;
	private Order o2;
	private Order o3;
	private Ober ober1;
	private Ober ober2;
	private List<Table> Tables = new ArrayList<>(); 
	@Before
	public void setUp() throws Exception {	
		MakeSerialiseClean();
		cafe = new Cafe();
		
		ober1 = new Ober(1, "Peters", "Wout","password");
		ober2 = new Ober(2, "Segers", "Nathalie", "password");
		//cafe.setActiveOber(ober1);
		cafe.login("Peters", "Wout","password");
		Tables.addAll(cafe.getTables());
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0); 
		
		o1 = new Order(l1, 2, ober1);
		o2 = new Order(l2, 2, ober1);
		o3 = new Order(l1, 1, ober2);	
	}
	
	@Test
	public void testGetTables() 
	{
		assertEquals(9, cafe.getTables().size());	 
	}
	
	@Test
	public void testSetActiveTable()
	{
		cafe.setActiveTable(Tables.get(1));
		assertEquals(Tables.get(1), cafe.getActiveTable());
	}

	@Test
	public void testGetActiveTable()
	{
		cafe.setActiveTable(Tables.get(1));
		assertEquals(Tables.get(1), cafe.getActiveTable());
	}
	
	@Test
	public void testGetActiveOber() {
		assertEquals(1, cafe.getActiveOber().getId());
		
	}
	
	@Test
	public void testCalculateUnpayedOrders() throws  QuantityToLowException, QuantityZeroException
	{
		cafe.setActiveTable(Tables.get(1));
		Table tableActive = cafe.getActiveTable();
		Ober ober1 = cafe.getActiveOber();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.makeOrder(l1, 2, tableActive);
		assertEquals(8, cafe.calculateUnpayedOrders(),PRECISION);
		assertEquals(8, cafe.calculateUnpayedOrders(ober1),PRECISION);
		
		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.makeOrder(l1, 2, tableActive);
		assertEquals(12, cafe.calculateUnpayedOrders(),PRECISION);
		assertEquals(12, cafe.calculateUnpayedOrders(ober1),PRECISION);
		cafe.setActiveTable(Tables.get(3));
		tableActive = cafe.getActiveTable();
		ober2.makeOrder(l1, 2, tableActive);
		assertEquals(16, cafe.calculateUnpayedOrders(),PRECISION);
		assertEquals(12, cafe.calculateUnpayedOrders(ober1),PRECISION);
		assertEquals(4, cafe.calculateUnpayedOrders(ober2),PRECISION);
		
		ober2.removeOrder(tableActive.getOrders().iterator().next(), tableActive);
		assertEquals(0, cafe.calculateUnpayedOrders(ober2),PRECISION);
		
		assertEquals(12, cafe.calculateUnpayedOrders(),PRECISION);
		
		
		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.payOrders(tableActive);
		assertEquals(8, cafe.calculateUnpayedOrders(),PRECISION);
		assertEquals(8, cafe.calculateUnpayedOrders(ober1),PRECISION);
		
		ober1.payOrders(Tables.get(1));
		assertEquals(0, cafe.calculateUnpayedOrders(),PRECISION);
		assertEquals(0, cafe.calculateUnpayedOrders(ober1),PRECISION);
	}
	
	@Test 	
	public void testGetUnpayedOrders() throws  QuantityToLowException, QuantityZeroException
	{
		OrderSet ordersTotal = new OrderSet();
		OrderSet ordersOber1 = new OrderSet();
		OrderSet ordersOber2 = new OrderSet();
		cafe.setActiveTable(Tables.get(1));
		Table tableActive = cafe.getActiveTable();
		Ober ober1 = cafe.getActiveOber();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.makeOrder(l1, 2, tableActive);
		Order order1 = new Order(l1,2,ober1);
		ordersTotal.add(order1);
		ordersTotal.add(order1);
		ordersOber1.add(order1);
		ordersOber1.add(order1);
		
		assertEquals(ordersTotal, cafe.getUnpayedOrders());
		assertEquals(ordersOber1, cafe.getUnpayedOrders(ober1));

		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.makeOrder(l1, 2, tableActive);
		ordersTotal.add(order1);
		ordersOber1.add(order1);
		assertEquals(ordersTotal, cafe.getUnpayedOrders());
		assertEquals(ordersOber1, cafe.getUnpayedOrders(ober1));
		cafe.setActiveTable(Tables.get(3));
		tableActive = cafe.getActiveTable();
		ober2.makeOrder(l1, 2, tableActive);
		Order order2 = new Order(l1,2,ober2);
		ordersTotal.add(order2);
		ordersOber2.add(order2);
		assertEquals(ordersTotal, cafe.getUnpayedOrders());
		assertEquals(ordersOber2, cafe.getUnpayedOrders(ober2));
		
		ober1.payOrders(Tables.get(1));
		ordersTotal.remove(order1);
		ordersTotal.remove(order1);
		ordersOber1.remove(order1);
		ordersOber1.remove(order1);
		assertEquals(ordersTotal, cafe.getUnpayedOrders());
		assertEquals(ordersOber1, cafe.getUnpayedOrders(ober1));
	}

	@Test 	
	public void testGetPayedOrders() throws QuantityToLowException, QuantityZeroException
	{
		OrderSet ordersTotal = new OrderSet();
		cafe.setActiveTable(Tables.get(1));
		Table tableActive = cafe.getActiveTable();
		Ober ober1 = cafe.getActiveOber();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.makeOrder(l1, 2, tableActive);
		Order order1 = new Order(l1,2,ober1);
		ordersTotal.add(order1);
		ordersTotal.add(order1);
		ober1.payOrders(tableActive);
		assertEquals(ordersTotal, cafe.getPayedOrders());

		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.makeOrder(l1, 3, tableActive);
		Order order2 = new Order(l1,3,ober1);
		ordersTotal.add(order2);
		ober1.payOrders(tableActive);
		OrderSet payedOrders = new OrderSet(cafe.getPayedOrders());
		assertEquals(ordersTotal, payedOrders);

		cafe.setActiveTable(Tables.get(3));
		tableActive = cafe.getActiveTable();
		ober2.makeOrder(l2, 2, tableActive);
		Order order3 = new Order(l2,2,ober2);
		ordersTotal.add(order3);

		assertNotEquals(ordersTotal, cafe.getPayedOrders());
	}
	
	@Test 	
	public void testCalculatePayedOrders() throws QuantityToLowException, QuantityZeroException
	{
		assertEquals(0, cafe.calculatePayedOrders(),PRECISION);
		cafe.setActiveTable(Tables.get(1));
		Table tableActive = cafe.getActiveTable();
		Ober ober1 = cafe.getActiveOber();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.makeOrder(l1, 2, tableActive);
		ober1.payOrders(tableActive);
		assertEquals(8, cafe.calculatePayedOrders(),PRECISION);
		
		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.payOrders(tableActive);
		assertEquals(12, cafe.calculatePayedOrders(),PRECISION);

		cafe.setActiveTable(Tables.get(3));
		tableActive = cafe.getActiveTable();
		ober2.makeOrder(l1, 2, tableActive);
		ober1.payOrders(tableActive);//not allowed
		assertEquals(12, cafe.calculatePayedOrders(),PRECISION);
		
		ober2.removeOrder(tableActive.getOrders().iterator().next(), tableActive);
		assertEquals(12, cafe.calculatePayedOrders(),PRECISION);

	}
	

	
	@Test
	public void testCreatePDF() throws FileNotFoundException, DocumentException
	{
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(o1);
		orders.add(o2);
		orders.add(o3);
		
		String Filename = cafe.createPDF(orders,false);
		  
		File f = new File(Filename);
		assertTrue(f.exists() && !f.isDirectory());
	}
	
	@Test
	public void testSendMail() throws MessagingException
	{
		Boolean bool =	cafe.SendMail("src/be/leerstad/Eindwerk_Project_Chez_Java.pdf");
		assertTrue(bool);
	}
	
	@Test
	public void testLogout(){
		cafe.logOut();
		assertTrue(new Ober().equals(cafe.getActiveOber()));
	}
	
	@Test
	public void testClose() throws QuantityToLowException, QuantityZeroException, DAOException, DAOloginNotAllowed{

		assertEquals(0, cafe.calculatePayedOrders(),PRECISION);
		cafe.setActiveTable(Tables.get(1));
		Table tableActive = cafe.getActiveTable();
		Ober ober1 = cafe.getActiveOber();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.makeOrder(l1, 2, tableActive);
		ober1.payOrders(tableActive);
		assertEquals(8, cafe.calculatePayedOrders(),PRECISION);
		
		cafe.setActiveTable(Tables.get(2));
		tableActive = cafe.getActiveTable();
		ober1.makeOrder(l1, 2, tableActive);
		ober1.payOrders(tableActive);
		assertEquals(12, cafe.calculatePayedOrders(),PRECISION);

		cafe.setActiveTable(Tables.get(3));
		tableActive = cafe.getActiveTable();
		ober2.makeOrder(l1, 2, tableActive);
		
		double unpayedOrders = cafe.calculateUnpayedOrders();
		double unpayedOber1 = cafe.calculateUnpayedOrders(ober1);
		cafe.close();
		Cafe cafe2 = new Cafe();
		cafe2.login("Segers", "Nathalie", "password");
		assertEquals(unpayedOrders, cafe2.calculateUnpayedOrders(),PRECISION);
		assertEquals(cafe.calculateUnpayedOrders(ober1), cafe2.calculateUnpayedOrders(ober1),PRECISION);
		//assertTrue(cafe2.getTables().equals(setTables));
	}
	
	@Test
	public void testLogin() throws DAOException, DAOloginNotAllowed {

		cafe.login("Peters", "Wout","password");
		assertEquals(cafe.getActiveOber(), ober1); 

		cafe.login("Segers", "Nathalie", "password");
		assertEquals(cafe.getActiveOber(), ober2);

		cafe.login("Peters", "Wout","password");
		assertEquals(cafe.getActiveOber(), ober1);
	}

	@After
	public void MakeSerialiseClean()
	{
		Serializer ser = new Serializer();
		ser.MakeSerialiseClean();

	}
	
    @AfterClass
    public static void after() throws DAOException {
		DBInitialiser dbInitialiser = new DBInitialiser();
		dbInitialiser.Initialise();
    }	
}
