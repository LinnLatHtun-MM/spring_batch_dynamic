package per.llt.spring_batch_dynamic.Writer;

import org.springframework.batch.item.ItemWriter;
import per.llt.spring_batch_dynamic.model.Product;

import java.util.List;

public class ProductItemWriter implements ItemWriter<Product> {
    @Override
    public void write(List<? extends Product> products) throws Exception {
        products.forEach(product -> System.out.println("Writing Product: " + product));
    }
}
