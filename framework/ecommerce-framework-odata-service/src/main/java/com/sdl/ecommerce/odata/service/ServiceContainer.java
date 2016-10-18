package com.sdl.ecommerce.odata.service;

import com.sdl.odata.service.ODataServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;

/**
 * ServiceContainer
 *
 * To avoid problems when quering on slashes, please use the following JVM arguments:
 * -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Dorg.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH=true
 *
 * @author nic
 */
@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@Import({
        //InMemoryDataSourceConfiguration.class,
        ODataServiceConfiguration.class
})
@ComponentScan (basePackages = {"com.sdl.ecommerce.odata"})
@ImportResource("classpath*:/META-INF/ecommerce-connector.xml")
public class ServiceContainer {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceContainer.class);

    public static void main(String[] args) {
        LOG.info("Starting E-Commerce container");

        SpringApplication springApplication = new SpringApplication(ServiceContainer.class);
        springApplication.setShowBanner(false);
        springApplication.run(args);

        LOG.info("E-Commerce Service application container started");
    }

}
