package com.lockbeck.entities.json.social_app_in_browse;

import com.lockbeck.entities.json.JsonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialAppsInBrowserService {
    private final SocialAppsInBrowserRepository socialAppsInBrowserRepository;

    public void create(List<SocialAppsInBrowserCreateRequest> requests, JsonEntity save) {
        if (requests.isEmpty()) {return;}
        for (SocialAppsInBrowserCreateRequest request : requests) {
            SocialAppsInBrowser social = new SocialAppsInBrowser();
            social.setName(request.getName());
            social.setUrl(request.getUrl());
            social.setLastSeen(request.getLastSeen());
            social.setJson(save);
            socialAppsInBrowserRepository.save(social);
        }
    }

    public List<SocialAppsInBrowserDTO> getList(List<SocialAppsInBrowser> socialAppsInBrowser) {
        List<SocialAppsInBrowserDTO> socialAppsInBrowserDTOs = new ArrayList<>();
        for (SocialAppsInBrowser socialApp : socialAppsInBrowser) {
            SocialAppsInBrowserDTO build = SocialAppsInBrowserDTO.builder()
                    .name(socialApp.getName())
                    .url(socialApp.getUrl())
                    .lastSeen(socialApp.getLastSeen())
                    .id(socialApp.getId())
                    .build();
            socialAppsInBrowserDTOs.add(build);

        }
        return socialAppsInBrowserDTOs;
    }

    public void delete(SocialAppsInBrowser socialAppsInBrowser) {
        socialAppsInBrowser.setJson(null);
        socialAppsInBrowserRepository.delete(socialAppsInBrowser);
    }
}
