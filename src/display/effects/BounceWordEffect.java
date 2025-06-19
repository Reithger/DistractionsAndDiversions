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

	
	private ArrayList<BounceWord> words;
	
	private int spaceWidth;
	
	private int spaceHeight;
	
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
	public void iterate(HandlePanel hp) {
		ArrayList<BounceWord> toRemove = new ArrayList<BounceWord>();
		for(BounceWord bw : words) {
			int[] position = bw.iterateMovement();
			if(bw.getAge() > 10000) {
				hp.removeElement(bw.getString() + "_" + bw.getBirth());
				toRemove.add(bw);
			}
			else {
				hp.addText(bw.getString() + "_" + bw.getBirth(), 5, "word_bounce", position[0], position[1], (int)(bw.getWordWidth() * 1.2), (int)(bw.getWordHeight() * 1.2), bw.getString(), DEFAULT_FONT, bw.getColor(), false, false, true);
			}
		}
		for(BounceWord bw : toRemove) {
			words.remove(bw);
		}
		if(words.size() > MAX_WORDS) {
			words.remove(0);
		}
	}

}
