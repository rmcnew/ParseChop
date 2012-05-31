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

package net.mcnewfamily.rmcnew.controller;

import net.mcnewfamily.rmcnew.dao.RosterXlsxReader;
import net.mcnewfamily.rmcnew.dao.TextFileWriter;
import net.mcnewfamily.rmcnew.model.UlnRecords;

import java.io.File;
import java.io.IOException;

public class ParseController {

	public void runWorkflow(File excelInputFile, File outputFolder) throws IOException, IllegalStateException {
        RosterXlsxReader rosterXlsxReader = new RosterXlsxReader();
        rosterXlsxReader.openXlsxFile(excelInputFile);
        UlnRecords ulnRecords = rosterXlsxReader.readRecords();
        TextFileWriter textFileWriter = new TextFileWriter();
        textFileWriter.writeRecords(ulnRecords, outputFolder);
	}
}
