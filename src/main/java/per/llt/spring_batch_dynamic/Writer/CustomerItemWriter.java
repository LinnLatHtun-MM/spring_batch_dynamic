package per.llt.spring_batch_dynamic.Writer;


import org.springframework.batch.item.ItemWriter;
import per.llt.spring_batch_dynamic.model.Customer;

import java.util.List;

public class CustomerItemWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        customers.forEach(customer -> System.out.println("Writing Customer: " + customer));
    }
}
