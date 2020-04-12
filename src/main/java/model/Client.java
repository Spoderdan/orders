package model;

/**
 * Model class coressponding to the data base table "client".
 * Contains the same fields as the table, constructors, getters, setters and a toString() method.
 */
public class Client {

    private int idclient;
    private String client_name;
    private String client_email;
    private String client_phone_number;
    private String client_city;

    /**
     * Parameterless constructor that calls the super() method.
     */
    public Client(){
        super();
    }

    /**
     * Constructor that contains all the fields of the client table.
     * @param idclient client id
     * @param client_name cient name
     * @param client_email client email
     * @param client_phone_number client phone number
     * @param client_city client city
     */
    public Client(int idclient, String client_name, String client_email, String client_phone_number, String client_city){
        super();
        this.idclient = idclient;
        this.client_name = client_name;
        this.client_email = client_email;
        this.client_phone_number = client_phone_number;
        this.client_city = client_city;
    }

    /**
     * Constructor that sets all fields except the client id.
     * @param client_name client name
     * @param client_email client email
     * @param client_phone_number client phone number
     * @param client_city client city
     */
    public Client(String client_name, String client_email, String client_phone_number, String client_city){
        super();
        this.client_name = client_name;
        this.client_email = client_email;
        this.client_phone_number = client_phone_number;
        this.client_city = client_city;
    }

    public int getIdclient(){
        return idclient;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public String getClient_phone_number() {
        return client_phone_number;
    }

    public String getClient_city() {
        return client_city;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public void setClient_phone_number(String client_phone_number) {
        this.client_phone_number = client_phone_number;
    }

    public void setClient_city(String client_city) {
        this.client_city = client_city;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idclient=" + idclient +
                ", client_name='" + client_name + '\'' +
                ", client_email='" + client_email + '\'' +
                ", client_phone_number='" + client_phone_number + '\'' +
                ", client_city='" + client_city + '\'' +
                '}';
    }
}
