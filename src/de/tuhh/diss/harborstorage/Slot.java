package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.PhysicalHarborStorage;
import de.tuhh.diss.harborstorage.sim.StoragePlace;

/**
 * The Slot program implements StoragePlace, which is used to set & get
 * the details of Slots available in Physical Harbour Storage System.
 * 
 * @authors Prashanth Reddy Ujjalli, Raja Vardhan Reddy Kothakapu
 * @version 1.0
 * @since 2017-01-06
 */
public class Slot implements StoragePlace {

	int number, height, width, depth, loadCapacity, positionX, positionY;

	/**
	 * This is the getter method used to retrieve the Slot Number of a
	 * particular slot.
	 * 
	 * @param null.
	 * @return Integer value (Slot Number).
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * This is the getter method used to retrieve the Width of a particular
	 * slot.
	 * 
	 * @param null.
	 * @return Integer value (Width).
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * This is the getter method used to retrieve the Width of a particular
	 * slot.
	 * 
	 * @param null.
	 * @return Integer value (Width).
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * This is the getter method used to retrieve the Depth of a particular
	 * slot.
	 * 
	 * @param null.
	 * @return Integer value (Depth).
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * This is the getter method used to retrieve the Load Capacity of a
	 * particular slot.
	 * 
	 * @param null.
	 * @return Integer value (Load capacity).
	 */
	public int getLoadCapacity() {
		return this.loadCapacity;
	}

	/**
	 * This is the getter method used to retrieve the X position of a particular
	 * slot.
	 * 
	 * @param null.
	 * @return Integer value (X Position of the slot).
	 */
	public int getPositionX() {
		return this.positionX;
	}

	/**
	 * This is the getter method used to retrieve the Y position of a particular
	 * slot.
	 * 
	 * @param null.
	 * @return Integer value (Y Position of the slot).
	 */
	public int getPositionY() {
		return this.positionY;
	}

	/**
	 * This is the method used to retrieve the slot details based on its X,Y
	 * positions in a particular PhysicalHarborStorage system
	 * 
	 * @param PhysicalHarborStorage, X position, Y Position of the slot.
	 * @return StoragePlace (Slot).
	 */
	public static StoragePlace getSlot(
			PhysicalHarborStorage physicalHarborStorage, int x, int y) {
		StoragePlace[] slots = physicalHarborStorage.getStoragePlacesAsArray();
		StoragePlace storagePlace = null;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].getPositionX() == x && slots[i].getPositionY() == y) {
				storagePlace = slots[i];
				break;
			}
		}
		return storagePlace;
	}
}
