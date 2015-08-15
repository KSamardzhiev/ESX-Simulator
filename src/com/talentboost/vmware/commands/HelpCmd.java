package com.talentboost.vmware.commands;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class implements ICommand interface and provide logic of command "help".
 * The main goal of the class is to provide ability to list all available
 * commands in ESXSimulator and short description of each command.
 * 
 * @author KSamardzhiev
 *
 */
public class HelpCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "help";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "help - This command list all available commands and short description of each of them.\n\n";

	/**
	 * @return String name of command "help".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * @param args
	 *            String arguments after the command help. In this case args
	 *            param is not used because after command "help" there are no
	 *            arguments.
	 * @return String result of execution of command "help".
	 */
	@Override
	public String execute(String args) {
		StringBuffer info = new StringBuffer();
		for (ICommand cmd : ESXSimulator.broker.getCommands()) {
			info.append(cmd.info());
		}
		return info.toString();
	}

	/**
	 * @return String information about command "help".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
