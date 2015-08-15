package com.talentboost.vmware.devices;

import com.talentboost.vmware.interfaces.IDevice;

/**
 * This class implements IDevice interface and provide logic of Video Card
 * device behavior in the ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class VideoCardDevice implements IDevice {

	/**
	 * String variable that stores the type of particular video card device.
	 */
	private final String TYPE = "VIDEO_CARD";
	/**
	 * String variable that stores the ID of particular video card device.
	 */
	private String id;
	/**
	 * String variable that stores the size of RAM of particular video card
	 * device.
	 */
	private int videoRAM;
	/**
	 * String variable that stores the number of displays of particular video
	 * card device.
	 */
	private byte numberDisplays;
	/**
	 * String variable that stores the maximum size of RAM that particular video
	 * card device can have.
	 */
	private final int MAX_RAM = 4194304;
	/**
	 * String variable that stores the maximum number of displays that
	 * particular video card device can have.
	 */
	private final byte MAX_DISPLAYS = 2;

	/**
	 * This constructor create an instance of VideoCardDevice class with three
	 * arguments (id, RAM and number of displays). It invokes 3 check methods
	 * that check the parameters for correctness. If one of these check methods
	 * fails, whole initialization fails too.
	 * 
	 * @param id
	 *            String ID of particular video card device.
	 * @param videoRAM
	 *            Int size of RAM of particular video card device.
	 * @param numberDisplays
	 *            Byte number of displays for particular video card device.
	 */
	public VideoCardDevice(String id, int videoRAM, byte numberDisplays) {

		checkID(id);
		this.id = id;

		checkRAM(videoRAM);
		this.videoRAM = videoRAM;

		checkNumberOfDisplays(numberDisplays);
		this.numberDisplays = numberDisplays;
	}

	/**
	 * This method check if the ID of the video card device is alphanumeric. In
	 * case this check fail, IllegalArgumentException is thrown.
	 * 
	 * @param id
	 *            String ID of the video card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkID(String id) {
		if (!id.matches("[A-Za-z0-9]+")) {
			throw new IllegalArgumentException("The ID of the device should be only alphanumerical");
		}
	}

	/**
	 * This method check if the size of RAM of the video card device is in range
	 * [1-MAX_RAM]. In case this check fail, IllegalArgumentException is thrown.
	 * 
	 * @param id
	 *            Int size of RAM of the video card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkRAM(int videoRAM) {
		if (videoRAM <= 0 || videoRAM > this.MAX_RAM) {
			throw new IllegalArgumentException("The RAM of the device should be in range 1 - 4194304 KB ");
		}
	}

	/**
	 * This method check if the number of displays of the video card device is
	 * in range [1-MAX_DISPLAYS]. In case this check fail,
	 * IllegalArgumentException is thrown.
	 * 
	 * @param numberDisplays
	 *            Byte number of displays of the video card device.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void checkNumberOfDisplays(byte numberDisplays) {
		if (numberDisplays <= 0 || numberDisplays > this.MAX_DISPLAYS) {
			throw new IllegalArgumentException("The number of displays of the device should be in range 1 or 2");
		}
	}

	/**
	 * @return String type of the video card device.
	 */
	@Override
	public String getType() {
		return this.TYPE;
	}

	/**
	 * @return String ID of the video card device.
	 */
	@Override
	public String getID() {
		return this.id;
	}

	/**
	 * @return String representation of VideoCardDevice object.
	 */
	@Override
	public String toString() {
		String result = String.format("%s %s %s %s", this.TYPE, this.id, this.videoRAM, this.numberDisplays);
		return result;

	}

	/**
	 * @return Int size of RAM of the video card device.
	 */
	public int getRAM() {
		return this.videoRAM;
	}

	/**
	 * @return Byte number of displays of the video card device.
	 */
	public byte getNumberOfDisplays() {
		return this.numberDisplays;
	}

}
