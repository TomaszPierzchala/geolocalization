package eu.tp.geolocation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import eu.tp.geolocation.model.WifiData;

class GetCurrentPositionTest {

	@Test
	void testPickUpWifisScannerResultsAtMac() throws IOException {
		String mockWifiScaner = "SSID BSSID             RSSI CHANNEL HT CC SECURITY (auth/unicast/group)\n" + 
				"                     UPC Wi-Free 46:32:c8:2f:b5:b8 -93  13 ,1       Y  -- WPA2(802.1x/AES,TKIP/TKIP) \n" + 
				"                          eloelo ac:22:05:83:cb:e3 -76  112     Y  -- WPA(PSK/TKIP/TKIP) WPA2(PSK/AES/TKIP) \n" + 
				"                    UPC505035510 fc:6f:b7:3e:86:a7 -89  52, 9      Y  DE WPA2(PSK/AES/AES) ";
		InputStream stream = new ByteArrayInputStream(mockWifiScaner.getBytes(StandardCharsets.UTF_8.name()));
		List<WifiData> resultList = GetCurrentPosition.pickUpWifiDataAtOsX(stream);
		stream.close();
		
		List<WifiData> expectedList = new LinkedList<WifiData>();
		expectedList.add(new WifiData("46:32:c8:2f:b5:b8", "-93",  "13"));
		expectedList.add(new WifiData("ac:22:05:83:cb:e3", "-76",  "112"));
		expectedList.add(new WifiData("fc:6f:b7:3e:86:a7", "-89",  "52"));
		
		assertEquals(expectedList.toString(), resultList.toString());
	}

	@Test
	void testPickUpWifisScannerResultsAtWindows() throws IOException {
		
		Path file = FileSystems.getDefault().getPath( "src/test/java", "eu/tp/geolocation", "natsh_wlan_show_networks_mode_bssid_mock.txt");
//		System.out.println(file.toAbsolutePath());
		InputStream stream = new ByteArrayInputStream(Files.readAllBytes(file));
		List<WifiData> resultList = GetCurrentPosition.pickUpWifiDataAtWindows(stream);
		stream.close();
		
		List<WifiData> expectedList = new LinkedList<WifiData>();
		expectedList.add(new WifiData("ac:d2:13:d0:12:50", "72",  "4"));
		expectedList.add(new WifiData("fa:60:37:34:a6:aa", "44",  "1"));
		expectedList.add(new WifiData("aa:21:00:01:02:1d", "40",  "11"));
		expectedList.add(new WifiData("ee:bb:cc:82:cc:12", "49",  "13"));
		expectedList.add(new WifiData("a1:20:c8:d0:11:ab", "72",  "4"));
		expectedList.add(new WifiData("a2:67:13:79:8b:da", "28",  "11"));
		expectedList.add(new WifiData("30:72:ac:6c:01:a3", "14",  "145"));
		expectedList.add(new WifiData("44:aa:f5:aa:11:b3", "16",  "1"));
		expectedList.add(new WifiData("ac:22:05:83:cc:10", "64",  "11"));
		expectedList.add(new WifiData("a1:27:d7:45:df:89", "62",  "11"));
		expectedList.add(new WifiData("ae:22:15:d0:10:57", "72",  "7"));
		expectedList.add(new WifiData("de:cb:0a:fe:12:23", "90",  "47"));
		
		assertEquals(expectedList.toString(), resultList.toString());
	}
}
