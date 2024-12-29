package Beauty_ECatalog.Beauty_ECatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
            "/", "/User/Login", "/User/Register", "/auth/logout", "/Category/**", "/Product/**", "/services/{id}", "/Service/**", "/ProductReviews", "/StoreReviews", "/Voucher/**", "/SaleTickets/**", "/Users/**", "/Inventory/**", "/Email/**"
        };
        registry.addInterceptor(getPermissionInterceptor())
                .excludePathPatterns(whiteList);
    }
}
