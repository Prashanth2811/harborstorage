package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.sim.StorageException;
import de.tuhh.diss.io.SimpleIO;

/**
 * The HarborStorageApp program is an User Interface Application for Harbour
 * Storage System
 * 
 * @authors Raja Vardhan Reddy Kothakapu, Prashanth Reddy Ujjalli
 * @version 1.0
 * @since 2017-01-06
 */
public class HarborStorageApp {

	public static final int TOTALSLOTS = 29;

	/**
	 * This is the main method which makes use of HarborStorageManagement Class
	 * for management of a harbour storage system.
	 * 
	 * @param args Unused.
	 * @return Nothing.
	 * @exception StorageException On input error.
	 * @see StorageException
	 */
	public static void main(String[] args) throws StorageException {
		int choice = 0;
		Packet[] packetDescription = new Packet[TOTALSLOTS];
		HarborStorageManagement harborStorage = new HarborStorageManagement();
		SimpleIO.println("Welcome to TUHH/DISS Harbor Storage Management\n");
		SimpleIO.println("****Main Menu****");
		// Validating user choice for storing/retrieving/aborting
		while (choice == 1 || choice == 2 || choice == 0) {
			SimpleIO.println("0: Quit Program");
			SimpleIO.println("1: Store a packet in the highbaystorage");
			SimpleIO.println("2: Retrieve a packet in the highbaystorage");
			SimpleIO.println("Your Choice: ");
			choice = SimpleIO.readInt();
			// Storing a new Packet
			if (choice == 1) {
				String description;
				int width;
				int height;
				int depth;
				int weight;
				String option;
				SimpleIO.println("*** Store a packet ***");
				SimpleIO.println("Description :");
				description = SimpleIO.readString();
				if (description.equals("0")) {
					SimpleIO.println("Description should not be \"0\". Please enter new description :");
					description = SimpleIO.readString();
				}
				SimpleIO.println("Width :");
				width = SimpleIO.readInt();
				SimpleIO.println("Height :");
				height = SimpleIO.readInt();
				SimpleIO.println("Depth :");
				depth = SimpleIO.readInt();
				SimpleIO.println("Weight :");
				weight = SimpleIO.readInt();
				SimpleIO.println("You entered packet " + description
						+ " of size : " + width + "*" + height + "*" + depth
						+ " and Weight : " + weight);
				SimpleIO.println("shall we store the packet?  (y/n)");
				option = SimpleIO.readString();
				if (option.equalsIgnoreCase("y")) {
					try {
						int id = harborStorage.storePacket(width, height,
								depth, description, weight);
						if (id != 0) {
							SimpleIO.println("Packet is Successfully Stored and the Packet ID is "
									+ id);
						} else {
							SimpleIO.println("Packet is not Stored.");
						}
					} catch (StorageException error) {
						continue;
					}
				} else {
					SimpleIO.println("You opted not to store");
					continue;
				}
			}
			// Retrieving a Packet
			else if (choice == 2) {
				String retrieveDescription;
				int descriptionFound = 0, count = 0;
				packetDescription = harborStorage.getPackets();
				for (int i = 0; i < TOTALSLOTS; i++) {
					if (packetDescription[i] == null)
						count++;
				}
				if (count == 29) {
					SimpleIO.println("No Packets available to retrieve");
					continue;
				} else {
					SimpleIO.println("Available packets :");
					for (int j = 0; j < packetDescription.length; j++) {
						if (packetDescription[j] != null) {
							SimpleIO.println("Packet ID : " + (j + 1) + " | Packet Descriprion: "
									+ packetDescription[j].description
									+ " | Size:  " + packetDescription[j].width
									+ "*" + packetDescription[j].height + "*"
									+ packetDescription[j].depth + " | Weight: "
									+ packetDescription[j].weight);
						}
					}
					SimpleIO.println("*** Enter description of packet to be retrieved (0 = Abort ) ***");
					retrieveDescription = SimpleIO.readString();
					if (retrieveDescription.equals("0")) {
						harborStorage.shutdown();
					} else {
						for (int i = 0; i < packetDescription.length; i++) {
							if (packetDescription[i] != null) {
								if (retrieveDescription
										.equals(packetDescription[i]
												.getDescription())) {
									harborStorage
											.retrievePacket(retrieveDescription);
									descriptionFound += 1;
									break;
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
						if (choice == 2 && descriptionFound == 0) {
							SimpleIO.println("Please Enter the Packets from the above Descriped List");
						}
					}
				}
			}
			// Aborting the Program
			else if (choice == 0) {
				SimpleIO.println("System ends.");
				harborStorage.shutdown();
				break;
			} else{
				SimpleIO.println("Enter Proper Input choice\n");
				choice=0;
				continue;
			}
		}
	}
}
