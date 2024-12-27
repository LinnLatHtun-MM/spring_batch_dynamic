package per.llt.spring_batch_dynamic.Processor;

import org.springframework.batch.item.ItemProcessor;
import per.llt.spring_batch_dynamic.model.Order;

public class OrderItemProcessor implements ItemProcessor<Order, Order> {
    @Override
    public Order process(Order order) throws Exception {
        order.setAmount(order.getAmount() * 0.9);
        return order;
    }
}
