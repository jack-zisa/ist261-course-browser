package dev.creoii.coursebrowser.frontend;

import dev.creoii.coursebrowser.Main;
import dev.creoii.coursebrowser.api.CourseManager;
import dev.creoii.coursebrowser.backend.course.Course;

import javax.swing.*;
import java.awt.*;

public class cartPage extends JFrame {
    private JPanel MainCartJP;
    private JList<Course> JCartList;
    private JTextField totalCostField;
    private JButton purchaseButton;
    private JButton removeSelectedButton;
    private JButton homePageButton;
    private JButton shopPageButton;
    private DefaultListModel<Course> cartModel = new DefaultListModel<>();

    public cartPage() {
        MainCartJP = new JPanel(new BorderLayout());
        setContentPane(MainCartJP);
        setTitle("Cart Page");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JCartList = new JList<>();
        totalCostField = new JTextField(10);
        totalCostField.setEditable(false);
        purchaseButton = new JButton("Purchase");
        removeSelectedButton = new JButton("Remove Selected");
        homePageButton = new JButton("Home Page");
        shopPageButton = new JButton("Shop Page");

        for (Course c : CourseManager.getCart()) {
            cartModel.addElement(c);
        }
        JCartList.setModel(cartModel);

        MainCartJP.add(new JScrollPane(JCartList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        bottomPanel.add(new JLabel("Total:"));
        bottomPanel.add(totalCostField);
        bottomPanel.add(purchaseButton);
        bottomPanel.add(removeSelectedButton);
        bottomPanel.add(homePageButton);
        bottomPanel.add(shopPageButton);
        MainCartJP.add(bottomPanel, BorderLayout.SOUTH);

        updateTotal();

        removeSelectedButton.addActionListener(e -> {
            Course selected = JCartList.getSelectedValue();
            if (selected != null) {
                cartModel.removeElement(selected);
                CourseManager.getCart().remove(selected);
                updateTotal();
            }
        });

        purchaseButton.addActionListener(e -> {
            for (Course course : CourseManager.getCart()) {
                Main.USER.purchase(course);
            }
            CourseManager.getCart().clear();
            cartModel.clear();
            updateTotal();
            JOptionPane.showMessageDialog(this, "Purchase successful!");
        });

        homePageButton.addActionListener(e -> {
            new homePage().setVisible(true);
            dispose();
        });

        shopPageButton.addActionListener(e -> {
            new tabbedshop().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void updateTotal() {
        double total = 0;
        for (Course c : CourseManager.getCart()) {
            total += c.price();
        }
        totalCostField.setText("$" + total);
    }
}


