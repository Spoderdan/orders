package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import connection.ConnectionFactory;

/**
 * This class performs querries on the data base, based on reflection, coressponding to the table selected.
 * @param <T> table of the data base
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    /**
     * Constructor that gets the type of the object T.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Utility method to determine if a string is an integer.
     * @param str string
     * @return true if the string is an integer, false otherwise.
     */
    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Utility method to determine if a string is a float.
     * @param str string
     * @return true if the string is a float, false otherwise.
     */
    private static boolean isFloat(String str){
        Pattern pattern = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
        return pattern.matcher(str).matches();
    }

    /**
     * Reflection method that returns a list of objects of type T from a result set.
     * @param resultSet result set from a querry
     * @return list of objects T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Creates a querry to select fields from the data base, depending on the type of T.
     * @param field field to be searched for
     * @return a string corresponding to a querry of select type.
     */
    private String createSelectQuery(String field) {
        return "SELECT * FROM `" +
                type.getSimpleName() +
                "` WHERE " +
                field +
                " =?";
    }

    /**
     * Creates a querry to insert an element in the data base, depending on the type of T.
     * @return a string corresponding to a querry of insert type.
     */
    private String createInsertQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("`").append(type.getSimpleName()).append("`");
        sb.append(" (");

        int i = 0;
        for(Field f : type.getDeclaredFields()){
            if(i == 0){
                i++;
                continue;
            }
            sb.append("`").append(f.getName()).append("`");
            if(i < type.getDeclaredFields().length - 1)
                sb.append(",");
            i++;
        }

        sb.append(") VALUES (");
        int j = 0;
        while(j < type.getDeclaredFields().length){
            if(j == 0){
                j++;
                continue;
            }
            sb.append("?");
            if(j < type.getDeclaredFields().length - 1)
                sb.append(",");
            j++;
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Creates a querry to update an element of the data base, depending on the type of T.
     * @param field field to be updated
     * @param value new value
     * @return a string corresponding to a querry of update type.
     */
    private String createUpdateQuery(String field, String value) {
        return "UPDATE `" +
                type.getSimpleName() +
                "` SET " +
                field +
                " =? WHERE " +
                value +
                " =?";
    }

    /**
     * Creates a querry to delete an element from the data base, depending on the type of T.
     * @param id id of the object T
     * @return a string corresponding to a querry of delete type.
     */
    private String createDeleteQuery(String id) {
        return "DELETE FROM `" +
                type.getSimpleName() +
                "` WHERE " +
                id +
                " =?";
    }

    /**
     * Finds all elements of type T from the data base.
     * @return a list of objects of type T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + "`" + type.getSimpleName() + "`";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Finds an object of type T from the data base.
     * @param id id of the object
     * @return returns the object corresponding to the id
     */
    public T find(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Field[] fields = type.getDeclaredFields();
        String query = createSelectQuery(fields[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Insert an object of type T from the data base, having the fields inside the list "values"
     * @param values list of fields for the new object
     * @return true if insertion succeded, false otherwise
     */
    public boolean insert(ArrayList<String> values) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for(String s : values){
                if(s.charAt(0) == '0')
                    statement.setString(i, s);
                else if(isInteger(s))
                    statement.setInt(i, Integer.parseInt(s));
                else if(isFloat(s))
                    statement.setFloat(i, Float.parseFloat(s));
                else
                    statement.setString(i, s);
                i++;
            }
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    /**
     * Update an element of type T from the data base.
     * @param field field to be updated
     * @param value new value for the field
     * @param id id of the element to be updated
     * @return true if update succeded, false othrwise
     */
    public boolean update(String field, String value, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();
        String query = createUpdateQuery(field, fields[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            if(value.charAt(0) == '0')
                statement.setString(1, value);
            else if(isInteger(value))
                statement.setInt(1, Integer.parseInt(value));
            else if(isFloat(value))
                statement.setFloat(1, Float.parseFloat(value));
            else
                statement.setString(1, value);
            statement.setInt(2, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    /**
     * Delete an object of type T from the data base.
     * @param id id of the element to be deleted
     * @return true if deletion succeded, false otherwise
     */
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        Field[] fields = type.getDeclaredFields();
        String query = createDeleteQuery(fields[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

}
