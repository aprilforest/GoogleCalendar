import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The panel which holds date string and the event view.
 */
public class EventPanel extends JPanel implements ChangeListener {
	private MyCalendar myCal;
	private JLabel eventLabel;
	private JLabel dateLabel;
	private JScrollPane scrollPanel;
	private static final Font font = new Font("Arial", Font.BOLD, 12);

	/**
	 * The constructor of the EventPanel.
	 * @param myCal the data model.
	 */
	public EventPanel(MyCalendar myCal) {
		this.myCal = myCal;
		myCal.attach(this);
		
		EventView eventView = new EventView(myCal);
		eventLabel = new JLabel(eventView);
	    scrollPanel = new JScrollPane(eventLabel,
	    		  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setPreferredSize(new Dimension(515, 300));
		scrollPanel.setBorder(null);
		
		JPanel datePanel = new JPanel();
		datePanel.setBackground(Color.WHITE);
		dateLabel = new JLabel(getDateHeader());
		dateLabel.setFont(font);
		datePanel.add(dateLabel);

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(datePanel);
		this.add(scrollPanel);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		eventLabel.repaint();
		dateLabel.setText(getDateHeader());
	}
	
	/**
	 * Adjusts the scroll bar to start from 8am.
	 */
	public void adjustScrollbar() {
		scrollPanel.getVerticalScrollBar().setValue(320);
	}
	
	/**
	 * @return the date header string.
	 */
	private String getDateHeader() {
		return String.format("%s %d/%d", myCal.getDayInWeekString(), myCal.getMonth(), myCal.getDay());
	}
}
