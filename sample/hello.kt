class Greeter(val name: String) {
    fun greet() {
        println("Hello, $name from Kotlin!")
    }
}

fun calculateSum(numbers: List<Int>): Int {
    return numbers.sum()
}

fun main() {
    val greeter = Greeter("World")
    greeter.greet()

    val numbers = listOf(1, 2, 3, 4, 5)

    // Higher-order functions
    val squares = numbers.map { it * it }
    val evenNumbers = numbers.filter { it % 2 == 0 }

    println("Sum: ${calculateSum(numbers)}")
    println("Squares: ${squares.joinToString(", ")}")
    println("Even numbers: ${evenNumbers.joinToString(", ")}")

    // When expression (pattern matching)
    numbers.forEach { n ->
        when {
            n % 2 == 0 -> println("$n is even")
            else -> println("$n is odd")
        }
    }

    // Data class example
    data class Person(val name: String, val age: Int)
    val person = Person("Alice", 30)
    println("Person: $person")

    // Nullable types
    var nullable: String? = "Hello"
    println("Length: ${nullable?.length}")
}
