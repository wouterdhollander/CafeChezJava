package be.leerstad.EindwerkChezJava.AllTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.leerstad.EindwerkChezJava.Exceptions.test.*;
import be.leerstad.EindwerkChezJava.database.test.AllDatabaseTests;
import be.leerstad.EindwerkChezJava.model.test.AllModelTests;

@RunWith(Suite.class)
@SuiteClasses({ AllDatabaseTests.class, AllExcepstionTests.class, AllModelTests.class })
public class AllTests {

}
