package display.effects;

import java.awt.Color;
import java.awt.Font;

import visual.composite.HandlePanel;

public class NounVerbedEffect extends ScreenEffect{

	private final static double DECAY_TIMER = 10000;
	private final static String FONT_PATH_LOCAL = "../../control/assets/OptimusPrinceps.ttf";
	private final static String FONT_PATH_JAR = "/control/assets/OptimusPrinceps.ttf";
	
	private int screenWid;
	
	private int screenHei;
	
	private String fontName;
	
	private String nounToVerb;
	
	private Font use;
	
	private long startTime;
	
	private int counter;
	
	public NounVerbedEffect(String phrase, int wid, int hei) {
		super(phrase);
		screenWid = wid;
		screenHei = hei;
	}

	@Override
	public void iterate(HandlePanel p) {
		if(fontName == null) {
			try {
				fontName =	p.registerFont(FONT_PATH_LOCAL);
			} catch (Exception e) {
				try {
					fontName = p.registerFont(FONT_PATH_JAR);
				} catch (Exception e1) {
					fontName = "Sans Serif";
				}
			}
			use = new Font(fontName, Font.PLAIN, 212);
		}
		
		if(nounToVerb != null) {
			double progress = (System.currentTimeMillis() - startTime) / DECAY_TIMER;
			progress = Math.sin((progress * 2.0 - 1.0) * (Math.PI / 2.0));
			
			int alph =  (int)((1.0 - Math.abs(progress)) * 255.0);
			
			alph = alph < 0 ? 0 : alph > 255 ? 255 : alph;
			
			Color col = new Color(255, 187, 65, alph);
			
			if(counter % 2 == 0) {
				p.handleText("nounverb2", "static", 15, screenWid / 2, screenHei / 2, screenWid, screenHei, use, nounToVerb, col);
				p.removeElement("nounverb1");
			}
			else {
				p.handleText("nounverb1", "static", 15, screenWid / 2, screenHei / 2, screenWid, screenHei, use, nounToVerb, col);
				p.removeElement("nounverb2");
			}
			
			counter++;
			
			if(progress > 1.0) {
				p.removeElementPrefixed("nounverb");
				deactivate();
			}
			
		}
	}

	@Override
	public void interpretFinalInput(String in) {
		if(!in.contains(getPhrase())){
			return;
		}
		int posit = in.indexOf(getPhrase()) + getPhrase().length() + 1;

		if(posit >= 0 && posit < in.length());
			String check = in.substring(posit);
		String[] words = check.split(" ");
		if(words.length >= 2) {
			nounToVerb = words[0].substring(0, 1).toUpperCase() + words[0].substring(1) + " " + words[1].substring(0, 1).toUpperCase() + words[1].substring(1);
			//nounToVerb = (words[0] + "  " + words[1]).toUpperCase();
			startTime = System.currentTimeMillis();
			counter = 0;
		}
	}

}
