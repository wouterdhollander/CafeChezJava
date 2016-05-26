package be.leerstad.EindwerkChezJava.Exceptions;

public class SerialiserException extends Exception {
    private static final long serialVersionUID = 19192L;

	public SerialiserException(){
		this("Not possible to deserialize!!");
	}
	public SerialiserException(String arg0) {
		super(arg0);
	}
}