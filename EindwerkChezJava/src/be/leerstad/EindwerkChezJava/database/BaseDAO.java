package be.leerstad.EindwerkChezJava.database;
import java.sql.Connection;
import java.sql.DriverManager;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class BaseDAO implements DAO {

    public static final String dbUrl = "jdbc:mysql://localhost:3306/cafechezjava?autoReconnect=true&useSSL=false";
    /* (non-Javadoc)
     * @see be.leerstad.EindwerkChezJava.database.DAO#getConnection()
     */
    public Connection getConnection() throws DAOException {
        try {
            return DriverManager.getConnection(dbUrl,"root","root");
        } catch (Exception e) {
            throw new DAOException();
        }
    }
}
