package be.leerstad.EindwerkChezJava.database;
import java.sql.Connection;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;

public interface DAO {
    Connection getConnection() throws DAOException;
}