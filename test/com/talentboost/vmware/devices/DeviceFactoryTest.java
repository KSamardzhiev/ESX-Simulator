package com.talentboost.vmware.devices;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.devices.DeviceFactory;
import com.talentboost.vmware.devices.HDControllerIDEDevice;
import com.talentboost.vmware.devices.HDControllerSCSIDevice;
import com.talentboost.vmware.devices.HardDiskDevice;
import com.talentboost.vmware.devices.NetworkCardDevice;
import com.talentboost.vmware.devices.VideoCardDevice;
import com.talentboost.vmware.interfaces.IDevice;
import com.talentboost.vmware.interfaces.IHDController;

public class DeviceFactoryTest {

	DeviceFactory factory;
	String type;

	@Before
	public void setUp() throws Exception {
		factory = new DeviceFactory();
		type = new String();
	}

	@Test
	public void testGetIDevice() {
		type = "NETWORK_CARD";
		String[] args = { "id1", "NETWORK_CARD", "id112", "FF-FF-FF-FF-FF-FF",
				"192.168.15.181" };
		assertTrue(factory.getDevice(type, args) instanceof IDevice);
	}

	@Test
	public void testGetNetworkCardDevice() {
		type = "NETWORK_CARD";
		String[] args = { "id1", "NETWORK_CARD", "id112", "FF-FF-FF-FF-FF-FF",
				"192.168.15.181" };
		assertTrue(factory.getDevice(type, args) instanceof NetworkCardDevice);
	}

	@Test
	public void testGetVideoCardDevice() {
		type = "VIDEO_CARD";
		String[] args = { "id1", "VIDEO_CARD", "id112", "512", "2" };
		assertTrue(factory.getDevice(type, args) instanceof VideoCardDevice);
	}

	@Test
	public void testGetIHDControllerDevice() {
		type = "HardDisk_Controller";
		String[] args = { "id1", "HardDisk_Controller", "id112", "IDE" };
		assertTrue(factory.getDevice(type, args) instanceof IHDController);
	}

	@Test
	public void testGetHDControllerIDEDevice() {
		type = "HardDisk_Controller";
		String[] args = { "id1", "HardDisk_Controller", "id112", "IDE" };
		assertTrue(factory.getDevice(type, args) instanceof HDControllerIDEDevice);
	}

	@Test
	public void testGetHDControllerSCSIDevice() {
		type = "HardDisk_Controller";
		String[] args = { "id1", "HardDisk_Controller", "id112", "SCSI" };
		assertTrue(factory.getDevice(type, args) instanceof HDControllerSCSIDevice);
	}

	@Test
	public void testGetHardDiskDevice() {
		type = "HardDisk";
		String[] args = { "id1", "HardDisk", "id112", "123456", "id11" };
		assertTrue(factory.getDevice(type, args) instanceof HardDiskDevice);
	}

}
