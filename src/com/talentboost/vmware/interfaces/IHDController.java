package com.talentboost.vmware.interfaces;

import java.util.Collection;

import com.talentboost.vmware.devices.HardDiskDevice;

public interface IHDController{

	public String getControllerType();
	public String addHardDisk(HardDiskDevice hardDisk);
	public String getID();
	public Collection<HardDiskDevice> getHardDisks();

}
