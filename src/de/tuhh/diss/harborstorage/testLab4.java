package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.PhysicalCrane;
import de.tuhh.diss.harborstorage.sim.PhysicalHarborStorage;
import de.tuhh.diss.harborstorage.sim.StorageElement;

public class testLab4 {

	/**
	 * @param args
	 */
	public static PhysicalCrane physicalCrane;
	public static PhysicalHarborStorage physicalHarborStorage = new PhysicalHarborStorage();

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Packet packet = new Packet(1, 2, 0, "Packet 1", 50);
		physicalCrane = physicalHarborStorage.getCrane();
		CraneControl craneControl = new CraneControl(physicalCrane);
		
//		StoragePlace[] slotDetails = physicalHarborStorage.getStoragePlacesAsArray();
//		StoragePlace suitableSlot = slotDetails[25];
//		System.out.println("----------- : " + suitableSlot.getPositionX()+" --------------- : " + suitableSlot.getPositionY());

		craneControl.storePacket(3, 2, packet);
		craneControl.storePacket(3, 2, packet);

		StorageElement storageElement = craneControl.retrievePacket(5, 2);
		System.out.println("ghjklkjhgf---------------"+storageElement.getDescription());
		craneControl.shutdown();
	}

}
