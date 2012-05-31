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

import javax.swing.*;
import java.awt.*;

public class AboutTab extends JComponent {

    private static final Dimension ABOUT_TAB_SIZE = new Dimension(295, 195);
	public AboutTab() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JEditorPane editorPane = new JEditorPane("text/html", ABOUT_TAB_HTML);
		editorPane.setEditable(false);
        editorPane.setPreferredSize(ABOUT_TAB_SIZE);
        editorPane.setVisible(true);
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setPreferredSize(ABOUT_TAB_SIZE);
        editorScrollPane.setVisible(true);
        this.add(editorScrollPane);
	}

    private final static String ABOUT_TAB_HTML = "<html>" +
        "<h2>ParseChop, Copyright (c) 2012 Richard Scott McNew.</h2>" +
        "<p>" +
        "ParseChop parses Excel personnel rosters into ULN text files" +
        " suitable for upload into TPS." +
        "</p><p>" +
        "ParseChop is free software: you can redistribute it and/or modify" +
        " it under the terms of the GNU General Public License as published by" +
        " the Free Software Foundation, either version 3 of the License, or" +
        " (at your option) any later version." +
        "</p><p>" +
        "ParseChop is distributed in the hope that it will be useful," +
        " but WITHOUT ANY WARRANTY; without even the implied warranty of" +
        " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the" +
        " GNU General Public License for more details." +
        "</p><p>" +
        "You should have received a copy of the GNU General Public License" +
        " along with ParseChop.  If not, see" +
        " <a href=\"http://www.gnu.org/licenses/\">http://www.gnu.org/licenses/</a>. " +
        "</p><p>" +
        " The source for ParseChop is available on GitHub: " +
        " <a href=\"https://github.com/rmcnew/ParseChop\">https://github.com/rmcnew/ParseChop</a> " +
        "</p><p>" +
        " ParseChop uses libraries from the Apache POI Project: " +
        " <a href=\"http://poi.apache.org/\">http://poi.apache.org/</a>" +
        "</p></html>" ;



}
