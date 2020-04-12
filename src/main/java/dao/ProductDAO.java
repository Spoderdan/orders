package dao;

import model.Client;
import model.Product;

/**
 * Data access class for the "product" table.
 * Contains some specific querries.
 */
public class ProductDAO extends AbstractDAO<Product>{

    private static String field;
    private static final String FIND = "SELECT * FROM product WHERE " + field + "=?";
    private static final String FIND_ALL = "SELECT * FROM product";
    private static final String INSERT = "INSERT INTO product (`product_name`,`product_price`,`product_stock`) VALUES (?,?,?)";
    private static final String UPDATE = "UPDATE product SET " + field + "=? WHERE idproduct=?";
    private static final String DELETE = "DELETE FROM product WHERE idproduct=?";

    public ProductDAO(String field){
        super();
        setField(field);
    }

    public void setField(String field){
        ProductDAO.field = field;
    }

}
