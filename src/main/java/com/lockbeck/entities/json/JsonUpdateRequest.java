package com.lockbeck.entities.json;

import com.lockbeck.entities.json.antivirus.AntivirusCreateRequest;
import com.lockbeck.entities.json.antivirus.AntivirusUpdateRequest;
import com.lockbeck.entities.json.social_app_in_browser.SocialAppsInBrowserCreateRequest;
import com.lockbeck.entities.json.social_app_in_browser.SocialAppsInBrowserUpdateRequest;
import com.lockbeck.entities.json.usb.USBCreateRequest;
import com.lockbeck.entities.json.usb.USBUpdateRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class JsonUpdateRequest {

    private String ipAddress;
    private String mac;
    private String name;
    private String os;
    private String systemType;
    private String cpu;
    private String ram;
    private String location;
    private Boolean remoteAccess;
    private String adminRight;
    private String firewall;
    private List<AntivirusUpdateRequest> antivirus;
    private Boolean hasLicence;
    private Boolean threeGModem;
    private Boolean internet;
    private String networkStatus;
    private List<USBUpdateRequest> usb;
    private Boolean dvd;
    private List<String> startUpApps;
    private List<String> installedApps;
    private Boolean ups;
    private Boolean plomba;
    private List<String> socialAppsInDesktop;
    private List<SocialAppsInBrowserUpdateRequest> socialAppsInBrowser;
    private Boolean hasAntivirusPsw;

}
