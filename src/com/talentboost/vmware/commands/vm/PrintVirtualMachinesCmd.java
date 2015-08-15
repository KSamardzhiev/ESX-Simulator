package com.talentboost.vmware.commands.vm;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class provide logic of command print-vms and implements ICommand
 * interface. It provide ability to print short description of all available
 * virtual machine in the ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class PrintVirtualMachinesCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "print-vms";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "print-vms - This command prints a human-friendly summary of all the current virtual machines\n\n";

	/**
	 * @return String name of command "print-vms".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method provide logic of execution of print-vms command. It iterate
	 * over the map with all available virtual machines and invokes their
	 * toString() method.
	 * 
	 * @param args
	 *            String arguments after the command print-vms. In this case
	 *            args param is not used because after command "print-vms" there
	 *            are no arguments.
	 * @return String result of execution of command "print-vms".
	 */
	@Override
	public String execute(String args) {

		StringBuffer result = new StringBuffer("ID\tName\tMemory\tCPUs\n");
		for (VirtualMachine vm : ESXSimulator.VMsStorage.values()) {
			result.append(vm.toString() + "\n");
		}
		result.append("-----------------------------------------------");
		return result.toString();
	}

	/**
	 * @return String information about command "print-vms".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
