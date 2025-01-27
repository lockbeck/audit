package com.lockbeck.entities.json.social_app_in_browse;

import com.lockbeck.entities.json.antivirus.Antivirus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialAppsInBrowserRepository extends JpaRepository<SocialAppsInBrowser,Integer> {
    List<SocialAppsInBrowser> findByJsonId(Integer jsonId);
}
