import kotlinx.coroutines.*
import kotlin.random.Random

fun test1() = runBlocking{
    println("prestart coroutines 1")
    launch {
        delay(500)// аналог sleep
        println("couroutine part 1")
    }//то что записанно в функцию будет выполняеться паралейно
    println("poststart coroutines 1")
}// блокирует основной процесс до того момента выполнения кода в

fun test2() = runBlocking {
    println("prestart coroutines 2")
    launch (Dispatchers.Default){// методы Dispatchers.IO используеться когда в теле функции используеться опрецации  ввода и вывода
        // Main когда у нас у вас Users interface
        // uncofiend безограниченая версия
        delay(500)
        println("couroutine part 2")
    }
    println("poststart coroutines 2 ")
}
fun test3() = runBlocking {
    println("prestart coroutines 3")
    val def = async (Dispatchers.Default){
        delay(500)
        println("couroutine part 3")
        val x = Random.nextInt()
        println("generated:$x")
        x
    }// разница между async and lanch в то что async возвращает какое либо значенение
    val result = def.await()// чтобы получить результаты работы потока async
    println("got $result")
    println("poststart coroutines 3 ")
}
fun test3_1() = runBlocking {
    println("prestart coroutines 3")
    val defs = mutableListOf<Deferred<Int>>()
    for(i in 1..5){
         defs.add( async (Dispatchers.Default){
            delay(Random.nextLong(100,1000))
            println("couroutine part 3# $i")
            val x = Random.nextInt()
            println("generated:$x")
            x
        })
    }// разница между async and lanch в то что async возвращает какое либо значенение
    var s=0
    defs.forEach{s+=it.await()}
    println("got $s")
    println("poststart coroutines 3 ")
}

fun test4() = CoroutineScope(Dispatchers.Default).launch {
        delay(500)
        println("coroutines 4 part ")
}// ну будет работать тк осоновно поток не остановлен
fun test5_1 ()=CoroutineScope(Dispatchers.Default).async {
    delay(500)
    111
}

suspend fun test5(){
    println("Start test1")
    val def = test5_1()
    val result = def.await()
    println("got $result")
    println("Finish test1")
}

fun main() {
    println("Start test1")
    test1()
    println("Finish test1")

    println("Start test2")
    test2()
    println("Finish test2")

    println("Start test3")
    test3()
    println("Finish test3")
    println("Start test3_1")
    test3_1()
    println("Finish test3_1")
    println("Start test4")
    test4()
    println("Finish test4")
    runBlocking { test5()}

}