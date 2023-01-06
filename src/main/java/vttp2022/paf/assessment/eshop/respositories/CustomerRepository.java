package vttp2022.paf.assessment.eshop.respositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.queries.Queries;

@Repository
public class CustomerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	// You cannot change the method's signature
	public Optional<Customer> findCustomerByName(String name) {
		SqlRowSet result = jdbcTemplate.queryForRowSet(Queries.SQL_FIND_CUSTOMER_BY_NAME, name);
		if (result.next()){
			Customer customer = new Customer();
			customer.setName(result.getString("name"));
			customer.setEmail(result.getString("email"));
			customer.setAddress(result.getString("address"));
			return Optional.of(customer);
		}
		else{
			return Optional.empty();
		}
	}

	//Task 6
	public Optional<JsonObject> findCustomerStatus(String name){
		SqlRowSet result = jdbcTemplate.queryForRowSet(Queries.SQL_FIND_CUSTOMER_STATUS, name);
		if (!result.next()){
			return Optional.empty();
		}
		else{
			result.previous();
			int dispatchedCount = 0;
			int pendingCount = 0;
			while(result.next()){
				if (result.getString("status").equals("dispatched")){
					dispatchedCount++;
				}
				else pendingCount++;
			}
			return Optional.of(Json.createObjectBuilder().add("name", name).add("dispatched", dispatchedCount).add("pending", pendingCount).build());
		}
	}

}
