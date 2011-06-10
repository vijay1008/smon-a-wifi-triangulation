package wifi.dal;

import java.util.ArrayList;

import wifi.Router;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	static final String _dbName="dbWireless";
	static final String routerTable="Router";
	static final String colID="RouterID";
	static final String colMac="macaddress";
	static final String colLong="longitude";
	static final String colLat="latitude";
	
	public DBHelper(Context context) {
		super(context, _dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE " + routerTable + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + 
				colMac + " TEXT , " + colLong + " NUMERIC , " + colLat + " NUMERIC;");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void InsertRouter(String macaddress, long longitude, long latitude)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colMac, macaddress);
		cv.put(colLong, longitude);
		cv.put(colLat, latitude);
	}
	
	public ArrayList<Router> GetRouters()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Router> result = new ArrayList<Router>();
		
		Cursor c = db.rawQuery("SELECT " + colMac + "," + colLong + "," + colLat + ");", null);
		
		while(!c.isAfterLast())
		{
			Router rt = new Router(c.getString(0), c.getLong(1), c.getLong(2));
			result.add(rt);
		}
		
		return result;		
	}

}
