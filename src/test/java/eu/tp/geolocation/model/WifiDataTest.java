package eu.tp.geolocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class WifiDataTest {

	@Disabled
	@Test
	void makeBlocking() {
		byte [] bigArr2 = new byte[1024*1024*1024];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			bout.write(bigArr2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ala");
		
	}
	
	@Test
	void ObjectToXML() {
		// Test inputs
		WifiData wifiData_strengthOK = new WifiData("wifiName", "12:34:56:78", "-51");
		WifiData wifiData_WRONG_strength = new WifiData("wifiName2", "69:00:23:77", "-51s");

		// Expected results
		final String MARSHALED_RESULT_OK_STRENGTH = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><wifiData><mac>12:34:56:78</mac><name>wifiName</name><strength>-51</strength></wifiData>";
		final String MARSHALED_RESULT_WRONG_STRENGTH = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><wifiData><mac>69:00:23:77</mac><name>wifiName2</name><strength>-999</strength></wifiData>";
		
		JAXBContext jaxbContext;
		
		try(ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(256)) {
			jaxbContext = JAXBContext.newInstance(WifiData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			//---
			// Set the Marshaller media type to JSON or XML
			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			// Set it to true if you need to include the JSON root element in the JSON output
			jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			// Set it to true if you need the JSON output to formatted
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//---

			// output pretty printed
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);

			// jaxbMarshaller.marshal(wifiData, file);

			jaxbMarshaller.marshal(wifiData_strengthOK, bArrOutStr);
			String outputStr = new String(bArrOutStr.toByteArray());
			
			System.out.println(outputStr);

			assertEquals(MARSHALED_RESULT_OK_STRENGTH, outputStr);
			
			bArrOutStr.reset();
			jaxbMarshaller.marshal(wifiData_WRONG_strength, bArrOutStr);
			outputStr = new String(bArrOutStr.toByteArray());
			
			System.out.println(outputStr);
			assertEquals(MARSHALED_RESULT_WRONG_STRENGTH, outputStr);
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void ListOfObjectsToXML() {
		// Test inputs
		WifiData wifiData[] = new WifiData[3];
		wifiData[0] = new WifiData("wifiName", "12:34:56:78", "-51");
		wifiData[1] = new WifiData("wifiName2", "69:00:23:77", "-51s");
		wifiData[2] = new WifiData("wifiName3", "00:11:22:33", "-134");
		WifiDatas listWifi = new WifiDatas(Arrays.asList(wifiData));

		// Expected results
		final String MARSHALED_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><wifiData><mac>12:34:56:78</mac><name>wifiName</name><strength>-51</strength></wifiData>";
		final String MARSHALED_RESULT_2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><wifiData><mac>69:00:23:77</mac><name>wifiName2</name><strength>-999</strength></wifiData>";
		
		JAXBContext jaxbContext;
		
		try(ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(512)) {
			jaxbContext = JAXBContext.newInstance(listWifi.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			 jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// jaxbMarshaller.marshal(wifiData, file);

			jaxbMarshaller.marshal(listWifi, bArrOutStr);
			String outputStr = new String(bArrOutStr.toByteArray());
			
			System.out.println(outputStr);

			assertEquals(MARSHALED_RESULT, outputStr);
			
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
