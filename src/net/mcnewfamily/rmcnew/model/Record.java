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

public class Record {

    private String name;
	private String ssn;
    private String rank;
    private String uln;

    public Record() {
    }

	public Record(String name, String ssn, String rank, String uln) {
		this.name = name;
		this.ssn = ssn;
		this.rank = rank;
		this.uln = uln;
	}

	public String getName() {
		return name;
	}

	public String getSsn() {
		return ssn;
	}

	public String getRank() {
		return rank;
	}

	public String getUln() {
		return uln;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Record record = (Record) o;

		if (!name.equals(record.name)) return false;
		if (!rank.equals(record.rank)) return false;
		if (!ssn.equals(record.ssn)) return false;
		if (!uln.equals(record.uln)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + ssn.hashCode();
		result = 31 * result + rank.hashCode();
		result = 31 * result + uln.hashCode();
		return result;
	}

}
