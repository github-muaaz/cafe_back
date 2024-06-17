package com.example.cafe.entity;

import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.entity.enums.PermissionEnum;
import com.example.cafe.entity.template.AbsUUIDEntity;
import com.example.cafe.utils.ColumnKey;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbsUUIDEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PageEnum defaultPage;

    @CollectionTable(name = ColumnKey.ROLE_PERMISSION,
            joinColumns = @JoinColumn(name = ColumnKey.ROLE_ID, referencedColumnName = ColumnKey.ID))
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = ColumnKey.PERMISSION, nullable = false)
    private Set<PermissionEnum> permissions;

    @CollectionTable(name = ColumnKey.ROLE_PAGES,
            joinColumns = @JoinColumn(name = ColumnKey.ROLE_ID, referencedColumnName = ColumnKey.ID))
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = ColumnKey.PAGE, nullable = false)
    private Set<PageEnum> pages;
}
