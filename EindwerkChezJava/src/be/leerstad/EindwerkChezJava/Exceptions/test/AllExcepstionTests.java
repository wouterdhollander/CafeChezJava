package be.leerstad.EindwerkChezJava.Exceptions.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DAOExceptionTest.class, InternalExceptionTest.class, QuantityToLowExceptionTest.class,
		QuantityZeroExceptionTest.class, SerialiserExceptionTest.class })
public class AllExcepstionTests {

}
