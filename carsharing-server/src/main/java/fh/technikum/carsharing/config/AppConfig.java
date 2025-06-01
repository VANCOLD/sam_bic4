package fh.technikum.carsharing.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for the application.
 * Defines beans to be managed by the Spring container.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and provides a {@link BCryptPasswordEncoder} bean.
     * <p>
     * BCryptPasswordEncoder is used for encrypting the password that is stored in the repository for any given user
     * </p>
     * @return a new instance of {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
