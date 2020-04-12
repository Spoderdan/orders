package model;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderDetails {

    private int idorderdetails;
    private String orderdetails_address;
    private Timestamp orderdetails_toa;

    public OrderDetails(){
        super();
    }

    public OrderDetails(int idorderdetails, String oderdetails_address, Timestamp oderdetails_toa) {
        this.idorderdetails = idorderdetails;
        this.orderdetails_address = oderdetails_address;
        this.orderdetails_toa = oderdetails_toa;
    }

    public OrderDetails(String oderdetails_address, Timestamp oderdetails_toa) {
        this.orderdetails_address = oderdetails_address;
        this.orderdetails_toa = oderdetails_toa;
    }

    public int getIdorderdetails() {
        return idorderdetails;
    }

    public void setIdorderdetails(int idorderdetails) {
        this.idorderdetails = idorderdetails;
    }

    public String getOrderdetails_address() {
        return orderdetails_address;
    }

    public void setOrderdetails_address(String orderdetails_address) {
        this.orderdetails_address = orderdetails_address;
    }

    public Timestamp getOrderdetails_toa() {
        return orderdetails_toa;
    }

    public void setOrderdetails_toa(Timestamp orderdetails_toa) {
        this.orderdetails_toa = orderdetails_toa;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "idorderdetails=" + idorderdetails +
                ", oderdetails_address='" + orderdetails_address + '\'' +
                ", oderdetails_toa='" + orderdetails_toa + '\'' +
                '}';
    }
}
