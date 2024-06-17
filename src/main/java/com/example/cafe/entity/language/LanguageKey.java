package com.example.cafe.entity.language;

import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE language_key SET deleted=true WHERE id=?")
public class LanguageKey extends AbsUUIDEntity {

    @Column(nullable = false, unique = true)
    private String key;

    private boolean deleted;
}
