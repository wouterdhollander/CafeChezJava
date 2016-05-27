package be.leerstad.EindwerkChezJava.database;


import java.sql.*;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOloginNotAllowed;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
import be.leerstad.EindwerkChezJava.model.test.OrderSetTest;

public class ChezJavaDAOImpl extends BaseDAO implements ChezJavaDAO{
    private static final String GET_ORDERS_DAY = "SELECT * from tblorders where date=?";
    private static final String INSERT_ORDER = "INSERT into tblorders (idLiquid, qty, date, idOber) VALUES (?, ?, ?,?)";
    //private static final String INSERT_ORDERS = "INSERT into tblorders (idLiquid, qty, date, idOber) VALUES (?, ?, ?,?)";
    private static final String GET_ALL_OBERS = "SELECT * from tblober";
    private static final String  GET_ALL_LIQUIDS = "SELECT * from tblliquids";
    private static final String  LOGIN = "SELECT * from tblober where lastName = ? And firstName = ? and password = ?"; 
    private static final String GET_ORDERS_OBER = "SELECT * from tblorders where idOber=?"; 

    //private static final String DELETE_OBER = "DELETE FROM tblorders where id =?";
    //private static final String UPDATE_OBER= "UPDATE tblorders SET firstName= ?, lastName = ? ,password = ? WHERE id = ? ";


    private static ChezJavaDAOImpl instance;

    private ChezJavaDAOImpl(){}

    public synchronized static ChezJavaDAO getInstance(){
        if (instance == null){
            instance = new ChezJavaDAOImpl();
        }
        return instance;
    }

	@Override
	public boolean insertOrder(Order order) {
      int result = 0;
      boolean bool = false;
      try (Connection connection = getConnection();
           PreparedStatement pStatement = connection
                   .prepareStatement(INSERT_ORDER);) {
          pStatement.setInt(1, order.getLiquid().getId());
          pStatement.setInt(2, order.getQuantity());
          Date date = Date.valueOf(order.getDate());
          //Date date = this.toDate(order.getDate());
          pStatement.setDate(3, date);
          pStatement.setInt(4, order.getOber().getId());
          result = pStatement.executeUpdate();

      } catch (Exception e) {
          System.out.println("Error insert Order. " + e.getMessage());
      }
      if (result == 1)
      {
    	  bool = true;
      }
      return bool;
  }

	@Override
	public boolean insertOrders(Set<Order> orders) throws DAOException {
      int[] result;
      boolean bool = false;
      try (Connection connection = getConnection();
           PreparedStatement pStatement = connection
                   .prepareStatement(INSERT_ORDER);) {
    	  for (Order order : orders) {
    		  pStatement.setInt(1, order.getLiquid().getId());
              pStatement.setInt(2, order.getQuantity());
              Date date = Date.valueOf(order.getDate());
              //Date date = this.toDate(order.getDate());
              pStatement.setDate(3, date);
              pStatement.setInt(4, order.getOber().getId());
              pStatement.addBatch();
		}
    	  int [] numInserts = pStatement.executeBatch();
    	  bool = numInserts.length ==  orders.size();

      } catch (Exception e) {
    	  throw new DAOException("Error getting obers. " + e.getMessage()); 
      }
      return bool;
  }
	

	@Override
	public OrderSet getOrder(Ober ober) throws DAOException {
		//Date date = Date.valueOf(localdate);

		//express niet met sql berekend (geen zin om innerjoins te gebruiken)
		Set<Liquid> liquidsDAO = this.getLiquids();
		List<Ober> obersDAO = this.getObers();
		
		OrderSet orders = new OrderSet();
        
        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection
                     .prepareStatement(GET_ORDERS_OBER);){
        	pStatement.setInt(1, ober.getId());
             ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
            int idliquid =	resultSet.getInt("idLiquid");
            	Liquid liquid = liquidsDAO.stream()
            			.filter(e -> e.getId() == idliquid)
            			.findFirst().get();
					Date date = resultSet.getDate("date");
					LocalDate localDate = date.toLocalDate();
					try {
						Order order = new Order(liquid, resultSet.getInt("qty"), ober, localDate);
						orders.add(order);
					} catch (QuantityToLowException | QuantityZeroException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            }
        } catch (SQLException e) {
            throw new DAOException(
                    "Error getting orders. " + e.getMessage());
        }
        return orders;
    }
	
	@Override
	public OrderSet getOrder(LocalDate localdate) throws DAOException 
	{
		Date date = Date.valueOf(localdate);

		//express niet met sql berekend (geen zin om innerjoins te gebruiken)
		Set<Liquid> liquidsDAO = this.getLiquids();
		List<Ober> obersDAO = this.getObers();
		
		OrderSet orders = new OrderSet();
        
        try (Connection connection = getConnection();
             PreparedStatement pStatement = connection
                     .prepareStatement(GET_ORDERS_DAY);){
        	pStatement.setDate(1, date);
             ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
            int idliquid =	resultSet.getInt("idLiquid");
            	Liquid liquid = liquidsDAO.stream()
            			.filter(e -> e.getId() == idliquid)
            			.findFirst().get();
            	int idober = resultSet.getInt("idOber");
            	Ober ober = obersDAO.stream()
            			.filter(e -> e.getId() == idober)
            			.findFirst().get();
				try {
					Order order = new Order(liquid, resultSet.getInt("qty"), ober, localdate);
					orders.add(order);
				} catch (QuantityToLowException | QuantityZeroException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
            }
        } catch (SQLException e) {
            throw new DAOException(
                    "Error getting orders. " + e.getMessage());
        }
        return orders;
    }
	
	
	@Override
	public Ober Login(String lastName, String firstName, String password) throws DAOException, DAOloginNotAllowed {
		// TODO Auto-generated method stub
        List<Ober> obers = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN);
        ){
        	preparedStatement.setString(1, lastName);
        	preparedStatement.setString(2, firstName);
        	preparedStatement.setString(3, password);
        	
        	ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            	//String idDB = resultSet.getString(1);
            	Ober ober = new Ober(resultSet.getInt("id"),resultSet.getString("lastName"),resultSet.getString("firstName"),resultSet.getString("password"));
                obers.add(ober);
            }
        } catch (Exception e) {
        	throw new DAOException("Error getting obers. " + e.getMessage());   
        }
        if (obers.size() > 1 )
        {
        	throw new DAOException("Internal error. Ober is counted multiple times");
        }
        else if(obers.size() == 0)
        {
        	throw new DAOloginNotAllowed();
        }
  
        return obers.get(0);
	}
    @Override
    public List<Ober> getObers() throws DAOException
    {
    	return getObers(GET_ALL_OBERS);
    }

	@Override
	public Set<Liquid> getLiquids() throws DAOException {
		Set<Liquid> liquids = new HashSet<>();

        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LIQUIDS);

        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            	Liquid l = new Liquid(resultSet.getInt("idLiquid"), resultSet.getString("liquidName"),resultSet.getDouble("Price"));

                liquids.add(l);
            }

        } catch (Exception e) {
            throw new DAOException(
                    "Error getting Liquids. " + e.getMessage());
        }
        return liquids;
	}

	@Override
	public LinkedHashMap<Ober, Double> topDrieOber() throws DAOException {
	    int aantal = 3;
	     String GET_TOP =  MessageFormat.format("select idober, tblober.firstname, tblober.lastName,tblober.password, sum(totalprice) as sumtotalprice from"
	    		+ "(Select tblorderlqd.idober as idober, price * qty as totalprice "
	    		+ "from(Select orders.idOber as idober, t.price as price, orders.qty as qty "
	    		+ "from tblorders orders left join tblliquids t on t.idLiquid= orders.idLiquid"
	    		+ ") as tblorderlqd"
	    		+ ") as tblsumoberInner "
	    		+ "join tblober ON tblober.id = idober "
	    		+ "group by idober Order by sumtotalprice DESC LIMIT {0,number,integer}", aantal);
	     
	        //List<Ober> obers = new ArrayList<>();
	     LinkedHashMap<Ober, Double> mapTopDrie = new LinkedHashMap<>();
	        
	        try (
	            Connection connection = getConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(GET_TOP);
	        ){
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	            	String idDB = resultSet.getString(1);
	            	int id = Integer.parseInt(idDB);

	            	Ober ober = new Ober(id,resultSet.getString("lastName"),resultSet.getString("firstName"),resultSet.getString("password"));
	                double sum = resultSet.getDouble("sumtotalprice");
	            	mapTopDrie.put(ober, sum);
	            	//obers.add(ober);
	            }
	        } catch (Exception e) {
	        	throw new DAOException("Error getting obers. " + e.getMessage());
	            
	        }
	        return mapTopDrie;
	}
	
    private List<Ober> getObers(String statement) throws DAOException {
        List<Ober> obers = new ArrayList<>();
        try (
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            	String idDB = resultSet.getString(1);
            	int id = Integer.parseInt(idDB);

            	Ober ober = new Ober(id,resultSet.getString("lastName"),resultSet.getString("firstName"),resultSet.getString("password"));
                obers.add(ober);
            }
        } catch (Exception e) {
        	throw new DAOException("Error getting obers. " + e.getMessage());
            
        }
        return obers;
    }

}
