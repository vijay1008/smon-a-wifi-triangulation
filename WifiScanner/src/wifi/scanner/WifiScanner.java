package wifi.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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


public class WifiScanner extends Activity
{

	WifiManager wifim;
	LinearLayout ll;
	ScrollView scroll;
	TextView tv;
	LinearLayout m_vwWifiLinearLayout;
	Boolean color;
	Timer myTimer;
	String[] macAdresses = {"00:12:44:ba:27:10","00:3a:98:72:ba:a0","00:17:Of:35:10:30","00:12:44:ba:78:10","00:12:44:ba:70:40","00:12:44:ba:7b:30","00:12:44:ba:78:10","00:3a:98:72:b8:50","00:3a:98:62:b5:00","00:3a:98:62:b7:00","00:12:44:ba:77:e0","00:24:97:f2:84:c0","00:12:44:ba:18:60","00:3a:98:62:b3:b0","00:12:44:ba:3a:D9","00:12:44:ba:3a:b0","00:24:97:f2:84:00","00:24:97:f2:83:80","00:24:97:f2:84:40","00:24:97:f3:0d:70"};

	private Handler handlerTimer = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		makeButtonListener();
		scan();	
		
		handlerTimer.removeCallbacks(taskUpdateWifis );
		handlerTimer.postDelayed(taskUpdateWifis , 100); 
	}
	
	private Runnable taskUpdateWifis = new Runnable() {
	       public void run() {     
	    	   			scan();

	            //Do this again in 30 seconds          
	            handlerTimer.postDelayed(this, 1000);
	    }
	};
	private void makeButtonListener()
	{
		findViewById(R.id.buttonScan).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				scan();
			}
		});
	}
	
	public void scan()
	{
		color = true;

		m_vwWifiLinearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		m_vwWifiLinearLayout.setOrientation(LinearLayout.VERTICAL);

		wifim = (WifiManager) this.getSystemService(WIFI_SERVICE);

		if (wifim.isWifiEnabled())
		{
			wifim.startScan();

			List<ScanResult> listSort = wifim.getScanResults();
			List<ScanResult> listSortCheck = new ArrayList<ScanResult>();

			while (true)
			{	
				
				listSortCheck.clear();
				listSortCheck.addAll(listSort);
				
				for (int i = 0; i < listSort.size(); i++)
				{
					if (i + 1 < listSort.size())
					{
						if (listSort.get(i + 1).level > listSort.get(i).level)
						{
							ScanResult sr = listSort.get(i);
							listSort.set(i, listSort.get(i + 1));
							listSort.set(i + 1, sr);
						}
					}
				}
				
				if (listSort.equals(listSortCheck))
				{			
					break;
				}
			}
			fillLayout(listSort);
		}
	}
	
	public void fillLayout(List<ScanResult> listSort)
	{
		m_vwWifiLinearLayout.removeAllViewsInLayout();
		for (ScanResult s : listSort)
		{
			for (int i = 0; i < macAdresses.length ; i++)
			{
				if (s.BSSID.equals(macAdresses[i]))
				{
					tv = new TextView(this);
					tv.setText("WifiInfo:");
					tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					m_vwWifiLinearLayout.addView(tv);
					
					tv = new TextView(this);
					tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

					tv.setText(" SSID: \t\t\t\t" + s.SSID + "\n Frequency: \t\t " + s.frequency + "\n Signal(dBm): \t" + s.level + "\n Mac: \t\t\t\t" + s.BSSID);
//					tv.setText(s.level + " - " + s.BSSID);

					if (color)
					{
						tv.setBackgroundColor(Color.DKGRAY);
						color = false;
					}
					else
					{
						tv.setBackgroundColor(Color.GRAY);
						color = true;
					}
					m_vwWifiLinearLayout.addView(tv);
				}
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		menu.findItem(R.id.SCAN_MENU_ITEM)
				.setOnMenuItemClickListener(new OnMenuItemClickListener()
				{
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						scan();
						return false;
					}
				});
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		return true;
	}
	
}