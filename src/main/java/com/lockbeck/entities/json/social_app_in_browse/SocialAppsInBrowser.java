package com.lockbeck.entities.json.social_app_in_browse;

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
    public String name;
    public String url;
    public String lastSeen;
    @ManyToOne
    @JoinColumn(name = "json_id")
    private JsonEntity json;
}
