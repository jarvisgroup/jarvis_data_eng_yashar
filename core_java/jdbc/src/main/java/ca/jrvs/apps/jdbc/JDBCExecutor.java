package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String args){
        DatabaseConnectionManager dcm  = new DatabaseConnectionManager("localhost", "hpussport","postgres","password");
        try{
            Connection connection = dcm.getConnection();
//            CustomerDAO customerDAO = new CustomerDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
