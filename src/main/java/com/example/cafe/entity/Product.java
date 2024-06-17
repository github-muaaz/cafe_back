package com.example.cafe.entity;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE id=?")
public class Product extends AbsUUIDEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Float price;

    @Column(columnDefinition = "text",length = 700)
    private String description;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String currency;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UniStatusEnum status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<FileImg> images;

    @ElementCollection()
    private Set<String> ingredient;

    private Float discount;
}
