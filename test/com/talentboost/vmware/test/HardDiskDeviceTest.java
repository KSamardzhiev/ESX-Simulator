package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.devices.HardDiskDevice;

public class HardDiskDeviceTest {

	HardDiskDevice hardDisk;

	@Before
	public void setUp() throws Exception {
		hardDisk = new HardDiskDevice("1id", "id3", 123423l);
	}

	@Test
	public void testGetType() {
		assertEquals("HardDisk", hardDisk.getType());
	}

	@Test
	public void testGetID() {
		assertEquals("1id", hardDisk.getID());
	}

	@Test
	public void testGetIDController() {
		assertEquals("id3", hardDisk.getIDController());
	}

	@Test
	public void testGetSize() {
		assertEquals(123423l, hardDisk.getSize());
	}

	@Test
	public void testToString() {
		assertEquals("HardDisk 1id 123423 id3", hardDisk.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationNegativeTestInvalidID() {
		new HardDiskDevice("1 id", "id3", 123423l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationNegativeTestInvalidIDController() {
		new HardDiskDevice("1id", "id@3", 123423l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationNegativeTestInvalidSize() {
		new HardDiskDevice("1id", "id3", -123423l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationNegativeTestInvalidSizeZero() {
		new HardDiskDevice("1id", "id3", 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationNegativeTestInvalidSizeOverMax() {
		new HardDiskDevice("1id", "id3", 1_000_000_000_0001l);
	}

}
