package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import info.Logging;

public class InputServerHandler implements Runnable {
        
    private Server parentServer = null;
    private Thread thread = null;
    private String message = "";
    private boolean finished = false;
    
    private InputStreamReader consoleInStream = new InputStreamReader(System.in);
    private OutputStreamWriter consoleOutStream = new OutputStreamWriter(System.out);
    private BufferedReader consoleInReader = new BufferedReader(consoleInStream);
    private BufferedWriter consoleOutWriter = new BufferedWriter(consoleOutStream);

    public InputServerHandler(Server server) {
        this.parentServer = server;
        
        Logging.log(Logging.DEBUG, "InputHandler making own thread");
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void listen() {
        Logging.log(Logging.DEBUG, "Started listening to console input");
        while (!finished) {
            try {
                this.message = this.consoleInReader.readLine();
                this.consoleOutWriter.write(this.message);
                if (!this.message.equals("")) {
                	this.parentServer.handleInput(this.message);
                }
            } catch (IOException e) {
                Logging.log(Logging.ERROR, "Could process message, ERROR: " + e.getMessage());
            }
        }
    }

    public void run() {
        this.listen();
    }
}