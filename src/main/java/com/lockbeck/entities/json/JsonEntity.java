package com.lockbeck.entities.json;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("IpAdress")
    private String ipAddress;

    @JsonProperty("MAC")
    private String mac;

    @JsonProperty("NameComputer")
    private String name;

    @JsonProperty("OS")
    private String os;

    @JsonProperty("CPU")
    private String cpu;

    @JsonProperty("RAM")
    private String ram;

    @JsonProperty("RemoteAccess")
    private Boolean remoteAccess;

    @JsonProperty("IsAdmin")
    private String adminRight;

    @JsonProperty("Firewall")
    private String firewall;

    @JsonProperty("Antiviruses")
    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<Antivirus> antivirus;

    @JsonProperty("HasLicensia")
    private Boolean hasLicence;

    @JsonProperty("ThreeGModem")
    private Boolean threeGModem;

    @JsonProperty("Internet")
    private Boolean internet;

    @JsonProperty("NetworkStatus")
    private String networkStatus;

    @JsonProperty("USB")
    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<USB> usb;

    @JsonProperty("DVD")
    private Boolean dvd;

    @JsonProperty("SturtupApps")
    @ElementCollection
    private List<String> startUpApps;

    @JsonProperty("InstalledApps")
    @ElementCollection
    private List<String> InstalledApps;

    @JsonProperty("UPS")
    private Boolean ups;

    @JsonProperty("PLOMBA")
    private Boolean plomba;

    @JsonProperty("SocialAppsInDesktop")
    @ElementCollection
    private List<String> socialAppsInDesktop;

    @JsonProperty("SocialAppsInBrowser")
    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<SocialAppsInBrowser> socialAppsInBrowser;
}
