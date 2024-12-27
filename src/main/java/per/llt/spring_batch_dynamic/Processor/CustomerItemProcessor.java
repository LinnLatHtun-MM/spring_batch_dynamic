package per.llt.spring_batch_dynamic.Processor;

import org.springframework.batch.item.ItemProcessor;
import per.llt.spring_batch_dynamic.model.Customer;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        customer.setName(customer.getName().toUpperCase());
        return customer;
    }
}
