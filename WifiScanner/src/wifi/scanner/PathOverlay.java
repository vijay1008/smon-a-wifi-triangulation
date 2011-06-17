package wifi.scanner;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PathOverlay extends Overlay {

    private final List<GeoPoint> m_arrPathPoints;

    private Point m_point;
    private Point m_point2;
    private Paint m_paint;
    private RectF m_rect;

    private static final int START_RADIUS = 5;
    private static final int PATH_WIDTH = 3;

    public PathOverlay(List<GeoPoint> pathPoints) {
	super();
	m_arrPathPoints = pathPoints;
	m_point = new Point();
	m_point2 = new Point();
	m_rect = new RectF();
	m_paint = new Paint();

    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	super.draw(canvas, mapView, shadow);

	if (!m_arrPathPoints.isEmpty()) {
	    m_paint.setARGB(255, 0, 255, 0);
	    m_paint.setStrokeWidth(PATH_WIDTH);

	    Projection projection = mapView.getProjection();

	    projection.toPixels(m_arrPathPoints.get(0), m_point);
	    m_rect.set(m_point.x - START_RADIUS, m_point.y - START_RADIUS, m_point.x + START_RADIUS, m_point.y + START_RADIUS);

	    canvas.drawOval(m_rect, m_paint);

	    for (int i = 0; i <= m_arrPathPoints.size() - 1; i++) {
		int j = i;
		j++;
		if (j <= m_arrPathPoints.size() - 1) {
		    projection.toPixels(m_arrPathPoints.get(i), m_point);
		    projection.toPixels(m_arrPathPoints.get(j), m_point2);
		    canvas.drawLine(m_point.x, m_point.y, m_point2.x, m_point2.y, m_paint);
		}
	    }
	}

    }
}
