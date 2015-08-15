package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.devices.HDControllerIDEDevice;
import com.talentboost.vmware.devices.HDControllerSCSIDevice;
import com.talentboost.vmware.devices.NetworkCardDevice;
import com.talentboost.vmware.devices.VideoCardDevice;

public class VirtualMachineTest {

	VirtualMachine vm;
	NetworkCardDevice networkCard;
	VideoCardDevice videoCard;
	HDControllerIDEDevice hdIDE;
	HDControllerSCSIDevice hdSCSI;
	NetworkCardDevice invalidNetworkCard;

	@Before
	public void setUp() throws Exception {
		vm = new VirtualMachine("id1", "OSX 10", 4096, (byte) 4);
		networkCard = new NetworkCardDevice("id12", "FF-FF-FF-FF-FF-FF");
		videoCard = new VideoCardDevice("id13", 1024, (byte) 2);
		hdIDE = new HDControllerIDEDevice("idHDIDE");
		hdSCSI = new HDControllerSCSIDevice("idHDSCSI");
		invalidNetworkCard = new NetworkCardDevice("id12", "FF-FF-AA-FF-FF-FF");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidId() {
		new VirtualMachine("id-1", "testOS", 1024, (byte) 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidName() {
		new VirtualMachine("id1", "test@OS", 1024, (byte) 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidMemory() {
		new VirtualMachine("id1", "test@OS", -1024, (byte) 4);
	}

	@Test
	public void testGetID() {
		assertEquals("id1", vm.getID());
	}

	@Test
	public void testGetName() {
		assertEquals("OSX 10", vm.getName());
	}

	@Test
	public void testGetMemory() {
		assertEquals(4096, vm.getMemory());
	}

	@Test
	public void testToString() {
		assertEquals("id1\tOSX 10\t4096\t4", vm.toString());
	}

	@Test
	public void testAddNetworkCardDevice() {
		vm.addDevice(networkCard);
		assertEquals(1, vm.getDevices().size());
		assertTrue(vm.getDevices().get("id12") instanceof NetworkCardDevice);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testAddNetworkCardDeviceWithExistingID() {
		vm.addDevice(networkCard);
		vm.addDevice(networkCard);
	}
	@Test
	public void testAddVideoCardDevice() {
		vm.addDevice(videoCard);
		assertEquals(1, vm.getDevices().size());
		assertTrue(vm.getDevices().get("id13") instanceof VideoCardDevice);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddVideoCardDeviceWithExistingID() {
		vm.addDevice(videoCard);
		vm.addDevice(videoCard);
	}
	
	@Test
	public void testAddHDControllerIDEDevice(){
		vm.addDevice(hdIDE);
		assertTrue(vm.getDevices().get("idHDIDE") instanceof HDControllerIDEDevice);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddHDControllerIDEDeviceWithExistingID(){
		vm.addDevice(hdIDE);
		vm.addDevice(hdIDE);
	}
	
	@Test
	public void testAddHDControllerSCSIDevice(){
		vm.addDevice(hdSCSI);
		assertTrue(vm.getDevices().get("idHDSCSI") instanceof HDControllerSCSIDevice);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddHDControllerSCSIDeviceWithExistingID(){
		vm.addDevice(hdSCSI);
		vm.addDevice(hdSCSI);
	}

	@Test
	public void testDeleteHDControllerSCSIDevice(){
		vm.addDevice(hdSCSI);
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);
		vm.deleteDevice(hdSCSI.getID());
		assertEquals(2, vm.getDevices().size());
		assertTrue(!vm.getDevices().containsKey(hdSCSI.getID()));	
	}
	
	@Test
	public void testDeleteHDControllerIDEDevice(){
		vm.addDevice(hdIDE);
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);
		vm.deleteDevice(hdIDE.getID());
		assertEquals(2, vm.getDevices().size());
		assertTrue(!vm.getDevices().containsKey(hdIDE.getID()));	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteHDControllerIDEDeviceWithNotExistingID(){
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);
		vm.deleteDevice(hdIDE.getID());	
	}


	@Test(expected=IllegalArgumentException.class)
	public void testDeleteHDControllerSCSIDeviceWithNotExistingID(){
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);
		vm.deleteDevice(hdSCSI.getID());	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddDeviceWithExistingID() {
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);
		vm.addDevice(invalidNetworkCard);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNetworkCardDeviceWithNotExistingID() {
		vm.addDevice(videoCard);
		vm.deleteDevice(networkCard.getID());
		
	}
	
	@Test
	public void testDeleteNetworkCardDevice() {
		vm.addDevice(videoCard);
		vm.addDevice(networkCard);
		vm.deleteDevice(networkCard.getID());
		assertTrue(!vm.getDevices().containsKey(networkCard.getID()));	
	}
	
	@Test
	public void testDeleteVideoCardDevice() {
		vm.addDevice(videoCard);
		vm.addDevice(networkCard);
		vm.deleteDevice(videoCard.getID());
		assertTrue(!vm.getDevices().containsKey(videoCard.getID()));	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteVideoDeviceWithNotExistingID() {
		vm.addDevice(networkCard);
		vm.deleteDevice(videoCard.getID());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteDeviceNegative() {
		vm.addDevice(videoCard);
		vm.deleteDevice(networkCard.getID());
		assertEquals(1, vm.getDevices().size());
	}

	@Test
	public void testGetDevice() {
		vm.addDevice(networkCard);
		vm.addDevice(videoCard);

		assertEquals(true, vm.getDevices().containsKey("id12"));
		assertEquals(false, vm.getDevices().containsKey("id122"));
		assertEquals(true, vm.getDevices().containsKey("id13"));
	}
	
	@Test
	public void testGetDevices() {
		assertTrue(vm.getDevices() instanceof Map<?,?>);
	}

	@Test
	public void testSetNameValidInput() {
		String newName = "windows 10";
		int memory = 1024;
		byte cpu = 2;
		vm.setNameMemoryCPUs(newName, memory, cpu);
		assertEquals(newName, vm.getName());
	}

	@Test
	public void testSetMemoryValidInput() {
		String newName = "windows 10";
		int memory = 1024;
		byte cpu = 2;
		vm.setNameMemoryCPUs(newName, memory, cpu);
		assertEquals(memory, vm.getMemory());
	}

	@Test
	public void testSetCPUsValidInput() {
		String newName = "windows 10";
		int memory = 1024;
		byte cpu = 2;
		vm.setNameMemoryCPUs(newName, memory, cpu);
		assertEquals(cpu, vm.getCPUs());
	}

	@Test
	public void testSetNameNegativeTest() {
		String newName = "windows@10";
		int memory = 1024;
		byte cpu = 2;
		String failResult = vm.setNameMemoryCPUs(newName, memory, cpu);
		assertEquals(
				"The name of the virtual machine must contain only alphanumeric characters and/or space.",
				failResult);
	}

	@Test
	public void testSetNameMemoryCPUsSameAsOldSpecifications() {
		String newName = "OSX 10";
		int newMemory = 4096;
		byte newCPU = 4;
		vm.setNameMemoryCPUs(newName, newMemory, newCPU);
		assertEquals(newName, vm.getName());
		assertEquals(newMemory, vm.getMemory());
		assertEquals(newCPU, vm.getCPUs());
	}

	@Test
	public void testSetNameNegativeTestEmptyString() {
		String newName = "";
		int memory = 1024;
		byte cpu = 2;
		String failResult = vm.setNameMemoryCPUs(newName, memory, cpu);
		assertEquals(
				"The name of the virtual machine must contain only alphanumeric characters and/or space.",
				failResult);
	}

	@Test
	public void testSetMemory() {
		String name = "windows 10";
		int newMemory = 1024;
		byte cpu = 2;
		vm.setNameMemoryCPUs(name, newMemory, cpu);
		assertEquals(newMemory, vm.getMemory());
	}

	@Test
	public void testSetMemoryNegativeTest() {
		String name = "windows 10";
		int newMemory = -1024;
		byte cpu = 2;
		String failResult = vm.setNameMemoryCPUs(name, newMemory, cpu);
		assertEquals(
				"The memory of the virtual machine can not be negative or zero value",
				failResult);
	}

	@Test
	public void testSetMemoryNegativeTestZero() {
		String name = "windows 10";
		int newMemory = 0;
		byte cpu = 2;
		String failResult = vm.setNameMemoryCPUs(name, newMemory, cpu);
		assertEquals(
				"The memory of the virtual machine can not be negative or zero value",
				failResult);
	}

}
