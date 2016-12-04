package net.codeartha.hexplayfair.utils;

public class Converter {
	public static void main(String[] args){
		//System.out.println(Dec2Hex(469));
		System.out.println(Hex2Dec("4E5A"));
	}
	
	public static String Dec2Hex( int decNbr ) {
		int ram[] = new int[50];
		int i = 0, length = 0;
		String result = "";
		
		while (decNbr > 0){
			ram[i] = decNbr % 16;
			decNbr = decNbr / 16;
			i++;
			length++;
		}
		
		for (i = length - 1; i>= 0; i--){
			char temp;
			switch(ram[i]){
				case 10: temp = 'A';
				break;
				case 11: temp = 'B';
				break;
				case 12: temp = 'C';
				break;
				case 13: temp = 'D';
				break;
				case 14: temp = 'E';
				break;
				case 15: temp = 'F';
				break;
				default: temp = (char) (48 + ram[i]); //we need to add 48 to get into the printable area of the ascii table 
			}
			
			result = result + temp;
		}
		
		
		return result;
	}
	
	public static int Hex2Dec(String hexNbr){
		hexNbr = hexNbr.toUpperCase();
		int result = 0;
		int multiplier = 0;
		
		for(int i = hexNbr.length() - 1; i >=0 ; i--){
			char ram = hexNbr.charAt(i);
			
			System.out.println(ram);
			switch (ram){
				case 'A': multiplier = 10;
				break;
				case 'B': multiplier = 11;
				break;
				case 'C': multiplier = 12;
				break;
				case 'D': multiplier = 13;
				break;
				case 'E': multiplier = 14;
				break;
				case 'F': multiplier = 15;
				break;
				default: multiplier = ((int) ram) - 48; //minus 48 to go back in the number region of ascii table
			}
			System.out.println(multiplier);
			result = (int) (result + (multiplier*Math.pow(16, hexNbr.length() - 1 - i)));
		}
		return result;
	}

}
