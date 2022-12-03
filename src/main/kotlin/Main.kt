import kotlin.math.abs
import kotlin.math.ceil

fun main(args: Array<String>) {


    var demand: Int = 0
    var invEnd: Int = 0
    var shortage: Int = 0
    var makeOrderQuantity :Int = 0
    var leadTime: Int = 0
    var todayTime : Int = 0
    var initialOrder :Int = 8
    var orderQuantity: Int = makeOrderQuantity + initialOrder
    println("enter inventory stock size")
    val stockSize: Int = readln().toInt()

    println("how many cycle do you need")
    val cycles: Int = readln().toInt()

    println("how many day in every cycle")
    val dayInCycle: Int = readln().toInt()

    println("write true for calculate Shortage and false to not calculate Shortage")
    val isShortageCalculated: Boolean = readln().equals("true", ignoreCase = true)

    println("enter initial Stock in inventory")
    var invBegin: Int = readln().toInt()

    var i: Int = 1
    leadTime = 2
    todayTime = leadTime + i

    while (i <= (cycles * dayInCycle)) {

        println("Enter random for Demand")
        demand = randomNumberDemand(readln().toInt())
        shortage += if (isShortageCalculated) if ((invBegin - demand) < 0) abs(invBegin - demand) else 0 else 0
        invEnd = if ((invBegin - demand) > 0) (invBegin - demand) else 0
        if (i % dayInCycle == 0) {
            println("enter random number for lead time")
            leadTime = randomNumberLeadTime(readln().toInt())
            makeOrderQuantity = stockSize - invEnd + shortage
            todayTime = i + leadTime +1
            orderQuantity = makeOrderQuantity
        }else{
            leadTime = if((leadTime-1) >= 0) (leadTime-1) else 0
            makeOrderQuantity = 0
        }
        println("cycle ${(ceil( (i.toDouble()/dayInCycle))).toInt()}\nday $i\ninvBegin $invBegin\ndemand $demand\ninvEnd $invEnd\nshortage $shortage\norder $makeOrderQuantity\nleadTime $leadTime \nin day number $todayTime")
        i++
        if (i == todayTime){
            invBegin = invEnd + orderQuantity
            initialOrder = 0
            makeOrderQuantity = 0
            shortage = 0
        }else{
            invBegin = invEnd
        }

    }


}

fun randomNumberDemand(x: Int): Int {
    return when (x) {
        in 1..10 -> 0
        in 11..35 -> 1
        in 36..70 -> 2
        in 71..91 -> 3
        in 92..100 -> 4
        else -> 0
    }
}

fun randomNumberLeadTime(x: Int): Int {
    return when (x) {
        in 1..6 -> 1
        in 7..9 -> 2
        0 -> 3
        else -> 0
    }
}