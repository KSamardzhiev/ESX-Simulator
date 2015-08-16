package com.talentboost.vmware.commands.vm;

import com.talentboost.vmware.ReadingThread;
import com.talentboost.vmware.ReturnMessage;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class provide logic of command "read-vm". It provide ability to read
 * particular virtual machine (read its configurations) from file and create it.
 * 
 * @author KSamardzhiev
 *
 */
public class ReadVirtualMachineCmd implements ICommand {

	/**
	 * This variable stores the name of the command.
	 */
	private final String NAME = "read-vm";
	/**
	 * This variable stores information about the command.
	 */
	private final String INFO = ReturnMessage.getMessage("MSG_READ_VM_INFO");

	/**
	 * @return String of the name of command "read-vm".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method provide the logic of reading particular virtual machine from
	 * file system. Also it uses threads, so it is available to read more than
	 * one virtual machine at the same time. When some thread is started the
	 * method return String message with the state of the ESXSimulator.
	 * 
	 * @param args
	 *            String with arguments after command "read-vm".
	 * @return String with the result of execution of command "read-vm".
	 */
	@Override
	public String execute(String args) {

		ReadingThread read = new ReadingThread(args);
		Thread thread = new Thread(read);
		thread.start();
		return ReturnMessage.getMessage("MSG_READ_VM_STARTED");
	}

	/**
	 * @return String with information about the command read-vm.
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
