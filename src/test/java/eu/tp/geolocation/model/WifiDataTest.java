package eu.tp.geolocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.jupiter.api.Test;

class WifiDataTest {

	// Marshall to XML
	@Test
	void ObjectToXML() {
		// Test inputs
		WifiData wifiData_strengthOK = new WifiData("12:34:56:78:90:ab", "-54", "8");
		WifiData wifiData_WRONG_strength = new WifiData("69:00:23:77:14:23", "-51s", "7");

		// Expected results
		final String MARSHALED_RESULT_OK_STRENGTH = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><wifiAccessPoint><macAddress>12:34:56:78:90:ab</macAddress><signalStrength>-54</signalStrength><channel>8</channel></wifiAccessPoint>";
		final String MARSHALED_RESULT_WRONG_STRENGTH = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><wifiAccessPoint><macAddress>69:00:23:77:14:23</macAddress><channel>7</channel></wifiAccessPoint>";

		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(256)) {
			jaxbContext = JAXBContext.newInstance(WifiData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Set the Marshaller media type to JSON or XML
			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/xml");
			// Set it to true if you need to include the JSON root element in the JSON
			// output
			jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			// Set it to true if you need the JSON output to formatted
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

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
		wifiData[0] = new WifiData("12:34:56:78:ef", "-49", "2");
		wifiData[1] = new WifiData("69:00:23:77:ab", "-51s", "10");
		wifiData[2] = new WifiData("00:11:22:33:cd", "-134", "123");
		WifiDatas listWifi = new WifiDatas(Arrays.asList(wifiData));

		// Expected results
		final String MARSHALED_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><wifiAccessPoints><wifiAccessPoint><macAddress>12:34:56:78:ef</macAddress><signalStrength>-49</signalStrength><channel>2</channel></wifiAccessPoint><wifiAccessPoint><macAddress>69:00:23:77:ab</macAddress><channel>10</channel></wifiAccessPoint><wifiAccessPoint><macAddress>00:11:22:33:cd</macAddress><signalStrength>-134</signalStrength><channel>123</channel></wifiAccessPoint></wifiAccessPoints><considerIp>false</considerIp>";

		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(512)) {
			jaxbContext = JAXBContext.newInstance(listWifi.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

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

	// Marshall to JSON
	@Test
	void ObjectToJSON() {
		// Test inputs
		WifiData wifiData_strengthOK = new WifiData("12:34:56:78:77", "-51", "13");
		WifiData wifiData_WRONG_strength = new WifiData("69:00:23:77:ab:02", "-51s", "45");

		// Expected results
		final String MARSHALED_RESULT_OK_STRENGTH = "{\"wifiAccessPoint\":{\"macAddress\":\"12:34:56:78:77\",\"signalStrength\":-51,\"channel\":13}}";
		final String MARSHALED_RESULT_WRONG_STRENGTH = "{\"wifiAccessPoint\":{\"macAddress\":\"69:00:23:77:ab:02\",\"channel\":45}}";

		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(256)) {
			jaxbContext = JAXBContext.newInstance(WifiData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// ---
			// Set the Marshaller media type to JSON or XML
			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			// Set it to true if you need to include the JSON root element in the JSON
			// output
			jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			// Set it to true if you need the JSON output to formatted
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// ---

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
	void ListOfObjectsToJSON() {
		// Test inputs
		WifiData wifiData[] = new WifiData[3];
		wifiData[0] = new WifiData("12:34:56:78", "-49", "2");
		wifiData[1] = new WifiData("69:00:23:77", "-51s", "113");
		wifiData[2] = new WifiData("00:11:22:33", "-134", "36");
		WifiDatas listWifi = new WifiDatas(Arrays.asList(wifiData));

		// Expected results
		final String MARSHALED_RESULT = "{\"wifiAccessPoints\":[{\"macAddress\":\"12:34:56:78\",\"signalStrength\":-49,\"channel\":2},{\"macAddress\":\"69:00:23:77\",\"channel\":113},{\"macAddress\":\"00:11:22:33\",\"signalStrength\":-134,\"channel\":36}],\"considerIp\":\"false\"}";

		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(512)) {
			jaxbContext = JAXBContext.newInstance(listWifi.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Set the Marshaller media type to JSON or XML
			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			// Set it to true if you need to include the JSON root element in the JSON
			// output
			jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			// output pretty printed
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.setProperty(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
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
