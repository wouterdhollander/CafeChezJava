package be.leerstad.EindwerkChezJava.database.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.leerstad.EindwerkChezJava.database.ChezJavaSerialiser;
@RunWith(Suite.class)
@SuiteClasses({ChezJavaSerialiserTest.class, BaseDAOTest.class, ChezJavaDAOImplTest.class})
public class AllDatabaseTests {

}