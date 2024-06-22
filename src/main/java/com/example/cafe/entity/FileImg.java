package com.example.cafe.entity;

import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE file_img SET deleted=true WHERE id=?")
public class FileImg extends AbsUUIDEntity {

    @Column(nullable = false, columnDefinition = "text")
    private String path;

    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] content;
}
