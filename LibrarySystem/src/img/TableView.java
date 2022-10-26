package img;

import javax.swing.*;

public class TableView {

    JFrame frame = new JFrame();

    TableView()
    {
        String[][] details =
                {
                    {"USER001",  "VAMSI1","HAR202212","2022-08-08","2022-08-23","Yes"},
                    {"USER002",  "VAMSI2","HAR202213","2022-08-09","2022-08-24","No"},
                    {"USER003",  "VAMSI3","HAR202214","2022-08-10","2022-08-25","Yes"},
                    {"USER004",  "VAMSI4","HAR202215","2022-08-11","2022-08-26","No"},
                    {"USER005",  "VAMSI5","HAR202216","2022-08-12","2022-08-27","Yes"},
                    {"USER006",  "VAMSI6","HAR202217","2022-08-13","2022-08-28","No"}
                };

        String[] columns = {"User Id", "Name", "Book Id", "Issue Date","Due Date", "Returned"};
        JTable table = new JTable(details, columns);

        JScrollPane pane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setBounds(100, 500, 1000, 350);
        panel.add(pane);

        frame.add(panel);
        frame.setSize(800, 300);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new TableView();
    }
}
