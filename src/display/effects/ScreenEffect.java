package display.effects;

import visual.composite.HandlePanel;

/**
 * 
 * Abstract class containing the foundations of how a Screen Effect works for adding new ones.
 * 
 * Built-in is that a ScreenEffect has an activation phrase (a string that, once seen in the socket
 * data, will activate or deactivate this ScreenEffect) and an interpretation function that toggles
 * whether or not the Effect should be called to iterate with each repaint of the Window.
 * 
 * Yeah it's tied to Frame Rate, I don't want to mess with more Threads right now after getting
 * the Socket library working, that was a mess.
 * 
 * Basically just extend this and override the iterate function for what should happen ~30 times
 * a second when it's active (ex. entities moving, fading, etc.) and the interpretFinalInput function
 * for what new string data from the captions socket should do (ex. add new words to the display)
 * 
 * All ScreenEffects draw to the same Window which is transparent.
 * 
 */

public abstract class ScreenEffect {
	
//---  Instance Variables   -------------------------------------------------------------------

	private boolean active;
	
	private String activationPhrase;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public ScreenEffect(String phrase) {
		activationPhrase = phrase;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public abstract void iterate(HandlePanel p);
	
	public abstract void interpretFinalInput(String in);
	
	public void searchActivationTerm(String in) {
		if(in.contains(activationPhrase)) {
			if(getActive()) {
				deactivate();
			}
			else {
				activate();
			}
		}
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public boolean getActive() {
		return active;
	}
	
	public String getPhrase() {
		return activationPhrase;
	}
	
}
