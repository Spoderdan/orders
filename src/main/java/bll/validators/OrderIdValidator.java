package bll.validators;

import connection.ConnectionFactory;
import dao.AbstractDAO;
import model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderIdValidator implements Validator<Order>{

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private int minClientId;
    private int maxClientId;
    private int minProductId;
    private int maxProductId;
    private int minOrderDetailsId;
    private int maxOrderDetailsId;

    public OrderIdValidator(){
        setIds();
    }

    private void setIds(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT MIN(client_idclient), MAX(client_idclient), MIN(product_idproduct), MAX(product_idproduct), MIN(orderdetails_idorderdetails), MAX(orderdetails_idorderdetails) FROM oderdetailsdb.order");
            resultSet = statement.executeQuery();
            minClientId = resultSet.getInt(1);
            maxClientId = resultSet.getInt(2);
            minProductId = resultSet.getInt(3);
            maxProductId = resultSet.getInt(4);
            minOrderDetailsId = resultSet.getInt(5);
            maxOrderDetailsId = resultSet.getInt(6);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderIdValidator: " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    @Override
    public void validate(Order order) {
        if (order.getClient_idclient() < minClientId || order.getClient_idclient() > maxClientId)
            throw new IllegalArgumentException("Client id is not a valid client id");
        if(order.getProduct_idproduct() < minProductId || order.getProduct_idproduct() > maxProductId)
            throw new IllegalArgumentException("Product id is not a valid product id");
        if(order.getOrderdetails_idorderdetails() < minOrderDetailsId || order.getOrderdetails_idorderdetails() > maxOrderDetailsId)
            throw new IllegalArgumentException("Order details id is not a valid order details id");
    }
}
