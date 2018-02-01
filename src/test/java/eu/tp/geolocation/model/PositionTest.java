package eu.tp.geolocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.jupiter.api.Test;

class PositionTest {

	// Marshall to XML
	@Test
	void ObjectToXML() {
		// Test inputs
		Position pos1 = new Position("34.45679019", "-12.987652113", "99999.99");
		Position pos2 = new Position("123.4567890123456", "-9.08765432109876", "999.9999");
		Position pos3 = new Position("33.44.4", "-9.0876543210987e", "123.456");

		// Expected results
		final String MARSHALED_RESULT_POS1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><location><lat>34.45679019</lat><lng>-12.987652113</lng></location><accuracy>99999.99</accuracy>";
		final String MARSHALED_RESULT_POS2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><location><lat>123.4567890123456</lat><lng>-9.08765432109876</lng></location><accuracy>999.9999</accuracy>";
		final String MARSHALED_RESULT_POS3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><location/><accuracy>123.456</accuracy>";
		
		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(256)) {
			jaxbContext = JAXBContext.newInstance(Position.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Set the Marshaller media type to JSON or XML
			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/xml");
			// Set it to true if you need to include the JSON root element in the JSON
			// output
			jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			// Set it to true if you need the JSON output to formatted
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// ----------------- pos1 --------------------------------
			jaxbMarshaller.marshal(pos1, bArrOutStr);
			String outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);

			assertEquals(MARSHALED_RESULT_POS1, outputStr);
			// ----------------- pos2 --------------------------------
			bArrOutStr.reset();
			jaxbMarshaller.marshal(pos2, bArrOutStr);
			outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);
			assertEquals(MARSHALED_RESULT_POS2, outputStr);
			
			// ----------------- pos3 --------------------------------
			bArrOutStr.reset();
			jaxbMarshaller.marshal(pos3, bArrOutStr);
			outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);
			assertEquals(MARSHALED_RESULT_POS3, outputStr);
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Marshall to JSON
	@Test
	void ObjectToJSON() {
		// Test inputs
		Position pos1 = new Position("34.45679019", "-12.987652113", "99999.99");
		Position pos2 = new Position("123.4567890123456", "-9.08765432109876", "999.9999");
		Position pos3 = new Position("33.44.4", "-9.0876543210987e", "123.456");

		// Expected results
		final String MARSHALED_RESULT_POS1 = "{\"location\":{\"lat\":34.45679019,\"lng\":-12.987652113},\"accuracy\":99999.99}";
		final String MARSHALED_RESULT_POS2 = "{\"location\":{\"lat\":123.4567890123456,\"lng\":-9.08765432109876},\"accuracy\":999.9999}";
		final String MARSHALED_RESULT_POS3 = "{\"location\":{},\"accuracy\":123.456}";
		
		JAXBContext jaxbContext;

		try (ByteArrayOutputStream bArrOutStr = new ByteArrayOutputStream(256)) {
			jaxbContext = JAXBContext.newInstance(Position.class);
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


			// ----------------- pos1 --------------------------------
			jaxbMarshaller.marshal(pos1, bArrOutStr);
			String outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);

			assertEquals(MARSHALED_RESULT_POS1, outputStr);

			// ----------------- pos2 --------------------------------
			bArrOutStr.reset();
			jaxbMarshaller.marshal(pos2, bArrOutStr);
			outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);
			assertEquals(MARSHALED_RESULT_POS2, outputStr);
			
			// ----------------- pos3 --------------------------------
			bArrOutStr.reset();
			jaxbMarshaller.marshal(pos3, bArrOutStr);
			outputStr = new String(bArrOutStr.toByteArray());

			System.out.println(outputStr);
			assertEquals(MARSHALED_RESULT_POS3, outputStr);

		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
