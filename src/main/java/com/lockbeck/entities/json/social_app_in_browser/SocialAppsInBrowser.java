package com.lockbeck.entities.json.social_app_in_browser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockbeck.entities.json.JsonEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "social_apps_in_browser")
@Entity
public class SocialAppsInBrowser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "varchar(10000)")
    private String url;

    private String lastSeen;

    @ManyToOne
    @JoinColumn(name = "json_id")
    @JsonBackReference
    private JsonEntity json;
}
