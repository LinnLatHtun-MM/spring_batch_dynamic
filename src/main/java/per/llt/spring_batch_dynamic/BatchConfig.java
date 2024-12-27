package per.llt.spring_batch_dynamic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.llt.spring_batch_dynamic.factory.BatchComponentFactory;
import per.llt.spring_batch_dynamic.factory.CsvReaderFactory;
import per.llt.spring_batch_dynamic.model.Customer;
import per.llt.spring_batch_dynamic.model.Order;
import per.llt.spring_batch_dynamic.model.Product;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BatchComponentFactory batchComponentFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, BatchComponentFactory batchComponentFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.batchComponentFactory = batchComponentFactory;
    }

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .start(processStep("customer.csv", new String[]{"id", "name", "email"}, Customer.class))
                .next(processStep("order.csv", new String[]{"orderId", "productName", "amount"}, Order.class))
                .next(processStep("product.csv", new String[]{"productId", "name", "price"}, Product.class))
                .build();
    }


    private <T> Step processStep(String fileName, String[] fieldNames, Class<T> entityClass) {
        return stepBuilderFactory.get("processStep-" + entityClass.getSimpleName())
                .<T, T>chunk(10)
                .reader(itemReader(fileName, fieldNames, entityClass))
                .processor(batchComponentFactory.getProcessor(entityClass))
                .writer(batchComponentFactory.getWriter(entityClass))
                .build();
    }


    private <T> ItemReader<T> itemReader(String fileName, String[] fieldNames, Class<T> entityClass) {
        return CsvReaderFactory.createReader(fileName, fieldNames, entityClass);
    }
}
