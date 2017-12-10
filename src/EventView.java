import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The class that draws the events on the given day.
 */
public class EventView implements Icon {
	private MyCalendar myCal;
	private static final int CELL_HEIGHT = 40;
	private static final int WIDTH = 500;
	private static final int HEIGHT = CELL_HEIGHT * 24;
	private static final int LEFT_WIDTH = 60;
	private static final int EVENT_OFFSET = 50;
	private static final int EVENT_WIDTH = 120;
	private static final Color BACKGOUND_COLOR = new Color(95, 115, 115, 50);
	private static final Color LINE_COLOR = new Color(85, 90, 90, 200);
	private static final Color TEXT_COLOR = new Color(50, 50, 50);
	private static final Color EVENT_COLOR = new Color(218, 129, 119);
	private static final Color EVENT_TEXT_COLOR = Color.WHITE;
	private static final float DASH[] = {3.0f};
	private static final Font font = new Font("Arial", Font.PLAIN, 12);
    private static final BasicStroke DASHED =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, DASH, 0.0f);
    
    /**
     * The constructor of the EventView.
     * @param myCal the data model.
     */
	public EventView(MyCalendar myCal) {
		this.myCal = myCal;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(font);
		g.setColor(BACKGOUND_COLOR);
		g.fillRect(LEFT_WIDTH, 0, WIDTH - LEFT_WIDTH, HEIGHT);
		
		g.setColor(LINE_COLOR);
		g.drawLine(0, 0, 0, HEIGHT);
		g.drawLine(LEFT_WIDTH, 0, LEFT_WIDTH, HEIGHT);
		for (int i = 0; i < 24; ++i) {
			g.drawLine(0, CELL_HEIGHT * i, WIDTH, CELL_HEIGHT * i);
		}
		
		g2.setStroke(DASHED);
		for (int i = 0; i < 24; ++i) {
			g.drawLine(LEFT_WIDTH, i * CELL_HEIGHT + CELL_HEIGHT / 2, WIDTH, i * CELL_HEIGHT + CELL_HEIGHT / 2);
		}
		
		g.setColor(TEXT_COLOR);
		for (int i = 0; i < 24; ++i) {
			int h = i % 12 == 0 ? 12 : i % 12;
			String t = String.format("%d%s", h, i >= 12 ? "pm" : "am");
			g.drawString(t, LEFT_WIDTH - 35 + (h < 10 ? 7 : 0), CELL_HEIGHT * i + 15);
		}
	    
		List<Event> eventList = myCal.getEventList();
		if (eventList != null) {
			for (Event event : eventList) {
				int t1 = event.getStartingTime();
				int t2 = event.getEndingTime();
				int y1 = t1 / 100 * CELL_HEIGHT;
				if (t1 % 100 >= 30) y1 += CELL_HEIGHT / 2;
				int y2 = (t2 / 100 + 1) * CELL_HEIGHT - 2;
				if (t2 % 100 <= 30) y2 -= CELL_HEIGHT / 2;
				g.setColor(EVENT_COLOR);
				g.fillRect(LEFT_WIDTH + EVENT_OFFSET, y1, EVENT_WIDTH, y2 - y1);				
				
				g.setColor(EVENT_TEXT_COLOR);
				g.drawString(event.getEventTitle(), LEFT_WIDTH + EVENT_OFFSET + 10, y1 + 18);
			}
		}
	}

	@Override
	public int getIconWidth() {
		return WIDTH;
	}

	@Override
	public int getIconHeight() {
		return HEIGHT;
	}
}
