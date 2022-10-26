package img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestWindow {
    JFrame frame = new JFrame();

    public JPanel createPanel3()
    {
        JPanel panel3 = new JPanel();
        panel3.setLayout(null);
        JLabel tab2 = new JLabel("Panel 3");
        tab2.setBounds(50,40,300,20);
        panel3.add(tab2);
        panel3.setBounds(200, 30, 700, 600);
        panel3.setBackground(Color.orange);

        return panel3;
    }

    public JPanel createPanel1()
    {
        JPanel addingPanel = new JPanel();
        addingPanel.setLayout(null);


        JLabel bookNameLabel = new JLabel("Panel 1");


        bookNameLabel.setBounds(50,40,300,20);


        addingPanel.add(bookNameLabel);


        addingPanel.setBounds(200, 30, 700, 600);
        addingPanel.setBackground(Color.pink);
        return addingPanel;
    }

    public JPanel createPanel2()
    {
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        JLabel tab2 = new JLabel("Panel 2");
        tab2.setBounds(50,40,300,20);
        panel2.add(tab2);
        panel2.setBounds(200, 30, 700, 600);
        panel2.setBackground(Color.green);

        return panel2;

    }
    TestWindow()
    {
        JButton issueButton = new JButton("Issue");
        JButton addButton = new JButton("Add Book");
        JButton searchButton = new JButton("Search Book");
        JButton logOutButton = new JButton("log out");

        frame.getContentPane().setLayout(null);
        issueButton.setBounds(10, 50, 110, 30);
        addButton.setBounds(10, 150, 110, 30);
        searchButton.setBounds(10, 250, 110, 30);
        logOutButton.setBounds(10, 350, 110, 30);

        JTabbedPane panes = new JTabbedPane();

        JPanel p1 = createPanel1();
        JPanel p2 = createPanel2();
        JPanel p3 = createPanel3();

        panes.setBounds(200,-50,800, 900);
        panes.add("Add book", p1);
        panes.add("Issue book", p2);
        panes.add("Search", p3);

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panes.setSelectedIndex(1);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panes.setSelectedIndex(0);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panes.setSelectedIndex(2);
            }
        });

        frame.add(issueButton);
        frame.add(addButton);
        frame.add(searchButton);
        frame.add(logOutButton);
        frame.add(panes);
        frame.pack();

        frame.setSize(1000, 800);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new TestWindow();


    }
}
