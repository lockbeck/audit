package com.lockbeck.entities.json.social_app_in_browser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialAppsInBrowserDTO {
    private Integer id;
    private String name;
    private String url;
    private String lastSeen;
}
