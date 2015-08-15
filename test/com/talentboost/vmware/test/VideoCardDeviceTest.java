package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.devices.VideoCardDevice;

public class VideoCardDeviceTest {

	private VideoCardDevice videoCard;
	@Before
	public void setUp() throws Exception {
		videoCard = new VideoCardDevice("1id89", 4096, (byte)2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidId() {
		new VideoCardDevice("id 1", 1024, (byte) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidRAM() {
		new VideoCardDevice("id1", -1024, (byte) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidRAMZero() {
		new VideoCardDevice("id1", 0, (byte) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidRAMOverMax() {
		new VideoCardDevice("id1", 50000000, (byte) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidDisplaysNumberOverMax() {
		new VideoCardDevice("id1", 1024, (byte) 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidDisplaysNumberZero() {
		new VideoCardDevice("id1", 1024, (byte) 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidDisplaysNegativeNumber() {
		new VideoCardDevice("id1", 1024, (byte) -4);
	}

	@Test
	public void testGetID(){
		assertEquals("1id89", videoCard.getID());
	}
	
	@Test
	public void testGetType(){
		assertEquals("VIDEO_CARD", videoCard.getType());
	}
	
	@Test
	public void testGetRAM(){
		assertEquals(4096, videoCard.getRAM());
	}
	
	@Test
	public void testGetNumberOfDisplays(){
		assertEquals(2, videoCard.getNumberOfDisplays());
	}
	
	@Test
	public void testToString(){
		assertEquals("VIDEO_CARD 1id89 4096 2",videoCard.toString());
	}
}
