package info;

import java.net.InetAddress;

//import org.apache.commons.codec.binary.Base64;

public class ServerInformation {
    // Connection info
    public InetAddress localIp = null;
    public InetAddress remoteIp = null;
    public int port = -1;
    
    // Server info
    public int supports = 0;
    public String version = "JM_v0.1";
}
