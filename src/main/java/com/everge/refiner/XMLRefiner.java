package com.everge.refiner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.everge.xml.TIMECARDLIST;

public class XMLRefiner {
	
	public void refineXML(File sourceFile, File destinationFile) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(TIMECARDLIST.class);
		Unmarshaller jaxbunMarshaller = jaxbContext.createUnmarshaller();
		TIMECARDLIST timeCardList =  (TIMECARDLIST) jaxbunMarshaller.unmarshal(sourceFile);
		List<TIMECARDLIST.TIMECARD> timeCards = timeCardList.getTIMECARD();
		for(TIMECARDLIST.TIMECARD timeCard : timeCards) {
			List<TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM> timeItemList = timeCard.getTIMEITEMLIST().getTIMEITEM();
			
			List<TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM> newTimeItemList = new ArrayList<TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM>();
			for(TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM timeItem : timeItemList) {
				if("Overtime".equalsIgnoreCase(timeItem.getTIMETYPE())) {
					TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM newItem = new TIMECARDLIST.TIMECARD.TIMEITEMLIST.TIMEITEM();
					newItem.setTIMETYPE("Overtime Premium");
					newItem.setPROPERTIESLIST(timeItem.getPROPERTIESLIST());
					newItem.setTIMEITEMEND(timeItem.getTIMEITEMEND());
					newItem.setTIMEITEMSTART(timeItem.getTIMEITEMSTART());
					newItem.setTIMEUNIT(timeItem.getTIMEUNIT());
					newItem.setTIMEUOM(timeItem.getTIMEUOM());
					newTimeItemList.add(newItem);
					}	
			}
			timeItemList.addAll(newTimeItemList);
		}
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(timeCardList, destinationFile);
	}

}
