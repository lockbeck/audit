package com.lockbeck.entities.json.social_app_in_browser;

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

    public List<SocialAppsInBrowserDTO> getList(Integer jsonId) {
        List<SocialAppsInBrowser> byJsonId = socialAppsInBrowserRepository.findAllByJsonId(jsonId);
        List<SocialAppsInBrowserDTO> socialAppsInBrowserDTOs = new ArrayList<>();
        for (SocialAppsInBrowser socialApp : byJsonId) {
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

    public void update(List<SocialAppsInBrowserUpdateRequest> dtoList, JsonEntity updateSave) {
        List<SocialAppsInBrowser> entityList = socialAppsInBrowserRepository.findAllByJsonId(updateSave.getId());

        for (SocialAppsInBrowser entity : entityList) {
            for (SocialAppsInBrowserUpdateRequest dto : dtoList) {
                if(entity.getId().equals(dto.getId())) {
                    entity.setName(dto.getName());
                    entity.setUrl(dto.getUrl());
                    entity.setLastSeen(dto.getLastSeen());
                    socialAppsInBrowserRepository.save(entity);
                }else{
                    SocialAppsInBrowser newEntity = new SocialAppsInBrowser();
                    newEntity.setName(dto.getName());
                    newEntity.setUrl(dto.getUrl());
                    newEntity.setLastSeen(dto.getLastSeen());
                    newEntity.setJson(updateSave);
                    socialAppsInBrowserRepository.save(newEntity);
                }
            }
        }
    }
}
