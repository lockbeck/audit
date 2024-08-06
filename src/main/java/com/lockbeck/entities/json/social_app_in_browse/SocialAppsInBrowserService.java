package com.lockbeck.entities.json.social_app_in_browse;

import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialAppsInBrowserService {
    private final SocialAppsInBrowserRepository socialAppsInBrowserRepository;

    public void create(List<SocialAppsInBrowserCreateRequest> requests, JsonEntity save) {
        for (SocialAppsInBrowserCreateRequest request : requests) {
            SocialAppsInBrowser social = new SocialAppsInBrowser();
            social.setName(request.getName());
            social.setUrl(request.getUrl());
            social.setLastSeen(request.getLastSeen());
            social.setJson(save);
            socialAppsInBrowserRepository.save(social);
        }
    }
}
