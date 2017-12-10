import java.util.*;
import java.io.*;

/**
 * The data model class manages all events. 
 */
public class EventManager {
	// The hashmap holds all events, keyed by date, e.g. 20171117.
	public HashMap<Integer, ArrayList<Event>> eventsMap;
	
	/**
	 * Constructor of EventManager class.
	 */
	public EventManager() {
		eventsMap = new HashMap<>();
	}
	

	/**
	 * Add a new event.
	 * @param date The date of the event, e.g. 20171117
	 * @param startingTime The starting time of the event, e.g. 1000 for 10:00am
	 * @param endingTime The ending time of the event, e.g. 1830 of 6:30pm
	 * @param title The event title.
	 * @return whether the event is added successfully.
	 */
	public boolean addEvent(int date, int startingTime, int endingTime, String title) {
		Event event = new Event(date, startingTime, endingTime, title);
		ArrayList<Event> eventsList = eventsMap.get(event.getDate());
		if (eventsList == null) {
			eventsList = new ArrayList<>();
			eventsMap.put(event.getDate(), eventsList);
		}
		for (Event e : eventsList) {
			// Check if there is already a conflict event.
			if (e.getStartingTime() >= event.getStartingTime() && e.getStartingTime() < event.getEndingTime()) {
				return false;
			}
			if (event.getStartingTime() >= e.getStartingTime() && event.getStartingTime() < e.getEndingTime()) {
				return false;
			}
		}
		eventsList.add(event);
		return true;
	}
	
	/**
	 * Gets the list of events on the given date.
	 * @param date the given date.
	 * @return the list of events on that date.
	 */
	public ArrayList<Event> getEventsbyDate(int date) {
		return eventsMap.get(date);
	}
	
	/**
	 * Loads all events from the given file.
	 * @param filename the file name.
	 * @return whether the events are loaded successfully.
	 */
	public boolean loadEventList(String filename) {
		try{
			FileInputStream fi = new FileInputStream(new File(filename));
			ObjectInputStream oi = new ObjectInputStream(fi);
			eventsMap = (HashMap)oi.readObject();
			traverseEvents();
			oi.close();
			fi.close();
		} catch(FileNotFoundException e) {
			System.out.println("File not found.");
			return false;
		} catch(IOException e) {
			System.out.println("Error Output Stream: " + e.toString());
			return false;
		} catch(ClassNotFoundException e) {
			System.out.println("Class not found.");
			return false;
		}
		return true;
	}
	
	/**
	 * Saves the event list into the given file.
	 * @param filename the file name.
	 * @return whether the events are saved successfully.
	 */
	public boolean saveEventList(String filename) {
		try {
			FileOutputStream fo = new FileOutputStream(new File(filename));
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(eventsMap);
			fo.close();
			oo.close();
			System.out.println("All data has been stored into \"events.txt.\"");
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return false;
		} catch (IOException e) {
			System.out.println("Error Output Stream: " + e.toString());
			return false;
		} 
		return true;
	}
	
	/**
	 * Traverse the events and print them, used for debug.
	 */
	public void traverseEvents(){
		for (int key: eventsMap.keySet()) {
			System.out.printf("%d Event List:\n", key);
			ArrayList<Event> list = eventsMap.get(key);
			for (Event event: list) {
				System.out.printf("Date: %d, StartingTime: %d, EndingTime: %d, Activity: %s.\n", 
						event.getDate(), event.getStartingTime(), event.getEndingTime(), event.getEventTitle() );
			}
		}
	}
}
