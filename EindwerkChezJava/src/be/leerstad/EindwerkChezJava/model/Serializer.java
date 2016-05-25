package be.leerstad.EindwerkChezJava.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;

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

	public List<Table> deserialise()
	{
		List<Table> tables = null;

			FileInputStream fis;
			try {
				fis = new FileInputStream(PATH);
				ObjectInputStream ois = new ObjectInputStream(fis);
				tables = (List<Table>) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				//als het bestand niet gevonden is, wil dit zeggen dat er een nieuw café gemaakt moet worden
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return tables;
	}
	
	
	public void MakeSerialiseClean()
	{
		File file = new File(PATH);
		file.delete();
	}
}
