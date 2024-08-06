package com.lockbeck.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @CreatedBy
    @Column(updatable = false,nullable = true)
    private Integer createdBy = 1;
    @CreatedDate
    @Column(updatable = false,nullable = true)
    private LocalDateTime createdDate = LocalDateTime.now();
    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
    private Boolean deleted  = Boolean.FALSE;
}
