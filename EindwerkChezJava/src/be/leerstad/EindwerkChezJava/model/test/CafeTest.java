package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOloginNotAllowed;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.Exceptions.TableNotAllowedException;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
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
	public void testSetActiveTable() throws ActiveOberNotSetException, TableNotAllowedException
	{
		cafe.setActiveTable(Tables.get(1));
		assertEquals(Tables.get(1), cafe.getActiveTable());
	}
	
	@Test
	public void testAddOrder() throws TableNotAllowedException, ActiveOberNotSetException, QuantityToLowException, QuantityZeroException {

		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o1);
		assertEquals(1,Tables.get(1).getOrders().size());
		Order o2 = new Order(l2, 1, ober1);
		cafe.addOrder(o2);
		assertEquals(2, Tables.get(1).getOrders().size());
		Order o3 = new Order(l2, 3, ober1);
		cafe.addOrder(o3);
		assertEquals(2, Tables.get(1).getOrders().size());

	}
	
	@Test(expected = TableNotAllowedException.class)
	public void testAddOrderInvallid() throws  TableNotAllowedException, ActiveOberNotSetException, DAOException, DAOloginNotAllowed{

		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o1);
	
		cafe.login("Segers", "Nathalie", "password");
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o1);

	}
	
	@Test
	public void testRemoveOrder() throws TableNotAllowedException, ActiveOberNotSetException, QuantityToLowException {
		Set<Order> orders = new HashSet<>();
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o1);
		cafe.removeOrder(o1);
		cafe.addOrder(o1);
		orders.add(o1);
			
		assertEquals(orders, Tables.get(1).getOrders());
	}
	
	@Test
	public void testPrintOutPayment() throws TableNotAllowedException, ActiveOberNotSetException, QuantityToLowException, QuantityZeroException {

		Order o = new Order(l1, 1, ober1);
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o);
		Order o2 = new Order(l2, 1, ober1);
		cafe.addOrder(o2);
		Order o3 = new Order(l1, 3, ober1);
		cafe.addOrder(o3);

		String payorder = cafe.printOutPayment();

		String payString = "tableID: 1\n Besteld door Ober: Peters Wout\n4 x Cola(2.0€) = 8.0€\n1 x Bier(3.0€) = 3.0€\ntotaal (€) = 11.0";
		assertEquals(payString, payorder);
		//assertTrue(payString.equals(payorder));


	}
	
	@Test
	public void testPayOrder() throws QuantityToLowException, QuantityZeroException, TableNotAllowedException, ActiveOberNotSetException
	{

		Order o = new Order(l1, 1, ober1);
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o);
		Order o2 = new Order(l2, 1, ober1);
		cafe.addOrder(o2);
		Order o3 = new Order(l1, 3, ober1);
		cafe.addOrder(o3);

		Table t = cafe.getActiveTable();
		cafe.payOrders();
		assertEquals(0, t.getOrders().size());

	}
	@Test (expected = TableNotAllowedException.class)
	public void testPayOrdersinvalid() throws TableNotAllowedException, ActiveOberNotSetException, QuantityToLowException, QuantityZeroException, DAOException, DAOloginNotAllowed {

			Order o = new Order(l1, 1, ober1);
			cafe.setActiveTable(Tables.get(1));
			cafe.addOrder(o);
			Order o2 = new Order(l2, 1, ober1);
			cafe.addOrder(o2);
			Order o3 = new Order(l2, 3, ober1);
			cafe.addOrder(o3);
			
			cafe.login("Segers", "Nathalie", "password");
			cafe.setActiveTable(Tables.get(1));
			cafe.payOrders();
		
	}
	@Test
	public void testCalculateOrdersActiveTable() throws ActiveOberNotSetException, TableNotAllowedException, QuantityToLowException, QuantityZeroException
	{

		Order o = new Order(l1, 1, ober1);
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o);
		Order o2 = new Order(l2, 1, ober1);
		cafe.addOrder(o2);
		Order o3 = new Order(l1, 3, ober1);
		cafe.addOrder(o3);

		
		assertEquals(11, cafe.calculateOrdersActiveTable(),PRECISION);
	}
	
	@Test
	public void testGetActiveOber() {
		assertEquals(1, cafe.getActiveOber().getId());
		
	}
	
	@Test
	public void testCalculateUnpayedOrderActiveOber() throws ActiveOberNotSetException, QuantityToLowException, QuantityZeroException, TableNotAllowedException
	{

		Order o = new Order(l1, 1, ober1);
		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o);
		Order o2 = new Order(l2, 1, ober1);
		cafe.addOrder(o2);
		Order o3 = new Order(l2, 3, ober1);
		
		cafe.addOrder(o3);


	  double result = cafe.calculateUnpayedOrderActiveOber();
		//System.out.println(result);
		assertEquals(14.0 ,result ,PRECISION);
		//assertTrue(result == 14.0);
		

	}

	@Test
	public void testGetTablesActiveOber() throws QuantityToLowException, QuantityZeroException, ActiveOberNotSetException, TableNotAllowedException
	{
		List<Table> tables = new ArrayList<>();
		tables.add(Tables.get(1));
		tables.add(Tables.get(2));
		tables.add(Tables.get(5));

		Order o = new Order(l1, 1, ober1);
		for (Table table : tables) {
			cafe.setActiveTable(table);
			cafe.addOrder(o);
		}


		List<Table> tablesActiveOber =cafe.getTablesActiveOber();
		assertEquals(tables, tablesActiveOber);
	}
	
	@Test
	public void getUsedTables() throws ActiveOberNotSetException, QuantityToLowException, QuantityZeroException, DAOException, DAOloginNotAllowed, TableNotAllowedException
	{
		Set<Table> tablesOber1 = new HashSet<>();
		List<Table> lstAllTables = new ArrayList<>();
		lstAllTables.addAll(Tables);
		tablesOber1.add(Tables.get(1));
		tablesOber1.add(Tables.get(2));
		tablesOber1.add(Tables.get(5));
		
		List<Table> tablesOber2 = new ArrayList<>();
		tablesOber2.add(Tables.get(3));
		tablesOber2.add(Tables.get(4));

		Order o = new Order(l1, 1, ober1);
		for (Table table : tablesOber1) {
			cafe.setActiveTable(table);
			cafe.addOrder(o);
		}
		cafe.login("Segers", "Nathalie", "password");
		for (Table table : tablesOber2) {
			cafe.setActiveTable(table);
			cafe.addOrder(o);
		}


		List<Table> usedTables =cafe.getUsedTables();
		//sorting with lambda! zoniet is de output eventueel wel hetzelfde maar de strings ervan niet
		usedTables.sort((Table t1, Table t2) -> t1.getId()-t2.getId());
		
		//sorting with lambda
		List<Table> usedActual = new ArrayList<>();
		usedActual.addAll(tablesOber1);
		usedActual.addAll(tablesOber2);
		usedActual.sort((Table t1, Table t2) -> t1.getId()-t2.getId());
		
		//assertTrue(usedActual.equals(usedTables));
		assertEquals(usedActual, usedTables);

	}
	
	
	//@Test
	public void testSendMail() throws MessagingException
	{

		cafe.SendMail("src/be/leerstad/03_-_Java_Basics_-_Classes_and_objects.pdf", ober1);

	}
	
	@Test
	public void testLogout(){
		cafe.logOut();
		assertTrue(null == cafe.getActiveOber());
	}
	
	@Test
	public void testClose() throws TableNotAllowedException, ActiveOberNotSetException, QuantityToLowException, QuantityZeroException, DAOException, DAOloginNotAllowed{
		List<Table> TablesCafe = new ArrayList<>();

		cafe.setActiveTable(Tables.get(1));
		cafe.addOrder(o1);
		Order o2 = new Order(l1, 1, ober1);
		cafe.addOrder(o2);
		Order o3 = new Order(l2, 3, ober1);
		cafe.addOrder(o3);
		TablesCafe = cafe.getTables();

			cafe.close();

		Cafe cafe2 = new Cafe();
		cafe2.login("Segers", "Nathalie", "password");
		assertEquals(cafe2.getTables(), TablesCafe);
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




}
