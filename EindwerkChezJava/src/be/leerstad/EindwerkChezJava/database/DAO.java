package be.leerstad.EindwerkChezJava.database;
import java.sql.Connection;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public interface DAO {
    /**
     * @return the connection.
     * @throws DAOException when its not possible to connect to dB
     */
    Connection getConnection() throws DAOException;
}