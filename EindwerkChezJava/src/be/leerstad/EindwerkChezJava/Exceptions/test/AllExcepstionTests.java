package be.leerstad.EindwerkChezJava.Exceptions.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ DAOExceptionTest.class, InternalExceptionTest.class, QuantityToLowExceptionTest.class,
		QuantityZeroExceptionTest.class, SerialiserExceptionTest.class })
public class AllExcepstionTests {

}
