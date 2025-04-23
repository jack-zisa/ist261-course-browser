package dev.creoii.coursebrowser.frontend;

import dev.creoii.coursebrowser.Main;
import dev.creoii.coursebrowser.api.CourseManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class tabbedshop extends JFrame {
    private JPanel MainJp;
    private List<JButton> courseButtons;
    private JButton viewCartButton;
    private JButton homePageButton;

    public tabbedshop() {
        MainJp = new JPanel();
        MainJp.setLayout(new GridLayout(6, 1));
        setContentPane(MainJp);
        setTitle("Shop Page");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowCloser());

        courseButtons = new ArrayList<>();
        viewCartButton = new JButton("View Cart");
        homePageButton = new JButton("Home Page");

        CourseManager.getCourses().forEach(course -> {
            JButton button = new JButton("Add " + course.name());
            button.addActionListener(e -> CourseManager.getCart().add(course));
            courseButtons.add(button);
        });

        courseButtons.forEach(MainJp::add);
        MainJp.add(viewCartButton);
        MainJp.add(homePageButton);

        /*historyButton.addActionListener(e -> {
            CourseManager.cart.add(new Course("History 101", "World history foundations", 40.0));
        });*/

        viewCartButton.addActionListener(e -> {
            new cartPage().setVisible(true);
            dispose();
        });

        homePageButton.addActionListener(e -> {
            new homePage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
