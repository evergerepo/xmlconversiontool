package com.everge.refiner;

import java.io.File;

import javax.xml.bind.JAXBException;

public class App {
	
	public static void main(String[] args) throws JAXBException {
		String sourcePath = args[0];
		String destinationPath = args[1];
		
//		String sourcePath = "/Users/abhisheksingh/Desktop/source/";
//		String destinationPath = "/Users/abhisheksingh/Desktop/dest/";
		
		XMLRefiner refiner = new XMLRefiner();
		File directory = new File(sourcePath);
		File[] files = directory.listFiles();
		for(File file : files) {
			if(file.isFile()) {
				File destinationFile = new File(destinationPath + file.getName());
				refiner.refineXML(file, destinationFile);
			}
		}
	}

}
