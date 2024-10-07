package com.lockbeck.entities.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockbeck.demo.Response;
import com.lockbeck.entities.audit.AuditEntity;
import com.lockbeck.entities.audit.AuditService;
import com.lockbeck.entities.json.antivirus.AntivirusRepository;
import com.lockbeck.entities.json.antivirus.AntivirusService;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserRepository;
import com.lockbeck.entities.json.social_app_in_browse.SocialAppsInBrowserService;
import com.lockbeck.entities.json.usb.UsbRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final JsonRepository jsonRepository;
    private final ObjectMapper objectMapper;
    private final AntivirusRepository antivirusRepository;
    private final UsbRepository usbRepository;
    private final SocialAppsInBrowserRepository socialAppsInBrowserRepository;
    private final AuditService auditService;

    private static final List<String> ILLEGAL_SOFTWARES = List.of("Telegram Desktop", "AnyDesk");

    /*public void create(JsonCreateRequest request) {

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

    }*/

    @Transactional
    public Response processJsonFiles(List<File> jsonFiles, Integer auditId) throws IOException {
        AuditEntity auditEntity = auditService.get(auditId);
//        String name = auditEntity.getInLetter().getSubject().getName();
//        File zipFile = Files.createTempFile(name+"_ishchi_stansiyalri" , ".zip").toFile();
//
//        try (FileOutputStream fos = new FileOutputStream(zipFile);
//             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
//
//            for (File jsonFile : jsonFiles) {
//                try (FileInputStream fis = new FileInputStream(jsonFile)) {
//                    ZipEntry zipEntry = new ZipEntry(jsonFile.getName());
//                    zipOut.putNextEntry(zipEntry);
//
//                    byte[] bytes = new byte[1024];
//                    int length;
//                    while ((length = fis.read(bytes)) >= 0) {
//                        zipOut.write(bytes, 0, length);
//                    }
//
//                    zipOut.closeEntry();
//                }
//            }
//        }catch (IOException e) {
//            System.out.println("saqlanmadi");
//        }
        for (File jsonFile : jsonFiles) {

            JsonEntity jsonEntity = objectMapper.readValue(jsonFile, JsonEntity.class);
            jsonEntity.setAudit(auditEntity);
            if (jsonRepository.findByAuditIdAndMac(auditId, jsonEntity.getMac()).isEmpty()) {

                jsonRepository.save(jsonEntity);
                antivirusRepository.saveAll(jsonEntity.getAntivirus());
                usbRepository.saveAll(jsonEntity.getUsb());
                socialAppsInBrowserRepository.saveAll(jsonEntity.getSocialAppsInBrowser());
            }
        }
        return new Response(200, "success");
    }


    public Resource createWordReport(Integer auditId) throws IOException {

        List<JsonEntity> all = jsonRepository.findAllByAuditId(auditId);
        double total = all.size();

        double hasRemoteAccess = 0;
        double adminTrue = 0;
        double noFirewall = 0;
        double noAntivirus = 0;
        double noUps = 0;
        double illegalSoftware = 0;
        double oldOs = 0;
        double noAntivirusLicense = 0;
        double noPlomba = 0;
        double hasInternet = 0;
        double hasThreeGModem = 0;


        HashMap<String, String> remoteAccessMap = new HashMap<>();
        HashMap<String, String> adminMap = new HashMap<>();
        HashMap<String, String> firewallMap = new HashMap<>();
        HashMap<String, String> antivirusMap = new HashMap<>();
        HashMap<String, String> upsMap = new HashMap<>();
        HashMap<String, String> illSoftMap = new HashMap<>();
        HashMap<String, String> oldOsMap = new HashMap<>();
        HashMap<String, String> threeGModemMap = new HashMap<>();

        for (JsonEntity jsonEntity : all) {
            if (jsonEntity.getUps().equals(Boolean.FALSE)) {
                noUps++;
                upsMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }

            if (jsonEntity.getInternet().equals(Boolean.TRUE)) {
                hasInternet++;
            }
            if (jsonEntity.getThreeGModem().equals(Boolean.TRUE)) {
                hasThreeGModem++;
                threeGModemMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            if (jsonEntity.getAdminRight().equals("Cheklanmagan")) {
                adminTrue++;
                adminMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            if (jsonEntity.getRemoteAccess().equals(Boolean.TRUE)) {
                hasRemoteAccess++;
                remoteAccessMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            boolean equal = false;
            if (!jsonEntity.getInstalledApps().isEmpty()) {
                for (String installedApp : jsonEntity.getInstalledApps()) {
                    for (String software : ILLEGAL_SOFTWARES) {
                        if (installedApp.equalsIgnoreCase(software)) {
                            illegalSoftware++;
                            illSoftMap.put(jsonEntity.getMac(), jsonEntity.getName());
                            equal = true;
                            break;
                        }
                    }
                    if (equal) {
                        break;
                    }
                }
            }


            if (
                    jsonEntity.getOs().toLowerCase().contains("windows 7")
                            || jsonEntity.getOs().toLowerCase().contains("windows 8")
                            || jsonEntity.getOs().toLowerCase().contains("windows xp")
            ) {
                oldOs++;
                oldOsMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            if (jsonEntity.getFirewall().equals("Ishga tushurilmagan")) {
                noFirewall++;
                firewallMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            if (jsonEntity.getAntivirus().isEmpty()) {
                noAntivirus++;
                antivirusMap.put(jsonEntity.getMac(), jsonEntity.getName());
            }
            if (jsonEntity.getHasLicence().equals(Boolean.FALSE)) {
                noAntivirusLicense++;
            }
            if (jsonEntity.getPlomba().equals(Boolean.FALSE)) {
                noPlomba++;
            }
        }

        XWPFDocument document = new XWPFDocument();

        // 4.3.1 bo'limi uchun default matn
        paragraph(document,
                "      4.3.1. 100 ishchi kompyuterlarining axborot xavfsizligini ta’minlashning dasturiy-texnik ko‘rsatkichlarini tekshiruvi natijalari",
                true,
                180,
                null,
                false
        );

        paragraph(document,
                "Texnik ko`rsatkichlar bo`yicha:",
                true,
                60,
                null,
                false
        );
        paragraph(document,
                "Zaxira elektr ta'minot manbaiga ega emas:  " + Math.round((noUps / total) * 100) + "%",
                false,
                60,
                null,
                false
        );

        paragraph(document,
                "Internetga ulangan kompyuterlar:  " + Math.round((hasInternet / total) * 100) + "%",
                false,
                60,
                null,
                false
        );

        paragraph(document,
                "3G modem qurilmasiga ega:  " + Math.round((hasThreeGModem / total) * 100) + "%",
                false,
                180,
                null,
                false
        );

        paragraph(document,
                "Foydalanuvchi huquqlari bo`yicha:",
                true,
                60,
                null,
                false
        );

        paragraph(document,
                "Cheklanmagan (administratorlik) huquqlariga ega:  " + Math.round((adminTrue / total) * 100) + "%",
                false,
                180,
                null,
                false
        );


        paragraph(document,
                "Tizim ko`rsatkichlari bo`yicha:",
                true,
                60,
                null,
                false
        );

        paragraph(document,
                "Potensial zaif MsRDP vositalaridan foydalaniladi:  " + Math.round((hasRemoteAccess / total) * 100 )+ "%",
                false,
                60,
                null,
                false
        );

        paragraph(document,
                "Ruxsat etilmagan dasturiy ta’minotlarga ega :  " + Math.round((illegalSoftware / total) * 100) + "%",
                false,
                60,
                null,
                false
        );

        paragraph(document,
                "Ma’nan eskirgan operatsion tizimlar:  " + Math.round((oldOs / total) * 100) + "%",
                false,
                180,
                null,
                false
        );

        paragraph(document,
                "Foydalanuvchi faoliyati bo‘yicha: Klient himoya vositalarini ishlatish bo‘yicha: :",
                true,
                60,
                null,
                false
        );

        paragraph(document,
                "Lokal tarmoqlararo ekran vositalari ishlatilmaydi:  " + Math.round((noFirewall / total) * 100) + "%",
                false,
                60,
                null,
                false
        );
        paragraph(document,
                "Antivirus vositasiga ega emas:  " + Math.round((noAntivirus / total) * 100) + "%",
                false,
                60,
                null,
                false
        );
        paragraph(document,
                "Antivirus vositasi litsenziyaga ega emas:  " + Math.round((noAntivirusLicense / total) * 100) + "%",
                false,
                60,
                null,
                false
        );
        paragraph(document,
                "Plombaga ega emas:  " + Math.round((noPlomba / total) * 100) + "%",
                false,
                200,
                null,
                false
        );

        paragraph(document,
                "4.4.1.\t ishchi kompyuterlarining dasturiy-texnik ko‘rsatkichlarini axborot xavfsizligi ta’minlanganlik holatini yaxshilash bo‘yicha takliflar",
                true,
                180,
                "0000FF",
                false
        );

        paragraph(document,
                "ishchi kompyuterlari axborot xavfsizligi holatini o‘rganish natijalari:",
                false,
                180,
                "0000FF",
                false
        );

        paragraph(document,
                "\t1.  Oʻz DSt ISO/IEC 27002:2016 davlat standartining 6.2.2 va 9.1.2 bandlariga muvofiq potensial zaif masofadan ulanish vositalarini o‘chirib qo‘yish yoki ulanishdagi xatoliklarni minimumga tushirgan holda masofadan ishlash uchun mo‘ljallangan maxsus vositalardan foydalanish (4-jadval).",
                false,
                180,
                "0000FF",
                false
        );

        paragraph(document,
                "19-jadval. Potensial zaif masofadan ulanish xizmati ishga tushirilgan ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true
        );

        XWPFTable tableRemoteAccess = getTable(document, (int) hasRemoteAccess);

        styleHeaderRow(tableRemoteAccess);

        AtomicReference<Integer> remoteI = new AtomicReference<>(1);
        remoteAccessMap.forEach((s, s2) -> {
            styleDataRow(tableRemoteAccess.getRow(remoteI.get()), remoteI.toString(), s2, s, remoteI.get() % 2 == 1);
            remoteI.updateAndGet(v -> v + 1);
        });
        space(document, 200);

        paragraph(document,
                "\t2.  Jiddiy xavfsizlik tahdidlarining oldini olish uchun xodimlarning bajaradigan funksional (lavozim) vazifalarini inobatga olib operatsion tizimdagi administratorlik huquqlarini cheklash. Funksional vazifalariga axborot-kommunikatsiya infratuzilmasiga xizmat ko‘rsatish, uning xavfsizligini ta’minlash kirmaydigan xodimlarda administratorlik huquqi bo‘lmasligi kerak. Xususan, operatsion tizimda cheklanmagan huquqlarga ega bo‘lgan quyidagi ishchi kompyuter foydalanuvchilari huquqlarini qayta qo‘rib chiqish maqsadga muvofiq:",
                false,
                180,
                "0000FF",
                false
        );

        paragraph(document,
                "20-jadval. Foydalanuvchisi cheklanmagan administratorlik huquqiga ega ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true
        );

        XWPFTable tableAdmin = getTable(document, (int) adminTrue);
        styleHeaderRow(tableAdmin);

        AtomicReference<Integer> adminI = new AtomicReference<>(1);
        adminMap.forEach((s, s2) -> {
            styleDataRow(tableAdmin.getRow(adminI.get()), adminI.toString(), s2, s, adminI.get() % 2 == 1);
            adminI.updateAndGet(v -> v + 1);
        });

        space(document, 200);

        paragraph(document,
                "\t3.  Ruxsatsiz foydalanish insidentlarini oldini olish uchun operatsion tizimning standart tarmoqlararo ekranini faollashrtirish (mazkur vazifa antivirus dasturiy ta’minoti tomonidan amalga oshirish, yoki boshqa lokal dasturiy tarmoqlararo ekran faoliyatiga ta’sir qilish holatlari bundan istisno).",
                false,
                180,
                "0000FF",
                false);

        paragraph(document,
                "21-jadval. Tarmoq trafigini nazorat qilish vositasi ishga tushirilmagan ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true);
        XWPFTable firewallTable = getTable(document, (int) noFirewall);
        styleHeaderRow(firewallTable);
        AtomicReference<Integer> firewallI = new AtomicReference<>(1);
        firewallMap.forEach((s, s2) -> {
            styleDataRow(firewallTable.getRow(firewallI.get()), firewallI.toString(), s2, s, firewallI.get() % 2 == 1);
            firewallI.updateAndGet(v -> v + 1);
        });
        space(document, 200);

        paragraph(document,
                "\t4.  Zararli dastur kodini (viruslar) tarqatish hodisalarining oldini olish uchun antivirus dasturiy ta’minotida litsenziyani faollashtirish (faqat litsenziyaga ega antivirus dasturidan foydalanish maqsadga muvofiq), antivirus ma’lumotlar bazalari signaturasini aktualligini ta’minlash, antivirus sozlamalarida uning xizmatlarini (modullarini) yoqish/o‘chirish uchun cheklov o‘rnatish. Quyidagi antivirus dasturiy ta’minotiga ega bo‘lmagan ishchi kompyuterlarida antivirus dasturiy ta’minotini o‘rnatish maqsadga muvofiq.",
                false,
                180,
                "0000FF",
                false);
        paragraph(document,
                "22-jadval. Antivirus dasturiy ta’minotiga ega bo‘lmagan ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true);

        XWPFTable antivirusTable = getTable(document, (int) noAntivirus);
        styleHeaderRow(antivirusTable);
        AtomicReference<Integer> antivirusI = new AtomicReference<>(1);
        antivirusMap.forEach((s, s2) -> {
            styleDataRow(antivirusTable.getRow(antivirusI.get()),antivirusI.toString(), s2, s, antivirusI.get() % 2 == 1);
            antivirusI.updateAndGet(v -> v + 1);
        });
        space(document, 200);

        paragraph(document,
                "\t6.  Ish jarayonida ma’lumot yaxlitligini yo‘qotish va xotira qurilmalari bilan bog‘liq insidentlarni oldini olish uchun ishchi kompyuterlarni uzluksiz (zaxira) elektr manbai (UPS) bilan ta’minlash.",
                false,
                180,
                "0000FF",
                false);

        paragraph(document,
                "24-jadval. Uzluksiz (zaxira) elektr manbai (UPS) bilan ta’minlanmagan ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true);

        XWPFTable upsTable = getTable(document, (int) noUps);
        styleHeaderRow(upsTable);
        AtomicReference<Integer> upsI = new AtomicReference<>(1);
        upsMap.forEach((s, s2) -> {
            styleDataRow(upsTable.getRow(upsI.get()),upsI.toString(), s2, s, upsI.get() % 2 == 1);
            upsI.updateAndGet(v -> v + 1);
        });
        space(document, 200);

        paragraph(document,
                "\t7.  Ma’lumotlardan ruxsatsiz foydalanish, zararli kodni tarqatish, shuningdek, boshqa xavfsizlik hodisalarining oldini olish uchun dasturiy ta’minotdan foydalanish siyosatini joriy etish (tegishli topshiriq asosida ruxsat etilmagan dasturlarni ishchi kompyuterdan olib tashlash).",
                false,
                180,
                "0000FF",
                false);

        paragraph(document,
                "25-jadval. Ruxsat etilmagan dasturiy ta’minotga ega bo‘lgan ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true);

        XWPFTable illAppsTable = getTable(document, (int) illegalSoftware);
        styleHeaderRow(illAppsTable);
        AtomicReference<Integer> illAppI = new AtomicReference<>(1);
        illSoftMap.forEach((s, s2) -> {
            styleDataRow(illAppsTable.getRow(illAppI.get()),illAppI.toString(), s2, s, illAppI.get() % 2 == 1);
            illAppI.updateAndGet(v -> v + 1);
        });

        space(document,200);

        paragraph(document,
                "\t8.  Potensial jinoyatkor tomonidan keng ommaga ma’lum bo‘lgan zaiflikdan foydalanish natijasida yuzaga keladigan xavfsizlik hodisalarining oldini olish uchun barcha ishchi kompyuterlar operatsion tizimini aktual versiyalarga yangilash.",
                false,
                180,
                "0000FF",
                false);

        paragraph(document,
                "26-jadval. Ma’nan eskirgan va ishlab chiquvchi tomonidan qo‘llab-quvvatlanmaydigan operatsion tizimga ega ishchi kompyuterlar. ",
                false,
                180,
                "0000FF",
                true);

        XWPFTable oldTable = getTable(document, (int) oldOs);
        styleHeaderRow(oldTable);
        AtomicReference<Integer> oldOsI = new AtomicReference<>(1);
        oldOsMap.forEach((s, s2) -> {
            styleDataRow(oldTable.getRow(oldOsI.get()),oldOsI.toString(), s2, s, oldOsI.get() % 2 == 1);
            oldOsI.updateAndGet(v -> v + 1);
        });

        space(document,200);

        paragraph(document,
                "9.  Oʻz DSt ISO/IEC 27002:2016 Davlat standartining 8.3 bandiga muvofiq tashqi ma’lumot tashish vositalarida axborot o‘qish va yozish jarayonini nazorat qilish (masalan, zamonaviy antivirus dasturiy ta’minotining markaziy boshqaruv imkoniyati orqali). ",
                false,
                180,
                "0000FF",
                false);

        paragraph(document,
                "27-jadval. Tashqi tashish vositalarini ishlatish nazorat qilinmaydigan ishchi kompyuterlar.",
                false,
                180,
                "0000FF",
                true);

        XWPFTable modemTable = getTable(document, (int) hasThreeGModem);
        styleHeaderRow(modemTable);
        AtomicReference<Integer> modemI = new AtomicReference<>(1);
        threeGModemMap.forEach((s, s2) -> {
            styleDataRow(modemTable.getRow(modemI.get()),modemI.toString(), s2, s, modemI.get() % 2 == 1);
            modemI.updateAndGet(v -> v + 1);
        });


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

    private static void space(XWPFDocument document, int space) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingBefore(space);
    }

    private static XWPFTable getTable(XWPFDocument document, int rows) {
        XWPFTable table = document.createTable(rows + 1, 3);



        CTTblWidth tblWidth = table.getCTTbl().getTblPr().addNewTblW();
        tblWidth.setType(STTblWidth.DXA);
        tblWidth.setW(BigInteger.valueOf(9346));
        return table;
    }

    private static void paragraph(XWPFDocument document, String text, boolean bold, int space, String color, boolean italic) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(14);
        run.setItalic(italic);
        run.setFontFamily("Times New Roman");
        run.setColor(color == null ? "000000" : color);
        paragraph.setSpacingAfter(space);
    }

    private static void styleHeaderRow(XWPFTable table) {
        XWPFTableRow row = table.getRow(0);
        setCellText(row.getCell(0), "№", "FFFFFF", true);
        setCellText(row.getCell(1), "Hostning tarmoqdagi nomi", "FFFFFF", true);
        setCellText(row.getCell(2), "Hostning MAC manzili", "FFFFFF", true);
    }

    private static void styleDataRow(XWPFTableRow row, String cell1Text, String cell2Text, String cell3Text, boolean isBold) {
        String bgColor = isBold ? "DCE6F1" : "FFFFFF";  // Alternating colors
        setCellText(row.getCell(0), cell1Text, bgColor, false);
        setCellText(row.getCell(1), cell2Text, bgColor, false);
        setCellText(row.getCell(2), cell3Text, bgColor, false);
    }

    private static void setCellText(XWPFTableCell cell, String text, String bgColor, boolean bold) {
        cell.setColor(bgColor);

        // Clear any existing paragraphs and create a new one
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();

        // Set paragraph alignment (optional, but can be important for table layout)
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        // Create a run to hold the text and apply formatting
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);  // Set font size to 14
        run.setBold(bold);    // Set bold if necessary
        run.setText(text);    // Set the text in the run
    }



}
