package com.Library.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class AdminWindow  {
    Connection con ;
    JFrame frame;
    AdminWindow()
    {
        frame = new JFrame("Admin");
        JButton issueButton = new JButton("Issue");
        JButton receiveButton = new JButton("Receive");
        JButton addButton = new JButton("Add Book");
        JButton searchButton = new JButton("Search Book");
        JButton logOutButton = new JButton("log out");
        JButton newUser = new JButton("Create User");

        frame.getContentPane().setLayout(null);
        issueButton.setBounds(40, 50, 110, 30);
        receiveButton.setBounds(40,150,110,30);
        addButton.setBounds(40, 250, 110, 30);
        searchButton.setBounds(40, 350, 110, 30);
        newUser.setBounds(40,450,110,30);
        logOutButton.setBounds(40, 700, 110, 30);

        JTabbedPane panes = new JTabbedPane();
        JPanel  addPanel =  createAddPanel();
        JPanel  searchPanel = createSearchPanel();
        JPanel  issuePanel = createIssuePanel();
        JPanel receivePanel = createReceivePanel();

        panes.add("Issue",issuePanel);
        panes.add("Receive", receivePanel);
        panes.add("Add",addPanel);
        panes.add("Search",searchPanel);

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panes.setSelectedIndex(0);
            }
        });

        receiveButton.addActionListener(new ActionListener() {
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
                panes.setSelectedIndex(2);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panes.setSelectedIndex(3);
            }
        });

        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new NewUserPanel();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });

        panes.setBounds(200,-50,800, 900);

        frame.add(issueButton);
        frame.add(receiveButton);
        frame.add(addButton);
        frame.add(searchButton);
        frame.add(newUser);
        frame.add(logOutButton);
        frame.add(panes);
        frame.pack();
        frame.setBounds(300,0,1000, 800);
        frame.setVisible(true);

    }
    //------------------------------------------------------------------------------------------------------------------
    //panel to issue books to the user.
    private JPanel createIssuePanel()
    {
        JLabel heading = new JLabel("Issue A Book");
        heading.setFont(new Font("consolas",Font.BOLD,20));
        JLabel UserId = new JLabel("User Id");
        JTextField useridField = new JTextField("");

        JLabel BookId = new JLabel("Book Id");
        JTextField bookid = new JTextField("");

        JLabel message = new JLabel("");
        JButton submit = new JButton("Submit");
        JButton reset = new JButton("Reset");

        JLabel pic = new JLabel();
        Image img = new ImageIcon(this.getClass().getResource("/img/IssueBookIcon.png")).getImage() ;

        pic.setIcon(new ImageIcon(img));

        heading.setBounds(300,50,300,40);
        pic.setBounds(50, 80, 300, 300);

        UserId.setBounds(400, 130,300,30);
        useridField.setBounds(400, 160, 300,30);
        BookId.setBounds(400, 190, 300, 30 );
        bookid.setBounds(400, 220, 300, 30);
        message.setBounds(400,260, 300,30);
        submit.setBounds(400, 300, 100, 30 );
        reset.setBounds(550,300,100,30);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String userIdEntered = useridField.getText();
                String boookIdEntered = bookid.getText();
                try
                {
                    createConnection();
                    issueBookToUser(userIdEntered,boookIdEntered);
                    message.setText("Issued Book to User: "+userIdEntered);

                }
                catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
                finally
                {
                    try
                    {
                        con.close();
                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                System.out.println("Book issued to the user");
//                con.close();
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                useridField.setText("");
                bookid.setText("");
                message.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(heading);
        panel.add(UserId);
        panel.add(useridField);
        panel.add(bookid);
        panel.add(BookId);
        panel.add(submit);
        panel.add(reset);
        panel.add(pic);
        panel.add(message);

        panel.setBounds(200, 30, 700, 600);
        panel.setBackground(Color.ORANGE);
        return panel;
    }

    // function to get the name of the book using book id number.
    private  String getBookName(String id) throws SQLException
    {

        Statement st = con.createStatement();
        String qu = String.format("select bookName from books where bookId ='%s';", id);
        System.out.println(qu);
        ResultSet res = st.executeQuery(qu);
        res.next();
        String name = res.getString(1);
        System.out.println(name);

        return name;
    }

    //getting the count of books taken by the user and update
    private int getOldTakenCount(String id) throws SQLException
    {

        Statement st = con.createStatement();
        String qu = String.format("select booksTaken from users where userId ='%s';", id);
        System.out.println(qu);
        ResultSet res = st.executeQuery(qu);
        res.next();
        int value= res.getInt(1);
        System.out.println("Old count: "+value);

        return value;
    }

    //function to get the count of copies present in the library
    private int getCopyCountBooksTable(String id) throws SQLException
    {
        Statement st = con.createStatement();
        String qu = String.format("select copyCount from books where bookId ='%s';", id);
        System.out.println(qu);
        ResultSet res = st.executeQuery(qu);
        res.next();
        int value= res.getInt(1);
        System.out.println("Old count: "+value);

        return value;
    }

    //issueing functionality
    private void issueBookToUser(String userid, String bookid) throws SQLException
    {

        Statement stm = con.createStatement();

        String name = getBookName(bookid);
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(15);

        //insert transaction in transaction table
        String insertTransactionQuery =String.format("insert into transactions (bookName, bookId, userId, issueDate, dueDate)values ('%s', '%s', '%s', '%s', '%s');",name, bookid, userid,today,due);
        System.out.println("TCQ: "+insertTransactionQuery);
        stm.executeUpdate(insertTransactionQuery);

        //update books taken count in users table
        int oldTakenCount = getOldTakenCount(userid);
        int newCount = oldTakenCount+1;
        String updateCountTakenQuery =String.format("update users set booksTaken = %s where userId ='%S';", newCount,userid);
        System.out.println("UPDQ: "+updateCountTakenQuery);
        stm.executeUpdate(updateCountTakenQuery);

        int copyOldCount = getCopyCountBooksTable(bookid);
        int upDatedCopyCount = copyOldCount-1;
        String bookCountUpdateQuery = String.format("update books set copyCount = %s where bookId ='%s';", upDatedCopyCount,bookid);
        System.out.println("BCUPQ: "+bookCountUpdateQuery);
        stm.executeUpdate(bookCountUpdateQuery);

    }

    //------------------------------------------------------------------------------------------------------------------
    //panel to receive books from user.
    private JPanel createReceivePanel()
    {
        //heading
        JLabel heading = new JLabel("Receive A Book");
        heading.setFont(new Font("consolas",Font.BOLD,20));

        //Adding picture
        JLabel pic = new JLabel();
        Image img = new ImageIcon(this.getClass().getResource("/img/hand-over.png")).getImage() ;
        pic.setIcon(new ImageIcon(img));
        pic.setBounds(50, 140, 300, 300);

        //labels and text fields
        JLabel UserId = new JLabel("User Id");
        JTextField useridField = new JTextField("");
        JLabel BookId = new JLabel("Book Id");
        JTextField bookid = new JTextField("");
        JButton submit = new JButton("Submit");
        JButton reset = new JButton("Reset");
        JLabel message = new JLabel("");

        //their positions and sizes
        heading.setBounds(250,50,300,40);
        UserId.setBounds(400, 150,300,30);
        useridField.setBounds(400, 180, 300,30);
        BookId.setBounds(400, 230, 300, 30 );
        bookid.setBounds(400, 260, 300, 30);
        submit.setBounds(400, 340, 100, 30 );
        reset.setBounds(550,340,100,30);
        message.setBounds(400,300,300,30);

        //on clicking submit - add returned date and calculate penalty if late submission.
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String userIdEntered = useridField.getText();
                String bookIdEntered = bookid.getText();
                try {
                    createConnection();
                    int pen = recieveBookFromUser(userIdEntered,bookIdEntered);
                    message.setText("Received Book. You have '"+pen+"' Rs penalty.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                useridField.setText("");
                bookid.setText("");
                message.setText("");
            }
        });


        //adding them to panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(heading);
        panel.add(pic);
        panel.add(UserId);
        panel.add(useridField);
        panel.add(bookid);
        panel.add(BookId);
        panel.add(submit);
        panel.add(reset);
        panel.add(message);

        //panel size and position
        panel.setBounds(200, 30, 700, 600);
        panel.setBackground(new Color(188, 228, 251));
        return panel;
    }

    // getting the returned books count for the user to update
    private int getOldReturnCount(String id) throws SQLException
    {

        Statement st = con.createStatement();
        String qu = String.format("select booksReturned from users where userId ='%s';", id);
        System.out.println(qu);
        ResultSet res = st.executeQuery(qu);
        res.next();
        int value= res.getInt(1);
        System.out.println("Old count: "+value);

        return value;
    }

    //changing the count of returned books in users table for the user who returned book.
    private void changeOldReturnedCount(String uid) throws SQLException
    {
        Statement stm = con.createStatement();
        int oldTakenCount = getOldReturnCount(uid);
        int newCount = oldTakenCount+1;
        String qu = String.format("update users set booksReturned = %s where userId='%s';", newCount, uid);
        System.out.println("RTCUPDQ: "+qu);
        stm.executeUpdate(qu);
    }

    //incrementing copycount in books table
    private void changeOldCopyCount(String bid) throws SQLException
    {
        Statement stm = con.createStatement();
        int copyOldCount = getCopyCountBooksTable(bid);
        int upDatedCopyCount = copyOldCount+1;
        String bookCountUpdateQueryReturned = String.format("update books set copyCount = %s where bookId ='%s';", upDatedCopyCount,bid);
        System.out.println("BCCUPQ: "+bookCountUpdateQueryReturned);
        stm.executeUpdate(bookCountUpdateQueryReturned);
    }

    //updting the returned date, marking as returned for the user who returned the book.
    private void updateReturnDateAndPenalty(LocalDate rDate, int pen, String bid, String uid) throws SQLException
    {
        Statement stm = con.createStatement();
        String updQuery = String.format("update transactions set returned=1, returnDate='%s', penalty='%s' where bookId='%s' and userId='%s';",rDate,pen,bid,uid);
        stm.executeUpdate(updQuery);
    }

    //calculating the penalty if the submitted date is after due date.
    private int calculatePenalty(String uid, String bid) throws SQLException
    {
        Statement stm = con.createStatement();
        String dateQuery = String.format("select dueDate from transactions where bookId ='%s' and userId='%s';", bid,uid);
        ResultSet res = stm.executeQuery(dateQuery);
        res.next();
        LocalDate due = res.getDate(1).toLocalDate();
        LocalDate returnDate = LocalDate.now();
        int penalty =0;

        if(returnDate.isAfter(due))
        {
            long diff = ChronoUnit.DAYS.between(returnDate, due);
            penalty = (int) (diff * 10);
        }
        updateReturnDateAndPenalty(returnDate,penalty,bid,uid);
        return penalty;
    }

    //function to implement the receive functionality of book.
    private int recieveBookFromUser(String userIdNum , String bookIdNum) throws SQLException
    {
        Statement stm = con.createStatement();
        changeOldReturnedCount(userIdNum);
        changeOldCopyCount(bookIdNum);
        int pen = calculatePenalty(userIdNum,bookIdNum);
        return pen;
    }

    //------------------------------------------------------------------------------------------------------------------
    // panel to add books to the library
    private JPanel createAddPanel()
    {
        JPanel addingPanel = new JPanel();
        addingPanel.setLayout(null);

        JLabel heading = new JLabel("Add A Book to Library");
        heading.setFont(new Font("consolas", Font.BOLD,20));

        JLabel pic = new JLabel();
        Image img = new ImageIcon(this.getClass().getResource("/img/AddBook.png")).getImage() ;
        pic.setIcon(new ImageIcon(img));

        JTextField bookNameField = new JTextField("");
        JTextField bookIdField = new JTextField("");
        JButton generate = new JButton("Generate ID");
        JTextField copies = new JTextField("");
        JTextField AuthorField = new JTextField("");
        JButton submit = new JButton("Add to Library");
        JButton reset = new JButton("Reset");

        JLabel bookNameLabel = new JLabel("Enter Book Name :");
        JLabel BookIdLabel = new JLabel("ID Generation :");
        JLabel copiesLabel = new JLabel("Enter copies count: ");
        JLabel AuthorLabel = new JLabel("Author name:");
        JLabel message = new JLabel("");

        heading.setBounds(250,50,300,40);
        pic.setBounds(50, 150, 300, 300);
        bookNameLabel.setBounds(400,150,300,20);
        bookNameField.setBounds(400,170, 300,30);
        generate.setBounds(400,220,150,30);
        BookIdLabel.setBounds(400,270,300,20);
        bookIdField.setBounds(400,290, 300,30);
        AuthorLabel.setBounds(400,340,300,20);
        AuthorField.setBounds(400,360,300,30);
        copiesLabel.setBounds(400,410,300,20);
        copies.setBounds(400,430,300,30);
        submit.setBounds(400,510,200,30);
        reset.setBounds(400,560,100,30);
        message.setBounds(400,470, 300,20);

        addingPanel.add(heading);
        addingPanel.add(pic);
        addingPanel.add(bookNameLabel);
        addingPanel.add(BookIdLabel);
        addingPanel.add(copiesLabel);
        addingPanel.add(bookNameField);
        addingPanel.add(generate);
        addingPanel.add(bookIdField);
        addingPanel.add(AuthorField);
        addingPanel.add(AuthorLabel);
        addingPanel.add(copies);
        addingPanel.add(submit);
        addingPanel.add(reset);
        addingPanel.add(message);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String bookName = bookNameField.getText();
                String bookid = generateBookId(bookName);
                bookIdField.setText(bookid);
                BookIdLabel.setForeground(Color.BLUE);
                BookIdLabel.setText("ID Generation :  SUCCESSFUL!");

            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String book =  bookNameField.getText().toLowerCase();
                String bookIdNum = bookIdField.getText();
                String author = AuthorField.getText().toLowerCase();
                int copiesNum  = Integer.parseInt(copies.getText());

                try
                {
                    createConnection();
                    Statement statement = con.createStatement();

                    String query = String.format("Insert into books (bookName, bookId, author, copyCount ) values ( '%s','%s','%s',%s);",book,bookIdNum,author,copiesNum);
                    System.out.println(query);
                    statement.executeUpdate(query);
                    message.setText(book+" of "+copiesNum+" copies added to library.");
                    message.setForeground(Color.blue);

                }
                catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
                finally {
                    System.out.println("connecting closed.");
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                bookNameField.setText("");
                bookIdField.setText("");
                AuthorField.setText("");
                copies.setText("");
                BookIdLabel.setText("ID Generation :");
                BookIdLabel.setForeground(Color.black);
                message.setText("");
            }
        });


        addingPanel.setBounds(200, 30, 700, 600);
        addingPanel.setBackground(new Color(201, 245, 81));
        return addingPanel;
    }

    private void createConnection() throws SQLException
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


    //function to generate unique book id for the book admin is adding to library
    private String generateBookId(String book)
    {
        int year = LocalDate.now().getYear();
        return book.substring(0,3).toUpperCase()+year;

    }

    //------------------------------------------------------------------------------------------------------------------

    // panel to search books in the library
    private JPanel createSearchPanel()
    {
        JLabel heading = new JLabel("Search A Book");
        heading.setFont(new Font("consolas",Font.BOLD,20));

        JLabel pic = new JLabel();
        Image img = new ImageIcon(this.getClass().getResource("/img/searching.png")).getImage() ;
        pic.setIcon(new ImageIcon(img));

        JLabel searchLabel1 = new JLabel("Search By Book Name: ");
        JLabel searchLabel2 = new JLabel("Search By Author :");
        JTextField bookSearch = new JTextField("");
        JTextField authorSearch = new JTextField("");
        JButton searchButton = new JButton("Search");
        JLabel message = new JLabel("");
        JButton reset = new JButton("Reset");

        heading.setBounds(250, 50, 300, 30);
        pic.setBounds(50,140,300,300);
        searchLabel1.setBounds(400,150, 300, 30);
        bookSearch.setBounds(400,180,300,30);
        searchLabel2.setBounds(400, 230, 300, 30);
        authorSearch.setBounds(400,260, 300,30);
        searchButton.setBounds(400,340, 100,30);
        reset.setBounds(550,340,100,30);
        message.setBounds(400,310,300,20);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String bookNameEntered = bookSearch.getText();
                String authorEntered = authorSearch.getText();
                try {
                    int value = searchTheBook(bookNameEntered,authorEntered);
//                    System.out.println("Value: "+value);
                    String mes = String.format(" %s copies available",value);
                    message.setText(mes);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                       System.out.println(ex.getMessage());
                    }
                }
//
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                message.setText("");
                bookSearch.setText("");
                authorSearch.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(heading);
        panel.add(pic);
        panel.add(searchLabel1);
        panel.add(searchLabel2);
        panel.add(bookSearch);
        panel.add(authorSearch);
        panel.add(searchButton);
        panel.add(reset);
        panel.add(message);

        panel.setBounds(200, 30, 700, 600);
        panel.setBackground(new Color(144, 245, 208));
        return panel;
    }

    //searching functionality
    private int searchTheBook(String book, String author) throws SQLException
    {
        createConnection();
        Statement stmt = con.createStatement();
        String query = "select copyCount from books where bookName Like '%"+book+"%' or author like'%"+author+"%';" ;
//        System.out.println(query);
        ResultSet res = stmt.executeQuery(query);
        res.next();
        int copiesAvailableValue = res.getInt(1);
        return copiesAvailableValue;
    }

    //------------------------------------------------------------------------------------------------------------------
//    public static void main(String[] args)
//    {
//        new AdminWindow();
//    }
}
