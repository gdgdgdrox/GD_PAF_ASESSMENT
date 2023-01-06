package vttp2022.paf.assessment.eshop.models;

import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class LineItem {

	private String item;
	private Integer quantity;

	public String getItem() { return this.item; }
	public void setItem(String item) { this.item = item; }

	public Integer getQuantity() { return this.quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }

	public static List<LineItem> createLineItems(List<String> items, List<String> quantities){
		List<LineItem> lineItems = new LinkedList<>();
		for (int i = 0; i < items.size(); i++){
			LineItem lineItem = new LineItem();
			lineItem.setItem(items.get(i));
			lineItem.setQuantity(Integer.parseInt(quantities.get(i)));
			lineItems.add(lineItem);
		}
		return lineItems;
	}

	public static JsonArray createLineItemsJsonArray(List<LineItem> lineItems){
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (LineItem lineItem : lineItems){
			JsonObject lineItemJson = Json.createObjectBuilder().add("item", lineItem.getItem())
										.add("quantity", lineItem.getQuantity())
										.build();
			jsonArrayBuilder.add(lineItemJson);
		}
		return jsonArrayBuilder.build();
	}
	
	@Override
	public String toString() {
		return "{" +
			" item='" + getItem() + "'" +
			", quantity='" + getQuantity() + "'" +
			"}";
	}



}
