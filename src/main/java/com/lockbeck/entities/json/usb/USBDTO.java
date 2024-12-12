package com.lockbeck.entities.json.usb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class USBDTO {
    private Integer id;
    private String deviceId;
    private String pNPDeviceID;
    private String description;
}
