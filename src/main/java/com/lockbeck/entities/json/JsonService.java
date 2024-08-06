package com.lockbeck.entities.json;

import com.lockbeck.demo.Response;
import com.lockbeck.entities.json.antivirus.AntivirusService;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserService;
import com.lockbeck.entities.json.usb.UsbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final JsonRepository jsonRepository;
    private final AntivirusService antivirusService;
    private final UsbService usbService;
    private final SocialAppsInBrowserService socialAppsInBrowserService;

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
}
