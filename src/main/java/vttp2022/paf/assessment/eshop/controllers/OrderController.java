package vttp2022.paf.assessment.eshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.services.CoordinateService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

@RestController
public class OrderController {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CoordinateService coordinateSvc;

	@Autowired
	private WarehouseService warehouseSvc;

	//TODO: Task 3
	@PostMapping(path="/order")
	public ResponseEntity<String> returnOrderStatus(@RequestBody MultiValueMap<String,String> payload){
		System.out.println("IN CONTROLLER");
		String name = payload.getFirst("name");
		Optional<Customer> optCustomer = customerRepo.findCustomerByName(name);

		//customer does not exists
		if (optCustomer.isEmpty()){
			JsonObject errorObj = Json.createObjectBuilder().add("error", "Customer " + name + " not found").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObj.toString());
		}

		//customer exists
		Customer customer = optCustomer.get();

		//create line items
		List<LineItem> lineItems = LineItem.createLineItems(payload.get("item"), payload.get("quantity"));
		
		//create order
		Order order = Order.createOrder(customer, lineItems);

		//Save order and save line items
		String result = coordinateSvc.insertOrderAndLineItem(order);

		//result is either a single digit 1 (since we inserting only 1 record) or a long string of error message. 
		//if length > 1, means it is an error message
		if (result.length() != 1){
			JsonObject errorObj = Json.createObjectBuilder().add("error", result).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorObj.toString());
		}
		else{
			// insert order and line items are successful. we now need to dispatch.
			System.out.println("DISPATCHING ORDER");
			OrderStatus os = warehouseSvc.dispatch(order);
			System.out.println("OS IN CONTROLLER " + os);
			JsonObject response = OrderStatus.createOrderStatusJson(os);
			return ResponseEntity.ok().body(response.toString());

		}

	}

	//Task 6
	@GetMapping(path="/api/order/{name}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getCustomerOrderStatus(@PathVariable String name){
		System.out.println("CUSTOMER NAME " + name);
		Optional<JsonObject> optObject = customerRepo.findCustomerStatus(name);
		if (optObject.isEmpty()){
			JsonObject errorObj = Json.createObjectBuilder().add("error", "Customer " + name + " either does not exist or does not have any orders placed yet").build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObj.toString());
		}
		return ResponseEntity.ok().body(optObject.get().toString());
	}


}
