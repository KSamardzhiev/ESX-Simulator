package com.talentboost.vmware.commands.devices;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.ReturnMessage;
import com.talentboost.vmware.devices.DeviceFactory;
import com.talentboost.vmware.devices.HardDiskDevice;
import com.talentboost.vmware.interfaces.ICommand;
import com.talentboost.vmware.interfaces.IDevice;
import com.talentboost.vmware.interfaces.IHDController;

/**
 * This class implements ICommand interface and provide logic of command
 * "add-dev" in ESXSimulator. It main goal is to provide ability of adding same
 * device to particular virtual machine. Devices that can be added are instances
 * of IDevice interface.
 * 
 * @author KSamardzhiev
 *
 */
public class AddDeviceCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "add-dev";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = ReturnMessage.getMessage("MSG_ADD_DEVICE_INFO");

	/**
	 * @return String name of command "add-dev".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method invoke authorizeInput method where is the logic of execution.
	 * The main goal is to add particular device on particular virtual machine.
	 * All needed parameters are provided after the command.
	 * 
	 * @param args
	 *            String of arguments after the command "add-dev"
	 * @return String result of execution of the command "add-dev".
	 */
	@Override
	public String execute(String args) {
		return this.authorizeInput(args);
	}

	/**
	 * This method authorize the input and execute the command in case of
	 * everything with the input is correct. Otherwise it return concrete String
	 * with the problem. In case the execution is successful it return String
	 * with the result.
	 * 
	 * @param args
	 *            String of arguments after the command "add-dev".
	 * @return String result of execution of the command "add-dev".
	 */
	private String authorizeInput(String args) {
		return checkArgs(args);
	}

	/**
	 * This method check arguments after command "add-dev" and if everything is
	 * successful, add particular device and return String with successful
	 * message. Otherwise, this method return String with the concrete problem.
	 * 
	 * @param args
	 *            String with arguments after command "add-dev".
	 * @return String result of execution of the command "add-dev".
	 */
	private String checkArgs(String args) {

		String[] argsArray = args.trim().split(ICommand.PATTERN_WITHOUT_NAME_ARG);
		if (!ESXSimulator.VMsStorage.containsKey(argsArray[0])) {
			return ReturnMessage.getMessage("MSG_ADD_DEVICE_ERROR_NOT_EXISTING_ID");
		}

		DeviceFactory factory = new DeviceFactory();

		if (argsArray[1].equals("HardDisk")) {
			try {
				IHDController controller = (IHDController) ESXSimulator.VMsStorage.get(argsArray[0]).getDevices()
						.get(argsArray[4]);
				HardDiskDevice hardDisk = (HardDiskDevice) factory.getDevice(argsArray[1], argsArray);
				controller.addHardDisk(hardDisk);
			} catch (IllegalArgumentException e) {
				return e.getMessage();
			}

			return ReturnMessage.getMessage("MSG_ADD_DEVICE_HARD_DISK_SUCCESS");
		}

		IDevice device = factory.getDevice(argsArray[1], argsArray);
		try {
			ESXSimulator.VMsStorage.get(argsArray[0]).addDevice(device);
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
		return ReturnMessage.getMessage("MSG_ADD_DEVICE_SUCCESS");

	}

	/**
	 * @return String information about command "add-dev".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
