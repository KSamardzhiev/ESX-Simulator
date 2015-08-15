package com.talentboost.vmware.commands;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.talentboost.vmware.commands.devices.AddDeviceCmd;
import com.talentboost.vmware.commands.devices.DeleteDeviceCmd;
import com.talentboost.vmware.commands.vm.CreateVirtualMachineCmd;
import com.talentboost.vmware.commands.vm.DeleteVirtualMachineCmd;
import com.talentboost.vmware.commands.vm.EditVirtualMachineCmd;
import com.talentboost.vmware.commands.vm.PrintVirtualMachinesCmd;
import com.talentboost.vmware.commands.vm.ReadVirtualMachineCmd;
import com.talentboost.vmware.commands.vm.SaveVirtualMachineCmd;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class is part of the Command Design Pattern. It provide logic of
 * invoking particular command of ESXSimulator.
 * 
 * @author KSamardzhiev
 *
 */
public class CommandBroker {
	/**
	 * This map stores all available commands. The key is String and it is the
	 * name of particular command and the value is the command that
	 * correspondence to name (e.g create-vm correspondence to instance of class
	 * CreateVirtualMachineCmd, etc.)
	 */
	private final Map<String, ICommand> commandMap = new LinkedHashMap<String, ICommand>();

	/**
	 * This constructor initialize the broker and put the necessary commands in
	 * the map.
	 */
	public CommandBroker() {
		this.init();
	}

	/**
	 * This method execute the command which is given like parameter and the
	 * arguments after it. If there is no command with same name, this method
	 * return message with information. Otherwise the return String is the
	 * result of execution.
	 * 
	 * @param command
	 *            String of the name of particular command.
	 * @param args.
	 *            String of the arguments after particular command.
	 * @return String message with the result of execution of particular
	 *         command.
	 */
	public String executeCommand(String command, String args) {
		if (!this.commandMap.containsKey(command)) {
			return "Err: Unknown command " + command;
		}
		return this.commandMap.get(command).execute(args);
	}

	/**
	 * This method is very useful in case of need a iterable collection of all
	 * available commands. It is used in implementation of the command "help"
	 * where is necessary to iterate over the available command and print same
	 * information.
	 * 
	 * @return Collection of instances of classes that implement ICommand
	 *         interface.
	 */
	public Collection<ICommand> getCommands() {
		return this.commandMap.values();
	}

	/**
	 * This method initialize the map with all commands. It put in it key-value
	 * respectively String name of command and instance of class that
	 * correspondence to this command. It is invoked every time the
	 * CommandBroker class instance is created.
	 */
	private void init() {
		this.commandMap.put("create-vm", new CreateVirtualMachineCmd());
		this.commandMap.put("delete-vm", new DeleteVirtualMachineCmd());
		this.commandMap.put("save-vm", new SaveVirtualMachineCmd());
		this.commandMap.put("read-vm", new ReadVirtualMachineCmd());
		this.commandMap.put("edit-vm", new EditVirtualMachineCmd());
		this.commandMap.put("add-dev", new AddDeviceCmd());
		this.commandMap.put("delete-dev", new DeleteDeviceCmd());
		this.commandMap.put("print-vms", new PrintVirtualMachinesCmd());
		this.commandMap.put("help", new HelpCmd());
		this.commandMap.put("stop", new StopESXSimulatorCmd());
		this.commandMap.put("start", new StartESXSimulatorCmd());

	}
}
