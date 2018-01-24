package eu.tp.geolocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class RunExternal {
	private static final String MAC_WIFI_SCANER = "airport";
	private static final String MAC_WIFI_SCANER_ARGS = "-s";
	private static final String MAC_WIFI_SCANER_PATH = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport";

	public static void main(String[] args) throws InterruptedException, IOException {
		String osName = System.getProperty("os.name");

		if (!osName.toLowerCase().contains("mac")) {
			throw new RuntimeException(
					"\nMAC OS is ONLY supportet system by now.\n" + "Want to have it at Windows or Linux,"
							+ "\nfind a system software scanning surounding wifi and replace \"airport\".");
		}

		String wifiScaner = null, wifiScanerArgs = MAC_WIFI_SCANER_ARGS;

		ProcessBuilder pb = new ProcessBuilder();

		File macWifiScanerPath = new File(MAC_WIFI_SCANER_PATH);
		if (macWifiScanerPath.exists()) {
			wifiScaner = MAC_WIFI_SCANER_PATH;
		} else {
			pb.command("find", "/", "-name", "airport");
			Process process = pb.start();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				wifiScaner = br.readLine();
			}

			if (!wifiScaner.substring(wifiScaner.lastIndexOf(File.separator) + 1).equals(MAC_WIFI_SCANER)) {
				throw new RuntimeException("Can NOT find \"" + MAC_WIFI_SCANER + "\"at your system.");
			}
		}

		int counter = 0;
		Process process = null;
		do {
			// try several times, as happens airport has no output
			counter++;
			pb.command(wifiScaner, wifiScanerArgs);

			System.out.println("run : " + wifiScaner + " " + wifiScanerArgs);

			Map<String, String> env = pb.environment();
			String content = env.entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
					.collect(Collectors.joining(", "));
			System.out.println(content);
			process = pb.start();
			process.waitFor();

		} while (process.getInputStream().read() == -1);
		System.out.println("Run " + counter + " times.");

		System.out.println("Output:\n" + output(process.getInputStream()));

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
}