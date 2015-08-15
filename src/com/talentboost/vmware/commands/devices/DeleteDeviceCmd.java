package com.talentboost.vmware.commands.devices;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class implements ICommand interface and provide logic of command
 * "delete-dev" in ESXSimulator. It main goal is to provide ability of delete
 * same device of particular virtual machine. Devices that can be deleted are
 * instances of IDevice interface.
 * 
 * @author KSamardzhiev -
 *
 */
public class DeleteDeviceCmd implements ICommand {
	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "delete-dev";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "delete-dev - This command provide ability to delete particular device on particular virtual machine\n\n";

	/**
	 * @return String name of command "delete-dev".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method invoke authorizeInput method where is the logic of execution.
	 * The main goal is to delete particular device on particular virtual
	 * machine. All needed parameters are provided after the command.
	 * 
	 * @param args
	 *            String of arguments after the command "delete-dev"
	 * @return String result of execution of the command "delete-dev".
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
	 *            String of arguments after the command "delete-dev".
	 * @return String result of execution of the command "delete-dev".
	 */
	private String authorizeInput(String args) {
		String[] argsArray = args.trim().split(ICommand.PATTERN_WITHOUT_NAME_ARG);
		if (!ESXSimulator.VMsStorage.containsKey(argsArray[0])) {
			return "Virtual machine with this ID doesn't exist";
		}

		try {
			ESXSimulator.VMsStorage.get(argsArray[0]).deleteDevice(argsArray[1]);
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
		return "The device is deleted!";
	}

	/**
	 * @return String information about command "delete-dev".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
