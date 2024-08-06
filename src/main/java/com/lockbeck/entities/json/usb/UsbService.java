package com.lockbeck.entities.json.usb;

import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsbService {
    private final UsbRepository usbRepository;

    public void create(List<USBCreateRequest> requests, JsonEntity save) {
        for (USBCreateRequest request : requests) {
            USB usb = new USB();
            usb.setDeviceId(request.getDeviceId());
            usb.setPNPDeviceID(request.getPNPDeviceID());
            usb.setDescription(request.getDescription());
            usb.setJson(save);
            usbRepository.save(usb);
        }
    }
}
