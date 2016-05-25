package be.leerstad.EindwerkChezJava.Exceptions;

public class TableNotAllowedException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	public TableNotAllowedException(){
		this("Table not allowed to you to order!");
	}
	public TableNotAllowedException(String arg0) {
		super(arg0);
	}
}
