package eu.tp.geolocation.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wifiAccessPoint")
public class WifiData {
	@XmlElement(name="macAddress")
	private String mac;
	@XmlElement(name="signalStrength")
	private Short strength;
	@XmlElement(name="channel")
	private Short channel = null;
	
	public WifiData() {
	}

	public WifiData(String mac, String strength, String channel) {
		super();
		this.mac = mac;
		try {
			Short toShort = Short.parseShort(strength);
			if( toShort >=0 ) {
				toShort = wlanSignalQualityTodBm(toShort);
			}
			this.strength = toShort;
			
		} catch (NumberFormatException nexp) {
			this.strength = null;
		}
		try {
			this.channel = Short.parseShort(channel);
		} catch (NumberFormatException nexp) {
			this.channel = null;
		}
	}

	private Short wlanSignalQualityTodBm(Short quality) {
		// converts wlanSignalQuality in % into dBm
		Short dBm = (short) ( (quality / 2.) - 100 );
		return dBm;
	}
	public Short getStrength() {
		return strength;
	}

	public Short getChannel() {
		return channel;
	}

	@Override
	public String toString() {
		return "[mac=" + mac + ", strength=" + strength + ", channel=" + channel + "]";
	}

}
