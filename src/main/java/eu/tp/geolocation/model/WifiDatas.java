package eu.tp.geolocation.model;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class WifiDatas {
	private final String FALSE = "false";
		
	@XmlElementWrapper(name="wifiAccessPoints")
    @XmlElement(name="wifiAccessPoint")
	private List<WifiData> wifis = new LinkedList<WifiData>();
	
	public WifiDatas() {
	}

	public WifiDatas(List<WifiData> wifis) {
		super();
		this.wifis = wifis;
	}

	@XmlElement(name="considerIp")
	public String getConsiderIp() {
		return FALSE;
	}

	public void setConsiderIp(String consider) {
		// Always "false" - no field provided
	}

	public List<WifiData> getWifis() {
		return wifis;
	}

	public void setWifis(List<WifiData> wifis) {
		this.wifis = wifis;
	}

}
