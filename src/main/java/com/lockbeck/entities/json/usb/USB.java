package com.lockbeck.entities.json.usb;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lockbeck.entities.json.JsonEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usb")
@Entity
public class USB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("DeviceId")
    private String deviceId;

    @JsonProperty("PNPDeviceID")
    private String pNPDeviceID;

    @JsonProperty("Description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "json_id")
    @JsonBackReference
    private JsonEntity json;
}
