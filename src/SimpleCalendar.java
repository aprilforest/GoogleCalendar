import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The class draws the whole calendar view.
 */
public class SimpleCalendar {
	private MyCalendar myCal;

	/**
	 * The constructor of SimpleCalendar.
	 */
	public SimpleCalendar() {
		myCal = new MyCalendar();
		CalendarPanel calendarPanel = new CalendarPanel(myCal);
		
		// The top panel includes left / right button and quit button.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JPanel moveButtonPanel = new JPanel();
		JButton moveLeftButton = new JButton("<");
		JButton moveRightButton = new JButton(">");
		moveLeftButton.setPreferredSize(new Dimension(30, 30));
		moveRightButton.setPreferredSize(new Dimension(30, 30));
		moveButtonPanel.add(moveLeftButton);
		moveButtonPanel.add(moveRightButton);
		moveButtonPanel.setBackground(Color.WHITE);
		topPanel.add(moveButtonPanel, BorderLayout.CENTER);
		JButton quitButton = new JButton("Quit");
		topPanel.add(quitButton, BorderLayout.EAST);
		topPanel.setBackground(Color.WHITE);
		
		// Button actions
		moveLeftButton.addActionListener(e -> {myCal.previousDay();});
		moveRightButton.addActionListener(e -> {myCal.nextDay();});
		quitButton.addActionListener(e -> {
			myCal.saveEventsToFile();
			System.exit(0);
		});
		
		// The left panel includes "Create" button and month calendar view.
		JPanel leftPanel = new JPanel();
		leftPanel.add(calendarPanel);
		leftPanel.setBackground(Color.WHITE);
		
		// The right panel includes current date and event view.
		EventPanel eventPanel = new EventPanel(myCal);
		
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(eventPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		// Adjust scroll bar to start with 8am.
		eventPanel.adjustScrollbar();
	}

	public static void main(String[] args) {
		new SimpleCalendar();
	}
}
