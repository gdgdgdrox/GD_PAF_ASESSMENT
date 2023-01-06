package vttp2022.paf.assessment.eshop.respositories;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.queries.Queries;

@Repository
public class OrderStatusRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertOrderStatus(OrderStatus os){
        return jdbcTemplate.update(Queries.SQL_INSERT_ORDER_STATUS, os.getOrderId(), os.getDeliveryId(), os.getStatus(), new Timestamp(System.currentTimeMillis()));
    }
}
