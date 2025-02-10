package com.lockbeck.entities.json;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockbeck.entities.audit.AuditEntity;
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
    @Column(columnDefinition = "varchar(10000)")
    private String ipAddress;

    @JsonProperty("MAC")
    @Column(columnDefinition = "varchar(10000)")
    private String mac;

    @JsonProperty("NameComputer")
    @Column(columnDefinition = "varchar(10000)")
    private String name;

    @JsonProperty("OS")
    @Column(columnDefinition = "varchar(10000)")
    private String os;

    @JsonProperty("systemType")
    private String systemType;

    @JsonProperty("CPU")
    @Column(columnDefinition = "varchar(10000)")
    private String cpu;

    @JsonProperty("RAM")
    @Column(columnDefinition = "varchar(10000)")
    private String ram;
    @JsonProperty("Location")
    @Column(columnDefinition = "varchar(10000)")
    private String location;

    @JsonProperty("RemoteAccess")
    private Boolean remoteAccess;

    @JsonProperty("IsAdmin")
    @Column(columnDefinition = "varchar(10000)")
    private String adminRight;

    @JsonProperty("Firewall")
    @Column(columnDefinition = "varchar(10000)")
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
    @Column(columnDefinition = "varchar(10000)")
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

    @JsonProperty("HasAntivirusPsw")
    private Boolean hasAntivirusPsw;

    @JsonProperty("PLOMBA")
    private Boolean plomba;

    @JsonProperty("SocialAppsInDesktop")
    @ElementCollection
    private List<String> socialAppsInDesktop;

    @JsonProperty("SocialAppsInBrowser")
    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<SocialAppsInBrowser> socialAppsInBrowser;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private AuditEntity audit;
}
