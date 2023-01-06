package vttp2022.paf.assessment.eshop.services;

import java.io.Reader;
import java.io.StringReader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.OrderStatusRepository;

@Service
public class WarehouseService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderStatusRepository orderStatusRepo;

	public static String baseURL = "http://paf.chuklee.com/dispatch";

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		//formulating the request body
		JsonArray lineItemsJsonArray = LineItem.createLineItemsJsonArray(order.getLineItems());
		JsonObject body = Json.createObjectBuilder().add("orderId", order.getOrderId())
								.add("name", order.getName())
								.add("address", order.getAddress())
								.add("email", order.getEmail())
								.add("lineItems", lineItemsJsonArray)
								.add("createdBy", "FOO GUO DONG")
								.build();

		// Set<Map.Entry<String,JsonValue>> postbody = body.entrySet();
		// for (Map.Entry<String, JsonValue> a : postbody){
		// 	System.out.println(a);
		// }

		//building the request entity
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);								
		HttpEntity<String> request = new HttpEntity<String>(body.toString(), header);
		String fullURL = "http://paf.chuklee.com/dispatch/" + order.getOrderId();
		String responseBody = "";
		OrderStatus os = new OrderStatus();

		//posting the request entity
		try{
			ResponseEntity<String> response = restTemplate.postForEntity(fullURL, request, String.class);
			if(response.getStatusCode().equals(HttpStatus.ACCEPTED)){
				System.out.println(" REQUEST WAS ACCEPTED !");
				responseBody = response.getBody();
				Reader strReader = new StringReader(responseBody);
        		JsonReader jsonReader = Json.createReader(strReader);
        		JsonObject jsonObject = jsonReader.readObject();
				os = OrderStatus.createOrderStatus(jsonObject, "dispatched");
				orderStatusRepo.insertOrderStatus(os);
				return os;	
			}
		
		}
		catch (HttpServerErrorException ex){
			System.out.println("GOT HTTP SERVER ERROR");
			ex.printStackTrace();
			JsonObject jsonObject = Json.createObjectBuilder()
								.add("orderId", order.getOrderId())
								.add("deliveryId", "null")
								.build();
			os = OrderStatus.createOrderStatus(jsonObject, "pending");
			orderStatusRepo.insertOrderStatus(os);
		}
		return os;
	}
}
