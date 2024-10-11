package lk.ijse.Repository;

import lk.ijse.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, String> {
    boolean existsByItemCode (String itemCode);
}
