package lk.ijse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonIgnore
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "itemCode", referencedColumnName = "itemCode")
    )
    @JsonIgnore
    private List<ItemEntity> items;
}

