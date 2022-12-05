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
//  to take input from user and convert it to integer
    println("enter inventory stock size")
    val stockSize: Int = readln().toInt()
//  to take input from user and convert it to integer
    println("how many cycle do you need")
    val cycles: Int = readln().toInt()
//  to take input from user and convert it to integer
    println("how many day in every cycle")
    val dayInCycle: Int = readln().toInt()
//  to take input from user and convert it to Boolean
    println("write true for calculate Shortage and false to not calculate Shortage")
    val isShortageCalculated: Boolean = readln().equals("true", ignoreCase = true)
//  to take input from user and convert it to integer
    println("enter initial Stock in inventory")
    var invBegin: Int = readln().toInt()

    // i for days counter
    var i = 1
    // initial lead time
    leadTime = 2
    // to know when order come
    todayTime = leadTime + i
    println("cycle\tday\tinvBegin\tdemand\tinvEnd\tshortage\torder\tleadTime\t")
    while (i <= (cycles * dayInCycle)) {

//        to use random number of section pdf
//        demand = randomNumberDemand(demandRandomNumber[i-1])

        // generate random number and send it to function to get the probability for demand
        demand = randomNumberDemand((0..100).random())

        // to calculate the shortage and if shortage not calculated will be 0
        shortage += if (isShortageCalculated) if ((invBegin - demand) < 0) abs(invBegin - demand) else 0 else 0

       // when order come : shortage will be minus from invEnd like section pdf and convert the value in shortage to 0 because it resumed (اتعوض يعني )
        if (i == todayTime) {
            invEnd = invBegin - demand - shortage
            shortage = 0
        } else {

            // to make invEnd >= 0
            invEnd = if ((invBegin - demand) > 0) (invBegin - demand) else 0
        }

        // to make order in last day in cycle and calculate when it comes and order Quantity
        if (i % dayInCycle == 0) {
            leadTime = randomNumberLeadTime((0..9).random())

//            to use random number in section pdf
//            leadTime = randomNumberLeadTime(leadTimeRandomNumber[(i/5)-1])

            makeOrderQuantity = stockSize - invEnd + shortage
            todayTime = i + leadTime + 1
            orderQuantity = makeOrderQuantity
        } else {
            // to count down for lead time look like 3 2 1 0
            leadTime = if ((leadTime - 1) >= 0) (leadTime - 1) else 0
            makeOrderQuantity = 0
        }
        // to calculate summation of invEnd
        summationOfEnding += invEnd

        // if we have shortage  make shortage Time increment by 1
        if (shortage > 0) shortageTime++
        println("${(ceil((i.toDouble() / dayInCycle))).toInt()}\t\t$i\t\t$invBegin\t\t\t$demand\t\t$invEnd\t\t$shortage\t\t$makeOrderQuantity\t\t\t$leadTime")

        i++
        // to add the order to invBegin in new day
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


// function to get probability for demand
fun randomNumberDemand(x: Int): Int {
    return when (x) {
//      if x between 1 and 6 return 1

        in 1..10 -> 0
        in 11..35 -> 1
        in 36..70 -> 2
        in 71..91 -> 3
        in 92..100 -> 4
        else -> 0
    }
}

// function to get probability for leadTime
fun randomNumberLeadTime(x: Int): Int {
    return when (x) {
//      if x between 1 and 6 return 1

        in 1..6 -> 1
        in 7..9 -> 2
        0 -> 3
        else -> 0
    }
}