package net.codeartha.playfair;

import java.awt.Point;



/**Allows the encrypt and decryptCoords functions to return 
 * two point objects each storing a different value
 * 
 * @author Artha
 */
final class PairPointReturn
{
	private final Point first;
	private final Point second;

	public PairPointReturn(Point first, Point second)
	{
		this.first = first;
		this.second = second;
	}

	public Point getFirst()
	{
		return first;
	}

	public Point getSecond()
	{
		return second;
	}
}
