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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static final List<String> ILLEGAL_SOFTWARES = List.of("Telegram Desktop", "AnyDesk");

    public void create(JsonCreateRequest request) {

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
        antivirusService.create(request.getAntivirus(), save);
        socialAppsInBrowserService.create(request.getSocialAppsInBrowser(), save);

    }

    @Transactional
    public void processJsonFiles(List<File> jsonFiles, Integer auditId) throws IOException {
        for (File jsonFile : jsonFiles) {
            JsonEntity jsonEntity = objectMapper.readValue(jsonFile, JsonEntity.class);
            jsonEntity.setAudit(auditService.get(auditId));
            jsonRepository.save(jsonEntity);
            antivirusRepository.saveAll(jsonEntity.getAntivirus());
            usbRepository.saveAll(jsonEntity.getUsb());
            socialAppsInBrowserRepository.saveAll(jsonEntity.getSocialAppsInBrowser());

        }
    }

    public Resource createWordReport() throws IOException {

        List<JsonEntity> all = jsonRepository.findAll();
        double total = all.size();

        double noUps = 0;
        double adminTrue = 0;
        double hasRemoteAccess = 0;
        double illegalSoftware = 0;
        double oldOs = 0;
        double noFirewall = 0;
        double noAntivirus = 0;
        double noAntivirusLicense = 0;
        double noPlomba = 0;
        double hasInternet = 0;
        double hasThreeGModem = 0;


        for (JsonEntity jsonEntity : all) {
            if (jsonEntity.getUps().equals(Boolean.FALSE)) {
                noUps++;
            }

            if (jsonEntity.getInternet().equals(Boolean.TRUE)) {
                hasInternet++;
            }
            if (jsonEntity.getThreeGModem().equals(Boolean.TRUE)) {
                hasThreeGModem++;
            }
            if (jsonEntity.getAdminRight().equals("Cheklanmagan")) {
                adminTrue++;
            }
            if (jsonEntity.getRemoteAccess().equals(Boolean.TRUE)) {
                hasRemoteAccess++;
            }
            boolean equal = false;
            for (String installedApp : jsonEntity.getInstalledApps()) {
                for (String software : ILLEGAL_SOFTWARES) {
                    if (installedApp.equalsIgnoreCase(software)) {
                        illegalSoftware++;
                        equal = true;
                        break;
                    }
                }
                if (equal) {
                    break;
                }
            }

            if(
                    jsonEntity.getOs().toLowerCase().contains("windows 7")
                    ||jsonEntity.getOs().toLowerCase().contains("windows 8")
                    ||jsonEntity.getOs().toLowerCase().contains("windows xp")
            ){
                oldOs++;
            }
            if(jsonEntity.getFirewall().equals("Ishga tushurilmagan")){
                noAntivirus++;
            }
            if(jsonEntity.getAntivirus().isEmpty()){
                noAntivirus++;
            }
            if(jsonEntity.getHasLicence().equals(Boolean.FALSE)){
                noAntivirusLicense++;
            }
            if(jsonEntity.getPlomba().equals(Boolean.FALSE)){
                noPlomba++;
            }
        }

        XWPFDocument document = new XWPFDocument();

        // 4.3.1 bo'limi uchun default matn
        paragraph(document,
                "      4.3.1. 100 ishchi kompyuterlarining axborot xavfsizligini ta’minlashning dasturiy-texnik ko‘rsatkichlarini tekshiruvi natijalari",
                true,
                180,
                null
        );

        paragraph(document,
                "Texnik ko`rsatkichlar bo`yicha:",
                true,
                60,
                null
        );
        paragraph(document,
                "Zaxira elektr ta'minot manbaiga ega emas:  " + (noUps / total) * 100 + "%",
                false,
                60,
                null
                );

        paragraph(document,
                "Internetga ulangan kompyuterlar:  " + (hasInternet / total) * 100 + "%",
                false,
                60,
                null
        );

        paragraph(document,
                "3G modem qurilmasiga ega:  " + (hasThreeGModem / total) * 100 + "%",
                false,
                180,
                null
        );

        paragraph(document,
                "Foydalanuvchi huquqlari bo`yicha:",
                true,
                60,
                null
        );

        paragraph(document,
                "Cheklanmagan (administratorlik) huquqlariga ega:  " + (adminTrue / total) * 100 + "%",
                false,
                180,
                null
        );


        paragraph(document,
                "Tizim ko`rsatkichlari bo`yicha:",
                true,
                60,
                null
        );

        paragraph(document,
                "Potensial zaif MsRDP vositalaridan foydalaniladi:  "+(hasRemoteAccess/total)*100+"%",
                false,
                60,
                null
        );

        paragraph(document,
                "Ruxsat etilmagan dasturiy ta’minotlarga ega :  " + (illegalSoftware / total) * 100 + "%",
                false,
                60,
                null
        );

        paragraph(document,
                "Ma’nan eskirgan operatsion tizimlar:  " + (oldOs/total)*100 + "%",
                false,
                180,
                null
        );

        paragraph(document,
                "Foydalanuvchi faoliyati bo‘yicha: Klient himoya vositalarini ishlatish bo‘yicha: :",
                true,
                60,
                null
                );

        paragraph(document,
                "Lokal tarmoqlararo ekran vositalari ishlatilmaydi:  " + (noFirewall /total)*100 + "%",
                false,
                60,
                null
        );
        paragraph(document,
                "Antivirus vositasiga ega emas:  " + (noAntivirus /total)*100 + "%",
                false,
                60,
                null
        );
        paragraph(document,
                "Antivirus vositasi litsenziyaga ega emas:  " + (noAntivirusLicense /total)*100 + "%",
                false,
                60,
                null
        );
        paragraph(document,
                "Plombaga ega emas:  " + (noPlomba /total)*100 + "%",
                false,
                60,
                null
        );

        paragraph(document,
                "4.4.1.\t ishchi kompyuterlarining dasturiy-texnik ko‘rsatkichlarini axborot xavfsizligi ta’minlanganlik holatini yaxshilash bo‘yicha takliflar",
                true,
                180,
                "#0000FF"
                );

        // JSON ma'lumotlarini kiritish
        /*List<JsonEntity> statistics = jsonRepository.findAll();
        for (JsonEntity statistic : statistics) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(statistic.getName() + ": " + statistic.getMac());
        }*/

        // Hujjatni "uploads" papkasiga saqlash
        Path uploadsDir = Paths.get("reports");
        if (!uploadsDir.toFile().exists()) {
            uploadsDir.toFile().mkdirs(); // Papkani yaratish, agar mavjud bo'lmasa
        }

        String fileName = "report.docx";
        Path filePath = uploadsDir.resolve(fileName);

        try (FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            document.write(out);
        }

        document.close();

        // FileSystemResource dan foydalanib, resursni qaytarish
        return new FileSystemResource(filePath.toFile());
    }

    private static void paragraph(XWPFDocument document, String text, boolean bold, int space,String color) {
        XWPFParagraph bigParagraph1 = document.createParagraph();
        XWPFRun run1_1 = bigParagraph1.createRun();
        run1_1.setText(text);
        run1_1.setBold(bold);
        run1_1.setFontSize(14);
        run1_1.setColor(color==null?"#000000":color);
        bigParagraph1.setSpacingAfter(space);
    }
}
