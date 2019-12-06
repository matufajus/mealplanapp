package com.melearning.mealplanapp.demodata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CSVReader {
	
	private String fileLocation;
	
	private List<Record> records;
	
	public CSVReader(String fileLocation) {
		this.fileLocation = fileLocation;
		this.records = new ArrayList<Record>();
	}
	
	public List<Record> getRecords() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation)));
		String line;
		int i = 0;
		br.readLine();
		while ((line = br.readLine()) != null) {
			if (line.charAt(line.length()-1) != '"') {
				br.readLine();
				String extraLine = br.readLine();
				line = line.concat(extraLine.trim());
			}
			line = line.substring(1, line.length()-1);
				String[] collumns = line.split("\",\"");
				Record record = new Record();
				collumns[0] = collumns[0].substring(collumns[0].indexOf("-")+1);
				record.setNumber(Integer.parseInt(collumns[0]));
				record.setTitle(collumns[2]);
				record.setAmmountUnits(collumns[4]);
				record.setIngredients(collumns[5]);
				record.setPreparation(collumns[6]);
				record.setImage(collumns[7]);	
				records.add(record);
			i++;
		}
		br.close();
		records.sort(Comparator.comparing(Record::getNumber));		
		return records;
	}

}
