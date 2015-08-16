package com.talentboost.vmware;

import java.util.HashMap;
import java.util.Map;

import com.talentboost.vmware.interfaces.IDevice;
import com.talentboost.vmware.interfaces.IHDController;

/**
 * This class provide logic of Virtual Machine behavior
 *         in the ESXSimulator.
 *
 * @author KSamardzhiev 
 */
public class VirtualMachine {
	/**
	 * This variable stores the name of particular virtual machine.
	 */
	private String name;
	/**
	 * This variable stores the unique ID of particular machine.
	 */
	private String id;
	/**
	 * This variable stores the memory of particular virtual machine.
	 */
	private int memory;
	/**
	 * This variable stores the number of CPUs of the virtual machine.
	 */
	private byte numberOfCPUs;

	/**
	 * This variable stores the number of Hard disk controllers of type IDE
	 * which are attached to the virtual machine.
	 */
	private byte numberIDE = 0;
	/**
	 * This variable stores the maximum number of Hard disk controllers of type
	 * IDE which can be attached to the virtual machine.
	 */
	private final byte MAX_IDE_NUMBER = 1;

	/**
	 * This variable stores the number of Hard disk controllers of type SCSI
	 * which are attached to the virtual machine.
	 */
	private byte numberSCSI = 0;
	/**
	 * This variable stores the maximum number of Hard disk controllers of type
	 * SCSI which can be attached to the virtual machine.
	 */
	private final byte MAX_SCSI_NUMBER = 4;

	/**
	 * This map stores devices that particular virtual machine have. The key is
	 * String representation of ID and value of the map is concrete
	 * implementation of IDevice interface. It can stores devices instance of
	 * classes which implements IDevice interface. Each device have unique ID
	 * and it is not possible to have two devices with same IDs.
	 */
	private Map<String, IDevice> devices = new HashMap<String, IDevice>();

	/**
	 * This constructor makes instance of VirtualMachine class with particular
	 * parameters.
	 * 
	 * @param id
	 *            - String representation of ID of particular virtual machine.
	 *            It can be only alphanumeric.
	 * @param name
	 *            - String representation of the name of particular virtual
	 *            machine. It can be only alphanumeric but it is possible to
	 *            contain spaces.
	 * @param memory
	 *            - Int variable representing the size of the RAM of the virtual
	 *            machine. It is measured in bytes (1KB = 1024bytes)
	 */
	public VirtualMachine(String id, String name, int memory, byte CPUs) {

		checkID(id);
		this.id = id;
		checkName(name);
		this.name = name;
		checkMemory(memory);
		this.memory = memory;
		checkCPUs(CPUs);
		this.numberOfCPUs = CPUs;
	}

	/**
	 * 
	 * @return String of the ID of the virtual machine.
	 */
	public String getID() {
		return this.id;
	}

	/**
	 * 
	 * @return String of the name of the virtual machine.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return Int variable representation of the memory of the virtual machine.
	 */
	public int getMemory() {
		return this.memory;
	}

	/**
	 * 
	 * @return Byte - number of CPUs of the virtual machines.
	 */
	public byte getCPUs() {
		return this.numberOfCPUs;
	}

	/**
	 * This method is used only when it is necessary to edit particular virtual
	 * machine because VirtualMachine class does not have constructor with no
	 * parameters. Virtual machine provide setting name, memory and number of
	 * CPUs only by this method because the execution of this has to be
	 * transactional. If one of the three set commands fail, the whole
	 * transaction must fail. After execution of this method, it return the
	 * result. If is catch IllegalArgumentException, the return result is String
	 * with the concrete problem. Otherwise it return String with success
	 * message.
	 * 
	 * @param name
	 *            String of the name which is wanted to be set to particular
	 *            virtual machine.
	 * @param memory
	 *            Int variable of the memory which is wanted to be set to
	 *            particular virtual machine.
	 * @param CPUs
	 *            Byte variable of the number of CPUs which is wanted to be set
	 *            to particular virtual machine.
	 */
	public String setNameMemoryCPUs(String name, int memory, byte CPUs) {
		try {
			this.checkCPUs(CPUs);
			this.checkName(name);
			this.checkMemory(memory);
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
		this.name = name;
		this.memory = memory;
		this.numberOfCPUs = CPUs;
		return ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_EDIT_SUCCESS");
	}

	/**
	 * This method add device to the map with all devices that particular
	 * virtual machine have. It throws IllegalArgumentException when the map
	 * contains device with ID which is the same as the ID of the device that is
	 * parameter because it is forbidden one virtual machine to have two devices
	 * with same ID.
	 * 
	 * @param device
	 *            - variable of concrete implementation of IDevice interface.
	 * @throws IllegalArgumentException
	 */
	public void addDevice(IDevice device) {
		if (this.devices.containsKey(device.getID())) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ADD_DELETE_DEVICE_ERROR_ID"));
		}

		if (device.getType().equals("HardDisk_Controller")) {
			checkControllerType((IHDController) device);
		}
		this.devices.put(device.getID(), device);
	}

	/**
	 * This method check the type of device which implement IHDController
	 * interface. There are two possible types: IDE and SCSI. This check is
	 * required because one virtual machine can have only 1 IDE hard disk
	 * controller and maximum 4 SCSI hard disk controllers.
	 * 
	 * @param device
	 *            Instanstace of class that implements IHDController interface.
	 */
	private void checkControllerType(IHDController device) {
		if (device.getControllerType().equals("IDE")) {
			checkNumberIDE();
			this.numberIDE++;
		}

		if (device.getControllerType().equals("SCSI")) {
			chechNumberSCSI();
			this.numberSCSI++;
		}

	}

	/**
	 * This method check if the number of SCSI hard disk controller attached to
	 * the virtual machine. If it has reached the maximum,
	 * IllegalArgumentException is thrown with the concrete problem.
	 */
	private void chechNumberSCSI() {
		if (this.numberSCSI == this.MAX_SCSI_NUMBER) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ADD_DEVICE_ERROR_OVER_MAX_HARD_DISKS"));
		}
	}

	/**
	 * This method check if the number of IDE hard disk controller attached to
	 * the virtual machine. If it has reached the maximum,
	 * IllegalArgumentException is thrown with the concrete problem.
	 */
	private void checkNumberIDE() {
		if (this.numberIDE == this.MAX_IDE_NUMBER) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ADD_DEVICE_ERROR_OVER_MAX_HARD_DISKS"));
		}

	}

	/**
	 * This method delete device from the map with all devices that particular
	 * virtual machine have. It throws IllegalArgumentException when the map
	 * does not contains device with ID of the device that is parameter.
	 * 
	 * @param deviceID
	 *            String representation of the ID of the device which is
	 *            necessary to be deleted.
	 */
	public void deleteDevice(String deviceID) {
		if (!this.devices.containsKey(deviceID)) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ADD_DELETE_DEVICE_ERROR_ID"));
		}

		this.devices.remove(deviceID);
	}

	/**
	 * This method returns the map of all devices that particular virtual
	 * machine have.
	 * 
	 * @return Map with key - String representation of ID of device and value -
	 *         instance of class which implements IDevice interface.
	 */
	public Map<String, IDevice> getDevices() {
		return this.devices;
	}

	/**
	 * This method is override because it helps representing particular virtual
	 * machine
	 */
	@Override
	public String toString() {
		return this.id + "\t" + this.name + "\t" + this.memory + "\t"
				+ this.numberOfCPUs;

	}

	/**
	 * This method check the given parameter whether if it is alphanumeric. It
	 * throws IllegalArgumentException if the parameter is not alphanumeric.
	 * 
	 * @param name
	 *            String name of particular virtual machine.
	 * @throws IllegalArgumentException
	 */
	private void checkName(String name) {
		if (!name.matches("[A-Za-z0-9' '-]+")) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ALFANUMBERIC_ERROR_NAME"));
		}
	}

	/**
	 * This method check the given parameter whether if it has negative value.
	 * It throws IllegalArgumentException if the parameter is negative.
	 * 
	 * @param memory
	 *            Int variable of the memory of particular virtual machine.
	 */
	private void checkMemory(int memory) {
		if (memory <= 0) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_MEMORY_ERROR_NOT_IN_RANGE"));
		}
	}

	/**
	 * This method check the given parameter whether if it in range [1-8]. It
	 * throws IllegalArgumentException if the parameter is not correct.
	 * 
	 * @param memory
	 *            Int variable of the number of CPUs of particular virtual
	 *            machine.
	 */
	private void checkCPUs(byte CPUs) {
		if (CPUs < 1 || CPUs > 8) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_CPUS_ERROR_NOT_IN_RANGE"));
		}
	}

	/**
	 * This method check the given parameter whether if it is alphanumeric. It
	 * throws IllegalArgumentException if the parameter is not alphanumeric.
	 * 
	 * @param id
	 *            String representation of the ID of particular virtual machine.
	 * @throws IllegalArgumentException
	 */
	private void checkID(String id) {
		if (!id.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException(
					ReturnMessage.getMessage("MSG_VIRTUAL_MACHINE_ALFANUMBERIC_ERROR_ID"));
		}
	}
}
