package reso.examples.dv_routing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import reso.common.Network;
import reso.common.Node;
import reso.ip.IPAddress;
import reso.ip.IPHost;
import reso.ip.IPInterfaceAdapter;
import reso.ip.IPLayer;
import reso.ip.IPRouter;
import reso.scheduler.AbstractScheduler;
import reso.scheduler.Scheduler;
import reso.utilities.FIBDumper;
import reso.utilities.NetworkBuilder;
import reso.utilities.NetworkGrapher;

import reso.examples.dv_routing.DVRoutingProtocol;

/**
 * Problem
 */
public class Problem {	
	private static IPAddress getRouterID(IPLayer ip) {
		IPAddress routerID= null;
		for (IPInterfaceAdapter iface: ip.getInterfaces()) {
			IPAddress addr= iface.getAddress();
			if (routerID == null)
				routerID= addr;
			else if (routerID.compareTo(addr) < 0)
				routerID= addr;
		}
		return routerID;
	}
	
	/**
	 * Configure the routing protocol on every router.
	 * If routerDst is not null, configure a single router as destination.
	 * 
	 * @param network
	 * @param routerDst
	 * @throws Exception
	 */
	private static void setupRoutingProtocol(Network network, String routerDst)
		throws Exception
	{
		for (Node n: network.getNodes()) {
			if (!(n instanceof IPRouter))
				continue;
			IPRouter router= (IPRouter) n;
			boolean advertise= (routerDst == null) || (n.name.equals(routerDst));
			router.addApplication(new DVRoutingProtocol(router, advertise));
			router.start();
		}
	}
	
	public static void main(String [] args) {
		try {
			String filename= "reso/examples/dv_routing/problem-graph.txt";
			// String filename= "reso/examples/dv_routing/demo-graph.txt";
            AbstractScheduler scheduler= new Scheduler();
            Network network= NetworkBuilder.loadTopology(filename, scheduler);
            setupRoutingProtocol(network, "A");
            // setupRoutingProtocol(network, "F");

            // Run simulation -- first convergence
			scheduler.run();

			((IPHost) network.getNodeByName("B")).getIPLayer().getInterfaceByName("eth0").setMetric(60);
			// ((IPHost) network.getNodeByName("D")).getIPLayer().getInterfaceByName("eth0").down();

			//DVrouting attrchanged a modifier
				//appeller a nouveau methode qui va recalculer les routes

			// lien longueur en metre et vitesse de transmission en bit/sec (2/3 vitesse lumiere)

            // Display forwarding table for each node
            FIBDumper.dumpForAllRouters(network);			
                            
            // Run simulation (for 0.1 sec (no))
            scheduler.runUntil(1);
                            
            // Display forwarding table for each node
            FIBDumper.dumpForAllRouters(network);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
