package wifi;

import com.google.android.maps.GeoPoint;

public class Router {

	String _macAddress = "";
	double _longitude = 0;
	double _latitude = 0;
	private int frequency;
	private int level;
	private String ssid;
	
	public Router(String macAddress, double latitude, double longitude) {
		super();
		this._macAddress = macAddress;
		this._latitude = latitude;
		this._longitude = longitude;
	}

	public String get_macAddress() {
		return _macAddress;
	}

	public void set_macAddress(String _macAddress) {
		this._macAddress = _macAddress;
	}

	public double get_longitude() {
		return _longitude;
	}

	public void set_longitude(long _longitude) {
		this._longitude = _longitude;
	}

	public double get_latitude() {
		return _latitude;
	}

	public void set_latitude(long _latitude) {
		this._latitude = _latitude;
	}
	
	public GeoPoint getGeoPoint()
	{
		double GEO_CONVERSION = 1E6;
		GeoPoint gpoint = new GeoPoint((int) (_latitude * GEO_CONVERSION), (int) (_longitude * GEO_CONVERSION));
		return gpoint;
	}

	public int getFrequency()
	{
		return frequency;
	}

	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String getSsid()
	{
		return ssid;
	}

	public void setSsid(String ssid)
	{
		this.ssid = ssid;
	}
	
	
	
	
}
