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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PaymentFrame extends JFrame {
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
        BalanceLabel.setFont(Constant.LABEL_FONT);
        BalanceLabel.setForeground(Constant.my_white);
        LabelPane.setBackground(Constant.my_gray);
        LabelPane.add(BalanceLabel);
        contentPane.add(LabelPane);

        JPanel ButtonChangePane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonChangePane.setBackground(Constant.my_gray);
        JButton AddNewUserButton = new JButton("Add new user");
        AddNewUserButton.setForeground(Constant.my_white);
        AddNewUserButton.setBackground(new Color(77,82,77));

        JButton RefreshButton = new JButton("Refresh");
        RefreshButton.setForeground(Constant.my_white);
        RefreshButton.setBackground(new Color(77,82,77));

        ButtonChangePane.add(AddNewUserButton);
        ButtonChangePane.add(RefreshButton);
        contentPane.add(ButtonChangePane);

        AddNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserByPayment().setVisible(true);
                PaymentFrame.this.dispose();
            }
        });

        JScrollPane ListPaymentHistoryCenter = new JScrollPane();
        ListPaymentHistoryCenter.setPreferredSize(new Dimension(780,600));
        contentPane.add(ListPaymentHistoryCenter,BorderLayout.CENTER);

        JTable PaymentHistoryTable = new JTable();
        PaymentHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel def = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
            }
        });

        JPanel SearchBottomPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(SearchBottomPane);
        SearchBottomPane.setBackground(Constant.my_gray);

        JTextField SearchUserNameField = new JTextField();
        SearchUserNameField.setColumns(15);

        JButton SearchUserNameButton = new JButton("Search by UserName");
        SearchUserNameButton.setForeground(Constant.my_white);
        SearchUserNameButton.setBackground(new Color(77,82,77));

        SearchUserNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!SearchUserNameField.getText().equals(""))
                {
                    String usernamesearch = SearchUserNameField.getText();

                    ArrayList<payment_history> ListHistoryUser = getDB.PaymentHistory.FunctionPaymentHistory.GetUpdateHistory(usernamesearch);

                        def.setRowCount(0);
                        for(payment_history i : ListHistoryUser)
                        {
                            def.addRow(new Object[] {
                                    i.getUsername(), i.getDate_create(), i.getDebt_pay(), i.getNew_balance()
                            });
                        }
                }
                else
                {
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Please input username",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SearchBottomPane.add(SearchUserNameField);
        SearchBottomPane.add(SearchUserNameButton);
    }

}
