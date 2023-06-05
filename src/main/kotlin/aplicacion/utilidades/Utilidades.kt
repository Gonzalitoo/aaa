package aplicacion.utilidades

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

fun <T: Any> mapper(dto: Any, obj: KClass<T>): T {
    val newInstance = obj.createInstance()
    val fieldsMapDTO = mutableMapOf<String, Any>()

    dto.javaClass.kotlin.memberProperties.forEach {
        fieldsMapDTO[it.name] = it.get(dto) as Any
    }

    obj.memberProperties
        .map { it as KMutableProperty<*> }
        .forEach { e ->
            fieldsMapDTO[e.name]?.let {e.setter.call(newInstance, it)}
        }

    return newInstance
}