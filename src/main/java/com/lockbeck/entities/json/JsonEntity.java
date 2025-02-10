package com.lockbeck.entities.json;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.entities.json.antivirus.Antivirus;
import com.lockbeck.entities.json.social_app_in_browser.SocialAppsInBrowser;
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

    @Column(columnDefinition = "varchar(10000)")
    private String ipAddress;

    @Column(columnDefinition = "varchar(10000)")
    private String mac;

    @Column(columnDefinition = "varchar(10000)")
    private String name;

    @Column(columnDefinition = "varchar(10000)")
    private String os;

    private String systemType;

    @Column(columnDefinition = "varchar(10000)")
    private String cpu;

    @Column(columnDefinition = "varchar(10000)")
    private String ram;
    @Column(columnDefinition = "varchar(10000)")
    private String location;

    private Boolean remoteAccess;

    @Column(columnDefinition = "varchar(10000)")
    private String adminRight;

    @Column(columnDefinition = "varchar(10000)")
    private String firewall;

    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<Antivirus> antivirus;

    private Boolean hasLicence;

    private Boolean threeGModem;

    private Boolean internet;

    @Column(columnDefinition = "varchar(10000)")
    private String networkStatus;

    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<USB> usb;

    private Boolean dvd;

    @ElementCollection
    private List<String> startUpApps;

    @ElementCollection
    private List<String> InstalledApps;

    private Boolean ups;

    private Boolean hasAntivirusPsw;

    private Boolean plomba;

    @ElementCollection
    private List<String> socialAppsInDesktop;

    @OneToMany(mappedBy = "json")
    @JsonManagedReference
    private List<SocialAppsInBrowser> socialAppsInBrowser;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private AuditEntity audit;
}
