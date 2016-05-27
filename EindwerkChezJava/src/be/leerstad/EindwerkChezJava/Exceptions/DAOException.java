package be.leerstad.EindwerkChezJava.Exceptions;

public class DAOException extends InternalException {
    private static final long serialVersionUID = 19192L;

    public DAOException() {
    	super("Error With Database");
    }
    
	public DAOException(String arg0) {
		super(arg0);
	}
}