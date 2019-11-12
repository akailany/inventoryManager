package inventoryManager.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
	private static DateTimeFormatter format = DateTimeFormatter.ISO_INSTANT;

	private TimeUtils() {
	}

	public static String toString(ZonedDateTime zonedDateTime) {
		return zonedDateTime.format(format);
	}

	public static ZonedDateTime fromString(String input) {
		try {
			return ZonedDateTime.parse(input, format);
		} catch (Exception exc) {
			System.err.println("Failed to parse date: " + input);
			return null;
		}
	}

	public static String nowToString() {
		return toString(ZonedDateTime.now());
	}
}
