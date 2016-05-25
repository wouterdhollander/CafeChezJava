package be.leerstad.EindwerkChezJava.model;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;

public class OrderSet extends HashSet<Order> {
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	public OrderSet()
	{
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double calcutateOrders(){
		double total =this
				.stream()
				.mapToDouble(Order::getPrice)
				.sum();
		return total;
	}
	
	public String printOutPayment()
	{
		StringBuilder b = new StringBuilder();
		if (this == null)
		{
			return b.toString();
		}
		b.append("Tafel besteld door ").append(this.iterator().next().getOber()).append("\n");

		//Set<Order> orders = this.getOrders();
		this.stream()
		.forEach(e -> b.append(e).append("\n"));
		b.append("totaal (€) = " + this.calcutateOrders());
		logger.info(b);
		
		return b.toString();
	}
	@Override
	public boolean add(Order o)
	{
		
		if (o == null)
		{
			return false;
		}
		if (!this.contains(o))
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
	
	@Override
	public boolean remove(Object obj)
	{
		if (!this.contains(obj))
		{
			return super.remove(obj);
		}
	
		for (Order order : this) {
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
		}
		return false; //als orderset object leeg is
	}
	
	@Override
	public boolean removeAll(Collection col)
	{
		OrderSet orders = new OrderSet();
		orders.addAll(col);//we maken van de collectie een orderset.

		//eerst kijken of we wel alles kunnen verwijderen, als dit niet zo is wordt er niets verwijderd!
		for (Order order : orders)
		{
			if (this.contains(order))
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
