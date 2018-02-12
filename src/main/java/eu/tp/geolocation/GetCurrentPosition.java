package eu.tp.geolocation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.glassfish.jersey.client.ClientConfig;

import eu.tp.geolocation.model.Position;
import eu.tp.geolocation.model.WifiData;
import eu.tp.geolocation.model.WifiDatas;

public class GetCurrentPosition {
	private static final String MAC_WIFI_SCANER = "airport";
	private static final String MAC_WIFI_SCANER_ARGS = "-s";
	private static final String MAC_WIFI_SCANER_PATH = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport";
	
	private static final String WIN_WIFI_SCANER = "netsh wlan show networks mode=bssid";

	private static String osName = System.getProperty("os.name");
	
	public static void main(String[] args) throws InterruptedException, IOException {

		Process process = null;
		process = findAndRunWifiScanner();
		
		byte[] readData = auxInStreamToByteTab(process.getInputStream());
		// auxiliary output
		System.out.println(pickUpWifiDataAtOsX(new ByteArrayInputStream(readData)));
		System.out.println(output(new ByteArrayInputStream(readData)));
		//
		List<WifiData> wifis = pickUpWifiData(new ByteArrayInputStream(readData));
		//wifis.sort(Comparator.comparing(WifiData::getStrength).reversed());
		
		Position pos = createRequestToGoogleMapsGeolocation(wifis	);
		System.out.println(pos);
	}

	private static Process findAndRunWifiScanner() throws InterruptedException, IOException {
		String [] wifiScaner = null;

		ProcessBuilder pb = new ProcessBuilder();
		Process process = null;

		if (osName.toLowerCase().contains("mac")) {
			wifiScaner = new String[2]; 
			wifiScaner[0] = findAirportPath(pb);
			wifiScaner[1] = MAC_WIFI_SCANER_ARGS;
		} else if (osName.toLowerCase().contains("win")) {
			wifiScaner = WIN_WIFI_SCANER.split("\\s");
		} else {
			throw new SystemNotSupportedException();
		}
		
		int counter = 0;

		do {
			// try several times, as happens airport has no output
			counter++;
			
			pb.command(wifiScaner);

			process = pb.start();
			process.waitFor();

		} while (process.getInputStream().read() == -1);
		System.out.println("The : " + Stream.of(wifiScaner).collect(Collectors.joining(" ")) );
		System.out.println("run " + counter + " times, before succeed.");
		return process;
	}

	private static String findAirportPath(ProcessBuilder pb) throws IOException, InterruptedException {
		// Mac only
		String wifiScaner;
		Process process;
		File macWifiScanerPath = new File(MAC_WIFI_SCANER_PATH);
		if (macWifiScanerPath.exists()) {
			wifiScaner = MAC_WIFI_SCANER_PATH;
		} else {
			pb.command("find", "/", "-name", "airport");
			process = pb.start();
			process.waitFor();//
			try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				wifiScaner = br.readLine();
			}

			if (!wifiScaner.substring(wifiScaner.lastIndexOf(File.separator) + 1).equals(MAC_WIFI_SCANER)) {
				throw new RuntimeException("Can NOT find \"" + MAC_WIFI_SCANER + "\"at your system.");
			}
		}
		return wifiScaner;
	}
	
	private static Position createRequestToGoogleMapsGeolocation(List<WifiData> wifiData) {
		WifiDatas wifis = new WifiDatas(wifiData);

		final String GOOGLE_API_KEY = System.getenv("GOOGLE_API_KEY");
		if (GOOGLE_API_KEY == null) {
			throw new RuntimeException("NOT defined the global variable GOOGLE_API_KEY");
		}

		ClientConfig config = new ClientConfig();
		config.property(MarshallerProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target("https://www.googleapis.com/geolocation/v1/geolocate?key=" + GOOGLE_API_KEY);

		Entity<WifiDatas> ent = Entity.entity(wifis, MediaType.APPLICATION_JSON);

		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(ent);
		System.out.println(
				"response Status : " + response.getStatus() + ",  " + response.getStatusInfo().getReasonPhrase());
		return response.readEntity(Position.class);
	}

	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	private static byte[] auxInStreamToByteTab(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[256];
		int bytesRead = 0;

		while ((bytesRead = input.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	public static List<WifiData> pickUpWifiDataAtOsX(InputStream inputStream) throws IOException {
		List<WifiData> wifis = new LinkedList<WifiData>();
	
		String pattern = "(.*)((\\p{XDigit}{2}:){5}\\p{XDigit}{2})\\s(-?\\d{1,})\\s+(\\d{1,})(\\s|,)(.*)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
	
		// Now create matcher object.
		Matcher m = null;
		
		try( BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)) ){
			String line = br.readLine(); // skip 1st line - headers
			while ((line = br.readLine()) != null) {
				m = r.matcher(line);
				if (m.find()) {
					// System.out.println(m.group(2) + " : " + m.group(4) + " : " + m.group(5));
					wifis.add(new WifiData(m.group(2), m.group(4), m.group(5)));
				}
			}
		}
		
		return wifis;  //.stream().filter(p -> p.getStrength() < -69 && p.getStrength() > -85).collect(Collectors.toList());// .subList(0,
																														// 6);
	}

	public static List<WifiData> pickUpWifiDataAtWindows(InputStream inputStream) throws IOException {
		// parsing output of "netsh wlan show networks mode=BSSID"
		List<WifiData> wifis = new LinkedList<WifiData>();
	
		String pattern = "((\\p{XDigit}{2}:){5}\\p{XDigit}{2}).*\\n.*\\s(\\d{1,2})\\%.*\\n.*\\n.*\\s:\\s(\\d{1,})";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
	
		// Now create matcher object.
		Matcher m = null;
		StringBuilder strb = new StringBuilder(1024);
		try( BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)) ){
			String line = br.readLine();
			strb.append(line); 
			strb.append("\n");
			while ((line = br.readLine()) != null) {
				strb.append(line); 
				strb.append("\n");
			}
			
			m = r.matcher(strb.toString());
			while(m.find()) {
				// System.out.println(m.group(1) + " : " + m.group(3) + " : " + m.group(4));
				wifis.add(new WifiData(m.group(1), m.group(3), m.group(4)));
			}
		}
		
		return wifis;
	}
	
	public static List<WifiData> pickUpWifiData(InputStream inputStream) throws IOException {
		if (osName.toLowerCase().contains("mac")) {
			return pickUpWifiDataAtOsX(inputStream);
		} else if (osName.toLowerCase().contains("win")) {
			return pickUpWifiDataAtWindows(inputStream);
		} else {
			throw new SystemNotSupportedException();
		}
	}
	
	private static class SystemNotSupportedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		SystemNotSupportedException(){
			super("\nSystem " + osName + " not supported yet.");
		}
	}
}