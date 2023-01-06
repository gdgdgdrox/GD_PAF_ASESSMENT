package vttp2022.paf.assessment.eshop.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class OrderStatus {

	private String orderId;
	private String deliveryId = "";
	private String status = "pending"; // or "dispatched"

	public String getOrderId() { return this.orderId; }
	public void setOrderId(String orderId) { this.orderId = orderId; }

	public String getDeliveryId() { return this.deliveryId; }
	public void setDeliveryId(String deliveryId) { this.deliveryId = deliveryId; }

	public String getStatus() { return this.status; }
	public void setStatus(String status) { this.status = status; }


	@Override
	public String toString() {
		return "{" +
			" orderId='" + getOrderId() + "'" +
			", deliveryId='" + getDeliveryId() + "'" +
			", status='" + getStatus() + "'" +
			"}";
	}

	public static OrderStatus createOrderStatus(JsonObject obj, String status){
		OrderStatus os = new OrderStatus();
		os.setOrderId(obj.getString("orderId"));
		os.setDeliveryId(obj.getString("deliveryId"));
		os.setStatus(status);
		return os;
	}

	public static JsonObject createOrderStatusJson(OrderStatus os){
		if (os.getStatus().equals("dispatched")){
			return Json.createObjectBuilder()
										.add("orderId", os.getOrderId())
										.add("deliveryId", os.getDeliveryId())
										.add("status", os.getStatus()).build();
		}
		else{
			return Json.createObjectBuilder()
										.add("orderId", os.getOrderId())
										.add("status", os.getStatus()).build();
		}
	}

}
