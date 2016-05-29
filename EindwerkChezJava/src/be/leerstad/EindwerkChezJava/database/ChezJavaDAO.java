package be.leerstad.EindwerkChezJava.database;


import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public interface ChezJavaDAO {
	
    /**
     * @param order the order object that will be inserted into the db
     * @return true if it succeeded
     */
    boolean insertOrder(Order order); //afrekenen
    /**
     * gets all the orders of a specific data
     * @param localdate the date
     * @return an orderset with all orders of a specific date
     * @throws DAOException due to DB errors (DB not found...)
     */
    OrderSet getOrder(LocalDate localdate) throws DAOException; //bepaalde dag de inkomsten
    /**
     * @param ober the ober object
     * @return an orderset with all orders of a specific ober
     * @throws DAOException due to DB errors (DB not found...)
     */
    OrderSet getOrder(Ober ober) throws DAOException; //bepaalde dag de inkomsten
    /**
     * give the top obers by most earning income
     * @param number the number of obers you want to have
     * @return a linked hashmap ordered by income
     * @throws DAOException due to DB errors (DB not found...)
     */
    LinkedHashMap<Ober, Double> topObers(int number) throws DAOException;
    /**
     * @return  gives all obers sorted by income
     * @throws DAOException due to DB errors (DB not found...)
     */
    LinkedHashMap<Ober, Double> topObers() throws DAOException;
    /**
     * @return a list with all the obers in the database
     * @throws DAOException due to DB errors (DB not found...)
     */
    List<Ober> getObers() throws DAOException;
	/**
	 * @return a list with all the obers in the DB
	 * @throws DAOException due to DB errors (DB not found...)
	 */
	Set<Liquid> getLiquids() throws DAOException;
	/**
	 * return an ober object if it's in the DB <p>
	 * a default ober is givin when the login is not allowed
	 * @param lastName the lastname of that is stored
	 * @param firstName the lastname of that is stored
	 * @param password the lastname of that is stored
	 * @return the ober object with all the parameters, a new Ober() - object when the ober is not in the database
	 * @throws DAOException throws when an error with the DB occurs (no connection,...)
	 */
	Ober Login(String lastName, String firstName, String password) throws DAOException;
	/**
	 * @param set a set of orders that will be inserted into the DB
	 * @return true if every order is correctly inserted
	 * @throws DAOException  throws when an error with the DB occurs (no connection,...)
	 */
	boolean insertOrders(Set<Order> set) throws DAOException; //afloggen


}
