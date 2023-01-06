package vttp2022.paf.assessment.eshop.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

// DO NOT CHANGE THIS CLASS
public class Order {

	private String orderId;
	private String deliveryId;
	private String name;
	private String address;
	private String email;
	private String status;
	private Date orderDate = new Date();
	private List<LineItem> lineItems = new LinkedList<>();

	public String getOrderId() { return this.orderId; }
	public void setOrderId(String orderId) { this.orderId = orderId; }

	public String getDeliveryId() { return this.deliveryId; }
	public void setDeliveryId(String deliveryId) { this.deliveryId = deliveryId; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }

	public String getStatus() { return this.status; }
	public void setStatus(String status) { this.status = status; }

	public Date getOrderDate() { return this.orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

	public Customer getCustomer() { 
		Customer customer = new Customer();
		customer.setName(name);
		customer.setAddress(address);
		customer.setEmail(email);
		return customer;
	}
	public void setCustomer(Customer customer) {
		name = customer.getName();
		address = customer.getAddress();
		email = customer.getEmail();
	}

	public List<LineItem> getLineItems() { return this.lineItems; }
	public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
	public void addLineItem(LineItem lineItem) { this.lineItems.add(lineItem); }

	public static Order createOrder(Customer customer, List<LineItem> lineItems){
		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString().substring(0,8));
		order.setName(customer.getName());;
		order.setAddress(customer.getAddress());
		order.setEmail(customer.getEmail());
		order.setOrderDate(new Date());
		order.setLineItems(lineItems);
		return order;
	}
	@Override
	public String toString() {
		return "{" +
			" orderId='" + getOrderId() + "'" +
			", deliveryId='" + getDeliveryId() + "'" +
			", name='" + getName() + "'" +
			", address='" + getAddress() + "'" +
			", email='" + getEmail() + "'" +
			", status='" + getStatus() + "'" +
			", orderDate='" + getOrderDate() + "'" +
			", lineItems='" + getLineItems() + "'" +
			"}";
	}

}
