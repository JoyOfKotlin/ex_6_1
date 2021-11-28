/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ex_6_1

import java.lang.RuntimeException

sealed class Option<out A> {
    abstract fun isEmpty(): Boolean

    fun <B> map(f: (A) -> B): Option<B> =
        when (this) {
            is None -> None
            is Some -> Some(f(value))
    }

    fun <B> flatMap(f: (A)-> Option<B>): Option<B> =
        map(f).getOrElse(None)

    fun getOrElse(default: @UnsafeVariance A): A = when (this) {
        is None -> default
        is Some -> value
    }

    fun getOrElse(default:()-> @UnsafeVariance A): A =
       when (this){
            is None -> default()
            is Some -> value
       }

    fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
        map {_ -> this }.getOrElse(default)

    internal object None: Option<Nothing>() {
        override fun isEmpty()=true
        override fun toString(): String = "None"
        override fun equals(other:Any?): Boolean = other ===None
        override fun hashCode(): Int = 0
    }
    internal data class Some<out A> (internal val value : A) : Option<A>() {
        override fun isEmpty()= false
    }
    companion object {
        operator fun <A> invoke (a:A?=null): Option<A> =
            when(a) {
                null -> None
                else -> Some(a)
            }
    }
}


fun max(list : List<Int>): Option<Int>  = Option.invoke(list.maxOrNull())
fun getDefaultInt(): Int = throw RuntimeException()
fun getDefaultList(): List<Int> = throw RuntimeException()
fun <B> getDefaultOption(): Option<B> =
    Option<B>()


class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
    public fun max() : Int? = null
}

fun main() {
    println(App().greeting)
    val max1=max(listOf(3,5,7,2,1)).getOrElse(::getDefaultInt)
    println(max1)

//    val max2=max(listOf()).getOrElse(::getDefault)
//    println(max2)

    val optionTest=max(listOf(1))
    val resultMap=optionTest.map{ listOf(it,-it)}
    println(resultMap)

    val resultFlatmapSome=Option(7).flatMap{Option(it*3)}
    println(resultFlatmapSome)

    println("\n")
   println(optionTest.orElse { getDefaultOption() })

}
