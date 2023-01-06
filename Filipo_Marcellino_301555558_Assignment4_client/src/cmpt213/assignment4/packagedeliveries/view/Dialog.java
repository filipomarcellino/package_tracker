package cmpt213.assignment4.packagedeliveries.view;

import cmpt213.assignment4.packagedeliveries.control.PackageManager;
import cmpt213.assignment4.packagedeliveries.model.Package;
import com.github.lgooddatepicker.components.DateTimePicker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 * Class Dialog is responsible for the dialog during the add package operation
 */
public class Dialog extends JDialog implements ActionListener {
    PackageManager packageManager;
    ArrayList<Package> packages;
    GridBagConstraints gbc;
    DateTimePicker eddField, expiryDateField;
    JPanel gui, buttons;
    JLabel type, name, notes, price, weight, expectedDeliveryDate, extra;
    JComboBox typeField;
    JTextField nameField, notesField, priceField, weightField, extraField;
    JButton create, cancel;
    boolean isATextField = true;

    /**
     * Constructor to set up all the layout managers, buttons, textfields, and labels
     */
    public Dialog(Frame parent, ArrayList<Package> packages) {
        super(parent, true);
        packageManager = new PackageManager();
        this.packages = packages;
        gbc = new GridBagConstraints();
        //adding panels
        gui = new JPanel(new GridBagLayout());
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttons = new JPanel();

        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
        type = new JLabel("Type: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gui.add(type, gbc);

        String[] types ={"Book", "Perishable", "Electronic"};
        typeField = new JComboBox(types);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        typeField.addActionListener(this);
        gui.add(typeField, gbc);

        name = new JLabel("Name: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gui.add(name, gbc);

        nameField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gui.add(nameField, gbc);

        notes = new JLabel("Notes: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gui.add(notes, gbc);

        notesField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gui.add(notesField, gbc);

        price = new JLabel("Price: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gui.add(price, gbc);

        priceField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gui.add(priceField, gbc);

        weight = new JLabel("Weight: ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gui.add(weight, gbc);

        weightField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gui.add(weightField, gbc);

        expectedDeliveryDate = new JLabel("Expected Delivery Date: ");
        expectedDeliveryDate.setPreferredSize(new Dimension(100,20));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gui.add(expectedDeliveryDate, gbc);

        eddField = new DateTimePicker();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gui.add(eddField, gbc);

        extra = new JLabel("Author: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gui.add(extra, gbc);

        extraField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gui.add(extraField, gbc);

        create = new JButton("Create");
        create.addActionListener(this);
        buttons.add(create);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gui.add(buttons, gbc);


        //adding all the components
        setContentPane(gui);
    }

    /**
     * method to show the dialog by setting setVisible to true
     */
    public void showDialog(String title) {
        setTitle(title);
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
    }
    /**
     * method to add package to arraylist by getting an instance from PackageFactory
     */
    private void addPackage(){
        double price = Double.parseDouble(priceField.getText());
        double weight = Double.parseDouble(weightField.getText());
        switch ((String)typeField.getSelectedItem()){
            case "Book":
                try{
                    packageManager.requestAddBook(nameField.getText(), notesField.getText(), price, weight, eddField.getDateTimePermissive(), (String) typeField.getSelectedItem(), extraField.getText());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "Perishable":
                try{
                    packageManager.requestAddPerishable(nameField.getText(), notesField.getText(), price, weight, eddField.getDateTimePermissive(), (String) typeField.getSelectedItem(), expiryDateField.getDateTimePermissive());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "Electronic":
                try{
                    packageManager.requestAddElectronic(nameField.getText(), notesField.getText(), price, weight, eddField.getDateTimePermissive(), (String) typeField.getSelectedItem(), Double.parseDouble(extraField.getText()));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    /**
     * method to validate the input written by the user
     */
    private Boolean validateData(){
        //name can't be empty
        String warning = "";
        if (nameField.getText().equals("")){
            warning += "Name cannot be empty\n";
        }
        //price needs to be positive
        if (priceField.getText().equals("")){
            warning += "Price cannot be empty\n";
        }
        else if (Double.parseDouble(priceField.getText()) < 0) {
            warning += "Price needs to be a positive number\n";
        }
        //weight needs to be positive
        if (weightField.getText().equals("")){
            warning += "Weight cannot be empty\n";
        }
        else if (Double.parseDouble(weightField.getText()) < 0) {
            warning += "Weight needs to be a positive number\n";

        }
        if (typeField.getSelectedItem().equals("Electronic") && isATextField && extraField.getText().equals("")){
            warning += "Environmental fee cannot be empty\n";
        }
        if (eddField.getDateTimeStrict() == null){
            warning += "Expected delivery date cannot be empty\n";
        }
        if (typeField.getSelectedItem().equals("Perishable") && expiryDateField.getDateTimeStrict() == null){
            warning += "Expiry date date cannot be empty\n";
        }

        if (!warning.equals("")) {
            JOptionPane.showMessageDialog(this, warning,"Invalid Input", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;

    }

    /**
     * Action listeners to respond to various button clicks and JComboBox changes
     */
    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == cancel){
            dispose();
        }
        else if(e.getSource() == create){
            if(validateData()){
                addPackage();
                dispose();
            }
        }
        else if(e.getSource() == typeField){
            gui.remove(extra);
            if (isATextField) {
                gui.remove(extraField);
            }
            else {
                gui.remove(expiryDateField);
            }
            if(Objects.equals(typeField.getSelectedItem(), "Book")){
                extra = new JLabel("Author: ");
                extraField = new JTextField(30);
                gbc.gridy = 6;
                gbc.gridx = 1;
                gui.add(extraField, gbc);
                isATextField = true;
            }
            else if(Objects.equals(typeField.getSelectedItem(), "Perishable")){
                extra = new JLabel("Expiry Date: ");
                expiryDateField = new DateTimePicker();
                gbc.gridy = 6;
                gbc.gridx = 1;
                gui.add(expiryDateField, gbc);
                isATextField = false;
            }
            else{
                extra = new JLabel("Environmental Fee: ");
                extraField = new JTextField(30);
                gbc.gridy = 6;
                gbc.gridx = 1;
                gui.add(extraField, gbc);
                isATextField = true;
            }
            gbc.gridy = 6;
            gbc.gridx = 0;
            gui.add(extra, gbc);
            gui.repaint();
            gui.revalidate();


        }
    }
}

