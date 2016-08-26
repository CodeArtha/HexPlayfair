package net.codeartha.playfair;

public class Sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Playfair pf = new Playfair();		// create new Playfair object
		pf.setKey("password");				// we set the passphrase for this object
		pf.generateKeyGrid();				// manipulates the passphrase to create the grid
		pf.printKeyGrid();					// just to debug
		
		pf.setMsgIn("atopsecretmessageyouwanttoencrypt");	// type in your message
		pf.encryptMsg();					// encrypting it
		System.out.println(pf.getMsgOut());
		pf.clearMsgIn();					// good practice to clear msgIn before setting a new one, but not necessary
		pf.setMsgIn(pf.getMsgOut());
		pf.clearMsgOut();					// clearing msgOut is always necessary!
		pf.decryptMsg();					// decrypting
		System.out.println(pf.getMsgOut());
	}

}
