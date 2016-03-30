package com.broship.user.model;

public class Device {

	private String id;
	private String os;
	private String version;
	private String model;
	private String resolution;

	public Device() {
		super();
	}

	public Device(String id, String os, String version, String model,
			String resolution) {
		super();
		this.id = id;
		this.os = os;
		this.version = version;
		this.model = model;
		this.resolution = resolution;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", os=" + os + ", version=" + version
				+ ", model=" + model + ", resolution=" + resolution + "]";
	}

}
