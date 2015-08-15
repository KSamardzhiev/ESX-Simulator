package com.talentboost.vmware.commands.vm;

import java.io.File;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class provide logic of command delete-vm and implements ICommand
 * interface. It provide ability to delete virtual machine.
 * 
 * @author KSamardzhiev
 *
 */
public class DeleteVirtualMachineCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "delete-vm";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "delete-vm - This command provide ability to delete particular virtual machine\n\n";

	/**
	 * @return String name of command "delete-vm".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method invoke authorizeInput method where is the logic of execution.
	 * The main goal is to delete particular virtual machine. All needed
	 * parameters are provided after the command.
	 * 
	 * @param args
	 *            String of arguments after the command "delete-vm"
	 * @return String result of execution of the command "delete-vm".
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
	 *            String of arguments after the command "delete-vm".
	 * @return String result of execution of the command "delete-vm".
	 */
	private String authorizeInput(String args) {
		if (!ESXSimulator.VMsStorage.containsKey(args)) {
			return "There is no virtual machine with this ID (ID: " + args + ")";
		}
		ESXSimulator.VMsStorage.remove(args);
		deleteFile(args);
		return "Virtual machine with ID: " + args + " is deleted!";
	}

	/**
	 * This method provide ability to delete a virtual machine file in which it
	 * was saved.
	 * 
	 * @param id
	 *            String of the ID of the virtual machine that is wanted to be
	 *            deleted.
	 */
	private void deleteFile(String id) {

		String fileName = String.format("%s.txt", id);
		String workingDirectory = System.getProperty("user.dir");
		String savedDirectory = "savedVMs";
		String absoluteFilePath = "";

		absoluteFilePath = workingDirectory + File.separator + savedDirectory + File.separator + fileName;

		File file = new File(absoluteFilePath);
		file.delete();
	}

	/**
	 * @return String information about command "delete-vm".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
