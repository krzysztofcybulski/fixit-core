package me.kcybulski.fixit.config

import me.kcybulski.fixit.bpmn.forms.ImagesFormType
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableConfigurationProperties(DataSourceProperties::class)
class CamundaConfiguration(private val dataSource: DataSource) {

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun processEngineConfiguration(): SpringProcessEngineConfiguration {
        val config = SpringProcessEngineConfiguration()

        config.dataSource = dataSource
        config.transactionManager = transactionManager()

        config.databaseSchemaUpdate = "true"
        config.history = "none"
        config.isJobExecutorActivate = true

        config.customFormTypes = listOf(ImagesFormType())

        return config
    }

}