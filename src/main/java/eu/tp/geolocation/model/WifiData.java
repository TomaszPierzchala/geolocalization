package eu.tp.geolocation.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WifiData {

	private String name, mac;
	private short strength;
	
	public WifiData() {
	}

	public WifiData(String name, String mac, String strength) {
		super();
		this.name = name;
		this.mac = mac;
		try {
		this.strength = Short.parseShort(strength);
		} catch (NumberFormatException nexp) {
			this.strength = -999;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public short getStrength() {
		return strength;
	}

	public void setStrength(short strength) {
		this.strength = strength;
	}
	

}
