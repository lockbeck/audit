package com.lockbeck.entities.json;

import com.lockbeck.entities.json.antivirus.Antivirus;
import com.lockbeck.entities.json.antivirus.AntivirusCreateRequest;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowser;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserCreateRequest;
import com.lockbeck.entities.json.usb.USB;
import com.lockbeck.entities.json.usb.USBCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter

public class JsonCreateRequest {


    private String IpAddress;
    private String MAC;
    private String NameComputer;
    private String OS;
    private String CPU;
    private String RAM;
    private Boolean RemoteAccess;
    private String AdminRight;
    private String Firewall;
    private List<AntivirusCreateRequest> Antivirus;
    private Boolean HasLicensia;
    private Boolean ThreeGModem;
    private Boolean Internet;
    private String NetworkStatus;
    private List<USBCreateRequest> USB;
    private Boolean DVD;
    private List<String> SturtupApps;
    private List<String> InstalledApps;
    private Boolean UPS;
    private Boolean PLOMBA;
    private List<String> SocialAppsInDesktop;
    private List<SocialAppsInBrowserCreateRequest> SocialAppsInBrowser;
}
