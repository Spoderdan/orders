package dao;

import model.OrderDetails;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data access class for the "orderdetails" table.
 * Contains some specific querries.
 */
public class OrderDetailsDAO extends AbstractDAO<OrderDetails> {

    private static String field;
    private static final String FIND = "SELECT * FROM orderdetails WHERE " + field + "=?";
    private static final String FIND_ALL = "SELECT * FROM orderdetails";
    private static final String INSERT = "INSERT INTO orderdetails (`orderdetails_address`,`orderdetails_toa`) VALUES (?,?)";
    private static final String UPDATE = "UPDATE orderdetails SET " + field + "=? WHERE idorderdetails=?";
    private static final String DELETE = "DELETE FROM orderdetails WHERE idorderdetails=?";

    public OrderDetailsDAO(String field){
        super();
        setField(field);
    }

    public long getDate(String newDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormat.parse(newDate);
        return date.getTime();
    }

    public void setField(String field){
        OrderDetailsDAO.field = field;
    }

}
