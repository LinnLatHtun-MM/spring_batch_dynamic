package per.llt.spring_batch_dynamic.factory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import per.llt.spring_batch_dynamic.Processor.CustomerItemProcessor;
import per.llt.spring_batch_dynamic.Processor.OrderItemProcessor;
import per.llt.spring_batch_dynamic.Processor.ProductItemProcessor;
import per.llt.spring_batch_dynamic.model.Customer;
import per.llt.spring_batch_dynamic.model.Order;
import per.llt.spring_batch_dynamic.model.Product;
import per.llt.spring_batch_dynamic.repo.CustomerRepository;
import per.llt.spring_batch_dynamic.repo.OrderRepository;
import per.llt.spring_batch_dynamic.repo.ProductRepository;

import java.util.List;

@Component
public class BatchComponentFactory {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public BatchComponentFactory(CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    public static <T> ItemProcessor<T, T> getProcessor(Class<T> entityClass) {
        if (entityClass == Customer.class) {
            return (ItemProcessor<T, T>) new CustomerItemProcessor();
        } else if (entityClass == Order.class) {
            return (ItemProcessor<T, T>) new OrderItemProcessor();
        } else if (entityClass == Product.class) {
            return (ItemProcessor<T, T>) new ProductItemProcessor();
        } else {
            throw new IllegalArgumentException("No processor found for class: " + entityClass.getName());
        }
    }

//    public static <T> ItemReader<T> getReader(Class<T> entityClass) {
//        if (entityClass == Customer.class) {
//            return (ItemReader<T>) new CustomerItemReader();
//        } else if (entityClass == Order.class) {
//            return (ItemReader<T>) new OrderItemReader();
//        } else if (entityClass == Product.class) {
//            return (ItemReader<T>) new ProductItemReader();
//        } else {
//            throw new IllegalArgumentException("No reader found for class: " + entityClass.getName());
//        }
//    }

    public <T> ItemWriter<T> getWriter(Class<T> entityClass) {
        if (entityClass == Customer.class) {
            return (ItemWriter<T>) customers -> customerRepository.saveAll((List<Customer>) customers);
        } else if (entityClass == Order.class) {
            return (ItemWriter<T>) orders -> orderRepository.saveAll((List<Order>) orders);
        } else if (entityClass == Product.class) {
            return (ItemWriter<T>) products -> productRepository.saveAll((List<Product>) products);
        } else {
            throw new IllegalArgumentException("No writer found for class: " + entityClass.getName());
        }
    }

}
