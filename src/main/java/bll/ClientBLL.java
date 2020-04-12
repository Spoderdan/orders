package bll;

import bll.validators.ClientEmailValidator;
import bll.validators.ClientPhoneNumberValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business logic class coressponding to the table "client".
 */
public class ClientBLL {

    private final List<Validator<Client>> validators;
    private final ClientDAO ClientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new ClientEmailValidator());
        validators.add(new ClientPhoneNumberValidator());

        ClientDAO = new ClientDAO("test");
    }

    private boolean valid(Client client) {
        for(Validator<Client> v : validators){
            try{
                v.validate(client);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    private boolean failValidation(Client client) {
        return valid(client);
    }

    private boolean failValidation(int id, String field, String value) {
        Client client = findClientById(id);
        if(field.equals("client_email"))
            client.setClient_email(value);
        else if(field.equals("client_phone_number"))
            client.setClient_phone_number(value);
        return valid(client);
    }

    public Client findClientById(int id) {
        Client client = ClientDAO.find(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    public Client findClientByName(String name){
        List<Client> list = ClientDAO.findAll();
        for(Client c : list)
            if(c.getClient_name().equals(name))
                return c;
        return null;
    }

    public List<Client> findAll(){
        return ClientDAO.findAll();
    }

    public boolean insert(Client client){
        if(failValidation(client))
            return false;
        ArrayList<String> values = new ArrayList<>();
        values.add(client.getClient_name());
        values.add(client.getClient_email());
        values.add(client.getClient_phone_number());
        values.add(client.getClient_city());
        return ClientDAO.insert(values);
    }

    public boolean update(String field, String value, int id){
        if (failValidation(id, field, value))
            return false;
        return ClientDAO.update(field, value, id);
    }

    public boolean delete(int id){
        return ClientDAO.delete(id);
    }

}
