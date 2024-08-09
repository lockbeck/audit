package com.lockbeck.entities.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockbeck.entities.audit.AuditService;
import com.lockbeck.entities.json.antivirus.AntivirusRepository;
import com.lockbeck.entities.json.antivirus.AntivirusService;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserRepository;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserService;
import com.lockbeck.entities.json.usb.UsbRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final JsonRepository jsonRepository;
    private final AntivirusService antivirusService;
    private final SocialAppsInBrowserService socialAppsInBrowserService;
    private final ObjectMapper objectMapper;
    private final AntivirusRepository antivirusRepository;
    private final UsbRepository usbRepository;
    private final SocialAppsInBrowserRepository socialAppsInBrowserRepository;
    private final AuditService auditService;

    public void create(JsonCreateRequest request){

        JsonEntity entity = new JsonEntity();
        entity.setIpAddress(request.getIpAddress());
        entity.setRemoteAccess(request.getRemoteAccess());
        entity.setAdminRight(request.getAdminRight());
        entity.setFirewall(request.getFirewall());


        entity.setThreeGModem(request.getThreeGModem());
        entity.setInternet(request.getInternet());
        entity.setNetworkStatus(request.getNetworkStatus());


        entity.setIpAddress(request.getIpAddress());
        entity.setInstalledApps(request.getInstalledApps());

        entity.setSocialAppsInDesktop(request.getSocialAppsInDesktop());

        JsonEntity save = jsonRepository.save(entity);
        antivirusService.create(request.getAntivirus(),save);
        socialAppsInBrowserService.create(request.getSocialAppsInBrowser(),save);

    }

    @Transactional
    public void processJsonFiles(List<File> jsonFiles,Integer auditId) throws IOException {
        for (File jsonFile : jsonFiles) {
            JsonEntity jsonEntity = objectMapper.readValue(jsonFile, JsonEntity.class);
            jsonEntity.setAudit(auditService.get(auditId));
            jsonRepository.save(jsonEntity);
            antivirusRepository.saveAll(jsonEntity.getAntivirus());
            usbRepository.saveAll(jsonEntity.getUsb());
            socialAppsInBrowserRepository.saveAll(jsonEntity.getSocialAppsInBrowser());

        }
    }

    public void createWordReport() throws IOException {
        XWPFDocument document = new XWPFDocument();

        // 4.3.1 bo'limi uchun default matn
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("4.3.1. 100 ishchi kompyuterlarining axborot xavfsizligini ta’minlashning dasturiy-texnik ko‘rsatkichlarini tekshiruvi natijalari");

        // JSON ma'lumotlarini kiritish
        List<JsonEntity> statistics = jsonRepository.findAll();
        for (JsonEntity statistic : statistics) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(statistic.getName() + ": " + statistic.getMac());
        }

        // Hujjatni saqlash
        try (FileOutputStream out = new FileOutputStream("report.docx")) {
            document.write(out);
        }

        document.close();
    }
}
