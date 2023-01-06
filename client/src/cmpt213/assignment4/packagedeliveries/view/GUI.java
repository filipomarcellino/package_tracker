package cmpt213.assignment4.packagedeliveries.view;

/*
 * Swing version
 */

import cmpt213.assignment4.packagedeliveries.control.PackageManager;
import cmpt213.assignment4.packagedeliveries.model.Package;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Class GUI is responsible for the main frame of the application
 */
public class GUI implements ActionListener, WindowListener {
    PackageManager packageManager;
    ArrayList<Package> packages;
    JFrame applicationFrame;
    JPanel mainPanel;
    JPanel showPanel;
    JPanel buttonPanel;
    JScrollPane scrollPane;
    JButton allButton;
    JButton overdueButton;
    JButton upcomingButton;
    JButton addButton;
    int lastViewClicked;

    /**
     * Default constructor to set up all the layout managers, buttons, scroll panels, and frame
     */
    public GUI(){
        //get array list of packages
        packageManager = new PackageManager();
        try{
            packages = packageManager.requestListAll();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //initializing all the panels
        lastViewClicked = 1;
        applicationFrame = new JFrame("Package Deliveries Tracker App");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        showPanel = new JPanel();
        showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(showPanel);
        scrollPane.setPreferredSize(new Dimension(400,500));

        //initializing all the buttons
        allButton = new JButton("All");
        overdueButton = new JButton("Overdue");
        upcomingButton = new JButton("Upcoming");
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        allButton.addActionListener(this);
        overdueButton.addActionListener(this);
        upcomingButton.addActionListener(this);
        allButton.setFocusPainted(false);
        overdueButton.setFocusPainted(false);
        upcomingButton.setFocusPainted(false);


        //adding components to buttonPanel
        buttonPanel.add(allButton);
        buttonPanel.add(overdueButton);
        buttonPanel.add(upcomingButton);

        //calling displayAllPackages to fill in scrollPane
        displayAllPackages();

        //adding components to mainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(addButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        applicationFrame.add(mainPanel);

        applicationFrame.addWindowListener(this);
        applicationFrame.pack();
        applicationFrame.setLocationRelativeTo(null);
        applicationFrame.setVisible(true);

    }

    /**
     * method to remove the current panel in scrollPane, access the arraylist, and display all Packages
     */
    private void displayAllPackages(){
        //remove the contents from showPanel
        showPanel.removeAll();
        try{
            packages = packageManager.requestListAll();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //if no packages in arraylist, add a label saying "No packages in the list"
        if (packages.size() == 0) {
            JLabel message = new JLabel("No packages in the list");
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            showPanel.add(message);
        }

        //for every package in packages, create a panel
        else {
            int packageNo = 0;
            for (Package aPackage : packages) {
                packageNo++;
                createPackagePanels(aPackage, packageNo);
            }
        }


        showPanel.revalidate();
        showPanel.repaint();
    }

    /**
     * method to remove the current panel in scrollPane, access the arraylist, and display all upcoming packages
     */
    private void displayUpcomingPackages(){
        //remove the contents from showPanel
        showPanel.removeAll();
        try{
            packages = packageManager.requestListUpcoming();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        int packageNo = 0;
        for (Package aPackage : packages) {
            packageNo++;
            createPackagePanels(aPackage, packageNo);
        }
        if (packageNo == 0) {
            JLabel message = new JLabel("No upcoming packages in the list");
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            showPanel.add(message);
        }


        showPanel.revalidate();
        showPanel.repaint();
    }

    /**
     * method to remove the current panel in scrollPane, access the arraylist, and display all overdue packages
     */
    private void displayOverduePackages(){
        //remove the contents from showPanel
        showPanel.removeAll();
        try{
            packages = packageManager.requestListOverdue();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        int packageNo = 0;
        for (Package aPackage : packages) {
            packageNo++;
            createPackagePanels(aPackage, packageNo);
        }
        if (packageNo == 0) {
            JLabel message = new JLabel("No Overdue packages in the list");
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            showPanel.add(message);
        }

        showPanel.revalidate();
        showPanel.repaint();
    }

    /**
     * method to create individual panels for each pacakge in the array list (contains action listeners for remove button and delivered checkbox)
     */
    private void createPackagePanels(Package aPackage, int packageNo){
        //create all the panels
        JPanel packagePanel = new JPanel();
        packagePanel.setLayout(new BorderLayout());
        JPanel bottomPackagePanel = new JPanel();
        bottomPackagePanel.setLayout(new BoxLayout(bottomPackagePanel, BoxLayout.X_AXIS));

        //create labels
        JLabel information  = new JLabel();
        information.setText("<html>" + aPackage.toString().replaceAll("\n", "<br/>") + "</html>");

        //create button and checkbox
        JButton remove = new JButton("Remove");
        JCheckBox delivered = new JCheckBox("Delivered");
        if(aPackage.getDelivered()){
            delivered.setSelected(true);
        }
        remove.addActionListener(e -> {
            //get the index of the Package object in the array list
            try{
                packageManager.requestRemove(aPackage.getId());
            }
            catch(Exception exception){
                exception.printStackTrace();
            }
            switch (lastViewClicked) {
                case 1 -> displayAllPackages();
                case 2 -> displayOverduePackages();
                case 3 -> displayUpcomingPackages();
            }
        });
        delivered.addActionListener(
                e -> {
            try{
                packageManager.requestMarkAsDelivered(aPackage.getId());
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
            switch (lastViewClicked) {
                case 1 -> displayAllPackages();
                case 2 -> displayOverduePackages();
                case 3 -> displayUpcomingPackages();
            }
        });

        //add components to package panel
        bottomPackagePanel.add(delivered);
        bottomPackagePanel.add(remove);
        packagePanel.add(information, BorderLayout.CENTER);
        packagePanel.add(bottomPackagePanel, BorderLayout.SOUTH);
        packagePanel.add(bottomPackagePanel, BorderLayout.SOUTH);

        //set borders for package panels
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Package # " + (packageNo));
        packagePanel.setBorder(title);

        //add package panel to showPanel
        showPanel.add(packagePanel);
        showPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        showPanel.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == addButton){
            cmpt213.assignment4.packagedeliveries.view.Dialog addDialog = new Dialog(applicationFrame, packages);
            addDialog.showDialog("Add Package");
            displayAllPackages();
        }
        else if(e.getSource() == allButton){
            lastViewClicked = 1;
            displayAllPackages();
        }
        else if(e.getSource() == overdueButton){
            lastViewClicked = 2;
            displayOverduePackages();
        }
        else if(e.getSource() == upcomingButton){
            lastViewClicked = 3;
            displayUpcomingPackages();
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * method to call PackageManager's exitProgram() function to deserialize object to JSON
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try{
            packageManager.exitProgram();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        System.out.println("I'm here");
        e.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}

