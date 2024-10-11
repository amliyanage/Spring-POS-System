package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
@Entity
public class OrderEntity implements SuperEntity {

    @Id
    private String orderId;
    private double balance;
    private String date;
    private double discount;
    private double total;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "itemCode", referencedColumnName = "itemCode")
    )
    private List<ItemEntity> items;
}

