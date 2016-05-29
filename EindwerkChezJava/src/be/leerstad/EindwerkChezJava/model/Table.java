package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class Table implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private OrderSet orders = new OrderSet();
	private Position position;
	private Position positionDefault;

	/**
	 * @param id the id of the table
	 */
	public Table(int id) {
		this.setId(id);
	}

	/**
	 * Return an OrdersSet object containing all the orders of the table
	 * @return the Orders of the Table Object
	 */
	public OrderSet getOrders() {
		//als er een order veranderd wordt naar een order dat al in de set wordt dit niet samengevoegd
		// trukje => terug door orderset laten gaan.
		OrderSet orders2 = new OrderSet(orders);
		orders = orders2;
		return orders;
	}
	


	private void setOrders(OrderSet orders) {
		this.orders = orders;
	}

	
	/**
	 * Return an Ober object representing the Active Ober
	 * if No ober is active a default Ober is givin.
	 * @return the active Ober of the Table Object
	 */
	public Ober getActiveOber()
	{
		if (orders.size() != 0)
		{
			return orders.iterator().next().getOber();
		}
		else
		{
			return new Ober();
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Table [id=" + id + ", orders=" + orders + ", activeOber=" + getActiveOber() + "]";
	}


	/**
	 * @return id passed to the constructor
	 */
	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}
	
	/**
	 * I make this as an extra ;-) so tables can move!
	 * @return the actual position of the object
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * I make this as an extra ;-) so tables can move!
	 * @param position the postion of the table
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	
	/**
	 * I make this as an extra ;-) so tables can move!
	 * @return the default position of the table
	 */
	public Position getPositionDefault() {
		return positionDefault;
	}

	/**
	 * I make this as an extra ;-) so tables can move!
	 * @param positionDefault the default position of the table
	 */
	public void setPositionDefault(Position positionDefault) {
		this.positionDefault = positionDefault;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
