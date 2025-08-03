package display.effects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;

import visual.composite.HandlePanel;

public class NounVerbedEffect extends ScreenEffect{

//---  Constants   ----------------------------------------------------------------------------
	
	private final static double DECAY_TIMER = 15000;
	
	private final static String FONT_PATH_LOCAL_COOL = "../../control/assets/OptimusPrinceps.ttf";
	private final static String FONT_PATH_JAR_COOL = "/control/assets/OptimusPrinceps.ttf";
	
	private final static int COOL_FONT_SIZE = 112;
	private final static Color COOL_FONT_COLOR_REF = new Color(255, 187, 65);
	private final static Color COOL_BACK_COLOR = new Color(0, 0, 0, 3);
	
	private final static String FONT_PATH_LOCAL_CUTE = "../../control/assets/Orange Gummy.ttf";
	private final static String FONT_PATH_JAR_CUTE = "/control/assets/Orange Gummy.ttf";
	
	private final static int CUTE_FONT_SIZE = 84;
	private final static Color CUTE_FONT_COLOR_REF = new Color(0, 255, 255);
	private final static Color CUTE_BACK_COLOR = new Color(255, 44, 255, 2);
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private int screenWid;
	
	private int screenHei;
	
	private String fontName;
	
	private String nounToVerb;
	
	private Font use;
	
	private Font soft;
	
	private long startTime;
	
	private int counter;
	
	private int wordHeight;
	
	private int turnPoint;
	
	private boolean isCute;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public NounVerbedEffect(String phrase, int wid, int hei, boolean cute) {
		super(phrase);
		screenWid = wid;
		screenHei = hei;
		isCute = cute;
	}
	
//---  Operations   ---------------------------------------------------------------------------

	@Override
	public void iterate(HandlePanel p) {
		enableFont(p);
		
		if(nounToVerb != null) {
			double perc = (System.currentTimeMillis() - startTime) / DECAY_TIMER;
			double progress = Math.sin((perc * 2.0 - 1.0) * (Math.PI / 2.0));
			
			int alph =  (int)((1.0 - Math.abs(progress)) * 324.0);
			
			alph = alph < 0 ? 0 : alph > 255 ? 255 : alph;
			
			Color col = isCute ? new Color(CUTE_FONT_COLOR_REF.getRed(), CUTE_FONT_COLOR_REF.getGreen(), CUTE_FONT_COLOR_REF.getBlue(), alph) : new Color(COOL_FONT_COLOR_REF.getRed(), COOL_FONT_COLOR_REF.getGreen(), COOL_FONT_COLOR_REF.getBlue(), alph);
			Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), alph / 8);
			
			Color back = isCute ? CUTE_BACK_COLOR : COOL_BACK_COLOR;
			
			boolean fadeOut = perc > .5;
			
			if(!fadeOut) {
				int cap = counter > 80 ? 80 : counter;
				p.handleRectangle("nounverb_background" + counter, "static", 10, screenWid / 2, screenHei / 2 + (int)(wordHeight * .1), screenWid * 2, (int)(wordHeight * .6) + cap, back, back);
			}
			else {
				if(turnPoint == -1) {
					turnPoint = counter;
				}
				int ref = turnPoint - (counter - turnPoint);
				p.removeElement("nounverb_background" + ref);
			}

			int moveMod = counter / 30;

			p.removeElementPrefixed("nounverb1");
			p.handleText("nounverb1", "static", 15, screenWid / 2, screenHei / 2, screenWid, screenHei, use, nounToVerb, col);
			p.handleText("nounverb1_soft1", "static", 15, screenWid / 2, screenHei / 2, screenWid * 2, screenHei, soft, nounToVerb, col2);
			p.handleText("nounverb1_soft2", "static", 15, screenWid / 2 - 5 - moveMod, screenHei / 2, screenWid * 2, screenHei, soft, nounToVerb, col2);
			p.handleText("nounverb1_soft3", "static", 15, screenWid / 2 + 5 + moveMod, screenHei / 2, screenWid * 2, screenHei, soft, nounToVerb, col2);

			counter++;
			
			if(perc > 1.0) {
				p.removeElementPrefixed("nounverb");
				deactivate();
				counter = 0;
			}
			
		}
	}

	@Override
	public void interpretFinalInput(String in) {
		if(!in.contains(getPhrase())){
			return;
		}
		int posit = in.indexOf(getPhrase()) + getPhrase().length() + 1;
		
		if(posit == -1 || posit >= in.length()) {
			deactivate();
			return;
		}

		String check = in.substring(posit);
			
		String[] words = check.split(" ");
		if(words.length >= 2) {
			nounToVerb = firstCapital(words[0]) + " " + firstCapital(words[1]);
			/*
			if(use != null) {
				FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(use);
				int index = 2;
				while(index < words.length && fm.stringWidth(nounToVerb + " " + words[index]) < screenWid * .9) {
					nounToVerb += " " + firstCapital(words[index]);
				}
			}
			*/
			startTime = System.currentTimeMillis();
			counter = 0;
			turnPoint = -1;
		}
	}
	
//---  Support Methods   ----------------------------------------------------------------------
	
	private void enableFont(HandlePanel p) {
		if(fontName == null) {
			try {
				fontName =	p.registerFont(isCute ? FONT_PATH_LOCAL_CUTE : FONT_PATH_LOCAL_COOL);
			} catch (Exception e) {
				try {
					fontName = p.registerFont(isCute ? FONT_PATH_JAR_CUTE : FONT_PATH_JAR_COOL);
				} catch (Exception e1) {
					fontName = "Sans Serif";
				}
			}
			use = new Font(fontName, Font.PLAIN, isCute ? CUTE_FONT_SIZE : COOL_FONT_SIZE);
			soft = new Font(fontName, Font.PLAIN, isCute ? CUTE_FONT_SIZE * 12 / 10 : COOL_FONT_SIZE * 12 / 10);
			FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(use);
			wordHeight = fm.getHeight();
		}
	}

	private String firstCapital(String in) {
		if(in.length() > 1)
			return in.substring(0, 1).toUpperCase() + in.substring(1);
		return "";
	}

}
