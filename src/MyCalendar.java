import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


enum MONTHS
{
	January, February, March, April, May, June, July, August, September, October, November, December;
}

enum MONS
{
	Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec;
}

enum DAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

enum DS
{
	Sun, Mon, Tue, Wed, Thu, Fri, Sat;
}

/**
 * The data model class which holds the data for Calendar view.
 */
public class MyCalendar {
	private List<ChangeListener> listeners;
	private Calendar calendar;
	private EventManager eventManager;
	private static final String EVENT_FILE = "event.txt";

	/**
	 * Constructor for MyCalendar class.
	 */
	public MyCalendar() {
		listeners = new ArrayList<>();
		calendar = new GregorianCalendar();
		eventManager = new EventManager();
		eventManager.loadEventList(EVENT_FILE);
	}
	
	/**
	 * Attach the given listener.
	 * @param listener the given listener to listen the change.
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Notifies all registered listeners.
	 */
	public void notifyListeners() {
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(ce);
		}
	}

	/**
	 * @return current year.
	 */
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * @return current month.
	 */
	public int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * @return current day in the month.
	 */
	public int getDay() {
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * @return the display string of current month, e.g. November
	 */
	public String getMonthString() {
		MONTHS[] arrayofMonth = MONTHS.values();
		return arrayofMonth[calendar.get(Calendar.MONTH)].toString();
	}
	
	/**
	 * @return the current day string, e.g. Monday.
	 */
	public String getDayInWeekString() {
		DAYS[] arrayofDay = DAYS.values();
		return arrayofDay[calendar.get(Calendar.DAY_OF_WEEK) - 1].toString();
	}
	
	/**
	 * @return the mm/dd/yy format for current date.
	 */
	public String getDateString() {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		return String.format("%d/%d/%2d", month, day, year % 100);
	}

	/**
	 * Moves to previous day.
	 */
	public void previousDay() {
		calendar.add(Calendar.DATE, -1);
		notifyListeners();
	}
	
	/**
	 * Moves to next day.
	 */
	public void nextDay() {
		calendar.add(Calendar.DATE, 1);
		notifyListeners();
	}
	
	/**
	 * Sets the current day.
	 * @param day the day.
	 */
	public void setDay(int day) {
		calendar.set(Calendar.DATE, day);
		notifyListeners();
	}
	
	/**
	 * @return the offset when drawing month calendar view.
	 */
	public int getMonthOffset() {
		GregorianCalendar c = new GregorianCalendar(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * @return the max days of current month.
	 */
	public int getMaxDayOfMonth() {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Checks if the day in the month calendar view is today.
	 * @param day the day.
	 * @return whether the day in the month calendar view is today.
	 */
	public boolean isToday(int day) {
		GregorianCalendar c = new GregorianCalendar();
		return calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
				calendar.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
				day == c.get(Calendar.DATE);
	}

	/**
	 * Parses the given time string.
	 * @param time the time string, e.g. 8:10am, 12:30pm
	 * @return the int value of the time, e.g. 810, 1230
	 */
	private int parseTime(String time) {
		int pos = time.indexOf(':');
		if (pos < 1) return -1;
		int hh = Integer.valueOf(time.substring(0, pos));
		int mm = Integer.valueOf(time.substring(pos + 1, pos + 3));
		if (time.indexOf("pm") >= 0) {
			if (hh != 12)
				hh += 12;
		} else {
			if (hh == 12)
				hh = 0;
		}
		return hh * 100 + mm;
	}
	
	/**
	 * Saves an event.
	 * @param title event title.
	 * @param date event date, e.g. "11/17/2017".
	 * @param startTime event starting time, e.g. "8:30am"
	 * @param endTime event ending time, e.g. "6:30pm"
	 * @return empty string is succeed, otherwise the error message.
	 */
	public String saveEvent(String title, String date, String startTime, String endTime) {
		String[] dateSeg = date.split("/");
		if (dateSeg.length != 3) {
			return "Wrong date format: " + date;
		}
		int year = 2000 + Integer.valueOf(dateSeg[2]);
		int month = Integer.valueOf(dateSeg[0]);
		int day = Integer.valueOf(dateSeg[1]);
		int dateNum = year * 10000 + month * 100 + day;
		int startTimeNum = parseTime(startTime);
		if (startTimeNum < 0) 
			return "Wrong start time format: " + startTime;
		int endTimeNum = parseTime(endTime);
		if (endTimeNum < 0) 
			return "Wrong end time format: " + endTime;
		if (endTimeNum <= startTimeNum) {
			return "End time cannot be earlier than start time";
		}
		if (!this.eventManager.addEvent(dateNum, startTimeNum, endTimeNum, title)) {
			return "Conflict event";
		}
		notifyListeners();
		return "";
	}
	
	/**
	 * @return the event list of current day.
	 */
	public List<Event> getEventList() {
		int date = getDateKey();
		return this.eventManager.getEventsbyDate(date);
	}

	/**
	 * Saves the events into the file.
	 */
	public void saveEventsToFile() {
		this.eventManager.saveEventList(EVENT_FILE);
	}
	
	/**
	 * @return the current date key. 
	 */
	private int getDateKey() {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		return year * 10000 + (month + 1) * 100 + day;
	}
}
