package com.urbanik;

import com.urbanik.core.Register;
import com.urbanik.dto.BlockDto;
import com.urbanik.dto.LicenseDto;
import com.urbanik.dto.RecordDto;
import com.urbanik.dto.VehicleDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class App {
    private JPanel panelMain;
    private JTabbedPane tabbedPane;
    private JPanel test;
    private JPanel vehicle;
    private JButton testSave;
    private JButton addVehicleButton;
    private JButton testLoad;
    private JTextArea textArea;
    private JButton testBTreeButton;
    private JPanel license;
    private JButton addLicenseButton;
    private JScrollPane vehicleTextField;
    private JScrollPane licenseTextArea;
    private JButton findLicenseButton;
    private JButton updateLicenseButton;
    private JButton sequencePrintOfLicensesFileButton;
    private JButton findVehicleByENButton;
    private JButton updateVehicleButton;
    private JButton sequencePrintOfVehiclesButton;
    private JButton loadFromFileButton;
    private JButton saveToFileButton;
    private JButton generateDataButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextArea licenseText;
    private JRadioButton prohibitedRadioButton;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JRadioButton wantedRadioButton;
    private JButton findVehicleByVINButton;
    private JButton sequencePrintOfByButton;
    private JButton sequencePrintOfByButton1;
    private JTextArea vehicleText;
    private JTextField textField12;
    private JTextField textField19;

    private static Register register;

    public App() {
        testBTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<BlockDto> blockDtos = register.testBTree();
                textArea.setText(writeBlocks(blockDtos));
            }
        });
        addLicenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LicenseDto dto = new LicenseDto(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()), new Date(Integer.parseInt(textField8.getText()) - 1900, Integer.parseInt(textField7.getText()), Integer.parseInt(textField4.getText())), prohibitedRadioButton.isSelected() ? true : false, Integer.parseInt(textField6.getText()));
                register.addLicense(dto);
                JOptionPane.showMessageDialog(null, "License number registered!");
                licenseText.setText(dto.toString());
            }
        });
        findLicenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                LicenseDto dto = new LicenseDto(Integer.parseInt(textField3.getText()));
                dto = register.findLicense(dto);
                if(dto != null) {
                    licenseText.setText(dto.toString());

                    licenseText.setText(dto.toString());
                    textField1.setText(dto.getDriverName());
                    textField2.setText(dto.getDriverSurname());
                    textField3.setText(String.valueOf(dto.getLicenseNumber()));
                    textField4.setText(String.valueOf(dto.getLicenseValidityUntil().getDay()));
                    textField7.setText(String.valueOf(dto.getLicenseValidityUntil().getMonth()));
                    textField8.setText(String.valueOf(dto.getLicenseValidityUntil().getYear()));
                    if ((dto.isProhibited() == false)) {
                        prohibitedRadioButton.setSelected(false);
                    } else {
                        prohibitedRadioButton.setSelected(true);
                    }
                    textField6.setText(String.valueOf(dto.getNumberOfViolationsInLast12Months()));
                }else{
                    JOptionPane.showMessageDialog(null, "License with this number doesnt exists!");
                }

            }
        });
        sequencePrintOfLicensesFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<BlockDto> blockDtos = register.getLicensesBlocks();
                licenseText.setText(writeBlocks(blockDtos));
            }
        });
        updateLicenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LicenseDto dto = new LicenseDto(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()), new Date(Integer.parseInt(textField8.getText()) - 1900, Integer.parseInt(textField7.getText()), Integer.parseInt(textField4.getText())), prohibitedRadioButton.isSelected() ? true : false, Integer.parseInt(textField6.getText()));
                dto = register.updateLicense(dto);
                if(dto != null) {
                    licenseText.setText(dto.toString());

                    textField1.setText(dto.getDriverName());
                    textField2.setText(dto.getDriverSurname());
                    textField3.setText(String.valueOf(dto.getLicenseNumber()));
                    textField4.setText(String.valueOf(dto.getLicenseValidityUntil().getDay()));
                    textField7.setText(String.valueOf(dto.getLicenseValidityUntil().getMonth()));
                    textField8.setText(String.valueOf(dto.getLicenseValidityUntil().getYear()));
                    if ((dto.isProhibited() == false)) {
                        prohibitedRadioButton.setSelected(false);
                    } else {
                        prohibitedRadioButton.setSelected(true);
                    }
                    textField6.setText(String.valueOf(dto.getNumberOfViolationsInLast12Months()));
                }else{
                    JOptionPane.showMessageDialog(null, "License with this number doesnt exists, or you try to update key parameter!");
                }
            }
        });
        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VehicleDto dto = new VehicleDto(textField5.getText(), textField9.getText(), Integer.parseInt(textField10.getText()), Integer.parseInt(textField11.getText()), wantedRadioButton.isSelected() ? true : false, new Date(Integer.parseInt(textField13.getText()) - 1900, Integer.parseInt(textField14.getText()), Integer.parseInt(textField15.getText())), new Date(Integer.parseInt(textField16.getText()) - 1900, Integer.parseInt(textField17.getText()), Integer.parseInt(textField18.getText())));
                register.addVehicle(dto);
                JOptionPane.showMessageDialog(null, "Vehicle registered!");
                vehicleText.setText(dto.toString());
            }
        });
        findVehicleByENButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VehicleDto dto = new VehicleDto();
                dto.setEvidenceNumber(textField5.getText());
                dto = register.findVehicleByEN(dto);
                if(dto != null) {
                    vehicleText.setText(dto.toString());

                    textField5.setText(dto.getEvidenceNumber());
                    textField9.setText(dto.getVIN());
                    textField10.setText(String.valueOf(dto.getNumberOfWheelAxles()));
                    textField11.setText(String.valueOf(dto.getOperatingWeight()));
                    if ((dto.isWanted() == false)) {
                        wantedRadioButton.setSelected(false);
                    } else {
                        wantedRadioButton.setSelected(true);
                    }
                    textField15.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField14.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField13.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));
                    textField18.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField17.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField16.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));

                }else{
                    JOptionPane.showMessageDialog(null, "Vehi with this number doesnt exists!");
                }
            }
        });
        findVehicleByVINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VehicleDto dto = new VehicleDto();
                dto.setVIN(textField9.getText());
                dto = register.findVehicleByVIN(dto);
                if(dto != null) {
                    vehicleText.setText(dto.toString());

                    textField5.setText(dto.getEvidenceNumber());
                    textField9.setText(dto.getVIN());
                    textField10.setText(String.valueOf(dto.getNumberOfWheelAxles()));
                    textField11.setText(String.valueOf(dto.getOperatingWeight()));
                    if ((dto.isWanted() == false)) {
                        wantedRadioButton.setSelected(false);
                    } else {
                        wantedRadioButton.setSelected(true);
                    }
                    textField15.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField14.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField13.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));
                    textField18.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField17.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField16.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));

                }else{
                    JOptionPane.showMessageDialog(null, "Vehi with this number doesnt exists!");
                }
            }
        });
        updateVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VehicleDto dto = new VehicleDto(textField5.getText(), textField9.getText(), Integer.parseInt(textField10.getText()), Integer.parseInt(textField11.getText()), wantedRadioButton.isSelected() ? true : false, new Date(Integer.parseInt(textField15.getText()) - 1900, Integer.parseInt(textField14.getText()), Integer.parseInt(textField13.getText())), new Date(Integer.parseInt(textField18.getText()) - 1900, Integer.parseInt(textField17.getText()), Integer.parseInt(textField16.getText())));
                dto.setEvidenceNumber(textField5.getText());
                dto = register.updateVehicle(dto);
                if(dto != null) {
                    vehicleText.setText(dto.toString());

                    textField5.setText(dto.getEvidenceNumber());
                    textField9.setText(dto.getVIN());
                    textField10.setText(String.valueOf(dto.getNumberOfWheelAxles()));
                    textField11.setText(String.valueOf(dto.getOperatingWeight()));
                    if ((dto.isWanted() == false)) {
                        wantedRadioButton.setSelected(false);
                    } else {
                        wantedRadioButton.setSelected(true);
                    }
                    textField13.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField14.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField15.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));
                    textField16.setText(String.valueOf(dto.getSTKValidityUntil().getDay()));
                    textField17.setText(String.valueOf(dto.getSTKValidityUntil().getMonth()));
                    textField18.setText(String.valueOf(dto.getSTKValidityUntil().getYear()));

                }else{
                    JOptionPane.showMessageDialog(null, "Vehicle with this number doesnt exists!");
                }
            }
        });
        sequencePrintOfVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<BlockDto> blockDtos = register.getVehiclesBlocks();
                vehicleText.setText(writeBlocks(blockDtos));
            }
        });
        sequencePrintOfByButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<BlockDto> blockDtos = register.getVehicelsByEnBlocks();
                vehicleText.setText(writeBlocks(blockDtos));
            }
        });
        sequencePrintOfByButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<BlockDto> blockDtos = register.getVehiclesByVinBlocks();
                vehicleText.setText(writeBlocks(blockDtos));
            }
        });
        generateDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.generateVehicles(Integer.parseInt(textField12.getText()));
                register.generateLicenses(Integer.parseInt(textField19.getText()));
            }
        });
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.saveToFile();
            }
        });
        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                register.loadFromFile();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(1450, 800));
        // licenses, byVin, byEn, vehicles
        register = Register.getInstance(400,300,200,200);

    }

    public String writeBlocks(ArrayList<BlockDto> blockDtos){

        String s = "";
        for (int i = 0; i < blockDtos.size(); i++) {
            BlockDto blockDto = blockDtos.get(i);
            s += "Block " + ((i < 9) ? (" " + (i +  1)) : (i + 1)) + " - address: " + blockDto.getAddress() + " and father address: " + blockDto.getFatherAddress() + ":\n";


            for (int j = 0; j < blockDto.getRecords().length; j++){
                RecordDto recordDto = blockDto.getRecords()[j];
                s += "Record " + ((j < 9) ? (" " + (j +  1)) : (j + 1)) + ": ";
                if(recordDto.isValid()){
                    s +=  recordDto.toString() + "\n";
                } else {
                    s += "Invalid record\n";
                }
            }
        }
        return s;
    }
}
