package wifi;

public class Router {

	String _macAddress = "";
	double _longitude = 0;
	double _latitude = 0;
	
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
	
	
	
	
}
