package com.talentboost.vmware.devices;

import com.talentboost.vmware.interfaces.IDevice;

/**
 * This class is part of the Factory Design Pattern. It provide ability to
 * return specified instance of particular class. It can instantiate every class
 * that implement IDevice interface.
 * 
 * @author KSamardzhiev
 *
 */
public class DeviceFactory {
	/**
	 * This constructor return instance of particular class that implements
	 * IDevice interface.
	 * 
	 * @param type
	 *            String with the type of particular device.
	 * @param argsArray
	 *            - String with arguments after particular device.
	 * @return Instance of class that implements IDevice interace.
	 */
	public IDevice getDevice(String type, String[] argsArray) {

		if (type.equals("NETWORK_CARD")) {
			if (argsArray.length == 5) {
				return new NetworkCardDevice(argsArray[2], argsArray[3], argsArray[4]);
			}
			return new NetworkCardDevice(argsArray[2], argsArray[3]);
		}

		if (type.equals("VIDEO_CARD")) {
			return new VideoCardDevice(argsArray[2], Integer.parseInt(argsArray[3]), Byte.parseByte(argsArray[4]));
		}

		if (type.equals("HardDisk_Controller")) {

			if (argsArray[3].equals("IDE")) {
				return new HDControllerIDEDevice(argsArray[2]);
			}
			if (argsArray[3].equals("SCSI")) {
				return new HDControllerSCSIDevice(argsArray[2]);
			}

		}

		if (type.equals("HardDisk")) {
			return new HardDiskDevice(argsArray[2], argsArray[4], Long.parseLong(argsArray[3]));
		}

		return null;

	}
}
