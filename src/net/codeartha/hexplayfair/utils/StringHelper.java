package net.codeartha.hexplayfair.utils;

import java.text.Normalizer;

public class StringHelper {

	/**
	 * This method removes any duplicated characters in a string
	 * 
	 * @param String
	 * @return String
	 */
	public static String removeDuplicates(String s) {
		StringBuilder noDupes = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			String si = Character.toString(s.charAt(i));
			if (noDupes.indexOf(si) == -1) {
				noDupes.append(si);
			}
		}
		return noDupes.toString();
	}

	/**
	 * This method replaces accents by their non-accented character (French
	 * support only)
	 * 
	 * @param String
	 * @return String
	 */
	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}
}
