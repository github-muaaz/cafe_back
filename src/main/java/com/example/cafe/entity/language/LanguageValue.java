package com.example.cafe.entity.language;

import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"language_id", "language_key_id"}))
public class LanguageValue extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    private LanguageKey languageKey;

    @Column(nullable = false)
    private String value;
}
