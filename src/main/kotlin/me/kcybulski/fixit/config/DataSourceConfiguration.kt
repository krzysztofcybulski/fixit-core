package me.kcybulski.fixit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration(private val dataSourceProperties: DataSourceProperties) {

    @Bean
    fun dataSource(): DataSource {
        val dataSource = SimpleDriverDataSource()
        dataSource.setDriverClass(org.postgresql.Driver::class.java)
        dataSource.url = dataSourceProperties.url
        dataSource.username = dataSourceProperties.username
        dataSource.password = dataSourceProperties.password
        return dataSource
    }

}

@ConstructorBinding
@ConfigurationProperties("spring.datasource")
data class DataSourceProperties(val url: String, val username: String, val password: String)