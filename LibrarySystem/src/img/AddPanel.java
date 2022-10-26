package img;

import javax.swing.*;

public class AddPanel {
    JPanel addingPanel ;
    AddPanel()
    {
        addingPanel = new JPanel();

        JTextArea bookName = new JTextArea();
        JTextField bookId = new JTextField();
        JButton generate = new JButton("Generate");
        JTextArea copies = new JTextArea();


        bookName.setBounds(500, 60, 300,40);
        generate.setBounds(500,90,300,40);
        bookId.setBounds(500,120, 300,40);
        copies.setBounds(500,150,300,40);

        addingPanel.add(bookName);
        addingPanel.add(generate);
        addingPanel.add(bookId);
        addingPanel.add(copies);

        addingPanel.setBounds(400, 30, 600, 600);

    }
}
