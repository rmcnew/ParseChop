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

package net.mcnewfamily.rmcnew.dao;

import net.mcnewfamily.rmcnew.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RosterXlsxReader {

    protected XSSFWorkbook workbook;

    public void openXlsxFile(String filename) throws IOException {
        if (Util.notNullAndNotEmpty(filename) ) {
            workbook = new XSSFWorkbook(new FileInputStream(filename));
        }
    }

    public void openXlsxFile(File file) throws IOException {
        if (file != null) {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
    }

    public UlnRecords readRecords() throws IOException, IllegalStateException {
        UlnRecords ulnRecords = new UlnRecords();
        XSSFSheet rosterSheet = workbook.getSheetAt(0);
        for (Row row : rosterSheet) {
            // NAME, SSN, RANK, ULN
            String name = Util.getCellValueAsStringOrEmptyString(row.getCell(0)).trim();
            if (name == null || name.isEmpty()) {
                continue; // name is a required field; do not process empty lines
            }
            String ssn = Util.getCellValueAsStringOrEmptyString(row.getCell(1)).trim();
            String rank = Util.getCellValueAsStringOrEmptyString(row.getCell(2)).trim();
            String uln = Util.getCellValueAsStringOrEmptyString(row.getCell(3)).trim();

            ssn = Util.zeroPadSSN(ssn);            
            
            Record record = new Record(name, ssn, rank, uln);
            ulnRecords.addRecord(record);
        }
        if (ulnRecords.isEmpty()) {
            throw new IllegalStateException("No records found in the Excel file!  Please ensure that the records are in the first sheet!");
        }
        return ulnRecords;
    }
}
