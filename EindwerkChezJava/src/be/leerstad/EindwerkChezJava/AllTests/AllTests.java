package be.leerstad.EindwerkChezJava.AllTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import be.leerstad.EindwerkChezJava.Exceptions.test.AllExcepstionTests;
import be.leerstad.EindwerkChezJava.database.test.AllDatabaseTests;
import be.leerstad.EindwerkChezJava.model.test.AllModelTests;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ AllDatabaseTests.class, AllExcepstionTests.class, AllModelTests.class })
public class AllTests {

}
