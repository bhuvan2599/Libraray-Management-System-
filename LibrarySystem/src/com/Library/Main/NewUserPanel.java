package com.Library.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class NewUserPanel extends JPanel {

    Connection con;
    JFrame newFrame = new JFrame("New User");
   public  NewUserPanel()
    {
        newFrame.getContentPane().setLayout(null);
        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);
        JLabel heading = new JLabel("Add NEW USER");
        heading.setFont(new Font("Consolas",Font.BOLD,28));
        JLabel pic = new JLabel();
        Image img = new ImageIcon((this.getClass().getResource("/img/add-contact.png"))).getImage();
        pic.setIcon(new ImageIcon(img));

        JLabel userNameLabel = new JLabel("User Name:");
        JTextField userNameField = new JTextField("");
        JLabel userIdLabel = new JLabel("User id:");
        JTextField userIdField = new JTextField("");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField passField = new JTextField("");
        JLabel proofLabel = new JLabel("Proof: ");
        JTextField proofField = new JTextField("");
        JLabel phoneLabel = new JLabel("Phone: ");
        JTextField phoneField = new JTextField("");
        JButton submit = new JButton("Create");
        JButton reset = new JButton("Reset");
        JLabel message = new JLabel("");


        heading.setBounds(300,30,300,30);
        message.setBounds(500,70,200,35);
        pic.setBounds(50,100, 300,300 );
        userNameLabel.setBounds(400,100,300,30);
        userNameField.setBounds(400,130,300,30);
        userIdLabel.setBounds(400,170,300,30);
        userIdField.setBounds(400,200,300,30);
        passwordLabel.setBounds(400,240, 300,30);
        passField.setBounds(400,270,300,30);
        proofLabel.setBounds(400,310,300,30);
        proofField.setBounds(400,340,300,30);
        phoneLabel.setBounds(400,380,300,30);
        phoneField.setBounds(400,410, 300,30);
        submit.setBounds(400,490,100,30);
        reset.setBounds(550, 490, 100,30);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name = userNameField.getText();
                String userid = userIdField.getText();
                String password = passField.getText();
                String proof = proofField.getText();
                String phn = phoneField.getText();
                try {
                    createUser(name, userid,password,proof,phn);
                    message.setText("Created!!!");
                    message.setFont(new Font("Consolas",Font.BOLD,30));
                    message.setForeground(Color.green);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                userIdField.setText("");
                userNameField.setText("");
                proofField.setText("");
                passField.setText("");
                phoneField.setText("");
                message.setText("");
            }
        });
        userPanel.add(heading);
        userPanel.add(pic);
        userPanel.add(userNameLabel);
        userPanel.add(userNameField);
        userPanel.add(userIdLabel);
        userPanel.add(userIdField);
        userPanel.add(passwordLabel);
        userPanel.add(passField);
        userPanel.add(proofLabel);
        userPanel.add(proofField);
        userPanel.add(phoneLabel);
        userPanel.add(phoneField);
        userPanel.add(submit);
        userPanel.add(reset);
        userPanel.add(message);

        userPanel.setBounds(0,0,800, 600);
        userPanel.setBackground(new Color(247, 237, 141));
        userPanel.setVisible(true);
        newFrame.add(userPanel);
        newFrame.setBounds(400,100,800,600);
        newFrame.setVisible(true);

    }

    private void createUser(String name, String uid, String pass,String pr, String phn) throws SQLException
    {
        createConnection();
        Statement st = con.createStatement();
        String qu = String.format("Insert into users(userName, userId, passcode, phone, proof ) values('%s','%s','%s','%s','%s');",name,uid,pass,phn,pr);
        st.executeUpdate(qu);
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

//    public static void main(String[] args)
//    {
//        new NewUserPanel();
//    }
}
