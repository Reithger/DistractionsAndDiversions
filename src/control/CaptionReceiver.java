package control;

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
	public void receiveSocketData(String socketData) {
		if(socketData.equals("Keepalive message")) {
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

