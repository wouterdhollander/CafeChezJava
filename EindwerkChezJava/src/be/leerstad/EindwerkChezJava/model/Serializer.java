package be.leerstad.EindwerkChezJava.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
/**
 * @author wouter
 * @version 0.1
 * @since 30/05/2016
 */
public class Serializer {
	private static final String  PATH = "SerialiseCafe.ser";
	public Serializer() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void serialise(List<Table> tables)
	{
		try {
			FileOutputStream fs = new FileOutputStream(PATH);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(tables);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Table> deserialise() throws SerialException
	{
		List<Table> tables = null;

		FileInputStream fis;

		try {
			fis = new FileInputStream(PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tables = (List<Table>) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			throw new SerialException();
		}

		return tables;
	}
	
	
	public void MakeSerialiseClean()
	{
		File file = new File(PATH);
		file.delete();
	}
}
