package be.leerstad.EindwerkChezJava.database.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import be.extern.ScriptRunner;
import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.database.BaseDAO;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class DBInitialiser {
private BaseDAO base;
private Connection con;
	public DBInitialiser() throws DAOException {
		BaseDAO base = new BaseDAO();
		con = base.getConnection();
	}
	public void Initialise() 
	{
		 try {
			URL filename = DBInitialiser.class.getResource("/be/leerstad/EindwerkChezJava/database/test/cafe.sql");
			File file2 = new File(filename.toURI());
			ScriptRunner scriptrunner = new ScriptRunner(con, false, false);
			scriptrunner.runScript(new BufferedReader(new FileReader(file2)));

		 } catch (IOException | SQLException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	

}
