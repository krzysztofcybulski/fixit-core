package me.kcybulski.fixit.config

import org.springframework.beans.BeanUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import java.sql.Driver
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration(private val dataSourceProperties: DataSourceProperties) {

    @Bean
    fun dataSource(): DataSource = SimpleDriverDataSource(
                BeanUtils.instantiateClass(Class.forName(dataSourceProperties.dataSource)) as Driver,
                dataSourceProperties.url,
                dataSourceProperties.username,
                dataSourceProperties.password
        )

}

@ConstructorBinding
@ConfigurationProperties("spring.datasource")
data class DataSourceProperties(val url: String, val username: String, val password: String, val dataSource: String)