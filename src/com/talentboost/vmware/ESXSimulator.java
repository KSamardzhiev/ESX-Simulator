package com.talentboost.vmware;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.talentboost.vmware.commands.CommandBroker;

/**
 * This class provide the logic behind the ESXSimulator. It is responsible for
 * reading and invoking the correct command from particular InputStream (e.g
 * file or console).
 * 
 * @author KSamardzhiev
 *
 */
public class ESXSimulator {
	/**
	 * This map stores all existing virtual machines in ESXSimulator. The key is
	 * ID of the virtual machine and the value is object of VirtualMachine
	 * class.
	 */
	public static Map<String, VirtualMachine> VMsStorage = new ConcurrentHashMap<String, VirtualMachine>();

	/**
	 * This instance of CommandBroker class is responsible to invoke particular
	 * command
	 */
	public static final CommandBroker broker = new CommandBroker();

	/**
	 * 
	 * @param instr
	 *            - type InputStream - System.in.
	 * @author Takes the user input and process it.
	 * @throws NoSuchElementException
	 *             if end of file is reached
	 */
	public static void processInput(InputStream instr) {
		Scanner inscan = new Scanner(instr);
		try {
			while (true) {
				String line = inscan.nextLine().trim();
				processLine(line);
			}
		} catch (NoSuchElementException e) {
			// end of file reached
		} finally {
			inscan.close();
		}
	}

	/**
	 * 
	 * @param line
	 *            String of user input line (e.g create-vm id 'name' memory).
	 * @author Process the String - parse each command and take arguments after
	 *         it. The command is invoked by CommandBroker class. If it doesn't
	 *         contain the command the user will be informed with message -
	 *         "Err: Unknown command ..."
	 * @throws NoSuchElementException
	 *             if command can not be parse
	 * 
	 */
	private static void processLine(String line) {
		Scanner linescan = new Scanner(line);
		try {
			String command = linescan.next();
			if (!linescan.hasNext()) {
				String result = broker.executeCommand(command, "null");
				Application.logger.debug(result);
				// System.out.println(result);
				if (command.equals("stop")) {
					System.exit(0);
				}

			} else {
				String cmdargs = linescan.nextLine().trim();

				String result = broker.executeCommand(command, cmdargs);

				Application.logger.debug(result);
				// System.out.println(result);
			}
		} catch (NoSuchElementException e) {
			System.out.println("Cannot parse command: " + line);
		} finally {
			linescan.close();
		}

	}

	/**
	 * This method takes InputStream parameter and read commands from this
	 * stream.
	 * 
	 * @param stream
	 *            InputStream from which to be read commands.
	 */
	public void run(InputStream stream) {
		ESXSimulator.processInput(stream);

	}

}
