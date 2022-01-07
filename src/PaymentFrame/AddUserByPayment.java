package PaymentFrame;

import ColorFont.Constant;
import getDB.Account.FunctionAccount;
import getDB.Address.FunctionAddress;
import getDB.Facility.FunctionFacility;
import table.account;
import table.covid_user;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import table.payment_user;
import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;


public class AddUserByPayment extends JFrame {
    public AddUserByPayment() {
        setTitle("Covid Management System");
        ImageIcon covid_icon=null;
        try {
            covid_icon=new ImageIcon(ImageIO.read(new File("rsc/covid_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(covid_icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(670, 770);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(Constant.my_gray);
        setContentPane(contentPane);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Constant.my_gray);
        mainPanel.setForeground(Constant.my_white);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Constant.my_gray);
        JLabel SignUpLabel = new JLabel("Add new user to the system");
        SignUpLabel.setFont(Constant.HEADER_FONT);
        SignUpLabel.setForeground(Constant.my_white);
        headerPanel.add(SignUpLabel);

        JLabel InputUserLabel = new JLabel("Username:");
        InputUserLabel.setFont(Constant.LABEL_FONT);
        InputUserLabel.setForeground(Constant.my_white);
        JTextField UserField = new JTextField();
        UserField.setFont(Constant.INFO_FONT);
        UserField.setColumns(20);

        JLabel InputPassLabel = new JLabel("Password:");
        InputPassLabel.setFont(Constant.LABEL_FONT);
        InputPassLabel.setForeground(Constant.my_white);
        JPasswordField PassField = new JPasswordField();
        PassField.setFont(Constant.INFO_FONT);
        PassField.setColumns(20);

        JLabel InputNameLabel = new JLabel("FullName:");
        InputNameLabel.setFont(Constant.LABEL_FONT);
        InputNameLabel.setForeground(Constant.my_white);
        JTextField NameField = new JTextField();
        NameField.setFont(Constant.INFO_FONT);
        NameField.setColumns(20);

        JLabel InputDobLabel = new JLabel("Date of birth (yyyy-MM-dd):");
        InputDobLabel.setFont(Constant.LABEL_FONT);
        InputDobLabel.setForeground(Constant.my_white);
        JTextField DobField = new JTextField();
        DobField.setFont(Constant.INFO_FONT);
        DobField.setColumns(20);

        JLabel InputAddressLabel = new JLabel("House Number:");
        InputAddressLabel.setFont(Constant.LABEL_FONT);
        InputAddressLabel.setForeground(Constant.my_white);
        JTextField AddressField = new JTextField();
        AddressField.setFont(Constant.INFO_FONT);
        AddressField.setColumns(20);

        JLabel ProvinceLable = new JLabel("Province:");
        ProvinceLable.setFont(Constant.LABEL_FONT);
        ProvinceLable.setForeground(Constant.my_white);
        JComboBox Province = new JComboBox();
        Province.setFont(Constant.INFO_FONT);
        JLabel WardLable = new JLabel("Ward:");
        WardLable.setFont(Constant.LABEL_FONT);
        WardLable.setForeground(Constant.my_white);
        JComboBox Ward = new JComboBox();
        Ward.setFont(Constant.INFO_FONT);
        JLabel DistrictLable = new JLabel("District:");
        DistrictLable.setFont(Constant.LABEL_FONT);
        DistrictLable.setForeground(Constant.my_white);
        JComboBox District = new JComboBox();
        District.setFont(Constant.INFO_FONT);

        Set<String> provinces = FunctionAddress.GetAllProvince();
        for (String s : provinces)
        {
            Province.addItem(s);
        }

        JLabel InputIdLabel = new JLabel("ID:");
        InputIdLabel.setFont(Constant.LABEL_FONT);
        InputIdLabel.setForeground(Constant.my_white);
        JTextField IdField = new JTextField();
        IdField.setFont(Constant.INFO_FONT);
        IdField.setColumns(20);

        JLabel FacilityLabel = new JLabel("Facility:");
        FacilityLabel.setFont(Constant.LABEL_FONT);
        FacilityLabel.setForeground(Constant.my_white);
        JComboBox Facility = new JComboBox();
        Facility.setFont(Constant.INFO_FONT);

        JLabel StatusLabel = new JLabel("Status:");
        StatusLabel.setFont(Constant.LABEL_FONT);
        StatusLabel.setForeground(Constant.my_white);
        JComboBox Status_patient = new JComboBox();
        Status_patient.setFont(Constant.INFO_FONT);

        Set<String> facilities = FunctionFacility.getNameFacility();
        for (String s : facilities)
        {
            Facility.addItem(s);
        }

        Status_patient.addItem("F0");
        Status_patient.addItem("F1");
        Status_patient.addItem("F2");
        Status_patient.addItem("F3");
        Status_patient.addItem("RV");
        Status_patient.addItem("NI");

        JPanel botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        botPanel.setBackground(Constant.my_gray);

        JButton CancelButton = new JButton("Cancel");
        CancelButton.setForeground(Constant.my_white);
        CancelButton.setBackground(new Color(77,82,77));
        CancelButton.setFont(Constant.INFO_FONT);

        JButton ConfirmButton = new JButton("Confirm");
        ConfirmButton.setBackground(new Color(77,82,77));
        ConfirmButton.setForeground(Constant.my_white);
        ConfirmButton.setFont(Constant.INFO_FONT);

        botPanel.add(CancelButton);
        botPanel.add(ConfirmButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(InputUserLabel, gbc);

        gbc.gridy++;
        mainPanel.add(InputIdLabel, gbc);
        gbc.gridy++;
        mainPanel.add(InputPassLabel, gbc);
        gbc.gridy++;
        mainPanel.add(InputNameLabel, gbc);
        gbc.gridy++;
        mainPanel.add(InputDobLabel, gbc);
        gbc.gridy++;
        mainPanel.add(InputAddressLabel, gbc);
        gbc.gridy++;
        mainPanel.add(ProvinceLable, gbc);
        gbc.gridy++;
        mainPanel.add(DistrictLable, gbc);
        gbc.gridy++;
        mainPanel.add(WardLable, gbc);
        gbc.gridy++;
        mainPanel.add(FacilityLabel, gbc);
        gbc.gridy++;
        mainPanel.add(StatusLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(UserField, gbc);

        gbc.gridy++;
        mainPanel.add(IdField, gbc);
        gbc.gridy++;
        mainPanel.add(PassField, gbc);
        gbc.gridy++;
        mainPanel.add(NameField, gbc);
        gbc.gridy++;
        mainPanel.add(DobField, gbc);
        gbc.gridy++;
        mainPanel.add(AddressField, gbc);
        gbc.gridy++;
        mainPanel.add(Province, gbc);
        gbc.gridy++;
        mainPanel.add(District, gbc);
        gbc.gridy++;
        mainPanel.add(Ward, gbc);
        gbc.gridy++;
        mainPanel.add(Facility, gbc);
        gbc.gridy++;
        mainPanel.add(Status_patient, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy++;
        mainPanel.add(botPanel, gbc);


        add(headerPanel);
        add(mainPanel);

        Province.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SelectedProvince = (String) Province.getSelectedItem();
                Ward.removeAllItems();
                District.removeAllItems();
                Set<String> districts = FunctionAddress.GetAllDistrict(SelectedProvince);
                for (String s : districts)
                {
                    District.addItem(s);
                }

            }
        });

        District.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SelectedDistrict = (String) District.getSelectedItem();
                String SelectedProvince = (String) Province.getSelectedItem();
                Ward.removeAllItems();
                Set<String> wards = FunctionAddress.GetAllWard(SelectedDistrict,SelectedProvince);
                for (String s : wards)
                {
                    Ward.addItem(s);
                }
            }
        });

        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserByPayment.this.dispose();
            }
        });

        ConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String province = (String) Province.getSelectedItem();
                String district = (String) District.getSelectedItem();
                String ward = (String) Ward.getSelectedItem();
                String facility_name = (String) Facility.getSelectedItem();
                String status_patient = (String) Status_patient.getSelectedItem();
                if (UserField.getText().isEmpty() || String.valueOf(PassField.getPassword()) ==""
                        || NameField.getText().isEmpty() || DobField.getText().isEmpty()|| AddressField.getText().isEmpty()
                        || IdField.getText().isEmpty() || province.equals("")|| district.equals("")|| ward.equals(""))
                {
                    JOptionPane.showMessageDialog(AddUserByPayment.this, "Please fill correcly information","error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    account acc = new account();
                    acc.setUsername(UserField.getText());
                    acc.setPass(String.valueOf(String.valueOf(PassField.getPassword()).hashCode()));
                    acc.setUser_role(3);
                    acc.setBan_unban(0);
                    if (FunctionAccount.CheckExisted(acc.getUsername()))
                    {
                        JOptionPane.showMessageDialog(AddUserByPayment.this, "Username's already existed","error",JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {

                        covid_user coviduser = new covid_user();
                        coviduser.setUsername(acc.getUsername());
                        coviduser.setFull_name(NameField.getText());
                        coviduser.setId(IdField.getText());

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            java.util.Date str = format.parse(DobField.getText());
                            java.sql.Date temp = new java.sql.Date(str.getTime());
                            coviduser.setDob(temp);
                            coviduser.setHouse_number(AddressField.getText());

                            coviduser.setAddress_id(FunctionAddress.GetAddressId(province,ward,district));
                            coviduser.setPatient_status(status_patient);
                            coviduser.setFacility_id(FunctionFacility.getIdFacilityByName(facility_name));

                            int CQuantity = getDB.Facility.FunctionFacility.GetCurrentQuantity(coviduser.getFacility_id());
                            int Capacity = getDB.Facility.FunctionFacility.GetCapacity(coviduser.getFacility_id());
                            if ( CQuantity >= Capacity && Capacity != -1)
                            {
                                JOptionPane.showMessageDialog(AddUserByPayment.this,"Facility is full", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                getDB.Facility.FunctionFacility.SetCurrentQuantity(coviduser.getFacility_id(), CQuantity + 1);
                                FunctionAccount.AddAccount(acc);
                                FunctionAccount.AddInfoAccount(coviduser);
                                payment_user paymentUser = new payment_user();
                                paymentUser.setUsername(acc.getUsername());
                                paymentUser.setBalance(50000);
                                paymentUser.setDebt(0);
                                getDB.PaymentUser.FunctionPaymentUser.AddPaymentAccount(paymentUser);
                                JOptionPane.showMessageDialog(AddUserByPayment.this, "Create successfully", "success", JOptionPane.ERROR_MESSAGE);
                                new PaymentFrame().setVisible(true);
                                AddUserByPayment.this.dispose();
                            }
                        } catch (ParseException err) {
                            JOptionPane.showMessageDialog(AddUserByPayment.this, "Please fill correct format date (yyyy-mm-dd)","error",JOptionPane.ERROR_MESSAGE);
                        }


                    }
                }
            }
        });
    }
}