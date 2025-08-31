package control;

import java.util.ArrayList;

import core.JavaReceiver;
import display.TransparentDisplaySpace;

/**
 * 
 * Central logic point for the main behavior of this project.
 * 
 * This class receives the socket data from the caption sub-program and passes it to the view
 * context to interpret. This class instantiates the TransparentDisplaySpace (the view class that
 * contains the WindowFrame and HandlePanel) and decides when the received string data from the
 * socket is ready to send for interpretation.
 * 
 * TODO: Some way that a person could import this as a library and add their own Effects without
 * having to change this code (an add function that takes any subclass of ScreenEffect and puts it
 * into the list of effects); what object would they instantiate for the library? Needs access to
 * this CaptionReceiver class to call an add function on it; maybe move Socket logic into this
 * constructor so this can be a starting point for the whole project).
 * 
 */

public class CaptionReceiver implements JavaReceiver{
	
//---  Constants   ----------------------------------------------------------------------------

	private static final double SIZE_MOD = .8;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private TransparentDisplaySpace tds;
	private double sizeMod;
	private String lastString;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public CaptionReceiver() {
		sizeMod = SIZE_MOD;
		tds = new TransparentDisplaySpace((int)(sizeMod * 1920), (int)(sizeMod * 1080));
	}

//---  Operations   ---------------------------------------------------------------------------
	
	@Override
	public void receiveSocketData(String socketData, ArrayList<String> tags) {
		if(socketData.startsWith("Keepalive message")) {
			return;
		}
		System.out.println("Distractions: " + socketData);
		if(lastString == null) {
			lastString = socketData;
		}
		boolean interpret = !socketData.startsWith(lastString.substring(0, lastString.length() / 2));
		if(interpret) {
			tds.applyStringData(lastString);
		}
		lastString = socketData;
	}

}

