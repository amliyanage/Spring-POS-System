package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "items")
@Entity
public class ItemEntity implements SuperEntity {

    @Id
    private String itemCode;
    private String itemName;
    private int itemQty;
    private double itemPrice;

    @ManyToMany(mappedBy = "items", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderEntity> orders;
}
