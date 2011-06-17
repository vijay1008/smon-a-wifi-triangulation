package wifi.scanner;

public class Wrilateration
{
	public static double getLongitude(double long1, double rssi1, double long2, double rssi2, double long3, double rssi3)
	{
		double dist1, dist2, dist3;
		double result;

		double maxRssi = rssi1 + rssi2 + rssi3;
		dist1 = rssi1 / maxRssi;
		dist2 = rssi2 / maxRssi;
		dist3 = rssi3 / maxRssi;
		
		result = (dist1 * long1) + (dist2 * long2) + (dist3 * long3);

		return result;
	}

	public static double getLatitude(double lat1, double rssi1, double lat2, double rssi2, double lat3, double rssi3)
	{
		double dist1, dist2, dist3;
		double result;

		double maxRssi = rssi1 + rssi2 + rssi3;
		dist1 = rssi1 / maxRssi;
		dist2 = rssi2 / maxRssi;
		dist3 = rssi3 / maxRssi;
		
		result = (dist1 * lat1) + (dist2 * lat2) + (dist3 * lat3);

		return result;
	}
}
