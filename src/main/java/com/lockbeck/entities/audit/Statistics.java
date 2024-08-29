package com.lockbeck.entities.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistics {
    private Integer completed;
    private Integer uncompleted;
}
