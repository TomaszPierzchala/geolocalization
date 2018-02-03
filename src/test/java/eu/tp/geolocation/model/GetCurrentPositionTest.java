package eu.tp.geolocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import eu.tp.geolocation.GetCurrentPosition;

class GetCurrentPositionTest {

	@Test
	void testReadWifisScannerResultsAtMac() throws IOException {
		String mockWifiScaner = "SSID BSSID             RSSI CHANNEL HT CC SECURITY (auth/unicast/group)\n" + 
				"                     UPC Wi-Free 46:32:c8:2f:b5:b8 -93  13 ,1       Y  -- WPA2(802.1x/AES,TKIP/TKIP) \n" + 
				"                          eloelo ac:22:05:83:cb:e3 -76  112     Y  -- WPA(PSK/TKIP/TKIP) WPA2(PSK/AES/TKIP) \n" + 
				"                    UPC505035510 fc:6f:b7:3e:86:a7 -89  52, 9      Y  DE WPA2(PSK/AES/AES) ";
		InputStream stream = new ByteArrayInputStream(mockWifiScaner.getBytes(StandardCharsets.UTF_8.name()));
		List<WifiData> resultList = GetCurrentPosition.pickUpWifiDataAtMAC(stream);
		
		List<WifiData> expectedList = new LinkedList<WifiData>();
		expectedList.add(new WifiData("46:32:c8:2f:b5:b8", "-93",  "13"));
		expectedList.add(new WifiData("ac:22:05:83:cb:e3", "-76",  "112"));
		expectedList.add(new WifiData("fc:6f:b7:3e:86:a7", "-89",  "52"));
		
		assertEquals(expectedList.toString(), resultList.toString());
	}

}
