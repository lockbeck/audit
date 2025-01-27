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
        if (requests.isEmpty()) {return;}
        for (USBCreateRequest request : requests) {
            USB usb = new USB();
            usb.setDeviceId(request.getDeviceId());
            usb.setPNPDeviceID(request.getPNPDeviceID());
            usb.setDescription(request.getDescription());
            usb.setJson(save);
            usbRepository.save(usb);
        }
    }

    public List<USBDTO> getList(Integer jsonId) {
        List<USB> byJsonId = usbRepository.findByJsonId(jsonId);
        List<USBDTO> dtos = new ArrayList<>();

        byJsonId.forEach(usb -> {
            USBDTO build = USBDTO.builder()
                    .deviceId(usb.getDeviceId())
                    .pNPDeviceID(usb.getPNPDeviceID())
                    .description(usb.getDescription())
                    .id(usb.getId())
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
