package bll;

import dao.OrderDAO;
import model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business logic class coressponding to the table "order".
 */
public class OrderBLL {

    //private Validator<Order> validator;
    private final OrderDAO OrderDAO;

    public OrderBLL(){
        //validator = new OrderIdValidator();
        OrderDAO = new OrderDAO("test");
    }

    /*private boolean failValidation(int id) {
        Order Order = OrderDAO.find(id);
        try{
            validator.validate(Order);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return true;
        }
        return false;
    }*/

    public Order findOrderById(int id) {
        Order order = OrderDAO.find(id);
        if (order == null)
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        return order;
    }

    public List<Order> findAll(){
        return OrderDAO.findAll();
    }

    public Order findOrderByClientId(int clientId){
        List<Order> list = OrderDAO.findAll();
        for(Order o : list)
            if(o.getClient_idclient() == clientId)
                return  o;
        return null;
    }

    public boolean insert(Order order){
        //if(failValidation(order.getIdOrder()))
         //   return false;
        ArrayList<String> values = new ArrayList<>();
        values.add(Integer.toString(order.getOrder_quantity()));
        values.add(Integer.toString(order.getClient_idclient()));
        values.add(Integer.toString(order.getProduct_idproduct()));
        values.add(Integer.toString(order.getOrderdetails_idorderdetails()));
        return OrderDAO.insert(values);
    }

    public boolean update(String field, String value, int id){
        //if (failValidation(id))
         //   return false;
        return OrderDAO.update(field, value, id);
    }

    public boolean delete(int id){
        //if (failValidation(id))
         //   return false;
        return OrderDAO.delete(id);
    }

}
