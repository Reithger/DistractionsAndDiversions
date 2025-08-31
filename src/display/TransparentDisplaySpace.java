package display;

import java.awt.Color;
import java.util.ArrayList;

import display.effects.BounceWordEffect;
import display.effects.CuteTitleEffect;
import display.effects.DarkSoulsEffect;
import display.effects.NounVerbedEffect;
import display.effects.ScreenEffect;
import visual.composite.HandlePanel;
import visual.frame.WindowFrame;

public class TransparentDisplaySpace {
	
//---  Constants   ----------------------------------------------------------------------------
	
	private static final Color TRANSPARENT = new Color(255, 255, 255, 0);
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private volatile WindowFrame wf;
	private volatile HandlePanel hp;
	
	private volatile ArrayList<ScreenEffect> effects;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public TransparentDisplaySpace(int width, int height) {
		initializeOnSize(width, height);
	}
	
	private void initializeOnSize(int width, int height) {
		if(wf != null) {
			if(effects != null) {
				for(ScreenEffect se : effects) {
					se.deactivate();
				}
			}
			wf.disposeFrame();
		}
		wf = new WindowFrame(width, height) {
			
			@Override
			public void repaint() {
				if(effects != null) {
					for(ScreenEffect se : effects) {
						if(se.getActive()) {
							se.iterate(hp);
						}
					}
				}
				super.repaint();
			}
		
		};
		wf.setFrameShapeNormal();
		hp = new HandlePanel(0, 0, wf.getWidth(), wf.getHeight()) {
			
			@Override
			public void keyEvent(char key) {
				System.out.println(key);
				if(key == 'c') {
					wf.resize(1440 * 4 / 5, 1080 * 4 / 5);
					hp.resize(1440 * 4 / 5, 1080 * 4 / 5);
					wf.removePanel("main");
					wf.addPanel("main", hp);
					wf.showPanel("main");
					initializeEffects(1440 * 4 / 5, 1080 * 4 / 5);
				}
				if(key == 'v') {
					wf.resize(1920 * 4 / 5, 1080 * 4 / 5);
					hp.resize(1920 * 4 / 5, 1080 * 4 / 5);
					wf.removePanel("main");
					wf.addPanel("main", hp);
					wf.showPanel("main");
					initializeEffects(1920 * 4 / 5, 1080 * 4 / 5);
				}
			}
			
		};
		wf.setBackgroundColor(TRANSPARENT);
		hp.setBackgroundColor(TRANSPARENT);
		wf.setShapedFrameTransparent(1f);
		wf.addPanel("main", hp);
		wf.showPanel("main");
		
		initializeEffects(wf.getWidth(), wf.getHeight());
	}
	
	private void initializeEffects(int wid, int hei) {
		if(effects != null) {
			for(ScreenEffect se : effects) {
				se.deactivate();
			}
			effects.clear();
		}
		effects = new ArrayList<ScreenEffect>();
		effects.add(new BounceWordEffect("bounce", wid, hei));
		effects.add(new DarkSoulsEffect("narrator", wid, hei));
		effects.add(new CuteTitleEffect("cute", wid, hei));
		
		
		effects.get(0).activate();
		//effects.get(0).interpretFinalInput("string of test text for seeing them bounce immediately");
		
		
		effects.get(1).activate();
		effects.get(1).interpretFinalInput("narrator Distractions Loaded");
		effects.get(1).interpretFinalInput("next");
		
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void applyStringData(String in) {
		for(ScreenEffect se : effects) {
			se.searchActivationTerm(in);
			if(se.getActive()) {
				try {
					se.interpretFinalInput(in);
				}
				catch(Exception e) {
					e.printStackTrace();
					se.deactivate();
				}
			}
		}
	}
	
}
