package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.vm.CreateVirtualMachineCmd;

public class CreateVirtualMachineCmdTest {

	CreateVirtualMachineCmd create;

	@Before
	public void setUp() throws Exception {
		create = new CreateVirtualMachineCmd();
		ESXSimulator.VMsStorage.clear();
	}

	@Test
	public void testGetName() {
		assertEquals("create-vm", create.getName());
	}

	@Test
	public void testExecuteWithCorrectInputFinalState() {
		String result = create.execute("id123 'windows 8' 1024 4");

		assertEquals("Virtual machine is created", result);
	}

	@Test
	public void testExecuteWithCorrectInputSpaceFinalState() {
		String result = create.execute("id123 'windows 8' 1024 4");

		assertEquals("Virtual machine is created", result);
	}

	@Test
	public void testExecuteWithCorrectInputCreation() {
		create.execute("id1 'windows 8' 1024 4");
		VirtualMachine created = ESXSimulator.VMsStorage.get("id1");

		assertEquals(true, ESXSimulator.VMsStorage.containsKey("id1"));
		assertEquals("id1", created.getID());
		assertEquals("windows 8", created.getName());
		assertEquals(1024, created.getMemory());
	}

	@Test
	public void testExecuteWithIncorrectInputIDCreation() {
		String result = create.execute("id1-1 'windows 8' 1024 4");

		assertEquals(
				"The ID of the virtual machine could be only alphanumeric",
				result);
	}

	@Test
	public void testExecuteWithIncorrectInputNameCreation() {
		String result = create.execute("id234 'windows@8' 1024 8");

		assertEquals(
				"The name of the virtual machine must contain only alphanumeric characters and/or space.",
				result);
	}

	@Test
	public void testExecuteWithIncorrectInputMemoryCreation() {
		String result = create.execute("id756 'windows8' -1024 4");

		assertEquals(
				"The memory of the virtual machine can not be negative or zero value",
				result);
	}
	
	@Test
	public void testExecuteWithIncorrectInputCPUsCreation() {
		String result = create.execute("id756 'windows8' 1024 0");

		assertEquals(
				"The number of CPUs is invalid. It should be in range [1-8]",
				result);
	}

	@Test
	public void testExecuteWithIncorrectInputExistingIDCreation() {
		String result = create.execute("id16 'windows8' 1024 4");
		String resultExisting = create.execute("id16 'osX' 1024 4");

		assertEquals("Virtual machine is created", result);
		assertEquals("Virtual Machine with this ID already existing!",
				resultExisting);
	}

	@Test
	public void testExecuteWithCorrectInputeExistingNameCreation() {
		String result = create.execute("id6 'windows8' 1024 4");
		String resultExisting = create.execute("id12 'windows8' 4096 4");

		assertEquals("Virtual machine is created", result);
		assertEquals("Virtual machine is created", resultExisting);
		assertEquals(true, ESXSimulator.VMsStorage.containsKey("id6"));
		assertEquals(true, ESXSimulator.VMsStorage.containsKey("id12"));
	}

	@Test
	public void testExecuteWithCorrectInputeExistingMemoryCreation() {
		String result = create.execute("id6 'windows8' 1024 4");
		String resultExisting = create.execute("id12 'OSX10' 1024 4");

		assertEquals("Virtual machine is created", result);
		assertEquals("Virtual machine is created", resultExisting);
		assertEquals(true, ESXSimulator.VMsStorage.containsKey("id6"));
		assertEquals(true, ESXSimulator.VMsStorage.containsKey("id12"));
	}

	@Test
	public void testInfo() {
		assertEquals(
				"create-vm - This command provide ability to create new virtual machine\n\n",
				create.info());
	}
	
	@Test
	public void testCreateVMWithMissingSpecifications() {
		assertEquals(
				"Missing specifications!",
				create.execute("id1 'test' 1024"));
	}
}
