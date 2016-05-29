package be.leerstad.EindwerkChezJava.database;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;

public interface ChezJavaDAO {
	
    boolean insertOrder(Order p); //afrekenen
    OrderSet getOrder(LocalDate localdate) throws DAOException; //bepaalde dag de inkomsten
    OrderSet getOrder(Ober ober) throws DAOException; //bepaalde dag de inkomsten
    LinkedHashMap<Ober, Double> topObers(int number) throws DAOException;
    LinkedHashMap<Ober, Double> topObers() throws DAOException;
    List<Ober> getObers() throws DAOException;
	Set<Liquid> getLiquids() throws DAOException;
	/**
	 * a default ober is givin when the login is not allowed
	 * @param lastName
	 * @param firstName
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	Ober Login(String lastName, String firstName, String password) throws DAOException;
	boolean insertOrders(Set<Order> set) throws DAOException; //afloggen


}
