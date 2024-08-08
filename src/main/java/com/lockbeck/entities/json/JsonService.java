package com.lockbeck.entities.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockbeck.demo.Response;
import com.lockbeck.entities.json.antivirus.AntivirusService;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserService;
import com.lockbeck.entities.json.usb.UsbService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final JsonRepository jsonRepository;
    private final AntivirusService antivirusService;
    private final UsbService usbService;
    private final SocialAppsInBrowserService socialAppsInBrowserService;
    private final ObjectMapper objectMapper;

    public void create(JsonCreateRequest request){
        JsonEntity entity = new JsonEntity();
        entity.setIpAddress(request.getIpAddress());
        entity.setRemoteAccess(request.getRemoteAccess());
        entity.setAdminRight(request.getAdminRight());
        entity.setFirewall(request.getFirewall());


        entity.setThreeGModem(request.getThreeGModem());
        entity.setInternet(request.getInternet());
        entity.setNetworkStatus(request.getNetworkStatus());


        entity.setIpAddress(request.getIpAddress());
        entity.setInstalledApps(request.getInstalledApps());

        entity.setSocialAppsInDesktop(request.getSocialAppsInDesktop());

        JsonEntity save = jsonRepository.save(entity);
        antivirusService.create(request.getAntivirus(),save);
        socialAppsInBrowserService.create(request.getSocialAppsInBrowser(),save);

    }

    @Transactional
    public void processJsonFiles(List<File> jsonFiles) throws IOException {
        for (File jsonFile : jsonFiles) {
            JsonEntity jsonEntity = objectMapper.readValue(jsonFile, JsonEntity.class);
            jsonRepository.save(jsonEntity);
        }
    }
}
