package dev.creoii.coursebrowser.frontend;

import dev.creoii.coursebrowser.Main;
import dev.creoii.coursebrowser.backend.course.Course;

import javax.swing.*;
import java.awt.*;

public class homePage extends JFrame {
    private JLabel JPortalLabel;
    private JPanel mainPanel;
    private JList<Course> ownedList;
    private JButton openClassButton;
    private JButton goToShopButton;
    private JButton goToCartButton;
    private DefaultListModel<Course> ownedModel = new DefaultListModel<>();

    public homePage() {
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        setTitle("Home Page");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowCloser());

        JPortalLabel = new JLabel("Welcome to JPortal", SwingConstants.CENTER);
        mainPanel.add(JPortalLabel, BorderLayout.NORTH);

        ownedList = new JList<>();
        mainPanel.add(new JScrollPane(ownedList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        openClassButton = new JButton("Open Class");
        goToShopButton = new JButton("Go to Shop");
        goToCartButton = new JButton("View Cart");
        buttonPanel.add(openClassButton);
        buttonPanel.add(goToShopButton);
        buttonPanel.add(goToCartButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        for (Course course : Main.USER.getPurchasedCourses()) {
            ownedModel.addElement(course);
        }
        ownedList.setModel(ownedModel);

        goToShopButton.addActionListener(e -> {
            new tabbedshop().setVisible(true);
            dispose();
        });

        goToCartButton.addActionListener(e -> {
            new cartPage().setVisible(true);
            dispose();
        });

        openClassButton.addActionListener(e -> {
            Course selected = ownedList.getSelectedValue();
            if (selected != null) {
                new ClassWindow(selected).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a class.");
            }
        });

        setVisible(true);
    }
}
