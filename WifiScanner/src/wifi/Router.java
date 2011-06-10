package wifi;

public class Router {

	String _macAddress = "";
	long _longitude = 0;
	long _latitude = 0;
	
	public Router(String _macAddress, long _longitude, long _latitude) {
		super();
		this._macAddress = _macAddress;
		this._longitude = _longitude;
		this._latitude = _latitude;
	}

	public String get_macAddress() {
		return _macAddress;
	}

	public void set_macAddress(String _macAddress) {
		this._macAddress = _macAddress;
	}

	public long get_longitude() {
		return _longitude;
	}

	public void set_longitude(long _longitude) {
		this._longitude = _longitude;
	}

	public long get_latitude() {
		return _latitude;
	}

	public void set_latitude(long _latitude) {
		this._latitude = _latitude;
	}
	
	
	
	
}
