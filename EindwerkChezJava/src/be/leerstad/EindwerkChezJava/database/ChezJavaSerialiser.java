package be.leerstad.EindwerkChezJava.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import be.leerstad.EindwerkChezJava.model.Table;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class ChezJavaSerialiser {
	private static final String  PATH = "SerialiseCafe.ser";
	/**
	 * Creates a serialiser object
	 */
    private static ChezJavaSerialiser instance;

    private ChezJavaSerialiser(){}

    /**
     * @return the ChezJavaSerialiser object
     */
    public synchronized static ChezJavaSerialiser getInstance(){
        if (instance == null){
            instance = new ChezJavaSerialiser();
        }
        return instance;
    }

	/**
	 * @param tables serialise a list of tables
	 */
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

	/**
	 * @return a list of all deserialised tables <p> is empty when no tables can be serialised
	 * @throws SerialException throws when its not possible to serialise.
	 */
	@SuppressWarnings("unchecked")
	public List<Table> deserialise() throws SerialException
	{
		List<Table> tables =  new ArrayList<>();

		try {
			FileInputStream fis = new FileInputStream(PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tables = (List<Table>) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			throw new SerialException();
		}

		return tables;
	}
	
	

	/**
	 * Deletes the serialised object.
	 */
	public void MakeSerialiseClean()
	{
		File file = new File(PATH);
		file.delete();
	}
}
