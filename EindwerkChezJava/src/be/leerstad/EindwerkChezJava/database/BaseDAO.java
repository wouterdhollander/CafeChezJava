package be.leerstad.EindwerkChezJava.database;
import java.sql.Connection;
import java.sql.DriverManager;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;

public class BaseDAO implements DAO {

    public static final String dbUrl = "jdbc:mysql://localhost:3306/cafechezjava?autoReconnect=true&useSSL=false";
    public Connection getConnection() throws DAOException {
        try {
            return DriverManager.getConnection(dbUrl,"root","root");
        } catch (Exception e) {
            throw new DAOException();
        }
    }
}
