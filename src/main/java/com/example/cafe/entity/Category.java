package com.example.cafe.entity;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id=?")
public class Category extends AbsUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Category parent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UniStatusEnum status;
}
