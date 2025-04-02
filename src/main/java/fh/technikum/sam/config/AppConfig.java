package fh.technikum.sam.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the application.
 * Defines beans to be managed by the Spring container.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and provides a {@link ModelMapper} bean.
     * <p>
     * ModelMapper is used for object mapping between different models in the application.
     * </p>
     *
     * @return a new instance of {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
