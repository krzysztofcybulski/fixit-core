package me.kcybulski.fixit.bpmn.forms

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType
import org.camunda.bpm.engine.variable.Variables
import org.camunda.bpm.engine.variable.value.TypedValue

class ImagesFormType : AbstractFormFieldType() {

    override fun getName(): String = "images"

    override fun convertModelValueToFormValue(modelValue: Any?): String {
        return "https://images-na.ssl-images-amazon.com/images/I/81cneGCkDmL.jpg"
    }

    override fun convertFormValueToModelValue(propertyValue: Any?): Any {
        return "https://images-na.ssl-images-amazon.com/images/I/81cneGCkDmL.jpg"
    }

    override fun convertToModelValue(propertyValue: TypedValue): TypedValue {
        return Variables.stringValue("https://images-na.ssl-images-amazon.com/images/I/81cneGCkDmL.jpg")
    }

    override fun convertToFormValue(propertyValue: TypedValue): TypedValue {
        return Variables.stringValue("https://images-na.ssl-images-amazon.com/images/I/81cneGCkDmL.jpg")
    }

}