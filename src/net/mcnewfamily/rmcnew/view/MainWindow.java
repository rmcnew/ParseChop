/*
 * Copyright (c) 2012 Richard Scott McNew.
 *
 * This file is part of ParseChop.
 *
 * ParseChop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ParseChop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ParseChop.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.mcnewfamily.rmcnew.view;

import net.mcnewfamily.rmcnew.controller.ParseController;
import net.mcnewfamily.rmcnew.model.Util;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String mainTitle = "ParseChop";
    private static JTabbedPane tabbedPane = new JTabbedPane();
    private static ParseTab parseTab;
    private static AboutTab aboutTab;

    public MainWindow(String s) throws HeadlessException {
        super(s);
    }

    public void addTab(String componentTitle, JComponent component) {
        if (component != null && Util.notNullAndNotEmpty(componentTitle)) {
            tabbedPane.addTab(componentTitle, component);
        }
    }

    public static ParseTab getParseTab() {
        return parseTab;
    }

    public static AboutTab getAboutTab() {
        return aboutTab;
    }

    public static void main(String[] args) {
        MainWindow mainWindow = null;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainWindow, e + "\n" + Util.convertStackTraceToString(e.getStackTrace()), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
        }
        mainWindow = new MainWindow(mainTitle);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = mainWindow.getContentPane();
        contentPane.add(tabbedPane);
        // add content
        parseTab = new ParseTab("Chop Into ULNs", "The ULNs were written to text files!", new ParseController());
        mainWindow.addTab("ParseChop", parseTab);
        aboutTab = new AboutTab();
        mainWindow.addTab("About", aboutTab);

        mainWindow.setPreferredSize(new Dimension(360, 240));
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}
