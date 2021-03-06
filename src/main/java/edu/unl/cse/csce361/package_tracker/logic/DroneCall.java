package edu.unl.cse.csce361.package_tracker.logic;

import edu.unl.cse.csce361.package_tracker.frontend.Printer;

public class DroneCall implements Runnable {
	private static final logicFacade logic = logicFacade.getInstance();
	private Thread t;
	private String threadName;
	private int destination;

	public DroneCall(String name, int destination) {
		threadName = name;
		this.destination = destination;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public void run() {
		try {
			int droneID = logic.findAvilableDrone();
			while (logic.findNextWarehouse(logic.getDrone().get(droneID).getCurrentLocation(), destination) != 0) {
				Printer.PrintDroneTakeOff(droneID, logic.getDrone().get(droneID).getCurrentLocation(), "");
				int nextLocation = logic.findNextWarehouse(logic.getDrone().get(droneID).getCurrentLocation(),
						destination);
				logic.getDrone().get(droneID).setStatus("Calling");
				int time = logic.findTimeNeededForWarehouse(logic.getDrone().get(droneID).getCurrentLocation(),
						nextLocation);
				int nextWarehouse = logic.findNextWarehouse(logic.getDrone().get(droneID).getCurrentLocation(),
						destination);
				logic.getDrone().get(droneID).setCurrentLocation(nextWarehouse);

				Thread.sleep(time);
				Printer.PrintDroneArrive(droneID, logic.getDrone().get(droneID).getCurrentLocation(), "");
				logic.getDrone().get(droneID).setStatus("Idle");
				Thread.sleep(1000);
			}
			Printer.PrintDroneFinal(droneID, destination, "");

		} catch (InterruptedException e) {
			Printer.printThreadException(threadName);
		}
	}

}
