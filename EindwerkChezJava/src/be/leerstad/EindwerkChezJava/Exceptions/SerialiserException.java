package be.leerstad.EindwerkChezJava.Exceptions;

public class SerialiserException extends InternalException {
    private static final long serialVersionUID = 19192L;

	public SerialiserException(){
		super("Error With Serialiser");
	}
    
	public SerialiserException(String arg0) {
		super(arg0);
	}
}
