package util;

import java.util.ArrayList;
import java.util.List;
import oshi.SystemInfo;
import oshi.software.os.InternetProtocolStats.IPConnection;
import oshi.software.os.OperatingSystem;

public class NetConnection {
    private final OperatingSystem os = new SystemInfo().getOperatingSystem();
    
    public List<IPConnection> getConnections() {
        List<Integer> ppidsJaContados = new ArrayList();
        List<IPConnection> connections = new ArrayList();
        List<IPConnection> network = this.os.getInternetProtocolStats().getConnections();
        
        for (IPConnection conn : network) {
            if (conn.getType().contains("tcp") && !ppidsJaContados.contains(conn.getowningProcessId())) {                
                ppidsJaContados.add(conn.getowningProcessId());
                connections.add(conn);
            }
        }
        
        return connections;
    }
}
