package com.talentboost.vmware.commands.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class provide logic of command edit-vm and implements ICommand
 * interface. It provide ability to edit existing virtual machine with some
 * parameters.
 * 
 * @author KSamardzhiev
 *
 */

public class EditVirtualMachineCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "edit-vm";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "edit-vm - This command provide ability to edit particular virtual machine.\n\n";

	/**
	 * @return String name of command "edit-vm".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method invoke authorizeInput method where is the logic of execution.
	 * The main goal is to edit existing virtual machine. All needed parameters
	 * are provided after the command.
	 * 
	 * @param args
	 *            String of arguments after the command "edit-vm"
	 * @return String result of execution of the command "edit-vm".
	 */
	@Override
	public synchronized String execute(String args) {
		return this.authorizeInput(args);
	}

	/**
	 * This method authorize the input and execute the command in case of
	 * everything with the input is correct. Otherwise it return concrete String
	 * with the problem. In case the execution is successful it return String
	 * with the result.
	 * 
	 * @param args
	 *            String of arguments after the command "edit-vm".
	 * @return String result of execution of the command "edit-vm".
	 */
	private String authorizeInput(String args) {

		String[] argsArray = this.splitArgs(args);

		if (!ESXSimulator.VMsStorage.containsKey(argsArray[0])) {
			return "Virtual machine with this ID doesn't exist";
		}

		return ESXSimulator.VMsStorage.get(argsArray[0]).setNameMemoryCPUs(argsArray[1], Integer.parseInt(argsArray[2]),
				Byte.parseByte(argsArray[3]));

	}

	/**
	 * @return String information about command "edit-vm".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

	/**
	 * This method split the input by whitespace and '. It return array with all
	 * needed parameters.
	 * 
	 * @param input
	 *            String of the raw input.
	 * @return String array with split input
	 */
	private String[] splitArgs(String input) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\']\\S*|\'.+?\')\\s*").matcher(input);
		while (m.find())
			list.add(m.group(1).replace("'", ""));

		String[] argsArray = new String[list.size()];
		argsArray = list.toArray(argsArray);
		return argsArray;
	}

}
