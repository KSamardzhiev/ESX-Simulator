package com.talentboost.vmware.devices;

import com.talentboost.vmware.ReturnMessage;
import com.talentboost.vmware.interfaces.IDevice;

/**
 * This class implements IDevice interface and provide logic of Hard Disk
 * behavior in the ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class HardDiskDevice implements IDevice {
	/**
	 * String variable that stores the type of the device.
	 */
	private final String TYPE = "HardDisk";
	/**
	 * String variable that stores the ID of the particular device of this type.
	 */
	private final String id;
	/**
	 * String variable that stores the ID of the controller to which this device
	 * is attached.
	 */
	private final String ID_CONTROLLER;
	/**
	 * String variable that stores the size of the particular device.
	 */
	private final long SIZE;
	/**
	 * String variable that stores the maximum possible size of particular
	 * device of this type.
	 */
	private final long MAXSIZE = 1_000_000_000_000l;

	/**
	 * This constructor create particular device with the necessary parameters.
	 * It invokes 3 check methods that check the parameters for correctness. If
	 * one of these check methods fails, whole initialization fails too.
	 * 
	 * @param id
	 *            String ID of the particular device.
	 * @param idController
	 *            String ID of the controller to which this device is attached.
	 * @param size
	 *            Long variable that represent the size of particular device.
	 */
	public HardDiskDevice(String id, String idController, long size) {
		chechID(id);
		this.id = id;
		checkIDController(idController);
		this.ID_CONTROLLER = idController;
		checkSize(size);
		this.SIZE = size;
	}

	/**
	 * This method check if the size of the hard disk device is in range
	 * [1-MAXSIZE]. If the size is not in this range, IllegalArgumentException
	 * is thrown.
	 * 
	 * @param size
	 *            Long variable that represent the size of the hard disk device.
	 * @throws IllegalArgumentException
	 */
	private void checkSize(long size) {
		if (size <= 0 || size > this.MAXSIZE) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HARD_DISK_DEVICE_ERROR_SIZE"));
		}

	}

	/**
	 * This method check if the ID of the controller to which this device is
	 * wanted to be attached is alphanumeric. In case this check fail,
	 * IllegalArgumentException is thrown.
	 * 
	 * @param idController
	 *            String ID of the controller to which this device is wanted to
	 *            be attached.
	 * @throws IllegalArgumentException
	 */
	private void checkIDController(String idController) {
		if (!idController.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HARD_DISK_DEVICE_ALPHANUMERIC_ERROR_CONTROLLER_ID"));
		}

	}

	/**
	 * This method check if the ID of the hard disk device is alphanumeric. In
	 * case this check fail, IllegalArgumentException is thrown.
	 * 
	 * @param id
	 *            String ID of the hard disk device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void chechID(String id) {
		if (!id.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(ReturnMessage.getMessage("MSG_HARD_DISK_DEVICE_ALPHANUMERIC_ERROR_ID"));
		}
	}

	/**
	 * @return String type of the hard disk device.
	 */
	@Override
	public String getType() {
		return this.TYPE;
	}

	/**
	 * @return String ID of the hard disk device.
	 */
	@Override
	public String getID() {
		return this.id;
	}

	/**
	 * @return String representation of HardDiskDevice instance.
	 */
	@Override
	public String toString() {
		String result = String.format("%s %s %s %s", this.TYPE, this.id, this.SIZE, this.ID_CONTROLLER);
		return result;
	}

	/**
	 * @return String ID of the hard disk controller to which this device is
	 *         attached.
	 */
	public String getIDController() {
		return this.ID_CONTROLLER;
	}

	/**
	 * 
	 * @return Long representation of the size of particular hard disk device.
	 */
	public long getSize() {
		return this.SIZE;
	}

}
