package com.lockbeck.entities.json;

import com.lockbeck.entities.json.antivirus.Antivirus;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowser;
import com.lockbeck.entities.json.usb.USB;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "json")
@Entity
public class JsonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToMany(mappedBy = "json")
    private List<Antivirus> antivirus;
    private Boolean hasLicence;
    private Boolean threeGModem;
    private Boolean internet;
    private String networkStatus;
    @OneToMany(mappedBy = "json")
    private List<USB> usb;
    private Boolean dvd;
    @ElementCollection
    private List<String> startUpApps;
    @ElementCollection
    private List<String> installedApps;
    private Boolean ups;
    private Boolean plomba;
    @ElementCollection
    private List<String> socialAppsInDesktop;
    @OneToMany(mappedBy = "json")
    private List<SocialAppsInBrowser> socialAppsInBrowser;
}
