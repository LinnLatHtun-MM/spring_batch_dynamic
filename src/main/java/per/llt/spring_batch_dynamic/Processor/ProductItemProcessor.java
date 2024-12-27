package per.llt.spring_batch_dynamic.Processor;

import org.springframework.batch.item.ItemProcessor;
import per.llt.spring_batch_dynamic.model.Product;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product product) throws Exception {
        product.setPrice(product.getPrice() * 1.1);
        return product;
    }
}
