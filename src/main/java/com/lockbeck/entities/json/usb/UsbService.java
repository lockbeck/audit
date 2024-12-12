package com.lockbeck.entities.json.usb;

import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<USBDTO> getList(List<USB> usb) {
        List<USBDTO> dtos = new ArrayList<>();

        usb.forEach(usb1 -> {
            USBDTO build = USBDTO.builder()
                    .deviceId(usb1.getDeviceId())
                    .pNPDeviceID(usb1.getPNPDeviceID())
                    .description(usb1.getDescription())
                    .id(usb1.getId())
                    .build();
            dtos.add(build);
        });
        return dtos;
    }

    public void delete(USB usb) {
        usb.setJson(null);
        usbRepository.delete(usb);
    }
}
