package model;

/**
 * Model class coressponding to the data base table "product".
 * Contains the same fields as the table, constructors, getters, setters and a toString() method.
 */
public class Product {

    private int idproduct;
    private String product_name;
    private float product_price;
    private int product_stock;

    /**
     * Parameterless constructor that calls the super() method.
     */
    public Product(){
        super();
    }

    /**
     * Constructor that contains all fields of the table.
     * @param idproduct product id
     * @param product_name product name
     * @param product_price product price
     * @param product_stock product sotck
     */
    public Product(int idproduct, String product_name, float product_price, int product_stock) {
        this.idproduct = idproduct;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
    }

    /**
     * Constructor that contains all fields except the product id.
     * @param product_name product name
     * @param product_price product price
     * @param product_stock product stock
     */
    public Product(String product_name, float product_price, int product_stock) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_stock = product_stock;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idproduct=" + idproduct +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", product_stock=" + product_stock +
                '}';
    }
}
