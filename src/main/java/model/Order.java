package model;

/**
 * Model class coressponding to the data base table "order".
 * Contains the same fields as the table, constructors, getters, setters and a toString() method.
 */
public class Order {

    private int idorder;
    private int order_quantity;
    private int client_idclient;
    private int product_idproduct;
    private int orderdetails_idorderdetails;

    /**
     * Parameterless constructor that calls the super() method.
     */
    public Order(){
        super();
    }

    /**
     * Constructor that contains all the fields of the table.
     * @param idorder order id
     * @param order_quantity order quantity
     * @param client_idclient client id
     * @param product_idproduct product id
     * @param orderdetails_idorderdetails orderdetails id
     */
    public Order(int idorder, int order_quantity, int client_idclient, int product_idproduct, int orderdetails_idorderdetails) {
        this.idorder = idorder;
        this.order_quantity = order_quantity;
        this.client_idclient = client_idclient;
        this.product_idproduct = product_idproduct;
        this.orderdetails_idorderdetails = orderdetails_idorderdetails;
    }

    /**
     *  Constructor that contains all fields except the order id.
     * @param order_quantity order quantity
     * @param client_idclient client id
     * @param product_idproduct product id
     * @param orderdetails_idorderdetails orderdetails id
     */
    public Order(int order_quantity, int client_idclient, int product_idproduct, int orderdetails_idorderdetails) {
        this.order_quantity = order_quantity;
        this.client_idclient = client_idclient;
        this.product_idproduct = product_idproduct;
        this.orderdetails_idorderdetails = orderdetails_idorderdetails;
    }

    public int getIdorder() {
        return idorder;
    }

    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public int getClient_idclient() {
        return client_idclient;
    }

    public void setClient_idclient(int client_idclient) {
        this.client_idclient = client_idclient;
    }

    public int getProduct_idproduct() {
        return product_idproduct;
    }

    public void setProduct_idproduct(int product_idproduct) {
        this.product_idproduct = product_idproduct;
    }

    public int getOrderdetails_idorderdetails() {
        return orderdetails_idorderdetails;
    }

    public void setOrderdetails_idorderdetails(int orderdetails_idorderdetails) {
        this.orderdetails_idorderdetails = orderdetails_idorderdetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idorder=" + idorder +
                ", order_quantity=" + order_quantity +
                ", client_idclient=" + client_idclient +
                ", product_idproduct=" + product_idproduct +
                ", orderdetails_idorderdetails=" + orderdetails_idorderdetails +
                '}';
    }
}
