package eu.tp.geolocation.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {
	@XmlElement(name="lat")
	private Double latitude;
	@XmlElement(name="lng")
	private Double longitude;
	
	public Location() {
	}

	public Location(String lat, String lng) {
		super();
		try {
			this.latitude = Double.parseDouble(lat);
		} catch (NumberFormatException nexp) {
			this.latitude = null;
		}
		try {
			this.longitude = Double.parseDouble(lng);
		} catch (NumberFormatException nexp) {
			this.longitude = null;
		}
	}

	@Override
	public String toString() {
		return "( " + latitude + ", " + longitude + " )";
	}

}
