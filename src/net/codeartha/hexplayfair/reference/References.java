package net.codeartha.hexplayfair.reference;

public class References {
	public static final String VERSION = "1.0.0-alpha";
	
	public static final int ARRAY_SIZE = 3; //key grid is square and index start at 0, so this is realsize -1
	
	public static final int GRID_SIZE = 4; //realgrid size, always = to ARRAY_SIZE + 1
	
	public static final String HELP = "Syntax: \n"
			+ "\tjava Playfair [action] \"passphrase\" \"message\" \n"
			+ "Or \n"
			+ "\tjava Playfair [action] --key \"passphrase\" --freetype \n"
			+ "\n" + "Actions: \n" + "--encrypt or -e \n"
			+ "--decrypt or -d \n" + "--help (ignores further arguments) \n"
			+ "--version (ignores further arguments) \n" + "\n" + "Key: \n"
			+ "--key/-k sets the key \n" + "\n" + "Modifier: \n"
			+ "--freetype (WIP) \n";

}
