package com.example.cafe.entity;

import com.example.cafe.entity.enums.OrderEnum;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.template.AbsUUIDEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id=?")
public class Order extends AbsUUIDEntity {

    @ManyToOne
    private Tables table;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderEnum status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> orderProducts;
}
