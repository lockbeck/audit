package com.lockbeck.entities.json.usb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class USBUpdateRequest {
    private Integer id;
    public String deviceId;
    public String pNPDeviceID;
    public String description;
}
