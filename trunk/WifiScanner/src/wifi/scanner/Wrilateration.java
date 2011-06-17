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
	
	
	public static double[] getGeo(double Lat1, double Long1,
	            double rssi1, double Lat2, double Long2, double rssi2,
	            double Lat3, double Long3, double rssi3)
	{
	    
	    double[] result = new double[2];
	    result[0]= getLatitude(Lat1, rssi1, Lat2, rssi2, Lat3, rssi3);
	    result[1] = getLongitude(Long1, rssi1, Long2, rssi2, Long3, rssi3);
	    return result;
	    
	}
}
