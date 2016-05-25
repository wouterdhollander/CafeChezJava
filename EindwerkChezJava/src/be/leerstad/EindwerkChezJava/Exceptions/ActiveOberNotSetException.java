package be.leerstad.EindwerkChezJava.Exceptions;

public class ActiveOberNotSetException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	public ActiveOberNotSetException(){
		this("Active Ober not set!!");
	}
	public ActiveOberNotSetException(String arg0) {
		super(arg0);
	}
}