package display.effects;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class BounceWord {
	
	private static Color[] colors;
	
	private static ArrayList<Font> fonts;
	
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
	private Font font;
	
	public BounceWord(String in, int wordHei, int wordWid, int wid, int hei, int startY) {
		birth = System.currentTimeMillis();
		Random rand = new Random();
		col = colors[rand.nextInt(colors.length)];
		font = fonts == null ? null : fonts.get(rand.nextInt(fonts.size()));
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
	
	public static void assignFonts(ArrayList<Font> in) {
		fonts = in;
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
		if(newX - wordWidth / 2 < 0) {
			newX = Math.abs(newX);
			xMom *= -1;
			if(xMom < 0) {
				xMom = wordWidth;
			}
		}
		else if(newX + wordWidth / 2 >= maxWid) {
			newX = maxWid - (newX + wordWidth / 2 - maxWid);
			xMom *= -.9;
			if(xMom > 0) {
				xMom = -wordWidth;
			}
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
	
	public Font getFont() {
		return font;
	}
	
	@Override
	public String toString() {
		return getString() + "_" + getBirth();
	}
}
