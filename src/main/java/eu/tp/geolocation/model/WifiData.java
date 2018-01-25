package eu.tp.geolocation.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WifiData {

	private String name, mac;
	private short strength;
	
	public WifiData() {
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
