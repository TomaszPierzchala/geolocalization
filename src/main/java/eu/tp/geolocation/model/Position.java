package eu.tp.geolocation.model;
import javax.xml.bind.annotation.XmlElement;

public class Position {
	@XmlElement(name="location")
	private Location location;
	@XmlElement(name="accuracy")
	private Float accuracy;
	
	public Position() {
	}

	public Position(Location loc, String acc) {
		super();
		this.location = loc;
		try {
			this.accuracy = Float.parseFloat(acc);
		} catch (NumberFormatException nexp) {
			this.accuracy = null;
		}
	}

	public Position(String lat, String lng, String acc) {
		this(new Location(lat, lng), acc);
	}

	@Override
	public String toString() {
		return "Position :  lat=" + location + "=lng  +/- " + accuracy + "m]";
	}
}
