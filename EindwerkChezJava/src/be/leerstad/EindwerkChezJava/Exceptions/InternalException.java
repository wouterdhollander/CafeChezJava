package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class InternalException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	/**
	 * constructor
	 */
	public InternalException(){
		//this.message = "An internal error occurred!" + "\n" + "Please report this so we can fix this";
		//this(message);
	}
	/**
	 * @param arg0 errormessage
	 */
	public InternalException(String arg0) {
		this.setMessage(arg0);
	}
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return message;
    }
    private void setMessage(String message) {
        this.message = message;
    }
    protected String message = "An internal error occurred!" + "\n" + "Please report this so we can fix this";
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return message;
    }
}