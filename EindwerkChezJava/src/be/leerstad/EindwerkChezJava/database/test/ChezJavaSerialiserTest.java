package be.leerstad.EindwerkChezJava.database.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
import be.leerstad.EindwerkChezJava.database.ChezJavaSerialiser;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.Table;
public class ChezJavaSerialiserTest {
	private ChezJavaSerialiser chezJavaSerialiser;
	private Liquid l1;
	private Liquid l2;
	private Ober ober1;
	private Table t1;
	private Table t2;
	private Table t3;
	private Order o1;
	private Order o2;
	private List<Table> setTables; 
	@Before
	public void setUp() throws Exception {
		l1 = new Liquid(1, "Cola", 2.0);
		l2 = new Liquid(2, "Bier", 3.0); 
		ober1 = new Ober(1, "Peters", "Wout","password");
		o1 = new Order(l1, 2, ober1);
		o2 = new Order(l2, 2, ober1);
		setTables =  new ArrayList<>();
		t1 = new Table(0);
		t2 = new Table(1);
		t3 = new Table(2);
		t1.getOrders().add(o1);
		t1.getOrders().add(o2);
		t2.getOrders().add(o2);
		setTables.add(t1);
		setTables.add(t2);
		setTables.add(t3);
		chezJavaSerialiser = ChezJavaSerialiser.getInstance();
	}
	@Test
	public void testGetInstance() {
		ChezJavaSerialiser ser = null;
		assertTrue(ser == null);
		ser = ChezJavaSerialiser.getInstance();
		assertTrue(ser != null);
	}
	
	@Test
	public void testSerialise() {
		chezJavaSerialiser.serialise(setTables);
	}

	@Test
	public void testDeserialise() throws SerialException 
	{
		chezJavaSerialiser.serialise(setTables);
		List<Table> setDesialiseTables  = chezJavaSerialiser.deserialise();
		assertEquals(setTables, setDesialiseTables);
	}
	
	@Test(expected = SerialException.class)
	public void testDeserialiseInvallid() throws SerialException 
	{
		List<Table> setDesialiseTables = chezJavaSerialiser.deserialise();
	}
	@After
	public void MakeSerialiseClean()
	{
		chezJavaSerialiser.MakeSerialiseClean();
	}



}
