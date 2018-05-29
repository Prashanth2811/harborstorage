package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StorageElement;

/**
 * The Packet program implements StorageElement, which is used to set & get
 * the details of Packets available in Physical Harbour Storage System.
 * 
 * @authors Raja Vardhan Reddy Kothakapu, Prashanth Reddy Ujjalli
 * @version 1.0
 * @since 2017-01-06
 */
public class Packet implements StorageElement {
	int width, height, depth, id, weight, x_location, y_location;
	public static int assignid = 0;
	String description;
	HarborStorageManagement harborStorageManagement;

	/**
	 * This is the constructor used to create a Packet with given attribute values
	 * 
	 * @param Width, Height, Depth, Description, Weight.
	 */
	public Packet(int width, int height, int depth, String description,
			int weight) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.description = description;
		this.weight = weight;
	}
	
	/**
	 * This is the getter method used to retrieve the Width of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (Width).
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * This is the getter method used to retrieve the Height of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (Height).
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * This is the getter method used to retrieve the Depth of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (Depth).
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * This is the getter method used to retrieve the ID of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (ID).
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * This is the getter method used to retrieve the Description of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return String value (Description).
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * This is the getter method used to retrieve the Weight of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (Width).
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * This is the getter method used to generate sequence number for a new packet
	 * 
	 * @param null.
	 * @return Integer value (ID).
	 */
	public int assignId() {
		assignid += 1;
		return assignid;
	}

	/**
	 * This is the getter method used to retrieve the X position of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (X Position).
	 */
	public int getXLocation() {
		return x_location;
	}

	/**
	 * This is the getter method used to retrieve the Y position of a
	 * particular Packet.
	 * 
	 * @param null.
	 * @return Integer value (Y position).
	 */
	public int getYLocation() {
		return y_location;
	}

	/**
	 * This is the setter method used to to set the location for a particular
	 * Packet in a Harbour Storage System.
	 * 
	 * @param X Position, Y Position.
	 * @return null.
	 */
	public void setLocation(int x, int y) {
		x_location = x;
		y_location = y;
	}

	/**
	 * This is the setter method used to to set the ID for a particular
	 * Packet in a Harbour Storage System.
	 * 
	 * @param ID.
	 * @return null.
	 */
	public void setId(int packetId) {
		id = packetId;
	}
}
