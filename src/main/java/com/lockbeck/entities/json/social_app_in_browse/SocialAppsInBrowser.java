package com.lockbeck.entities.json.social_app_in_browse;

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

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Url")
    private String url;

    @JsonProperty("LastSeen")
    private String lastSeen;

    @ManyToOne
    @JoinColumn(name = "json_id")
    @JsonBackReference
    private JsonEntity json;
}
