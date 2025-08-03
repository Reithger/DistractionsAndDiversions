package display.effects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.ArrayList;

import visual.composite.HandlePanel;

public class BounceWordEffect extends ScreenEffect{

	private static final Font DEFAULT_FONT = new Font("Sans Serif", Font.BOLD, 32);
	private static final Color[] DEFAULT_WORD_COLORS = new Color[] {Color.red, Color.orange, Color.yellow, Color.green,
			Color.blue, Color.magenta, Color.pink, Color.cyan};
	
	private static final int MAX_WORDS = 50;
	
	private static final String JAR_PREFIX = "../../control/assets/";
	private static final String LOCAL_PREFIX = "/control/assets/";
	
	private static final String[] FONTS = new String[] {"Howdybun.ttf", "Orange Gummy.ttf",
														"Super Adorable.ttf", "SuperMario256.ttf"};

	
	private volatile ArrayList<BounceWord> words;
	
	private ArrayList<Font> fonts;
	
	private int spaceWidth;
	
	private int spaceHeight;
	
	private HandlePanel ref;
	
	public BounceWordEffect(String activate, int wid, int hei) {
		super(activate);
		words = new ArrayList<BounceWord>();
		spaceWidth = wid;
		spaceHeight = hei;
		BounceWord.assignColors(DEFAULT_WORD_COLORS);
	}
	
	@Override
	public void interpretFinalInput(String in) {
		int yStart = 0;
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(DEFAULT_FONT);
		for(String s : in.split(" ")) {
			if(s.length() > 3) {
				words.add(new BounceWord(s, fm.getHeight(), fm.stringWidth(s + " "), spaceWidth, spaceHeight, yStart));
				yStart -= 3;
			}
		}
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		if(ref != null) {
			ref.removeElementPrefixed("bounce");
		}
	}
	
	@Override
	public void iterate(HandlePanel hp) {
		if(fonts == null) {
			fonts = new ArrayList<Font>();
			for(String s : FONTS) {
				String nom;
				try {
					nom = hp.registerFont(JAR_PREFIX + s);
				}
				catch(Exception e) {
					try {
						nom = hp.registerFont(LOCAL_PREFIX + s);
					}
					catch(Exception e1) {
						nom = "";
					}
				}
				if(nom != null && !nom.equals("")) {
					fonts.add(new Font(nom, Font.PLAIN, 22));
				}
			}
			BounceWord.assignFonts(fonts);
		}
		if(ref == null) {
			ref = hp;
		}
		ArrayList<BounceWord> toRemove = new ArrayList<BounceWord>();
		for(int i = 0; i < words.size(); i++) {
			BounceWord bw = i < words.size() ? words.get(i) : null;
			if(bw == null) {
				continue;
			}
			if(bw.getAge() > 10000) {
				hp.removeElement("bounce_" + bw.toString());
				toRemove.add(bw);
			}
			else {
				int[] position = bw.iterateMovement();
				Font use = bw.getFont();
				hp.addText("bounce_" + bw.toString(), 5, "word_bounce", position[0], position[1], (int)(bw.getWordWidth() * 1.2), (int)(bw.getWordHeight() * 1.2), bw.getString(), use == null ? DEFAULT_FONT : use, bw.getColor(), false, false, true);
			}
		}
		for(BounceWord bw : toRemove) {
			hp.removeElement("bounce_" + bw.toString());
			words.remove(bw);
		}
		if(words.size() > MAX_WORDS) {
			BounceWord bw = words.get(0);
			hp.removeElement("bounce_" + bw.toString());
			words.remove(0);
		}
	}

}
