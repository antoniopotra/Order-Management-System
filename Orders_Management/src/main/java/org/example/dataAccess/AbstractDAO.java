package org.example.dataAccess;

import org.example.connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * implements basic CRUD methods for
 *
 * @param <T> the model which corresponds to a table from the database
 */
public class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * creates a query for selecting an object from a database
     *
     * @param field based on any field
     * @return the query
     */
    private String createSelectQuery(String field) {
        return "select * from " + type.getSimpleName().toLowerCase() + "s where " + field + " = ?";
    }

    private String createSelectQuery() {
        return "select * from " + type.getSimpleName().toLowerCase() + "s";
    }

    private String createInsertQuery(T t) {
        StringBuilder queryBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        queryBuilder.append("insert into ").append(type.getSimpleName().toLowerCase()).append("s (");
        valuesBuilder.append("values (");

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;

            try {
                fieldValue = field.get(t);
                if (fieldValue != null) {
                    queryBuilder.append(fieldName).append(",");
                    valuesBuilder.append("'").append(fieldValue).append("',");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        queryBuilder.setLength(queryBuilder.length() - 1);
        valuesBuilder.setLength(valuesBuilder.length() - 1);

        queryBuilder.append(") ").append(valuesBuilder).append(");");

        return queryBuilder.toString();
    }

    private String createDeleteQuery(String field) {
        return "delete from " + type.getSimpleName().toLowerCase() + "s where " + field + " = ?";
    }

    /**
     * finds an object by an id in the database (assuming the id is unique)
     *
     * @param id the id of the object
     * @return the object
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.isEmpty())
                return null;

            return result.get(0);
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.isEmpty())
                return null;

            return result;
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * inserts an object in the database
     *
     * @param t the object
     * @throws SQLException if there is an error
     */
    public void insert(T t) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * deletes an object from the database
     *
     * @param id the id of the object (assuming unique)
     * @throws SQLException if there was an error
     */
    public void deleteById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * creates a list of objects from a result set obtained after a query
     *
     * @param resultSet the result set from where the objects will be extracted
     * @return the list of objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        Constructor<?> constructor = null;

        for (Constructor<?> item : constructors) {
            constructor = item;
            if (item.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            while (resultSet.next()) {
                assert constructor != null;
                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }

                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * gets the name of the fields of an object
     *
     * @param object the object
     * @return a string array with the name of the fields
     */
    public String[] getFieldNames(T object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        return fieldNames;
    }
}