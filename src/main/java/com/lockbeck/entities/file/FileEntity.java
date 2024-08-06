package com.lockbeck.entities.file;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String originalName;
    @Column
    private String extension;
    @Column
    private Long size;
    @Column
    private String path;
    @Column
    private String downloadUrl;





//    @OneToOne(mappedBy = "file")
//    private DiplomaEntity diploma;
//
//    @OneToOne(mappedBy = "file")
//    private RequirementOrderEntity requirementOrder;
//
//    @OneToOne(mappedBy = "file")
//    private AdvancedCertificateEntity advancedCertificate;
//
//    @OneToOne(mappedBy = "file")
//    private CertOfConformityEntity certOfConformity;


//    @OneToOne(mappedBy = "file")
//    private NetworkEntity usedNetwork;
//
//    @OneToOne(mappedBy = "file")
//    private NetProviderEntity netProvider;
//
//    @OneToOne(mappedBy = "file")
//    private CategoryFileEntity importanceFile;
}
