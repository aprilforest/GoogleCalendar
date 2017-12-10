import java.io.*;

/**
 * Data class holds the event info.
 */
public class Event implements Serializable{
	private int date;            // Eg: 20171012 -> YYYY/MM/DD
	private int startingTime;    // Eg: 1530 -> 3:30 pm
	private int endingTime;      // Eg: 1830 -> 6:30 pm
	private String eventTitle;   // Eg: Clean the Garden
	
	/**
	 * Constructor of Event class.
	 * @param date The date of the event, e.g. 20171117
	 * @param startingTime The starting time of the event, e.g. 1000 for 10:00am
	 * @param endingTime The ending time of the event, e.g. 1830 of 6:30pm
	 * @param eventTitle The event title.
	 */
	public Event(int date, int startingTime, int endingTime, String eventTitle) {
		this.date = date;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.eventTitle = eventTitle;
	}
	
	/**
	 * @return the date of the event.
	 */
	public int getDate(){
		return date;
	}
	
	/**
	 * @return the starting time of the event.
	 */
	public int getStartingTime() {
		return startingTime;
	}
	
	/**
	 * @return the ending time of the event.
	 */
	public int getEndingTime() {
		return endingTime;
	}
	
	/**
	 * @return the event title.
	 */
	public String getEventTitle() {
		return eventTitle;
	}
}
