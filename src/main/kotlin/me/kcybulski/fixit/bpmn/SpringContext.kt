package me.kcybulski.fixit.bpmn

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class SpringContext : ApplicationContextAware {

    override fun setApplicationContext(context: ApplicationContext) {
        SpringContext.context = context
    }

    companion object {

        private var context: ApplicationContext? = null

        fun <T : Any> getBean(beanClass: Class<T>): T {
            return context!!.getBean(beanClass)
        }
    }
}