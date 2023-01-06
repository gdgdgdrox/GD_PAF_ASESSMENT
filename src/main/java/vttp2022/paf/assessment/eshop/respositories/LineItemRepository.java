package vttp2022.paf.assessment.eshop.respositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.queries.Queries;

@Repository
public class LineItemRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int[] insertIntoLineItems(Order order){
        List<LineItem> lineItems = order.getLineItems();
        List<Object[]> lineItemsList = lineItems.stream().map(item -> new Object[]{order.getOrderId(), item.getItem(), item.getQuantity()}).toList();
        return jdbcTemplate.batchUpdate(Queries.SQL_INSERT_LINE_ITEMS, lineItemsList);
    }
}
