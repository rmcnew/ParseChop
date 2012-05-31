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

import net.mcnewfamily.rmcnew.model.Record;
import net.mcnewfamily.rmcnew.model.UlnRecords;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class TextFileWriter {

    public void writeRecords(UlnRecords ulnRecords, File outputFolder) throws IOException {
        if (ulnRecords != null && !ulnRecords.isEmpty() && outputFolder != null) {
            Set<String> ulns = ulnRecords.keySet();
            for (String uln: ulns) {
                ArrayList<Record> records = ulnRecords.get(uln);
                String outputFilename = outputFolder.getAbsolutePath() + File.separator +  uln + "-" + records.size() + ".txt";
                //System.out.println("Writing file:  " + outputFilename);
                FileWriter fileWriter = new FileWriter(outputFilename);
                for (Record record : records) {
                    fileWriter.write(record.getSsn() +  "\r\n");
                }
                fileWriter.flush();
                fileWriter.close();
            }
        } else {
            throw new IllegalArgumentException("Cannot create text files from null or empty UlnRecords!");
        }
    }
}
