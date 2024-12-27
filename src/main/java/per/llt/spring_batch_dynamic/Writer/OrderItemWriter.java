package per.llt.spring_batch_dynamic.Writer;

import org.springframework.batch.item.ItemWriter;
import per.llt.spring_batch_dynamic.model.Order;

import java.util.List;

public class OrderItemWriter implements ItemWriter<Order> {

    @Override
    public void write(List<? extends Order> orders) throws Exception {
        orders.forEach(order -> System.out.println("Writing Order: " + order));
    }
}
