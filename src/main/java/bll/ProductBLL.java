package bll;

import dao.ProductDAO;
import model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business logic class coressponding to the table "product".
 */
public class ProductBLL {

    private final ProductDAO ProductDAO;

    public ProductBLL() {
        ProductDAO = new ProductDAO("test");
    }

    public Product findProductById(int id) {
        Product product = ProductDAO.find(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    public Product findProductByName(String name){
        List<Product> list = ProductDAO.findAll();
        for(Product p : list)
            if(p.getProduct_name().equals(name))
                return p;
        return null;
    }

    public List<Product> findAll(){
        return ProductDAO.findAll();
    }

    public boolean insert(Product product){
        ArrayList<String> values = new ArrayList<>();
        values.add(product.getProduct_name());
        values.add(Double.toString(product.getProduct_price()));
        values.add(Integer.toString(product.getProduct_stock()));
        return ProductDAO.insert(values);
    }

    public boolean update(String field, String value, int id){
        return ProductDAO.update(field, value, id);
    }

    public boolean delete(int id){
        return ProductDAO.delete(id);
    }

}
