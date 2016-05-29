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
			 //File file = new File("C:/Users/wouter/Documents/2015-16 Avondschool/Programmeren III/workspaceOefeningen/EindwerkChezJava/src/lib/cafe.sql");	 
			 
			//URL filename = DBInitialiser.class.getResource("/lib/cafe.sql");
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
