package cinema

fun printCinema(cinema: MutableList<MutableList<Boolean>>) {
    val rows = cinema.size
    val cols = cinema[0].size
    
    var headRow: String = "  "
    for (i in 1..cols) {
        headRow += i.toString()
        headRow += " "
    }
    println()
    println("Cinema: ")
    println(headRow)
    
    for (i in cinema.indices) {
        var line:String = (i + 1).toString()
        for (j in cinema[i].indices) {
            if (cinema[i][j]) {
                line += " B"     
            } else {
                line += " S"
            }
        }
        println(line)
    }
    println()
}

fun buyTicket(seats: MutableList<MutableList<Boolean>>) {
    println("Enter a row number: ")
    val rowNumber = readln().toInt()
    println("Enter a seat number in that row: ")
    val seatNumber = readln().toInt() 
    
    if (rowNumber > seats.size || seatNumber > seats[0].size) {
        println("Wrong input!")
        buyTicket(seats)
        return
    }
    
    if (seats[rowNumber - 1][seatNumber - 1]) {
        println("That ticket has already been purchased!")
        buyTicket(seats)
        return
    } 
    
    if (seats.size * seats[0].size <= 60) {
        println()
        println("Ticket price: $10")
    } else {
        val price = if (rowNumber > seats.size / 2) 8
                    else 10
        println()
        println("Ticket price: $$price")
    }
    
    seats[rowNumber - 1][seatNumber - 1] = true
}

fun printStatistics(seats: MutableList<MutableList<Boolean>>) {
    val numberOfPurchasedTickets = getNumberOfSoldTickets(seats)
    val percentage = getPercentageOfSoldTickets(seats, numberOfPurchasedTickets)
    val currentIncome = getCurrentIncome(seats)
    val totalIncome = getMaxTotal(seats)
    
    println()
    println("Number of purchased tickets: $numberOfPurchasedTickets")
    println("Percentage: $percentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
    println()
}

fun getNumberOfSoldTickets(seats: MutableList<MutableList<Boolean>>): Int {
    var numberOfSoldTickets: Int = 0
    
    for (i in seats.indices) {
        for (j in seats[0].indices) {
            if (seats[i][j]) numberOfSoldTickets++
        }
    }
    return numberOfSoldTickets
}

fun getPercentageOfSoldTickets(seats: MutableList<MutableList<Boolean>>, soldTickets: Int): String {
    val maxNumberOfTickets = (seats.size * seats[0].size).toDouble()
    val result = soldTickets / maxNumberOfTickets * 100.0
    return "%.2f".format(result)
}

fun getCurrentIncome(seats: MutableList<MutableList<Boolean>>): Int {
    var sum: Int = 0
    
    for (row in seats.indices) {
        for (seat in seats[0].indices) {
            if (seats[row][seat]) sum += getPriceOfTicket(seats, row)
        }
    }
    return sum
}

fun getMaxTotal(seats: MutableList<MutableList<Boolean>>): Int {
    var sum: Int = 0
    
    for (row in seats.indices) {
        for (seat in seats[0].indices) {
            sum += getPriceOfTicket(seats, row)
        }
    }
    return sum
}

fun getPriceOfTicket(seats: MutableList<MutableList<Boolean>>, rowIndex: Int): Int {
    var price: Int = 0
    if (seats.size * seats[0].size <= 60) {
        price = 10
    } else {
        if (rowIndex + 1 > seats.size / 2) price = 8
        else price = 10
    }
    return price
}

fun main() {
    println("Enter the number of rows:")
    val numberOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numberOfSeats = readln().toInt()
    
    var seats = MutableList(numberOfRows) { MutableList<Boolean>(numberOfSeats) { false } }
    
    // printCinema(seats)
    
    while (true) {
        println("""
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
""")
        
        val menu = readln().toInt()
        
        when (menu) {
            1 -> printCinema(seats)
            2 -> buyTicket(seats)
            3 -> printStatistics(seats)
            0 -> break
            else -> println("Enter valid number for menu interaction!")
        }
    }
    
}
