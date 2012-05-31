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

package net.mcnewfamily.rmcnew.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Util {

	public static boolean notNullAndNotEmpty(String string) {
		return (string != null && !string.isEmpty());
	}

    public static String convertStackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder builder = new StringBuilder("");
        if (stackTraceElements != null) {
            for (StackTraceElement element : stackTraceElements ) {
                builder.append("\t");
                builder.append(element.toString());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public static String getCellValueAsStringOrEmptyString(Cell cell) {
        if (cell == null) {
            return "";
        }
        String value;
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue().toString();
                } else {
                    value = Integer.toString((int)cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = Boolean.toString(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                value = "";
        }
        return value;
    }

    public static String zeroPadSSN(String ssn) {
        int ssnInt = Integer.parseInt(ssn);
        return String.format("%09d", ssnInt);
    }

    public static File attachXlsxExtensionIfMissing(File outputFile) {
        String filename = outputFile.getAbsolutePath();
        //System.out.println("Filename is: " + filename);
        if ( filename.endsWith(".xlsx") || filename.endsWith(".XLSX") ) {
            // do nothing
        } else if ( filename.endsWith(".xls") || filename.endsWith(".XLS") ) {
            // replace xls with xlsx
            filename = filename + "x";
            outputFile = new File(filename);
        }
        else {
            filename = filename + ".xlsx";
            outputFile = new File(filename);
        }
        //System.out.println("Fixed file is: " + outputFile.getAbsolutePath());
        return outputFile;
    }

    public static final FileNameExtensionFilter EXCEL_FILTER = new FileNameExtensionFilter("Excel spreadsheets", "xlsx", "xls", "XLSX", "XLS");

    public static void main(String[] args) {
        // tests
        int test1 = 333445555;
        int test2 =    112222;
        int test3 =  77889999;
        int test4 =       123;

        System.out.println(test1 + " => " + zeroPadSSN(String.valueOf(test1)));
        System.out.println(test2 + " => " + zeroPadSSN(String.valueOf(test2)));
        System.out.println(test3 + " => " + zeroPadSSN(String.valueOf(test3)));
        System.out.println(test4 + " => " + zeroPadSSN(String.valueOf(test4)));

    }
}
