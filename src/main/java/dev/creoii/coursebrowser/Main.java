package dev.creoii.coursebrowser;

import dev.creoii.coursebrowser.backend.DataLoader;
import dev.creoii.coursebrowser.backend.User;
import dev.creoii.coursebrowser.frontend.homePage;

import javax.swing.*;
import java.util.UUID;

public class Main {
    public static final User USER = new User(UUID.fromString("63ac3985-e03b-4624-9f12-c1aed754a577"));

    public static void main(String[] args) {
        DataLoader.load();

        SwingUtilities.invokeLater(homePage::new);
    }
}