package display;

import java.awt.Color;
import java.util.ArrayList;

import display.effects.BounceWordEffect;
import display.effects.NounVerbedEffect;
import display.effects.ScreenEffect;
import visual.composite.HandlePanel;
import visual.frame.WindowFrame;

public class TransparentDisplaySpace {
	
//---  Constants   ----------------------------------------------------------------------------
	
	private static final Color TRANSPARENT = new Color(255, 255, 255, 0);
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private WindowFrame wf;
	private HandlePanel hp;
	
	private ArrayList<ScreenEffect> effects;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public TransparentDisplaySpace(int width, int height) {
		effects = new ArrayList<ScreenEffect>();
		wf = new WindowFrame(width, height) {
		
			@Override
			public void repaint() {
				super.repaint();
				for(ScreenEffect se : effects) {
					if(se.getActive()) {
						se.iterate(hp);
					}
				}
			}
		
		};
		wf.setFrameShapeNormal();
		hp = new HandlePanel(0, 0, wf.getWidth(), wf.getHeight());
		wf.setBackgroundColor(TRANSPARENT);
		hp.setBackgroundColor(TRANSPARENT);
		wf.setShapedFrameTransparent(1f);
		wf.addPanel("main", hp);
		wf.showPanel("main");
		
		effects.add(new BounceWordEffect("bounce", wf.getWidth(), wf.getHeight()));
		effects.add(new NounVerbedEffect("narrator", wf.getWidth(), wf.getHeight()));
		
		/*
		effects.get(0).activate();
		effects.get(0).interpretFinalInput("string of test text for seeing them bounce immediately");
		
		effects.get(1).activate();
		effects.get(1).interpretFinalInput("narrator bonfire restored");
		effects.get(1).interpretFinalInput("next");
		*/
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void applyStringData(String in) {
		for(ScreenEffect se : effects) {
			se.searchActivationTerm(in);
			if(se.getActive())
				se.interpretFinalInput(in);
		}
	}
	
}
