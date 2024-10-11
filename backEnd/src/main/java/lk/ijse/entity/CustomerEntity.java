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
@Table(name = "customer")
@Entity
public class CustomerEntity implements SuperEntity {

    @Id
    private String customerId;
    private String customerName;
    private String customerAddress;
    private double customerSalary;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderEntity> orders;
}
