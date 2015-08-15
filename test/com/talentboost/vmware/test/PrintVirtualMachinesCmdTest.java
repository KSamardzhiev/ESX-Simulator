package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.vm.PrintVirtualMachinesCmd;

public class PrintVirtualMachinesCmdTest {
	PrintVirtualMachinesCmd print;
	VirtualMachine vm = new VirtualMachine("id1", "testVM", 4096, (byte) 4);
	VirtualMachine vm1 = new VirtualMachine("id2", "testVM2", 1024, (byte) 4);
	VirtualMachine vm2 = new VirtualMachine("id3", "testVM3", 120000, (byte) 4);
	@Before
	public void setUp() throws Exception {
		print = new PrintVirtualMachinesCmd();
		ESXSimulator.VMsStorage.put(vm.getID(),vm);
		ESXSimulator.VMsStorage.put(vm1.getID(),vm1);
		ESXSimulator.VMsStorage.put(vm2.getID(),vm2);
	}

	@Test
	public void testExecutionHeadingTest() {
		String result = print.execute(null);
		assertTrue(result.contains("ID\tName\tMemory\tCPUs"));
	}
	
	@Test
	public void testExecutionContainParticularVirtualMachine() {
		String result = print.execute(null);
		assertTrue(result.contains(vm.toString()));
		assertTrue(result.contains(vm1.toString()));
		assertTrue(result.contains(vm2.toString()));
	}

	@Test
	public void testExecutionEnd() {
		String result = print.execute(null);
		assertTrue(result.contains("-----------------------------------------------"));
	}
}
