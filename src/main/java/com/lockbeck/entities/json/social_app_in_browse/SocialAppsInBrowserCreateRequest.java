package com.lockbeck.entities.json.social_app_in_browse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAppsInBrowserCreateRequest {
    public String name;
    public String url;
    public String lastSeen;
}
