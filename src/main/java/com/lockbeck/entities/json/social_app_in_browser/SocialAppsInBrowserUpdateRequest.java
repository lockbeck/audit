package com.lockbeck.entities.json.social_app_in_browser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAppsInBrowserUpdateRequest {
    private Integer id;
    public String name;
    public String url;
    public String lastSeen;
}
