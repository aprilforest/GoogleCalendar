import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The dialog used to create a new event.
 */
public class EventDialog extends JDialog implements ActionListener {
	private MyCalendar myCal;
	private JTextField titleText;
	private JTextField dateText;
	private JTextField startTimeText;
	private JTextField endTimeText;
	private JTextField errorBar;

	/**
	 * Constructor of EventDialog.
	 * @param myCal the data model.
	 */
	public EventDialog(MyCalendar myCal) {
		this.myCal = myCal;
		this.setLayout(new BorderLayout());
		// Blocks parent frame.
		this.setModal(true);
		
		titleText = new JTextField();
		this.add(titleText, BorderLayout.NORTH);
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout());
		dateText = new JTextField(7);
		startTimeText = new JTextField(7);
		endTimeText = new JTextField(7);
		datePanel.add(dateText);
		datePanel.add(startTimeText);
		datePanel.add(new JLabel("to"));
		datePanel.add(endTimeText);
		this.add(datePanel, BorderLayout.WEST);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		this.add(saveButton, BorderLayout.EAST);
		
		errorBar = new JTextField();
		errorBar.setEditable(false);
		errorBar.setForeground(Color.RED);
		errorBar.setBackground(this.getBackground());
		this.add(errorBar, BorderLayout.SOUTH);
		
		titleText.setText("Untitled Event");
		titleText.grabFocus();
		dateText.setText(myCal.getDateString());
		startTimeText.setText("10:30am");
		endTimeText.setText("11:30am");
	}

	/**
	 * Handles the event when the "Create" button is clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Assuming all fields are correct.
		String errorMsg = this.myCal.saveEvent(
				titleText.getText().trim(), dateText.getText().trim(), 
				startTimeText.getText().trim(), endTimeText.getText().trim());
		if (errorMsg.isEmpty()) {
			this.dispose();
		} else {
			errorBar.setText(errorMsg);
		}
	}
}
