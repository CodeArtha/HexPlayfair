package net.codeartha.hexplayfair;

import net.codeartha.hexplayfair.reference.References;

/*
 Syntax:
 java Playfair [action] "passphrase" "message"
 Or
 java Playfair [action] --key "passphrase" "message"

 New syntax coming soon: (will allow multiline text entry, warning format not retained)
 java Playfair [action] --key "passphrase" --freetype

 Actions:
 --encrypt /-e
 --decrypt /-d
 --help (ignores further arguments)
 --version (prints version and legal owner)

 Key:
 --key/-k sets the key

 Modifier:
 --freetype (user may type multiline and end with \eof) ==WIP

 */

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ((args != null) && (args.length > 0)) {
			switch (args[0]) {
			case "--help":
				ShowHelp();
				break;
			case "--version":
				ShowVersion();
				break;
			case "-e":
				// should go straight to next case without break
			case "--encrypt":
				HexPlayfair pfe = new HexPlayfair();

				if (args[1] == "--key" || args[1] == "-k") {
					pfe.setKey(args[2]);
					pfe.generateKeyGrid();
					if (args[3] == "--freetype") {
						System.out
								.println("Feature not implemented yet. Just write your message between quotes.");
						pfe.clearAll();
						pfe = null;
						System.exit(0);
					} else {
						pfe.setMsgIn(args[3]);
					}
				} else {
					pfe.setKey(args[1]);
					pfe.generateKeyGrid();
					pfe.setMsgIn(args[2]);
				}

				pfe.encryptMsg();
				System.out.println(pfe.getMsgOut());
				pfe.clearAll();
				pfe = null;

				break;
			case "-d":
				// should go straight to next case
			case "--decrypt":
				HexPlayfair pfd = new HexPlayfair();

				if (args[1] == "--key" || args[1] == "-k") {
					pfd.setKey(args[2]);
					pfd.generateKeyGrid();
					if (args[3] == "--freetype") {
						System.out
								.println("Feature not implemented yet. Just write your message between quotes.");
						pfd.clearAll();
						pfd = null;
						System.exit(0);
					} else {
						pfd.setMsgIn(args[3]);
					}
				} else {
					pfd.setKey(args[1]);
					pfd.generateKeyGrid();
					pfd.setMsgIn(args[2]);
				}

				pfd.decryptMsg();
				System.out.println(pfd.getMsgOut());
				pfd.clearAll();
				pfd = null;

				break;
			default:
				System.out
						.println("I don't understand what you want me to do!");
				System.out.println("Check the syntax of your command.");
				System.out
						.println("Type \"java PlayFair --help \" to display syntax help");
				break;
			}
			System.exit(0);
		} else
			System.out
					.println("You cannot use this application without specifying arguments untill someone creates a GUI.");

	}

	private static void ShowHelp() {
		System.out.print(References.HELP);
	}

	private static void ShowVersion() {
		System.out.println("Current version: " + References.VERSION);
	}

}
