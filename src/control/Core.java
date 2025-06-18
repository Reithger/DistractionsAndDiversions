package control;

import core.SocketControl;

/**
 * 
 * Main class of this project that contains the 'main' function.
 * 
 * Does the setup of the Socket environment to initialize the captions sub-program and
 * create the CaptionReceiver object that takes the socket data from the sub-program.
 * 
 * Sets up this end to listen on port 6000 and send out on port 6005.
 * 
 * See CaptionReceiver class for most details of the operations of this project.
 * 
 */

public class Core {

	public static void main(String[] args) {
		SocketControl socket = new SocketControl();
		
		socket.createSocketInstance("captions");
		
		socket.setInstanceListenPort("captions", 6000);
		socket.setInstanceSendPort("captions", 6005);
		
		socket.verifySubprogramReady("./distdac/", "StylizedCaptions.jar", "../assets/StylizedCaptions.jar", "/control/assets/StylizedCaptions.jar");		
		socket.setInstanceSubprogramJava("captions", "./distdac/StylizedCaptions.jar");
		
		socket.attachJavaReceiver("captions", new CaptionReceiver());
		
		socket.setInstanceKeepAlive("captions", 2000);
		socket.setInstanceTimeout("captions", 10000);
		
		socket.runSocketInstance("captions");
	}
	
}
