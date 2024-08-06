package com.lockbeck.entities.json.usb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class USBCreateRequest {
    public String DeviceId;
    public String PNPDeviceID;
    public String Description;
}
