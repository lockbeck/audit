package com.lockbeck.entities.json.antivirus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AntivirusDTO {
    private Integer id;
    private String name;
    private String status;
    private String updatedAt;
    // For referencing JsonEntity by its ID
}
