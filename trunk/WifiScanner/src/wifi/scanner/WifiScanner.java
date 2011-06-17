package wifi.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.maps.MapView;

import wifi.Router;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WifiScanner extends Activity {

    // private MapView m_vwMap;
    WifiManager wifim;
    LinearLayout ll;
    ScrollView scroll;
    TextView tv;
    LinearLayout m_vwWifiLinearLayout;
    Boolean color;
    Timer myTimer;
    String[] macAdresses = { "00:12:44:ba:27:10", "00:3a:98:72:ba:a0",
	    "00:17:Of:35:10:30", "00:12:44:ba:78:10", "00:12:44:ba:70:40",
	    "00:12:44:ba:7b:30", "00:12:44:ba:78:10", "00:3a:98:72:b8:50",
	    "00:3a:98:62:b5:00", "00:3a:98:62:b7:00", "00:12:44:ba:77:e0",
	    "00:24:97:f2:84:c0", "00:12:44:ba:18:60", "00:3a:98:62:b3:b0",
	    "00:12:44:ba:3a:D9", "00:12:44:ba:3a:b0", "00:24:97:f2:84:00",
	    "00:24:97:f2:83:80", "00:24:97:f2:84:40", "00:24:97:f3:0d:70" };

    ArrayList<Router> _routers = new ArrayList<Router>();

    Trilateration trilateration;

    private Handler handlerTimer = new Handler();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	// m_vwMap = (MapView) findViewById(R.id.m_vwMap);
	// m_vwMap.setBuiltInZoomControls(true);

	makeButtonListener();
	scan();
	addRouters();

	handlerTimer.removeCallbacks(taskUpdateWifis);
	handlerTimer.postDelayed(taskUpdateWifis, 100);
    }

    private void addRouters() {
	_routers.add(new Router("00:12:44:ba:78:10", 51.451900, 5.480755));
	_routers.add(new Router("00:12:44:ba:27:10", 51.452012, 5.480837));
	_routers.add(new Router("00:3a:98:62:b5:00", 51.451857, 5.480882));
	_routers.add(new Router("00:12:44:ba:70:40", 51.451925, 5.480987));
	_routers.add(new Router("00:17:0f:35:10:30", 51.451988, 5.481058));
	_routers.add(new Router("00:3a:98:72:b8:50", 51.451915, 5.481273));
	_routers.add(new Router("00:3a:98:72:ba:a0", 51.452032, 5.481283));
	_routers.add(new Router("00:12:44:ba:7b:30", 51.451940, 5.481375));
	_routers.add(new Router("00:3a:98:62:b7:00", 51.451855, 5.481529));
	_routers.add(new Router("00:12:44:ba:77:e0", 51.451769, 5.481456));
	_routers.add(new Router("00:24:97:f2:83:80", 51.451563, 5.481635));
	_routers.add(new Router("00:24:97:f2:84:00", 51.451690, 5.481781));
	_routers.add(new Router("00:24:97:f2:84:40", 51.451587, 5.481856));
	_routers.add(new Router("00:24:97:f3:0d:70", 51.451574, 5.482076));
	_routers.add(new Router("00:24:97:f2:84:c0", 51.451814, 5.481907));
	_routers.add(new Router("00:12:44:ba:18:60", 51.451804, 5.482146));
	_routers.add(new Router("00:3a:98:62:b3:b8", 51.451826, 5.482443));
	_routers.add(new Router("00:12:44:ba:3a:b0", 51.451725, 5.482439));
    }

    private Runnable taskUpdateWifis = new Runnable() {
	public void run() {
	    scan();

	    // Do this again in 30 seconds
	    handlerTimer.postDelayed(this, 1000);
	}
    };

    private void makeButtonListener() {
	findViewById(R.id.buttonScan).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		scan();
	    }
	});
    }

    public void scan() {
	color = true;

	m_vwWifiLinearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
	m_vwWifiLinearLayout.setOrientation(LinearLayout.VERTICAL);

	wifim = (WifiManager) this.getSystemService(WIFI_SERVICE);

	if (wifim.isWifiEnabled()) {
	    wifim.startScan();

	    List<ScanResult> listSort = wifim.getScanResults();
	    List<ScanResult> listSortCheck = new ArrayList<ScanResult>();

	    while (true) {

		listSortCheck.clear();
		listSortCheck.addAll(listSort);

		for (int i = 0; i < listSort.size(); i++) {
		    if (i + 1 < listSort.size()) {
			if (listSort.get(i + 1).level > listSort.get(i).level) {
			    ScanResult sr = listSort.get(i);
			    listSort.set(i, listSort.get(i + 1));
			    listSort.set(i + 1, sr);
			}
		    }
		}

		if (listSort.equals(listSortCheck)) {
		    break;
		}
	    }

	    ArrayList<Router> _routers2 = new ArrayList<Router>();
	    for (ScanResult s : listSort) {
		for (int i = 0; i < _routers.size(); i++) {
		    if (s.BSSID.equals(_routers.get(i).get_macAddress())) {
			_routers.get(i).setFrequency(s.frequency);
			_routers.get(i).setLevel(s.level);
			_routers.get(i).setSsid(s.SSID);

			_routers2.add(_routers.get(i));
		    }
		}
	    }

	    fillLayout(_routers2);
	    testGeo(_routers2);
	}
    }

    public void testGeo(ArrayList<Router> routers) {
	ArrayList<Router> test = new ArrayList<Router>();
	test.clear();

	for (int i = 0; i < routers.size(); i++) {
	    if (test.size() < 3) {
		test.add(routers.get(i));
	    }
	}

	if (!(test.size() < 3)) {
	    double[] geo = Trilateration.MyTrilateration(test.get(0)
		    .get_latitude(), test.get(0).get_longitude(), Trilateration
		    .calcDistance(test.get(0).getLevel()), test.get(1)
		    .get_latitude(), test.get(1).get_longitude(), Trilateration
		    .calcDistance(test.get(1).getLevel()), test.get(2)
		    .get_latitude(), test.get(2).get_longitude(), Trilateration
		    .calcDistance(test.get(2).getLevel()));

	    tv = new TextView(this);
	    tv.setText("Location:\n Lat:\t " + geo[0] + "\n Long:\t " + geo[1]
		    + "\n");
	    tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		    LayoutParams.WRAP_CONTENT));
	    m_vwWifiLinearLayout.addView(tv, 0);
	} else {
	    tv = new TextView(this);
	    tv.setText("No Location: ");
	    tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		    LayoutParams.WRAP_CONTENT));
	    m_vwWifiLinearLayout.addView(tv, 0);
	}
	// Toast toast = Toast.makeText(getApplicationContext(), ""+ geo[0] +
	// " - " + geo[1], Toast.LENGTH_SHORT);
	// toast.show();

    }

    public void fillLayout(ArrayList<Router> routers) {
	m_vwWifiLinearLayout.removeAllViewsInLayout();
	for (Router r : routers) {
	    tv = new TextView(this);
	    tv.setText("WifiInfo:");
	    tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		    LayoutParams.WRAP_CONTENT));
	    m_vwWifiLinearLayout.addView(tv);

	    tv = new TextView(this);
	    tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		    LayoutParams.WRAP_CONTENT));

	    double distance = trilateration.calcDistance(convertDbmToRSSI(r
		    .getLevel()));

	    // tv.setText(" SSID: \t\t\t\t" + "" + "\n Frequency: \t\t " +
	    // r.frequency + "\n Signal(dBm): \t" + r.level + "\n Mac: \t\t\t\t"
	    // + r.get_macAddress());
	    // tv.setText(s.level + " - " + s.BSSID);

	    tv.setText(" SSID: \t\t\t\t" + r.getSsid() + "\n Frequency: \t\t "
		    + r.getFrequency() + "\n Signal(dBm): \t" + r.getLevel()
		    + "\n Mac: \t\t\t\t" + r.get_macAddress() + "\nDistance: "
		    + distance);
	    // tv.setText(s.level + " - " + s.BSSID);

	    if (color) {
		tv.setBackgroundColor(Color.DKGRAY);
		color = false;
	    } else {
		tv.setBackgroundColor(Color.GRAY);
		color = true;
	    }
	    m_vwWifiLinearLayout.addView(tv);

	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu, menu);

	menu.findItem(R.id.SCAN_MENU_ITEM).setOnMenuItemClickListener(
		new OnMenuItemClickListener() {
		    @Override
		    public boolean onMenuItemClick(MenuItem item) {
			scan();
			return false;
		    }
		});
	return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	return true;
    }

    private int convertDbmToRSSI(int value) {
	int returnValue = -10;
	switch (value) {
	case -113:
	    returnValue = 0;
	    break;
	case -112:
	    returnValue = 1;
	    break;
	case -111:
	    returnValue = 2;
	    break;
	case -110:
	    returnValue = 3;
	    break;
	case -109:
	    returnValue = 4;
	    break;
	case -108:
	    returnValue = 5;
	    break;
	case -107:
	    returnValue = 6;
	    break;
	case -106:
	    returnValue = 7;
	    break;
	case -105:
	    returnValue = 8;
	    break;
	case -104:
	    returnValue = 9;
	    break;
	case -103:
	    returnValue = 10;
	    break;
	case -102:
	    returnValue = 11;
	    break;
	case -101:
	    returnValue = 12;
	    break;
	case -100:
	    returnValue = 13;
	    break;
	case -99:
	    returnValue = 14;
	    break;
	case -98:
	    returnValue = 15;
	    break;
	case -97:
	    returnValue = 16;
	    break;
	case -96:
	    returnValue = 17;
	    break;
	case -95:
	    returnValue = 18;
	    break;
	case -94:
	    returnValue = 19;
	    break;
	case -93:
	    returnValue = 20;
	    break;
	case -92:
	    returnValue = 21;
	    break;
	case -91:
	    returnValue = 22;
	    break;
	case -90:
	    returnValue = 23;
	    break;
	case -89:
	    returnValue = 24;
	    break;
	case -88:
	    returnValue = 25;
	    break;
	case -87:
	    returnValue = 26;
	    break;
	case -86:
	    returnValue = 27;
	    break;
	case -85:
	    returnValue = 28;
	    break;
	case -84:
	    returnValue = 29;
	    break;
	case -83:
	    returnValue = 30;
	    break;
	case -82:
	    returnValue = 31;
	    break;
	case -81:
	    returnValue = 32;
	    break;
	case -80:
	    returnValue = 33;
	    break;
	case -79:
	    returnValue = 34;
	    break;
	case -78:
	    returnValue = 35;
	    break;
	case -77:
	    returnValue = 36;
	    break;
	case -75:
	    returnValue = 37;
	    break;
	case -74:
	    returnValue = 38;
	    break;
	case -73:
	    returnValue = 39;
	    break;
	case -72:
	    returnValue = 40;
	    break;
	case -70:
	    returnValue = 41;
	    break;
	case -69:
	    returnValue = 42;
	    break;
	case -68:
	    returnValue = 43;
	    break;
	case -67:
	    returnValue = 44;
	    break;
	case -65:
	    returnValue = 45;
	    break;
	case -64:
	    returnValue = 46;
	    break;
	case -63:
	    returnValue = 47;
	    break;
	case -62:
	    returnValue = 48;
	    break;
	case -60:
	    returnValue = 49;
	    break;
	case -59:
	    returnValue = 50;
	    break;
	case -58:
	    returnValue = 51;
	    break;
	case -56:
	    returnValue = 52;
	    break;
	case -55:
	    returnValue = 53;
	    break;
	case -53:
	    returnValue = 54;
	    break;
	case -52:
	    returnValue = 55;
	    break;
	case -50:
	    returnValue = 56;
	    break;
	case -49:
	    returnValue = 58;
	    break;
	case -48:
	    returnValue = 59;
	    break;
	case -47:
	    returnValue = 60;
	    break;
	case -46:
	    returnValue = 61;
	    break;
	case -45:
	    returnValue = 62;
	    break;
	case -44:
	    returnValue = 64;
	    break;
	case -43:
	    returnValue = 66;
	    break;
	case -42:
	    returnValue = 67;
	    break;
	case -41:
	    returnValue = 68;
	    break;
	case -40:
	    returnValue = 69;
	    break;
	case -39:
	    returnValue = 70;
	    break;
	case -38:
	    returnValue = 71;
	    break;
	case -37:
	    returnValue = 72;
	    break;
	case -35:
	    returnValue = 73;
	    break;
	case -34:
	    returnValue = 74;
	    break;
	case -33:
	    returnValue = 75;
	    break;
	case -32:
	    returnValue = 76;
	    break;
	case -30:
	    returnValue = 77;
	    break;
	case -29:
	    returnValue = 78;
	    break;
	case -28:
	    returnValue = 79;
	    break;
	case -27:
	    returnValue = 80;
	    break;
	case -25:
	    returnValue = 81;
	    break;
	case -24:
	    returnValue = 82;
	    break;
	case -23:
	    returnValue = 83;
	    break;
	case -22:
	    returnValue = 84;
	    break;
	case -20:
	    returnValue = 85;
	    break;
	case -19:
	    returnValue = 86;
	    break;
	case -18:
	    returnValue = 87;
	    break;
	case -17:
	    returnValue = 88;
	    break;
	case -16:
	    returnValue = 89;
	    break;
	case -15:
	    returnValue = 90;
	    break;
	case -14:
	    returnValue = 91;
	    break;
	case -13:
	    returnValue = 92;
	    break;
	case -12:
	    returnValue = 93;
	    break;
	case -10:
	    returnValue = 94;
	    break;
	default:
	    // returnValue = 100;
	    break;
	}
	return returnValue;
    }

}