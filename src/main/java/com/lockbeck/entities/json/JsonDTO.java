package com.lockbeck.entities.json;

import com.lockbeck.entities.json.antivirus.AntivirusDTO;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserDTO;
import com.lockbeck.entities.json.usb.USBDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonDTO {
    private Integer id;
    private String ipAddress;
    private String mac;
    private String name;
    private String os;
    private String cpu;
    private String ram;
    private Boolean remoteAccess;
    private String adminRight;
    private String firewall;
    private List<AntivirusDTO> antivirus;
    private Boolean hasLicence;
    private Boolean threeGModem;
    private Boolean internet;
    private String networkStatus;
    private List<USBDTO> usb;
    private Boolean dvd;
    private List<String> startUpApps;
    private List<String> installedApps;
    private Boolean ups;
    private Boolean plomba;
    private List<String> socialAppsInDesktop;
    private List<SocialAppsInBrowserDTO> socialAppsInBrowser;
    private Boolean hasAntivirusPsw;

}
