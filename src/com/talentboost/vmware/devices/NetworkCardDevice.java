package com.talentboost.vmware.devices;

import com.talentboost.vmware.interfaces.IDevice;

/**
 * This class implements IDevice interface and provide logic of Network Card
 * device behavior in the ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class NetworkCardDevice implements IDevice {

	/**
	 * String variable that stores the type of particular network card device.
	 */
	private final String TYPE = "NETWORK_CARD";
	/**
	 * String variable that stores the ID of particular network card device.
	 */
	private final String id;
	/**
	 * String variable that stores the MAC address of particular network card
	 * device.
	 */
	private final String macAddr;
	/**
	 * String variable that stores the IP address of particular network card
	 * device.
	 */
	private final String ipAddr;

	/**
	 * This constructor create an instance of NetworkCardDevice class with two
	 * arguments (id and MAC address). It invokes other constructor in the class
	 * with 3 parameters. If the other constructor fail, whole initialization
	 * fail too.
	 * 
	 * @param id
	 *            String ID of particular network card device.
	 * @param macAddr
	 *            String of MAP address of particular network device.
	 */
	public NetworkCardDevice(String id, String macAddr) {
		this(id, macAddr, null);
	}

	/**
	 * This constructor create an instance of NetworkCardDevice class with two
	 * arguments (id and MAC address). It invokes 3 check methods which check
	 * the parameters for correctness. If one these check methods fails, whole
	 * initialization fails too.
	 * 
	 * @param id
	 *            String ID of particular network card device.
	 * @param macAddr
	 *            String of MAP address of particular network device.
	 */
	public NetworkCardDevice(String id, String macAddr, String ipAddr) {
		chechkID(id);
		this.id = id;

		checkMAC(macAddr);
		this.macAddr = macAddr;

		checkIP(ipAddr);
		this.ipAddr = ipAddr;
	}

	/**
	 * This method check if the ID of the network card device is alphanumeric.
	 * In case this check fail, IllegalArgumentException is thrown.
	 * 
	 * @param id
	 *            String ID of the network card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void chechkID(String id) {
		if (!id.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException("The ID of the device should be only alphanumerical");
		}
	}

	/**
	 * This method check if the MAC address of the network card device is
	 * alphanumeric. In case this check fail, IllegalArgumentException is
	 * thrown.
	 * 
	 * @param id
	 *            String MAC address of the network card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkMAC(String macAddr) {
		if (!macAddr.matches("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$")) {
			throw new IllegalArgumentException(
					"The MAC address of the device should be in the format of \"HH-­HH-­HH-­HH-­HH-­HH\", where \"H\" is a hexadecimal number");
		}

	}

	/**
	 * This method check if the IP address of the network card device is
	 * alphanumeric. In case this check fail, IllegalArgumentException is
	 * thrown.
	 * 
	 * @param id
	 *            String IP address of the network card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkIP(String ipAddr) {
		if (ipAddr != null && !ipAddr.matches(
				"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
			throw new IllegalArgumentException(
					"The IP address of the device should be in the format of \"A.B.C.D\", where A,B,C,D are numbers in range 0-255");
		}

	}

	/**
	 * @return String type of the network card device.
	 */
	@Override
	public String getType() {
		return this.TYPE;
	}

	/**
	 * @return String ID of the network card device.
	 */
	@Override
	public String getID() {
		return this.id;
	}

	/**
	 * @return String representation of NetworkCardDevice object.
	 */
	@Override
	public String toString() {
		String result;
		if (ipAddr == null) {
			result = String.format("%s %s %s", this.TYPE, this.id, this.macAddr);
			return result;
		}
		result = String.format("%s %s %s %s", this.TYPE, this.id, this.macAddr, this.ipAddr);
		return result;
	}

	/**
	 * @return String MAC address of the network card device.
	 */
	public String getMACAddr() {
		return this.macAddr;
	}

	/**
	 * @return String IP address of the network card device.
	 */
	public String getIPAddr() {
		return this.ipAddr;
	}

}
