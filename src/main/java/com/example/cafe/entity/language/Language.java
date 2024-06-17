package com.example.cafe.entity.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language extends AbsUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UniStatusEnum status;
}
