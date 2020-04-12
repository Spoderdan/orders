package dao;

import model.Client;

/**
 * Data access class for the "client" table.
 * Contains some specific querries.
 */
public class ClientDAO extends AbstractDAO<Client>{

    private static String field;
    private static final String FIND = "SELECT * FROM client WHERE " + field + "=?";
    private static final String FIND_ALL = "SELECT * FROM client";
    private static final String INSERT = "INSERT INTO client (`client_name`,`client_email`,`client_phone_number`) VALUES (?,?,?)";
    private static final String UPDATE = "UPDATE client SET " + field + "=? WHERE idclient=?";
    private static final String DELETE = "DELETE FROM client WHERE idclient=?";

    public ClientDAO(String field){
        super();
        setField(field);
    }

    public void setField(String field){
        ClientDAO.field = field;
    }

}
