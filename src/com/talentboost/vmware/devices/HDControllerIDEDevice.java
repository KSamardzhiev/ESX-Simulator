package com.talentboost.vmware.devices;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.talentboost.vmware.ReturnMessage;
import com.talentboost.vmware.interfaces.IDevice;
import com.talentboost.vmware.interfaces.IHDController;

/**
 * This class implements IHDController interface and IDevice interface and
 * provide logic of IDE Hard Disk Controller device behavior in the
 * ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class HDControllerIDEDevice implements IHDController, IDevice {
	/**
	 * String variable that stores the general type of the device.
	 */
	private final String TYPE = "HardDisk_Controller";
	/**
	 * String variable that stores the specific type of the device.
	 */
	private final String TYPE_CONTROLLER = "IDE";
	/**
	 * String variable that stores the ID of the device.
	 */
	private final String ID;
	/**
	 * Byte variable that stores the maximum number of Hard Disks that can be
	 * attached to this type of hard disk controller.
	 */
	private final byte MAX_NUMBER_HARD_DISK = 4;
	/**
	 * Map data structure that stores the attached Hard Disks to this device.
	 * The key is the id of particular hard disk and the value is instance of
	 * HardDiskDevice class.
	 */
	private Map<String, HardDiskDevice> hardDisks = new HashMap<String, HardDiskDevice>();

	/**
	 * This constructor create an instance of HDControllerIDEDevice class with
	 * one arguments (id). It invokes 1 check methods that check the parameter
	 * (id) for correctness. If this check methods fails, whole initialization
	 * fails too.
	 * 
	 * @param id
	 *            String ID of particular HDControllerIDE device.
	 */
	public HDControllerIDEDevice(String id) {
		checkID(id);
		this.ID = id;
	}

	/**
	 * This method check if the ID of the HDControllerIDE device is
	 * alphanumeric. In case this check fail, IllegalArgumentException is
	 * thrown.
	 * 
	 * @param id
	 *            String ID of the HDControllerIDE device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkID(String id) {
		if (!id.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HDC_IDE_DEVICE_ALPHANUMERIC_ERROR_ID"));
		}
	}

	/**
	 * This method put particular HardDiskDevice object to the map with all
	 * attached hard disks. It invokes 2 check method that check if there is no
	 * attached hard disk with this id already and if there is free space for
	 * more hard disks (IDE hard disk controller allow only 4 hard disk attached
	 * to it). If any of this checks fail, whole adding process fail too.
	 * 
	 * @param hardDisk
	 *            HardDiskDevice object which is wanted to be attached to this
	 *            HDController device.
	 * 
	 */
	@Override
	public String addHardDisk(HardDiskDevice hardDisk) {
		checkIDHardDisk(hardDisk.getID());
		checkNumberOfHardDisks();
		this.hardDisks.put(hardDisk.getID(), hardDisk);
		return ReturnMessage.getMessage("MSG_HDC_IDE_DEVICE_ADD_HARD_DISK_SUCCESS");
	}

	/**
	 * This method check if there is no attached hard disk with same id already.
	 * If this method fail, it thrown IllegalArgumentException.
	 * 
	 * @param hardDiskID
	 *            - HardDiskDevice that is wanted to be attached to this
	 *            HDController.
	 * @throws IllegalArgumentException
	 */
	private void checkIDHardDisk(String hardDiskID) {
		if (this.hardDisks.containsKey(hardDiskID)) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HDC_IDE_DEVICE_ADD_HARD_DISK_ERROR_EXISTING_ID"));
		}

	}

	/**
	 * This method check if there is free space for more hard disks (IDE hard
	 * disk controller allow only 4 hard disk attached to it). If this method
	 * fail, it thrown IllegalArgumentException.
	 * 
	 * @param hardDiskID
	 *            - HardDiskDevice that is wanted to be attached to this
	 *            HDController device.
	 * @throws IllegalArgumentException
	 */
	private void checkNumberOfHardDisks() {
		if (this.hardDisks.size() == this.MAX_NUMBER_HARD_DISK) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HDC_IDE_DEVICE_ADD_HARD_DISK_ERROR_MAX_NUMBER_OF_HARD_DISKS"));
		}

	}

	/**
	 * @return String general type of the HDControlerIDE device.
	 */
	@Override
	public String getType() {
		return this.TYPE;
	}

	/**
	 * @return String ID of the HDControlerIDE device.
	 */
	@Override
	public String getID() {
		return this.ID;
	}

	/**
	 * @return String specific type of the HDControlerIDE device.
	 */
	@Override
	public String getControllerType() {
		return this.TYPE_CONTROLLER;
	}

	/**
	 * @return String representation of HDControllerIDEDevice object.
	 */
	@Override
	public String toString() {
		String result = String.format("%s %s %s", this.TYPE, this.ID, this.TYPE_CONTROLLER);
		return result;
	}

	/**
	 * @return Collection of all Hard Disk devices that are attached to this
	 *         HDController device.
	 */
	@Override
	public Collection<HardDiskDevice> getHardDisks() {
		return this.hardDisks.values();
	}

}
