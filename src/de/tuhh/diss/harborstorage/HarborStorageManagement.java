package de.tuhh.diss.harborstorage;

import java.util.ArrayList;
import de.tuhh.diss.harborstorage.sim.HighBayStorage;
import de.tuhh.diss.harborstorage.sim.PhysicalCrane;
import de.tuhh.diss.harborstorage.sim.PhysicalHarborStorage;
import de.tuhh.diss.harborstorage.sim.StorageException;
import de.tuhh.diss.harborstorage.sim.StoragePlace;
import de.tuhh.diss.io.SimpleIO;

/**
 * The HarborStorageManagement program implements HighBayStorage, which has all
 * the logics needed in a Harbour Storage Management System.
 * 
 * @authors Prashanth Reddy Ujjalli, Raja Vardhan Reddy Kothakapu
 * @version 1.0
 * @since 2017-01-06
 */
public class HarborStorageManagement implements HighBayStorage {

	public static final int TOTALSLOTS = 29;
	public static final int SLOTDEPTH = 2;
	int numPackets;
	//creating a ArrayList to store Slot Number of already filled slots.
	ArrayList<Integer> filledSlots = new ArrayList<Integer>(); 
	CraneControl craneControl;
	// creating a Packet Array to store the details of available Packets in Harbour
	Packet[] storedPackets = new Packet[TOTALSLOTS];
	PhysicalHarborStorage physicalHarborStorage;

	/**
	 * This is the default constructor used to create a Physical crane
	 * 
	 * @param null.
	 */
	HarborStorageManagement() {
		physicalHarborStorage = new PhysicalHarborStorage();
		PhysicalCrane physicalCrane = physicalHarborStorage.getCrane();
		craneControl = new CraneControl(physicalCrane);
	}

	/**
	 * This is the method used to store a new Packet in Harbour Storage System.
	 * 
	 * @param Width, Height, Depth, Description, Weight.
	 * @return Integer value (Packet ID).
	 * @exception StorageException On input error.
	 * @see StorageException
	 */
	public int storePacket(int width, int height, int depth,
			String description, int weight) throws StorageException {
		int id;
		Packet packet = new Packet(width, height, depth, description, weight);
		StoragePlace suitableSlotNumber = findSuitableSlot(packet);
		if (suitableSlotNumber == null) {
//			Throwing a new Exception When Suitable Slot not found
			throw new StorageException("No Suitable Slot Found");
		} else {
			int x = suitableSlotNumber.getPositionX();
			int y = suitableSlotNumber.getPositionY();
			try {
				// using synchronized method to avoid thread interference 
				synchronized (packet) {
					craneControl.storePacket(x, y, packet);
//					Adding Packet to storedPackets, after storing it in a slot.
					storedPackets[numPackets] = packet;
					id = storedPackets[numPackets].assignId();
					storedPackets[numPackets].setId(id);
					storedPackets[numPackets].setLocation(x, y);
//					if (numPackets < TOTALSLOTS - 1)
						numPackets++;
					// updating new Slot Number in filledSlots
					filledSlots.add(suitableSlotNumber.getNumber());
				}
			} catch (Exception e) {
				throw new StorageException(e.getMessage());
			}
			return id;
		}
	}

	/**
	 * This is the method used to retrieve a existing Packet in Harbour Storage System.
	 * 
	 * @param Description.
	 * @return null.
	 * @exception StorageException On input error.
	 * @see StorageException
	 */
	public void retrievePacket(String description) throws StorageException {
		Packet retrieve = null;
		for (int i = 0; i < numPackets; i++) {
			retrieve = storedPackets[i];
			if (retrieve != null
					&& description.equals(retrieve.getDescription())) {
				int x = retrieve.getXLocation();
				int y = retrieve.getYLocation();
				Packet retpacket = (Packet) craneControl.retrievePacket(x, y);
				System.out.println("::::::::::::: "+ retpacket.getId());
				StoragePlace retrievedSlot = Slot.getSlot(
						physicalHarborStorage, x, y);
				storedPackets[i] = null;
				SimpleIO.println("Packet retrieved");
				for (int j = 0; j < filledSlots.size(); j++) {
					if (filledSlots.get(j) == retrievedSlot.getNumber()) {
						// removing slot number from filled slots List
						filledSlots.remove(j);
						break;
					}
				}
			}
		}
	}

	/**
	 * This is the method used to retrieve all available Packets in Harbour.
	 * 
	 * @param null.
	 * @return Packet[] (Array of Packets).
	 */
	public Packet[] getPackets() {
		//
		return storedPackets;
	}

	/**
	 * This is the method used to find Suitable Slot for a new Packet in Harbour.
	 * 
	 * @param Packet.
	 * @return StoragePlace (Slot).
	 */
	public StoragePlace findSuitableSlot(Packet packetTOStore) {
		int slotFound = 0;
		int slotNumber = 0;
//		Retrieve slot details from PhysicalHarborStorage
		StoragePlace[] slots = physicalHarborStorage.getStoragePlacesAsArray();
//		Minimum Volume is always Packet Volume
		int minVolume = packetTOStore.getWidth() * packetTOStore.getHeight()
				* packetTOStore.getDepth();
//		Smallest Volume available to store the Packet
		int smallestVolume = (slots[0].getHeight() * slots[0].getWidth() * slots[0]
				.getDepth());
		for (int i = (slots.length) - 1; i >= 0; i--) {
			if (packetTOStore.getWidth() <= slots[i].getWidth()
					&& packetTOStore.getHeight() <= slots[i].getHeight()
					&& packetTOStore.getWeight() <= slots[i].getLoadCapacity()
					&& packetTOStore.getDepth() <= SLOTDEPTH) {
				int newVolume = (slots[i].getHeight() * slots[i].getWidth() * slots[i]
						.getDepth());
				/* Finding the best suitable slot, based on Minimum Volume of
				   Slot & Slot Volume greater than Packet Volume*/
				if (minVolume <= newVolume && newVolume <= smallestVolume) {
					{
						if (filledSlots.isEmpty()) {
							slotNumber = slots[i].getNumber();
							smallestVolume = newVolume;
							slotFound += 1;
						} else if (!filledSlots
								.contains((slots[i].getNumber()))) {
							slotNumber = slots[i].getNumber();
							smallestVolume = newVolume;
							slotFound += 1;
						}
					}
				}
			}
		}
		if (slotFound == 0) {
			SimpleIO.println("No Slot is Found");
			return null;
		} else {
			return slots[slotNumber];
		}
	}

	/**
	 * This is the method used to shutdown the Physical Crane.
	 * 
	 * @param null.
	 * @return null.
	 */
	public void shutdown() {
		craneControl.shutdown();
	}
}
