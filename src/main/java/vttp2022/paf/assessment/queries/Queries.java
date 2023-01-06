package vttp2022.paf.assessment.queries;

public class Queries {
    public static final String SQL_FIND_CUSTOMER_BY_NAME = "select * from customers where name = ?";
    public static final String SQL_INSERT_ORDER = "insert into online_order(order_id, name, order_date) values(?, ?, ?)"; 
    public static final String SQL_INSERT_LINE_ITEMS = "insert into line_items(order_id, item, quantity) values (?,?,?)";
    public static final String SQL_INSERT_ORDER_STATUS = """
        INSERT INTO order_status(order_id, delivery_id, status, status_update) VALUES(
                ?, ?, ?, ?
        )
            """;
    public static final String SQL_FIND_CUSTOMER_STATUS = """
        select customers.name, status as status from customers
        join online_order 
        on customers.name=online_order.name
        join order_status
        on online_order.order_id=order_status.order_id
        where customers.name = ?;
        
            """;
    
}
