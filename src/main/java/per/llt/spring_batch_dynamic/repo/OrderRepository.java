package per.llt.spring_batch_dynamic.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.llt.spring_batch_dynamic.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
