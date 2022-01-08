package PaymentFrame;

import ColorFont.Constant;
import table.covid_user;
import table.payment_history;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PaymentFrame extends JFrame {
    static public DefaultTableModel def = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PaymentFrame frame = new PaymentFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public PaymentFrame()
    {
        setTitle("Manager - Covid Management System");
        ImageIcon covid_icon=null;
        try {
            covid_icon=new ImageIcon(ImageIO.read(new File("rsc/covid_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(covid_icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 720);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(Constant.my_gray);
        setContentPane(contentPane);

        JPanel LabelPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel BalanceLabel = new JLabel("Balance");
        BalanceLabel.setFont(Constant.HEADER_FONT);
        BalanceLabel.setForeground(Constant.my_white);
        LabelPane.setBackground(Constant.my_gray);
        LabelPane.add(BalanceLabel);
        contentPane.add(LabelPane);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Constant.my_gray);

        JTextField SearchUserNameField = new JTextField();
        SearchUserNameField.setFont(Constant.INFO_FONT);
        SearchUserNameField.setColumns(20);
        SearchUserNameField.setForeground(Color.GRAY);
        SearchUserNameField.setText("Username");

        SearchUserNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (SearchUserNameField.getText().equals("Username")) {
                    SearchUserNameField.setText("");
                    SearchUserNameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (SearchUserNameField.getText().isEmpty()) {
                    SearchUserNameField.setForeground(Color.GRAY);
                    SearchUserNameField.setText("Username");
                }
            }
        });

        JButton SearchUserNameButton = new JButton("Search");
        SearchUserNameButton.setForeground(Constant.my_white);
        SearchUserNameButton.setBackground(new Color(77,82,77));
        SearchUserNameButton.setFont(Constant.INFO_FONT);

        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setForeground(Constant.my_white);
        RefreshButton.setBackground(new Color(77,82,77));
        RefreshButton.setFont(Constant.INFO_FONT);

        topPanel.add(SearchUserNameField);
        topPanel.add(SearchUserNameButton);
        topPanel.add(RefreshButton);


        JScrollPane ListPaymentHistoryCenter = new JScrollPane();
        ListPaymentHistoryCenter.setPreferredSize(new Dimension(780,600));

        JTable PaymentHistoryTable = new JTable();
        PaymentHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        PaymentHistoryTable.getTableHeader().setFont(Constant.TABLE_HEADER);
        PaymentHistoryTable.setFont(Constant.TABLE_FONT);

        PaymentHistoryTable.setModel(def);
        def.addColumn("Username");
        def.addColumn("Date Created");
        def.addColumn("Debt pay");
        def.addColumn("New balance");

        ArrayList<payment_history> ListHistory = getDB.PaymentHistory.FunctionPaymentHistory.GetAllHistory();
        for(payment_history i : ListHistory)
        {
            def.addRow(new Object[] {
                    i.getUsername(), i.getDate_create(), i.getDebt_pay(), i.getNew_balance()
            });
        }
        ListPaymentHistoryCenter.setViewportView(PaymentHistoryTable);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botPanel.setBackground(Constant.my_gray);

        JButton AddNewUserButton = new JButton("Add new user");
        AddNewUserButton.setForeground(Constant.my_white);
        AddNewUserButton.setBackground(new Color(77,82,77));
        AddNewUserButton.setFont(Constant.INFO_FONT);

        botPanel.add(AddNewUserButton);

        add(topPanel);
        add(ListPaymentHistoryCenter);
        add(botPanel);

        SearchUserNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!SearchUserNameField.getText().equals("Username"))
                {
                    String usernamesearch = SearchUserNameField.getText();

                    ArrayList<payment_history> ListHistoryUser = getDB.PaymentHistory.FunctionPaymentHistory.GetUpdateHistory(usernamesearch);

                    if(ListHistoryUser.size() > 0) {
                        def.setRowCount(0);
                        for(payment_history i : ListHistoryUser)
                        {
                            def.addRow(new Object[] {
                                    i.getUsername(), i.getDate_create(), i.getDebt_pay(), i.getNew_balance()
                            });
                        }
                    }
                    else JOptionPane.showMessageDialog(PaymentFrame.this, "There is no username match your search",
                            "Search",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Please input username",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        RefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                def.setRowCount(0);
                ArrayList<payment_history> ListHistory = getDB.PaymentHistory.FunctionPaymentHistory.GetAllHistory();
                for(payment_history i : ListHistory)
                {
                    def.addRow(new Object[] {
                            i.getUsername(), i.getDate_create(), i.getDebt_pay(), i.getNew_balance()
                    });
                }
                SearchUserNameField.setText("");
            }
        });

        AddNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserByPayment().setVisible(true);
            }
        });
    }

}
