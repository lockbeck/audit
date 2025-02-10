package com.lockbeck.entities.json.social_app_in_browser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialAppsInBrowserRepository extends JpaRepository<SocialAppsInBrowser,Integer> {

    List<SocialAppsInBrowser> findAllByJsonId(Integer id);
}
