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

public class PathOverlayRouters extends Overlay {

    private final List<GeoPoint> m_arrPathPoints;

    private Point m_point;
    private Point m_point2;
    private Paint m_paint;
    private RectF m_rect;

    private static final int START_RADIUS = 5;
    private static final int PATH_WIDTH = 3;

    public PathOverlayRouters(List<GeoPoint> pathPoints) {
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
	    m_paint.setARGB(255, 255, 0, 0);
	    m_paint.setStrokeWidth(PATH_WIDTH);

	    Projection projection = mapView.getProjection();

	    for (GeoPoint gp : m_arrPathPoints) {

		projection.toPixels(gp, m_point);
		m_rect.set(m_point.x - START_RADIUS, m_point.y - START_RADIUS, m_point.x + START_RADIUS, m_point.y + START_RADIUS);

		canvas.drawOval(m_rect, m_paint);

	    }
	}

    }
}
