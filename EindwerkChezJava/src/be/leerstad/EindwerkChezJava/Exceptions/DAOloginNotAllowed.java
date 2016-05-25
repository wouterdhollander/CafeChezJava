package be.leerstad.EindwerkChezJava.Exceptions;

public class DAOloginNotAllowed extends Exception {
    private static final long serialVersionUID = 19192L;

	public DAOloginNotAllowed(){
		this("Login Not Allowed!!");
	}
	public DAOloginNotAllowed(String arg0) {
		super(arg0);
	}
}

