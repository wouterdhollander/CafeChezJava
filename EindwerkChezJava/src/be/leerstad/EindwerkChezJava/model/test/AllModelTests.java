package be.leerstad.EindwerkChezJava.model.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.leerstad.EindwerkChezJava.database.ChezJavaSerialiser;
import be.leerstad.EindwerkChezJava.database.test.ChezJavaSerialiserTest;


@RunWith(Suite.class)
@SuiteClasses({ CafeTest.class, LiquidTest.class, OberTest.class, OrderSetTest.class, OrderTest.class, 
	PDFgeneratorTest.class,TableTest.class})
public class  AllModelTests {
	


}

