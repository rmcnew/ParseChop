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

import net.mcnewfamily.rmcnew.business_rule.AfghanUnknownPriorityMosGoesToBagram;
import net.mcnewfamily.rmcnew.business_rule.KuwaitQatarSingleHub;
import net.mcnewfamily.rmcnew.business_rule.MakeAllMilitaryServiceBranchA;
import net.mcnewfamily.rmcnew.model.config.*;
import net.mcnewfamily.rmcnew.model.data.*;
import net.mcnewfamily.rmcnew.model.SheetNotFoundException;
import net.mcnewfamily.rmcnew.dao.ManifestXlsxReader;
import net.mcnewfamily.rmcnew.model.Constants;
import net.mcnewfamily.rmcnew.model.Util;

import java.io.File;
import java.io.IOException;

public abstract class ParseController {

    public abstract void runWorkflow(File inputFile, File outputFile) throws IOException, SheetNotFoundException;

    protected static Manifest processManifestFile(File manifestInputFile, String sheetName) throws IOException, SheetNotFoundException {
        if (manifestInputFile != null && Util.notNullAndNotEmpty(sheetName)) {
            CrcManifestProcessorConfig config = CrcManifestProcessorConfig.getInstance();
            LocationAliasMap aliasMap = config.getAliasMap();
            DestinationHubMap hubMap = config.getHubMap();
            PriorityMOSMap mosMap = config.getMosMap();

            ManifestXlsxReader manifestXlsxReader = new ManifestXlsxReader();
            manifestXlsxReader.openXlsxFile(manifestInputFile);
            Manifest manifest = manifestXlsxReader.readManifest(sheetName);

            for (Record record : manifest.getRecords()) {
                processFinalDestination(record, aliasMap);
                processHubLookup(record, hubMap);
                applyBusinessRules(record, mosMap);
            }
            manifest.doSummaryCounts();
            return manifest;
        } else {
            throw new IllegalArgumentException("Cannot create manifest from null or empty file or sheetName!");
        }
    }

    protected static void processFinalDestination(Record record, LocationAliasMap aliasMap) {
        String finalDestination = record.getFinalDestination();
        // strip prefixes and suffixes
        finalDestination = Util.stripLocationPrefixesAndSuffixes(finalDestination);
        // location alias substitution
        if (finalDestination.isEmpty()) {
            finalDestination = Constants.UNKNOWN;
        }
        String alias = aliasMap.get(finalDestination);
        if (alias != null) {
            //System.out.println("Replaced alias:  " + finalDestination + " => " + alias);
            record.setFinalDestination(alias);
        } else {
            record.setFinalDestination(finalDestination);
        }
    }

    protected static void processHubLookup(Record record, DestinationHubMap hubMap) {
        String finalDestination = record.getFinalDestination();
        // hub look-up
        HubCountry hubCountry = hubMap.get(finalDestination);
        if (hubCountry != null) {
            //System.out.println("Found hub: " + finalDestination + " => " + hubCountry);
            record.setHub(hubCountry.getHub());
            if (!record.getCountry().equalsIgnoreCase(hubCountry.getCountry())) {
                System.out.println("Countries do not match!  " + record.getCountry() + " != " + hubCountry.getCountry());
            }
            record.setCountry(hubCountry.getCountry());
        } else {
            if (finalDestination.equalsIgnoreCase(Constants.UNKNOWN)) {
                record.setHub(Constants.UNKNOWN);
            } else {
                record.setHub(Constants.NOT_FOUND);
            }
        }
    }

    protected static void applyBusinessRules(Record record, PriorityMOSMap mosMap) throws IOException {
        KuwaitQatarSingleHub.applyRule(record);
        AfghanUnknownPriorityMosGoesToBagram.applyRule(record, mosMap);
        MakeAllMilitaryServiceBranchA.applyRule(record);
    }

	@Override
	public void runWorkflow(File manifestInputFile, File manifestOutputFile) throws IOException, SheetNotFoundException {
		Manifest preManifest = processManifestFile(manifestInputFile, Constants.PREMANIFEST_SHEET);
		writeResults(preManifest, manifestOutputFile);
	}

	private void writeResults(Manifest preManifest, File preManifestOutputFile) throws IOException {
		PreManifestXlsxWriter xlsxWriter = new PreManifestXlsxWriter();
		xlsxWriter.openXlsxForWriting(preManifestOutputFile);
		xlsxWriter.writePreManifest(preManifest);
		xlsxWriter.close();
	}
}
