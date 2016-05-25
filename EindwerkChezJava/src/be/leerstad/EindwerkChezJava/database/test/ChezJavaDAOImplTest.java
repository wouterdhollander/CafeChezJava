package be.leerstad.EindwerkChezJava.database.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOloginNotAllowed;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAO;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;



public class ChezJavaDAOImplTest {
	private ChezJavaDAO ChezJavaDAOimpl;
	private Liquid l1;
	private Liquid l2;
	private Liquid l3;
	 private Ober o1;
	 private Ober o2;
	 private Ober o3;
	 private Ober o4;
	 private List<Ober> obers = new ArrayList<>();
	 private DBInitialiser dbInitialiser;
	private static final float PRECISION = 0.01F;
	@Before
	public void setUp() throws Exception {
		dbInitialiser = new DBInitialiser();
		dbInitialiser.Initialise();
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0);   
		l3 = new Liquid(1, "Cola", 2.0); 

		  o1 = new Ober(1, "Peters", "Wout", "password");
		  o2 = new Ober(2, "Segers", "Nathalie", "password");
		  o3 = new Ober(3, "Vandenbroeck", "Ilse", "password");
		  o4= new Ober(4, "Desmet", "Patrick", "password");
		  
		  obers.add(o1);
		  obers.add(o2);
		  obers.add(o3);
		  obers.add(o4);
	}
	@Test (expected = DAOloginNotAllowed.class)
	public void testLoginInvallid() throws DAOloginNotAllowed
	{
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();

		Ober ober;
		try {
			ober = ChezJavaDAOimpl.Login("Peters","Wout","Fout password");
			assertEquals(ober, o1);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testGetInstance() {
		assertTrue(ChezJavaDAOimpl == null);
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		assertTrue(ChezJavaDAOimpl != null);
	}

	@Test
	public void testInsertOrder() {
		Order order;
		try {
			order = new Order(l1, 200, o1);
			ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
			assertEquals(true, ChezJavaDAOimpl.insertOrder(order));
		} catch (QuantityToLowException | QuantityZeroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testInsertOrders() throws QuantityToLowException, QuantityZeroException, DAOException {
		OrderSet orders = new OrderSet();

			orders.add(new Order(l1, 1, o1));
			orders.add(new Order(l2, 2, o1));
			orders.add(new Order(l3, 3, o1));


			ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();

				assertEquals(true, ChezJavaDAOimpl.insertOrders(orders));

	}
	
	@Test
	public void testGetOrder() throws DAOException {
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();

		LocalDate ld = LocalDate.of(2009, 12, 25);

		OrderSet lstOrder = new OrderSet();

			lstOrder = ChezJavaDAOimpl.getOrder(ld);

		assertEquals(2, lstOrder.size());

	}
	
	@Test
	public void testLogin() throws DAOException, DAOloginNotAllowed
	{
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();

		Ober ober;

			ober = ChezJavaDAOimpl.Login("Peters","Wout","password");
			assertEquals(ober, o1);

	}
		
	@Test
	public void testGetObers() throws DAOException {
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		List<Ober> obersDAO = new ArrayList<Ober>();

			obersDAO = ChezJavaDAOimpl.getObers();

		
		assertEquals(o1, obersDAO.get(0));
		assertEquals(obersDAO, obers);
	}

	@Test
	public void testGetLiquids() throws DAOException {
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		Set<Liquid> liquidsDAO = new HashSet<>();

			liquidsDAO = ChezJavaDAOimpl.getLiquids();

		assertEquals(liquidsDAO.iterator().next(), new Liquid(1, "Cola", 1.5));
		assertEquals(13, liquidsDAO.size());
	}
	
	//TODO
	//@Test
	public void topDrieOber() throws DAOException {
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		//linke
		
		LinkedHashMap<Ober, Double> mapTopDrie = new LinkedHashMap<>();

			mapTopDrie = ChezJavaDAOimpl.topDrieOber();
			Set set = mapTopDrie.entrySet();
			//set.

	}
	
	@After
	public void MakeDBClean()
	{
		dbInitialiser.Initialise();
	}
}
