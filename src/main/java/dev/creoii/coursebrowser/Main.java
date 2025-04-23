package dev.creoii.coursebrowser;

import dev.creoii.coursebrowser.api.UserManager;
import dev.creoii.coursebrowser.backend.DataLoader;
import dev.creoii.coursebrowser.backend.DataSaver;
import dev.creoii.coursebrowser.backend.User;
import dev.creoii.coursebrowser.frontend.homePage;

import javax.swing.*;

public class Main {
    public static final User USER = new User();

    public static void main(String[] args) {
        DataLoader.load();

        UserManager.registerUser(USER);

        SwingUtilities.invokeLater(homePage::new);

        DataSaver.save(USER);
    }
}