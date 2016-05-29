package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author wouter
 */
public class InternalException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	public InternalException(){
		//this.message = "An internal error occurred!" + "\n" + "Please report this so we can fix this";
		//this(message);
	}
	public InternalException(String arg0) {
		this.setMessage(arg0);
	}
    public String getMessage() {
        return message;
    }
    private void setMessage(String message) {
        this.message = message;
    }
    protected String message = "An internal error occurred!" + "\n" + "Please report this so we can fix this";
    
    @Override
    public String toString() {
        return message;
    }
}