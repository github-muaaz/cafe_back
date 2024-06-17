package com.example.cafe.entity;

import com.example.cafe.entity.enums.OrderEnum;
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
public class OrderProduct extends AbsUUIDEntity {

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    private Integer quantity;
}
