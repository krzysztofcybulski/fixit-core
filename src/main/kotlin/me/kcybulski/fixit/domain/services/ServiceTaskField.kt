package me.kcybulski.fixit.domain.services

import kotlin.annotation.AnnotationTarget.FIELD

@Target(FIELD)
annotation class ServiceTaskField(val name: String)