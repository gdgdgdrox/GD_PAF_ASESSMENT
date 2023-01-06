package vttp2022.paf.assessment.eshop.respositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.queries.Queries;

@Repository
public class OrderRepository {
	/*	private String orderId;
	private String deliveryId;
	private String name;
	private String address;
	private String email;
	private String status;
	private Date orderDate = new Date();
	private List<LineItem> lineItems = new LinkedList<>(); */

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// TODO: Task 3
	public Order createOrder(Customer customer, List<LineItem> lineItems){
		Order order = new Order();
		order.setOrderId(UUID.randomUUID().toString().substring(0,8));
		order.setName(customer.getName());;
		order.setAddress(customer.getAddress());
		order.setEmail(customer.getEmail());
		order.setOrderDate(new Date());
		order.setLineItems(lineItems);
		return order;
	}

	public String insertOrder(Order order){
		try{
			int count = jdbcTemplate.update(Queries.SQL_INSERT_ORDER, order.getOrderId(), order.getName(), order.getOrderDate());
			return String.valueOf(count);
		}
		catch (DataAccessException e){
			return e.getMessage();
		}
	}


}
