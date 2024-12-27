package per.llt.spring_batch_dynamic.factory;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class CsvReaderFactory {

    /**
     * Creates a FlatFileItemReader for a given entity type.
     *
     * @param <T>        the type of the entity
     * @param fileName   the name of the CSV file
     * @param fieldNames the array of field names mapping to the CSV columns
     * @param targetType the class type of the entity
     * @return a configured FlatFileItemReader
     */
    public static <T> FlatFileItemReader<T> createReader(String fileName, String[] fieldNames, Class<T> targetType) {
        return new FlatFileItemReaderBuilder<T>()
                .name(targetType.getSimpleName() + "Reader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names(fieldNames)
                .targetType(targetType)
                .lineMapper(createLineMapper(targetType, fieldNames)) // Use dynamic LineMapper
                .build();


    }

    /**
     * Creates a dynamic LineMapper for a given entity type.
     * This method uses reflection to map CSV columns to the fields of the entity.
     *
     * @param targetType the class type of the entity
     * @param fieldNames the array of field names mapping to the CSV columns
     * @param <T>        the type of the entity
     * @return a LineMapper configured for the entity type
     */
    private static <T> LineMapper<T> createLineMapper(Class<T> targetType, String[] fieldNames) {
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();

        // Create a tokenizer to split CSV data
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(fieldNames); // Map the CSV columns to field names
        lineTokenizer.setDelimiter(","); // Assuming CSV is comma-delimited

        // Create a field set mapper to convert fields to entity
        BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(targetType);

        // Use reflection to ensure the fields exist and map them correctly
        for (String fieldName : fieldNames) {
            Field field = ReflectionUtils.findField(targetType, fieldName);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
            }
        }

        // Set the tokenizer and field set mapper
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
