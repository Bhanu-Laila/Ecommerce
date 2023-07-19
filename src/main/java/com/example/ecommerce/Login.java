package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {

    public Customer customerLogin(String userName, String password){
        String loginQuery = "select * from customer where email = '"+userName+"' and passwords='"+password+"'";
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try{
            if(rs.next()){
                return new Customer(rs.getInt("id"), rs.getNString("name"), rs.getNString("email"), rs.getNString("mobile"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        Login login = new Login();
        Customer customer = login.customerLogin("bhanulaila1998@gmail.com", "Bhanu@115");
        System.out.println("Welcome: "+customer.getName());
    }
}
