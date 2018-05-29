package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.PhysicalCrane;
import de.tuhh.diss.harborstorage.sim.StorageElement;
import de.tuhh.diss.harborstorage.sim.StorageException;

/**
 * The CraneControl program, which is used control a Physical Crane in a Harbour
 * Storage System
 * 
 * @authors Prashanth Reddy Ujjalli, RajaVardhan Reddy Kothakapu
 * @version 1.0
 * @since 2017-01-06
 */
public class CraneControl {
	PhysicalCrane physicalCrane;

	/**
	 * This is the constructor used to start a Physical crane
	 * 
	 * @param null.
	 */
	CraneControl(PhysicalCrane cr) {
		physicalCrane = cr;
		physicalCrane.start();
	}

	/**
	 * This is the method used to load a new Packet to Crane, move the Crane to
	 * desired Slot location, store Packet & coming back to initial loading
	 * position.
	 * 
	 * @param Width, Height, Depth, Description, Weight.
	 * @return null.
	 * @exception StorageException
	 *                On input error.
	 * @see StorageException
	 */
	public void storePacket(int x, int y, StorageElement packet)
			throws StorageException {
		physicalCrane.loadElement(packet);
		moveToX(x);
		moveToY(y);
		try {
			physicalCrane.storeElement();
		} catch (Exception e) {
			System.out.println(e);
			throw new StorageException(e.getMessage());
		} finally {
			moveToY(physicalCrane.getLoadingPosY());
			moveToX(physicalCrane.getLoadingPosX());
		}
	}

	/**
	 * This is the method used to move the Crane to desired Slot location, load
	 * existing Packet to Crane, coming back to initial loading position & then
	 * unloading the Packet.
	 * 
	 * @param X position, Y Position of slot.
	 * @return StorageElement (Packet).
	 */
	public StorageElement retrievePacket(int x, int y) {
		moveToX(x);
		moveToY(y);
		physicalCrane.retrieveElement();
		moveToY(physicalCrane.getLoadingPosY());
		moveToX(physicalCrane.getLoadingPosX());
		StorageElement storageElement = physicalCrane.unloadElement();
		return storageElement;
	}

	/**
	 * This is the method used to move the Crane to desired X location
	 * 
	 * @param X position
	 * @return null.
	 */
	public void moveToX(int x) {
		int newX;
		int actualX = physicalCrane.getPositionX();
		if (actualX < x) {
			physicalCrane.forward();
			newX = physicalCrane.getPositionX();
			while (newX < x) {
				newX = physicalCrane.getPositionX();
			}
			physicalCrane.stopX();
		} else if (actualX == x) {
			physicalCrane.stopX();
		} else {
			physicalCrane.backward();
			newX = physicalCrane.getPositionX();
			while (newX > x) {
				newX = physicalCrane.getPositionX();
			}
			physicalCrane.stopX();
		}
	}

	/**
	 * This is the method used to move the Crane to desired Y location
	 * 
	 * @param Y position
	 * @return null.
	 */
	public void moveToY(int y) {
		int newY;
		int actualY = physicalCrane.getPositionY();
		if (actualY < y) {
			physicalCrane.up();
			newY = physicalCrane.getPositionY();
			while (newY < y) {
				newY = physicalCrane.getPositionY();
			}
			physicalCrane.stopY();
		} else if (actualY == y) {
			physicalCrane.stopY();
		} else {
			physicalCrane.down();
			newY = physicalCrane.getPositionY();
			while (newY > y) {
				newY = physicalCrane.getPositionY();
			}
			physicalCrane.stopY();
		}
	}

	/**
	 * This is the constructor used to stop a Physical crane
	 * 
	 * @param null.
	 */
	public void shutdown() {
		physicalCrane.shutdown();
	}
}
