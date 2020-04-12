package dao;

import model.Order;

/**
 * Data access class for the "order" table.
 * Contains some specific querries.
 */
public class OrderDAO extends AbstractDAO<Order>{

    private static String field;
    private static final String FIND = "SELECT * FROM order WHERE " + field + "=?";
    private static final String FIND_ALL = "SELECT * FROM order";
    private static final String INSERT = "INSERT INTO order (`order_quantity`,`client_idclient`,`product_idproduct`, `orderdetails_idorderdetails`) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE order SET " + field + "=? WHERE idorder=?";
    private static final String DELETE = "DELETE FROM order WHERE idorder=?";

    public OrderDAO(String field){
        super();
        setField(field);
    }

    public void setField(String field){
        OrderDAO.field = field;
    }

}
