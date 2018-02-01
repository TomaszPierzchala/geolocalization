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
			this.strength = Short.parseShort(strength);
		} catch (NumberFormatException nexp) {
			this.strength = null;
		}
		try {
			this.channel = Short.parseShort(channel);
		} catch (NumberFormatException nexp) {
			this.channel = null;
		}
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
