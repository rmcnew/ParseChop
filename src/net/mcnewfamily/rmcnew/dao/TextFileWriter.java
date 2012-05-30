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

import net.mcnewfamily.rmcnew.model.data.Manifest;
import net.mcnewfamily.rmcnew.model.UlnRecords;
import net.mcnewfamily.rmcnew.model.Constants;
import net.mcnewfamily.rmcnew.model.Util;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextFileWriter {

    XSSFWorkbook workbook;
    FileOutputStream fileOutputStream;

    public void openXlsxForWriting(File file) throws IOException {
        if (file != null) {
            workbook = new XSSFWorkbook();
            fileOutputStream = new FileOutputStream(file);
        }
    }

    public void close() throws IOException {
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    protected void writeRecords(UlnRecords ulnRecords, String sheetName) {
        if (ulnRecords != null && !ulnRecords.isEmpty()) {
            XSSFSheet finalManifestSheet = ulnRecords.toSheetEssence(sheetName).toXSSFSheet(workbook);
            for (int columnIndex = 0; columnIndex < 13; columnIndex++) {
                finalManifestSheet.autoSizeColumn(columnIndex);
            }
        } else {
            throw new IllegalArgumentException("Cannot create XLSX sheet from null or empty UlnRecords!");
        }
    }

    protected void writeSummaryTable(Manifest manifest, String sheetName) {
        if (manifest != null) {
            XSSFSheet manifestCountsSheet = manifest.toSheetEssence(sheetName).toXSSFSheet(workbook);
            for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
                manifestCountsSheet.autoSizeColumn(columnIndex);
            }
        } else {
            throw new IllegalArgumentException("Cannot create XLSX sheet from null or empty manifest!");
        }
    }

    protected void writeInstructions(Manifest manifest) {
        if (manifest != null) {
            XSSFSheet destSheet = workbook.createSheet(Constants.INSTRUCTIONS_SHEET);
            XSSFSheet srcSheet = manifest.getInstructions();
            Util.copyXSSFSheet(srcSheet, destSheet);
            for (int columnIndex = 0; columnIndex < 13; columnIndex++) {
                destSheet.autoSizeColumn(columnIndex);
            }
        } else {
            throw new IllegalArgumentException("Cannot create instructions sheet from null Manifest!");
        }
    }

    protected void createTextBox(XSSFSheet xssfSheet, XSSFClientAnchor xssfClientAnchor, String text) {
        if (xssfSheet != null) {
            XSSFDrawing xssfDrawing = xssfSheet.createDrawingPatriarch();
            XSSFTextBox xssfTextBox = xssfDrawing.createTextbox(xssfClientAnchor);
            xssfTextBox.setText(new XSSFRichTextString(text));

        } else {
            throw new IllegalArgumentException("Cannot create drawing on  null or empty sheet!");
        }
    }

    public void copyInstructionsSheet() {

    }
}
