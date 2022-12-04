import kotlin.math.abs
import kotlin.math.ceil

fun main(args: Array<String>) {
    var demand: Int = 0
    var invEnd: Int = 0
    var shortage = 0
    var makeOrderQuantity = 0
    var leadTime: Int
    var todayTime: Int
    var initialOrder = 8
    var orderQuantity = makeOrderQuantity + initialOrder
    var shortageTime = 0
    var summationOfEnding = 0

    val demandRandomNumber: MutableList<Int> =
        mutableListOf(24, 35, 65, 81, 54, 3, 87, 27, 73, 70, 47, 45, 48, 17, 9, 42, 87, 26, 36, 40, 7, 63, 19, 88, 94)
    val leadTimeRandomNumber: List<Int> = listOf(5, 0, 3, 4, 8)
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

    var i = 1
    leadTime = 2
    todayTime = leadTime + i
    println("cycle\tday\tinvBegin\tdemand\tinvEnd\tshortage\torder\tleadTime\t")
    while (i <= (cycles * dayInCycle)) {

//        to use random number of section pdf
//        demand = randomNumberDemand(demandRandomNumber[i-1])

        demand = randomNumberDemand((0..100).random())
        shortage += if (isShortageCalculated) if ((invBegin - demand) < 0) abs(invBegin - demand) else 0 else 0
        if (i == todayTime) {
            invEnd = invBegin - demand - shortage
            shortage = 0
        } else {
            invEnd = if ((invBegin - demand) > 0) (invBegin - demand) else 0
        }
        if (i % dayInCycle == 0) {
            leadTime = randomNumberLeadTime((0..9).random())

//            to use random number in section pdf
//            leadTime = randomNumberLeadTime(leadTimeRandomNumber[(i/5)-1])

            makeOrderQuantity = stockSize - invEnd + shortage
            todayTime = i + leadTime + 1
            orderQuantity = makeOrderQuantity
        } else {
            leadTime = if ((leadTime - 1) >= 0) (leadTime - 1) else 0
            makeOrderQuantity = 0
        }
        summationOfEnding += invEnd
        if (shortage > 0) shortageTime++
        println("${(ceil((i.toDouble() / dayInCycle))).toInt()}\t\t$i\t\t$invBegin\t\t\t$demand\t\t$invEnd\t\t$shortage\t\t$makeOrderQuantity\t\t\t$leadTime")
        i++
        if (i == todayTime) {
            invBegin = invEnd + orderQuantity
            initialOrder = 0
            makeOrderQuantity = 0

        } else {
            invBegin = invEnd
        }

    }
    println("The number of shortage times = $shortageTime times out of ${cycles * dayInCycle} days")
    println("Average End of Inventory = %.2f".format(summationOfEnding.toDouble() / (cycles * dayInCycle)) + " units")


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