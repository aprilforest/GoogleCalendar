import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * The panel including "Create" button and month calendar view.
 */
public class CalendarPanel extends JPanel implements ChangeListener {
	private MyCalendar myCal;
	private JLabel monthLabel;
	private JButton[][] dayButton = new JButton[6][7];
	private ActionListener[][] listeners = new ActionListener[6][7];
	
	/**
	 * The constructor of CalendarPanel.
	 * @param myCal the data model.
	 */
	public CalendarPanel(MyCalendar myCal) {
		this.myCal = myCal;
		myCal.attach(this);

		// "Create" button.
		JButton btn = new JButton("CREATE");
		btn.setForeground(Color.WHITE);
		btn.setBackground(Color.RED);
		btn.setOpaque(true);
		btn.setFont(new Font("Arial", Font.BOLD, 12));
		btn.setPreferredSize(new Dimension(100, 30));
		btn.setBorderPainted(false);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new EventDialog(myCal);
				dialog.pack();
				dialog.setVisible(true);			
			}
		});
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		btnPanel.add(btn);
		
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);
		JPanel calendarGrid = new JPanel();
		calendarGrid.setBackground(Color.WHITE);
		add(btnPanel, BorderLayout.NORTH);
		add(headerPanel, BorderLayout.WEST);
		add(calendarGrid, BorderLayout.SOUTH);
		
		monthLabel = new JLabel();
		monthLabel.setHorizontalAlignment(JLabel.LEFT);
		headerPanel.add(monthLabel);
		
		calendarGrid.setLayout(new GridLayout(7, 7));		
		String colName[] = {"S", "M", "T", "W", "T", "F", "S"};
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (i == 0) {
					JLabel cell = new JLabel(colName[j]);
					cell.setHorizontalAlignment(JLabel.CENTER);
					calendarGrid.add(cell);
				} else {
					JButton button = new JButton();
					calendarGrid.add(button);
					dayButton[i - 1][j] = button;
				}
			}
		}
		// Initialize the month calendar view.
		update();
	}

	private void update() {
		monthLabel.setText(myCal.getMonthString() + " " + myCal.getYear());
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				int dayNumber = i * 7 + (j + 1) - myCal.getMonthOffset();
				int maxDay = myCal.getMaxDayOfMonth();
				String label = (dayNumber > 0 && dayNumber <= maxDay) ? String.valueOf(dayNumber) : "";
				dayButton[i][j].setText(label);
				dayButton[i][j].setPreferredSize(new Dimension(25, 20));
				dayButton[i][j].setOpaque(true);

				if (!myCal.isToday(dayNumber)) {
					dayButton[i][j].setBorder(null);
					if (dayNumber == myCal.getDay()) {
						// Selected day
						dayButton[i][j].setBackground(new Color(220, 220, 220));
					} else {
						dayButton[i][j].setBackground(Color.WHITE);
					}
				}
				if (listeners[i][j] != null) {
					dayButton[i][j].removeActionListener(listeners[i][j]);
					listeners[i][j] = null;
				}
				if (dayNumber > 0 && dayNumber <= maxDay) {
					listeners[i][j] = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							myCal.setDay(dayNumber);
						}
					};
					dayButton[i][j].addActionListener(listeners[i][j]);
				}
			}
		}
	}

	/**
	 * Updates the panel when calendar data is changed.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		update();
	}
}
