package display.effects;

import java.awt.Color;
import java.util.Random;

public class BounceWord {
	
	private static Color[] colors;
	
	private String word;
	private long birth;
	
	private double xMom;
	private double yMom;
	
	private int xPos;
	private int yPos;
	
	private int maxWid;
	private int maxHei;
	
	private int wordHeight;
	private int wordWidth;
	
	private Color col;
	
	public BounceWord(String in, int wordHei, int wordWid, int wid, int hei, int startY) {
		birth = System.currentTimeMillis();
		Random rand = new Random();
		col = colors[rand.nextInt(colors.length)];
		maxWid = wid;
		maxHei = hei;
		xPos = (int)(rand.nextDouble() * wid);
		yPos = startY;
		xMom = (rand.nextDouble() - .5) * 250.0;
		yMom = 2;
		word = in;
		wordHeight = wordHei;
		wordWidth = wordWid;
	}
	
	public static void assignColors(Color[] in) {
		colors = in;
	}
	
	public Color getColor() {
		return col;
	}
	
	public int getWordHeight() {
		return wordHeight;
	}
	
	public int getWordWidth() {
		return wordWidth;
	}
	
	public int[] iterateMovement() {
		int newX = (int)(xPos + xMom);
		if(newX < 0) {
			newX = Math.abs(newX);
			xMom *= -1;
		}
		else if(newX + wordWidth >= maxWid) {
			newX = maxWid - (newX + wordWidth - maxWid);
			xMom *= -.9;
		}
		xMom -= xMom > 0 ? .6 : xMom < 0 ? -.6 : 0;
		int newY = (int)(yPos + yMom);
		if(newY + wordHeight > maxHei) {
			newY = maxHei - (newY + wordHeight - maxHei);
			yMom *= -.8;
			xMom *= .8;
		}
		yMom += (Math.abs(yMom) > 15) ? 10 : 5;
		
		if(Math.abs(yMom) < 3 && yPos > maxHei * 14 / 15) {
			yMom = 0;
			newY = maxHei - wordHeight / 2;
		}

		xPos = newX;
		yPos = newY;
		return new int[] {newX, newY};
	}
	
	public String getString() {
		return word;
	}
	
	public long getAge() {
		return System.currentTimeMillis() - birth;
	}
	
	public long getBirth() {
		return birth;
	}
	
	public double getTotalMomentum() {
		return xMom + yMom;
	}
	
	@Override
	public String toString() {
		return getString() + "_" + getBirth();
	}
}
