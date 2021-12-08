package mx.edu.utez.RESTCustomersObed.model;

import mx.edu.utez.RESTCustomersObed.service.ConnectionMysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoCustomer {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;

    public List<Customer> findAll(){
        List<Customer> listCustomer = new ArrayList<>();

        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers;");
            rs = cstm.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();

                customer.setCustomerNumber(rs.getInt("employeeNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setContactLastName(rs.getString("contactLastFirstName"));

                listCustomer.add(customer);
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return listCustomer;
    }

    public Customer findByCustomerNumber(int customerNumber){
        Customer customer  = null;

        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers WHERE customerNumber = ?;");
            cstm.setInt(1, customerNumber);
            rs = cstm.executeQuery();

            if(rs.next()){
                customer = new Customer();

                customer.setCustomerNumber(rs.getInt("employeeNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactFirstName(rs.getString("contactLastName"));
                customer.setContactLastName(rs.getString("contactFirstName"));
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return customer;
    }

    public boolean save(Customer customer, boolean isCreate){
        boolean flag = false;

        try{
            con = ConnectionMysql.getConnection();
            if(isCreate){
                cstm = con.prepareCall("INSERT INTO customers(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);");

                cstm.setInt(1, customer.getCustomerNumber());
                cstm.setString(2, customer.getContactLastName());
                cstm.setString(3, customer.getContactLastName());
                cstm.setString(4, customer.getContactFirstName());
                cstm.setString(5, customer.getPhone());
                cstm.setString(6, customer.getAddressLine1());
                cstm.setString(7, customer.getAddressLine2());
                cstm.setString(8, customer.getCity());
                cstm.setString(9, customer.getState());
                cstm.setString(10, customer.getPostalCode());
                cstm.setString(11, customer.getCountry());
                cstm.setInt(12, customer.getSalesRepEmployeeNumber());
                cstm.setDouble(13, customer.getCreditLimit());
            } else {
                cstm = con.prepareCall("UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, city = ? , state = ? , postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ?  WHERE customerNumber = ?;");

                cstm.setString(1, customer.getContactLastName());
                cstm.setString(2, customer.getContactLastName());
                cstm.setString(3, customer.getContactFirstName());
                cstm.setString(4, customer.getPhone());
                cstm.setString(5, customer.getAddressLine1());
                cstm.setString(6, customer.getAddressLine2());
                cstm.setString(7, customer.getCity());
                cstm.setString(8, customer.getState());
                cstm.setString(9, customer.getPostalCode());
                cstm.setString(10, customer.getCountry());
                cstm.setInt(11, customer.getSalesRepEmployeeNumber());
                cstm.setDouble(12, customer.getCreditLimit());
                cstm.setInt(13, customer.getCustomerNumber());
            }

            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return flag;
    }

    public boolean delete(int customerNumber){
        boolean flag = false;
        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("DELETE FROM customers WHERE customerNumber = ?;");
            cstm.setInt(1, customerNumber);
            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return flag;
    }
}
