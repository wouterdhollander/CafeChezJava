package be.leerstad.EindwerkChezJava.model;

import java.lang.invoke.MethodHandles;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collection;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
/**
 * @author wouter
 * @version 0.1
 * @since 30/05/2016
 */
public class OrderSet extends HashSet<Order> {
	//private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * creates an empty orderset
	 */
	public OrderSet()
	{
		super();
	}
	
	/**
	 * @param col a collection that will be added to the orderset
	 */
	public OrderSet(Collection col)
	{
		this.addAll(col);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * return the total price of all the orders
	 * @return the total price of all the orders
	 */
	public double calcutateOrders(){
		double total =this
				.stream()
				.mapToDouble(Order::getPrice)
				.sum();
		return total;
	}
	
	/**
	 * gives the printout of all the orders in this class
	 * if no orders are in this class "geen bestellingen" is returned;
	 * @return a string thats the printout of all the orders
	 */
	public String printOutPayment()
	{
		StringBuilder b = new StringBuilder();
		if (this.size() == 0)
		{
			return "geen bestellingen!";
		}
		b.append("Tafel besteld door ").append(this.iterator().next().getOber()).append("\n");

		this.stream()
		.forEach(e -> b.append(e).append("\n"));
		DecimalFormat f = new DecimalFormat("##.00");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		b.append("totaal (€) = " +f.format(this.calcutateOrders()));
		//logger.info(b);
		
		return b.toString();
	}
	/* (non-Javadoc)
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	@Override
	public boolean add(Order o)
	{
		if (o == null)
		{
			return false;
		}

		if (!super.contains(o))
		{
			return super.add(o);
		}
		
		for (Order order : this) {
			{
				if (order.equals(o))
				{
					try {
						//order.setQuantity(order.getQuantity() + o.getQuantity());
						Order order2 = new Order(o.getLiquid(), order.getQuantity() + o.getQuantity(), o.getOber(), o.getDate());
						
						super.remove(order);
						super.add(order2);
						return true;
					} catch (QuantityToLowException | QuantityZeroException e) {
						// TODO Auto-generated catch block
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.util.HashSet#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object obj)
	{
		if (!super.contains(obj))
		{
			boolean bool = super.remove(obj);
			return bool;
		}
	
		for (Order order : this) 
		{
			if (order.equals(obj))
			{
				Order o2 = (Order) obj;
				try {
					Order order2 = new Order(o2.getLiquid(), order.getQuantity() - o2.getQuantity(), o2.getOber(), o2.getDate());	
					super.remove(order);
					super.add(order2);
//						order.setQuantity(order.getQuantity() - o2.getQuantity());//niet gebruiken want dan wordt het object zelf ook veranderd!
					return true;
				} catch (QuantityToLowException e) {
					// TODO Auto-generated catch block
					return false;
				} catch (QuantityZeroException e) {
					super.remove(o2);
					return true;
				}
			}
		}

		return false; //als orderset object leeg is
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection col)
	{
		return super.addAll(col);
	}
	
	/* (non-Javadoc)
	 * @see java.util.HashSet#contains(java.lang.Object)
	 */
//	@Override
//	public boolean contains(Object obj) 
//	{
//		if (!(obj instanceof  Order))
//		{
//			return false;
//		}
//		
//		for (Order order : this) 
//		{
//			Order o = (Order) obj;
//			if (!(order.equals(o) && order.getQuantity() == o.getQuantity()))
//			{
//				return false;
//			}
//		}	
//		return true;	
//	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection col)
	{
		OrderSet orders = new OrderSet();
		orders.addAll(col);//we maken van de collectie een orderset.
		for (Order order : orders)
		{
			if (!this.contains(order))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractSet#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return obj.toString().equals(this.toString());
	}
	
	/* (non-Javadoc)
	 * It's only possible to remove the collection if the collection if the ordersset completly envelops the collections
	 * @see java.util.AbstractSet#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection col)
	{
		OrderSet orders = new OrderSet();
		orders.addAll(col);//we maken van de collectie een orderset.

		//eerst kijken of we wel alles kunnen verwijderen, als dit niet zo is wordt er niets verwijderd!
		for (Order order : orders)
		{
			if (super.contains(order))
			{
				int aantal =this.stream().filter(o -> o.equals(order)).findFirst().get().getQuantity();
				if (aantal<order.getQuantity())
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		
		//check is ok dus de orders mogen verwijderd worden
		for (Order order : orders)
		{
			this.remove(order);
		}

		return true;
	}
	
}
