package dev.creoii.coursebrowser.frontend;

import dev.creoii.coursebrowser.Main;
import dev.creoii.coursebrowser.backend.DataSaver;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowCloser implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        DataSaver.save(Main.USER);
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
