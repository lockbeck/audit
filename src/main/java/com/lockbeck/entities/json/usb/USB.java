package com.lockbeck.entities.json.usb;

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
    public String deviceId;
    public String pNPDeviceID;
    public String description;
    @ManyToOne
    @JoinColumn(name = "json_id")
    private JsonEntity json;
}
