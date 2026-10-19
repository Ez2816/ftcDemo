package com.qualcomm.robotcore.hardware;

import java.util.LinkedHashMap;
import java.util.Map;

/** Looks up devices by the names set in the Driver Station robot configuration. */
public class HardwareMap {
    private final Map<String, HardwareDevice> devices = new LinkedHashMap<>();

    public <T> T get(Class<? extends T> classOrInterface, String deviceName) {
        HardwareDevice device = devices.get(deviceName);
        if (device == null || !classOrInterface.isInstance(device)) {
            throw new IllegalArgumentException(String.format(
                    "Unable to find a hardware device with name \"%s\" and type %s",
                    deviceName, classOrInterface.getSimpleName()));
        }
        return classOrInterface.cast(device);
    }

    public void put(String deviceName, HardwareDevice device) {
        devices.put(deviceName, device);
    }
}
