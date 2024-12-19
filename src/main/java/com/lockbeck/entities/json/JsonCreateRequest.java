package com.lockbeck.entities.json;

import com.lockbeck.entities.json.antivirus.Antivirus;
import com.lockbeck.entities.json.antivirus.AntivirusCreateRequest;
import com.lockbeck.entities.json.antivirus.AntivirusDTO;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowser;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserCreateRequest;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserDTO;
import com.lockbeck.entities.json.usb.USB;
import com.lockbeck.entities.json.usb.USBCreateRequest;
import com.lockbeck.entities.json.usb.USBDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter

public class JsonCreateRequest {


    private String ipAddress;
    private String mac;
    private String name;
    private String os;
    private String cpu;
    private String ram;
    private String location;
    private Boolean remoteAccess;
    private String adminRight;
    private String firewall;
    private List<AntivirusCreateRequest> antivirus;
    private Boolean hasLicence;
    private Boolean threeGModem;
    private Boolean internet;
    private String networkStatus;
    private List<USBCreateRequest> usb;
    private Boolean dvd;
    private List<String> startUpApps;
    private List<String> installedApps;
    private Boolean ups;
    private Boolean plomba;
    private List<String> socialAppsInDesktop;
    private List<SocialAppsInBrowserCreateRequest> socialAppsInBrowser;
    private Boolean hasAntivirusPsw;
}
