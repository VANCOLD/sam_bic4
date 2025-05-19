package fh.technikum.carsharing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String title = "SAM BIC4 - Car Sharing API";
    private static final String version = "1.0";
    private static final String apiDescription =
            """
            ## 🚗 **CarSharing API** 🚦
            
            Powering the next generation of urban mobility, our API provides seamless access to
            carsharing services. Enable users to discover, book, and manage vehicle rentals
            with just a few API calls.
            
            ### 🔑 **Core Features**
            - 👤 **User Management**: Registration, authentication, and profile management
            - 🚘 **Vehicle Catalog**: Real-time availability of cars with filters
            - 📅 **Reservation System**: Flexible booking with dynamic pricing
            - 📍 **Location Services**: Find nearby vehicles using geolocation
            - 💳 **Payment Integration**: Secure transaction processing
            
            ### ⚙️ **Technical Highlights**
            - 📊 Dynamic pricing based on time/distance
            - ⏱️ 99.9% API uptime SLA
            
            ### 🚀 **Quick Start**
            1. Register users via `/user/register`
            2. Authenticate to get token (`/user/login`)
            3. Search available cars (`/vehicles/available`)
            4. Create reservation (`/reservations`)
            5. Process payment (`/payments`)
            """;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(apiDescription));
    }
}