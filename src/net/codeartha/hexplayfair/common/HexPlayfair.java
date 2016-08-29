package net.codeartha.hexplayfair.common;

import java.awt.Point;

import net.codeartha.hexplayfair.reference.References;
import net.codeartha.hexplayfair.common.PairPointReturn;
import net.codeartha.hexplayfair.utils.StringHelper;

/**
 * @author codeartha
 * 
 *         note: I chose not to encrypt bigrams that contains double letter for
 *         easier translation to binary file later on.
 * 
 */
public class HexPlayfair {
	private String[][] keyGrid = new String[References.ARRAY_SIZE][References.ARRAY_SIZE]; // String[3][5] create array
																						 // of 3 rows 5 columns =>
																						 // keyGrid[y][x]
	private String key = "";
	private String msgIn = "";
	private String msgOut = "";

	public HexPlayfair() {
	}

	public HexPlayfair(String password) {
		this.generateKeyGrid(password);
	}

	public String getMsgOut() {
		return this.msgOut;
	}

	public String getMsgIn() {
		return this.msgIn;
	}

	public void setKey(String k) {
		k = StringHelper.stripAccents(k);
		this.key = k.toLowerCase().replaceAll("v", "u").replaceAll(" ", "");
	}

	public void setMsgIn(String m) {
		m = StringHelper.stripAccents(m);
		this.msgIn = m.toLowerCase().replaceAll("v", "u").replaceAll(" ", "");
		if (this.msgIn.length() % 2 != 0) // odd number of characters
		{
			this.msgIn = this.msgIn.concat("x"); // we add a character, we chose
													// X.
		}
	}

	public void clearMsgIn() {
		this.msgIn = "";
	}

	public void clearMsgOut() {
		this.msgOut = "";
	}

	public void clearKey() {
		this.key = "";
	}

	public void clearGrid() {
		this.keyGrid = null;
	}

	public void clearAll() {
		clearMsgIn();
		clearMsgOut();
		clearKey();
		clearGrid();
	}

	public void decryptMsg() {
		this.clearMsgOut(); // added to avoid appending to previous msgOut

		int i = 0;
		while (i < this.msgIn.length() - 1) // -1 to avoid ArrayOutOfBound
											// because we scan two letters by
											// two
		{
			// get the next bigram
			String char1 = msgIn.substring(i, i + 1);
			String char2 = msgIn.substring(i + 1, i + 2);

			// for each char in the bigram we find it's coordinates in the grid
			Point locationChar1 = getLetterCoords(char1);
			Point locationChar2 = getLetterCoords(char2);

			// putting those coordinates trough the Playfair cipher ruleset to
			// get the clear letters coordinates
			PairPointReturn clearLetters = decryptCoords(locationChar1,
					locationChar2);

			// each Point contains the coordinates of the cleartext letters
			Point firstClear = clearLetters.getFirst();
			Point secondClear = clearLetters.getSecond();

			// we find the cleartext letter that matches the cleartext
			// coordinates and append it to the output message
			this.msgOut = this.msgOut
					+ this.keyGrid[firstClear.y][firstClear.x]
					+ this.keyGrid[secondClear.y][secondClear.x];
			i = i + 2; // we go through the text two letters at the time
		}
	}

	public void encryptMsg() {
		this.clearMsgOut(); // added to avoid appending to previous msgOut

		int i = 0;
		while (i < this.msgIn.length() - 1) // -1 to avoid ArrayOutOfBound
											// because we scan two letters by
											// two
		{
			// get the next bigram
			String char1 = msgIn.substring(i, i + 1);
			String char2 = msgIn.substring(i + 1, i + 2);

			// for each char in the bigram we find it's coordinates in the grid
			Point locationChar1 = getLetterCoords(char1);
			Point locationChar2 = getLetterCoords(char2);

			// we put those coordinates trough the Playfair cipher ruleset to
			// get the clear letters coordinates
			PairPointReturn cryptedLetters = encryptCoords(locationChar1,
					locationChar2);

			// each Point contains the coordinates of the cleartext letters
			Point firstCrypt = cryptedLetters.getFirst();
			Point secondCrypt = cryptedLetters.getSecond();

			// we find the cleartext letter that matches the cleartext
			// coordinates and append it to the output message
			this.msgOut = this.msgOut
					+ this.keyGrid[firstCrypt.y][firstCrypt.x]
					+ this.keyGrid[secondCrypt.y][secondCrypt.x];
			i = i + 2;
		}
	}

	/**
	 * Finds a letter in a bidimensional array and return's it's x and y
	 * position
	 * 
	 * @param letter
	 * @return Point
	 */
	private Point getLetterCoords(String letter)// Object[][] array,
	{
		letter = letter.toLowerCase(); // just in case...

		if (letter.equals("v")) {
			letter = "u"; // v doesn't exist, we always look for u
		}

		Object[][] array = this.keyGrid; // needed for debug
		if (letter == null || array == null)
			return null;

		for (int y = 0; y < array.length; y++) {
			Object[] row = array[y];
			if (row != null) {
				for (int x = 0; x < row.length; x++) {
					if (letter.equals(row[x])) {
						return new Point(x, y); // point (x,y)
					}
				}
			}
		}
		return null; // value not found in array
	}

	/**
	 * Supposed to be only for debug purpose, but could be adapted for
	 * interactive display Prints the keyGrid directly to console output.
	 */
	public void printKeyGrid() {
		for (int y = 0; y <= References.ARRAY_SIZE; y++) // = lines = y
		{
			String temp = "";
			for (int x = 0; x <= References.ARRAY_SIZE; x++) // = columns = x
			{
				temp = temp + " [" + this.keyGrid[y][x] + "]";
			}
			System.out.println(temp);
		}
	}

	/**
	 * Set keyGrid to a new array containing the secret key and all rest of the
	 * letters of the alphabet
	 */
	public void generateKeyGrid() {
		this.key = StringHelper.removeDuplicates(this.key + "0123456789abcdef");

		int k = 0;
		for (int y = 0; y <= References.ARRAY_SIZE; y++) {
			for (int x = 0; x <= References.ARRAY_SIZE; x++) {
				this.keyGrid[y][x] = Character.toString(this.key.charAt(k));
				k++;
			}
		}
		// not in the binary version anymore, key might be needed to generate file header
		//this.clearKey(); // good time to erase the key, not needed later anyway
	}

	/**
	 * Set keyGrid to a new array containing the secret key and all rest of the
	 * letters of the alphabet
	 * 
	 * @param secret
	 */
	public void generateKeyGrid(String secret) {
		this.key = secret.toLowerCase();
		this.key = StringHelper.removeDuplicates(this.key + "0123456789abcdef");

		int k = 0;
		for (int y = 0; y <= References.ARRAY_SIZE; y++) {
			for (int x = 0; x <= References.ARRAY_SIZE; x++) {
				this.keyGrid[y][x] = Character.toString(this.key.charAt(k));
				k++;
			}
		}

		this.clearKey(); // good time to erase the key, not needed later anyway
	}

	/**
	 * Takes two Points (=coords) as parameter, A being the coordinates of the
	 * first letter, B the ones of the second one. This function returns the
	 * coordinates of the encrypted characters, they then have to be passed to
	 * the keyGrid to extract the encrypted character.
	 * 
	 * @param point
	 *            A
	 * @param point
	 *            B
	 * @return Object PairPointReturn
	 */
	private static PairPointReturn encryptCoords(Point clearA, Point clearB) {

		Point cryptA = new Point(0, 0);
		Point cryptB = new Point(0, 0);

		if (clearA.x == clearB.x && clearA.y == clearB.y) // two letters are the
															// same => do
															// nothing
		{
			return new PairPointReturn(clearA, clearB);
		} else if (clearA.x == clearB.x && clearA.y != clearB.y) // two letters
																	// on the
																	// same
																	// column
		{
			cryptA.x = clearA.x;
			cryptA.y = (clearA.y + 1) % References.GRID_SIZE;
			cryptB.x = clearB.x;
			cryptB.y = (clearB.y + 1) % References.GRID_SIZE;
			return new PairPointReturn(cryptA, cryptB);
		} else if (clearA.y == clearB.y && clearA.x != clearB.x) // letters on
																	// the same
																	// row
		{
			cryptA.x = (clearA.x + 1) % References.GRID_SIZE;
			cryptA.y = clearA.y;
			cryptB.x = (clearB.x + 1) % References.GRID_SIZE;
			cryptB.y = clearB.y;
			return new PairPointReturn(cryptA, cryptB);
		} else // letters form a rectangle
		{
			cryptA.x = clearB.x;
			cryptA.y = clearA.y;
			cryptB.x = clearA.x;
			cryptB.y = clearB.y;
			return new PairPointReturn(cryptA, cryptB);
		}
	}

	/**
	 * This function takes two already encrypted coordinates and return their
	 * clear form. They will then have to be matched with the keyGrid to return
	 * the two clear characters.
	 * 
	 * @param cryptedA
	 * @param cryptedB
	 * @return Object PairPointReturn
	 */
	private static PairPointReturn decryptCoords(Point cryptedA, Point cryptedB) {
		Point clearA = new Point(0, 0);
		Point clearB = new Point(0, 0);

		if (cryptedA.x == cryptedB.x && cryptedA.y == cryptedB.y) // the two
																	// letters
																	// are the
																	// same =>
																	// do
																	// nothing
		{
			return new PairPointReturn(cryptedA, cryptedB);
		} else if (cryptedA.x == cryptedB.x && cryptedA.y != cryptedB.y) // two
																			// letters
																			// on
																			// the
																			// same
																			// column
		{
			clearA.x = cryptedA.x;
			clearA.y = (cryptedA.y + References.GRID_SIZE - 1) % References.GRID_SIZE; // +4 mod(5) to solve the issue
												// with removing 1 that
												// sometimes gives negative
												// numbers instead of looping
												// around
			clearB.x = cryptedB.x;
			clearB.y = (cryptedB.y + References.GRID_SIZE - 1) % References.GRID_SIZE;
			return new PairPointReturn(clearA, clearB);
		} else if (cryptedA.y == cryptedB.y && cryptedA.x != cryptedB.x) // two
																			// letters
																			// on
																			// the
																			// same
																			// row
		{
			clearA.x = (cryptedA.x + References.GRID_SIZE - 1) % References.GRID_SIZE;
			clearA.y = cryptedA.y;
			clearB.x = (cryptedB.x + References.GRID_SIZE - 1) % References.GRID_SIZE;
			clearB.y = cryptedB.y;
			return new PairPointReturn(clearA, clearB);
		} else // two letters form a rectangle
		{
			clearA.x = cryptedB.x;
			clearA.y = cryptedA.y;
			clearB.x = cryptedA.x;
			clearB.y = cryptedB.y;
			return new PairPointReturn(clearA, clearB);
		}
	}
}
