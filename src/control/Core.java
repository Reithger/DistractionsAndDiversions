package control;

import core.SocketControl;

/**
 * 
 * Main class of this project that contains the 'main' function.
 * 
 * Does the setup of the Socket environment to initialize the captions sub-program and
 * create the CaptionReceiver object that takes the socket data from the sub-program.
 * 
 * Sets up this end to listen on port 6000 and send out on port 6005 for Captions.
 * 
 * Sets up this end to listen on port 6500 and send out on port 6505 for receiving instructions.
 * 
 * TODO: Need a flexible receiver that can have multiple senders on a port by pro-actively ending
 * a connection once it receives anything on it.
 * 
 * See CaptionReceiver class for most details of the operations of this project.
 * 
 */

public class Core {

	public static void main(String[] args) {
		SocketControl socket = new SocketControl();
		CaptionReceiver cr = new CaptionReceiver();
		
		// For listening to and establishing a Captions sub-program that controls this program
		
		socket.createSocketInstance("captions");
		
		socket.setInstanceListenPort("captions", 6000);
		
		socket.setValidationQuiet(true);
		socket.verifySubprogramReady("./distdac/", "StylizedCaptions.jar", "../assets/StylizedCaptions.jar", "/control/assets/StylizedCaptions.jar");		
		socket.setInstanceSubprogramJava("captions", "./distdac/StylizedCaptions.jar");
		
		
		socket.attachJavaReceiver("captions", cr);
		
		socket.setInstanceKeepAlive("captions", 2000);
		socket.setInstanceTimeout("captions", 10000);
		
		socket.setInstanceQuiet("captions", true);
		System.out.println("Should be silent!");
		
		try {
			socket.runSocketInstance("captions");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
