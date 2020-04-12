package bll;

import dao.OrderDetailsDAO;
import model.OrderDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business logic class coressponding to the table "orderdetails".
 */
public class OrderDetailsBLL {

    private final OrderDetailsDAO OrderDetailsDAO;

    public OrderDetailsBLL() {
        OrderDetailsDAO = new OrderDetailsDAO("test");
    }

    public OrderDetails findOrderDetailsById(int id) {
        OrderDetails orderDetails = OrderDetailsDAO.find(id);
        if (orderDetails == null)
            throw new NoSuchElementException("The OrderDetails with id =" + id + " was not found!");
        return orderDetails;
    }

    public List<OrderDetails> findAll(){
        return OrderDetailsDAO.findAll();
    }

    public boolean insert(OrderDetails orderDetails){
        ArrayList<String> values = new ArrayList<>();
        values.add(orderDetails.getOrderdetails_address());
        values.add(String.valueOf(orderDetails.getOrderdetails_toa()));
        return OrderDetailsDAO.insert(values);
    }

    public boolean update(String field, String value, int id){
        return OrderDetailsDAO.update(field, value, id);
    }

    public boolean delete(int id){
        return OrderDetailsDAO.delete(id);
    }

}
