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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UlnRecords {

	private HashMap<String, ArrayList<Record>> ulnRecords = new HashMap<String, ArrayList<Record>>();

	public void addRecord(Record record) {
		if (record != null) {
			String uln = record.getUln();
			ArrayList<Record> records;
			if (ulnRecords.containsKey(uln)) {
				records = ulnRecords.get(uln);
			} else {
				records = new ArrayList<Record>();
			}
			records.add(record);
			ulnRecords.put(uln, records);
		}
	}

    public ArrayList<Record> get(Object o) {
        return ulnRecords.get(o);
    }

    public boolean isEmpty() {
        return ulnRecords.isEmpty();
    }

    public Set<String> keySet() {
        return ulnRecords.keySet();
    }
}
