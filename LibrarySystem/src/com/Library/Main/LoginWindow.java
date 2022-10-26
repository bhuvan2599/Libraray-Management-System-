package com.Library.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
//import java.awt.*;

public class LoginWindow {
    Connection con;
    JFrame login ;
    public boolean valid = false;
    JTextField idField;
    JTextField passField;
    LoginWindow()
    {
        login = new JFrame("Login");
        login.getContentPane().setLayout(null);
        JLabel userIdLabel = new JLabel("User Id:");
        idField = new JTextField("");
        JLabel passWordLabel = new JLabel("Password:");
        passField = new JTextField("");
        JButton submit = new JButton("Login");


        userIdLabel.setBounds(100, 50, 300,30);
        idField.setBounds(100, 80, 300, 30);
        passWordLabel.setBounds(100, 110, 300,30);
        passField.setBounds(100,140, 300,30);
        submit.setBounds(200,190,100,30);


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    String mes = validateCredentials();

                    if(mes.equals("Failed"))
                    {
                        passWordLabel.setText("Password:  Wrong !!!");
                        passWordLabel.setForeground(Color.RED);
                    }
                    else
                    {
                        login.dispose();
                        System.out.println(mes);
                        new AdminWindow();
                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        login.add(userIdLabel);
        login.add(idField);
        login.add(passWordLabel);
        login.add(passField);
        login.add(submit);

        login.setBounds(500,200,500,300);

        login.setVisible(true);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private String validateCredentials() throws SQLException
    {
        createConnection();
        String adminId = idField.getText();
        String passFromField = passField.getText();
        Statement stm = con.createStatement();
        String uq = String.format("set @m = '%s';",passFromField);
        String query1 = "call library.login(@m);";
        String query2 = "select @m;";
        stm.executeUpdate(uq);
        stm.execute(query1);
        ResultSet res= stm.executeQuery(query2);
        res.next();
//        String passFromDB = res.getString(1);
        String mes = res.getString(1);
        return mes;
//        return passFromField.equals(passFromDB);
    }

   public  void createConnection() throws SQLException
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","root");
            System.out.println("DB Connected...!!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        LoginWindow win = new LoginWindow();

    }
}
