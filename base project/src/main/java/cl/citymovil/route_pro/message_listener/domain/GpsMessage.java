package cl.citymovil.route_pro.message_listener.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import cl.citymovil.route_pro.solver.util.GpsMessageDatetimeXmlAdapter;

/*
 <row>
 <ppu>BHXR-60</ppu>
 <datetime>23-09-2015 11:49:41</datetime>
 <latitud>-36.69401932</latitud>
 <longitud>-73.10673523</longitud>
 <speed>17</speed>
 <heading>25</heading>
 <type>14</type>
 </row>
 */

@XmlRootElement(name = "row")
public class GpsMessage {

	private String externalId;
	private double latitude;
	private double longitude;
	private double speed;
	private double heading;
	private int type;
	private Date datetime;

	@XmlElement(name = "ppu")
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@XmlElement(name="latitud")
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@XmlElement(name="longitud")
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@XmlElement
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@XmlElement
	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	@XmlElement
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlElement
	@XmlJavaTypeAdapter(GpsMessageDatetimeXmlAdapter.class)
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

}
