package com.talentboost.vmware.commands.vm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;

public class ReadVirtualMachineCmdTest {

	ReadVirtualMachineCmd read;

	@Before
	public void setUp() throws Exception {
		read = new ReadVirtualMachineCmd();
		ESXSimulator.VMsStorage.clear();
	}

	@Test
	public void testGetName() {
		assertEquals("read-vm", read.getName());
	}

	@Test
	public void testGetInfo() {
		assertEquals(
				"read-vm - This command provide ability to read some virtual machine and save it.",
				read.info());
	}
}
