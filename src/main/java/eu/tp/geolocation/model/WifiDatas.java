package eu.tp.geolocation.model;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WifiDatas {

	
	private List<WifiData> wifis = new LinkedList<WifiData>();
	
	public WifiDatas() {
	}

	public WifiDatas(List<WifiData> wifis) {
		super();
		this.wifis = wifis;
	}

    @XmlElement(name="wifiData")
	public List<WifiData> getWifis() {
		return wifis;
	}

	public void setWifis(List<WifiData> wifis) {
		this.wifis = wifis;
	}

}
