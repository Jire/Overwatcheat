package com.pixelaimbot.util

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngineFactory
import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager
import kotlin.reflect.KProperty

object Dojo {
	
	val factory = KotlinJsr223JvmLocalScriptEngineFactory()
	val engine: ScriptEngine = factory.scriptEngine
	
	fun script(script: String): Any? = engine.eval(script)
	
	operator inline fun <reified T> invoke(function: String) = engine.eval("$function()") as T
	
	operator fun invoke(function: String): Unit = invoke<Unit>(function)
	
	@Suppress("UNCHECKED_CAST")
	operator fun <T> get(name: String): T = engine.eval(name) as T
	
	operator fun <T> set(name: String, value: T) {
		engine.eval("$name = $value")
	}
	
	class Setting<out T> {
		private var value: T? = null
		
		operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
			if (value == null) value = get<T>(property.name)
			return value!!
		}
	}
	
}