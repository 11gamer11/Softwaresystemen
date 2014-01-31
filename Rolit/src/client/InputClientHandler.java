package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import info.Logging;

public class InputClientHandler implements Runnable {
        
    private Client parentClient = null;
    private Thread thread = null;
    private String message = "";
    private boolean finished = false;
    
    private InputStreamReader consoleInStream = new InputStreamReader(System.in);
    private OutputStreamWriter consoleOutStream = new OutputStreamWriter(System.out);
    private BufferedReader consoleInReader = new BufferedReader(consoleInStream);
    private BufferedWriter consoleOutWriter = new BufferedWriter(consoleOutStream);

    public InputClientHandler(Client client) {
        this.parentClient = client;
        
        Logging.log(Logging.DEBUG, "InputHandler making own thread");
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void run() {
        Logging.log(Logging.DEBUG, "Started listening to console input");
        while (!finished) {
            try {
                this.message = this.consoleInReader.readLine();
                this.consoleOutWriter.write(this.message);
                this.parentClient.handleInput(this.message);
            } catch (IOException e) {
                Logging.log(Logging.ERROR, "Could process message, ERROR: " + e.getMessage());
            }
        }
    }
}