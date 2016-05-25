package be.leerstad.EindwerkChezJava.model.test;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.leerstad.EindwerkChezJava.model.Serializer;

@RunWith(Suite.class)
@SuiteClasses({ CafeTest.class, LiquidTest.class, OberTest.class,OrderTest.class, TableTest.class, SerializerTest.class, OrderSetTest.class })
public class AllTests {
	
}

