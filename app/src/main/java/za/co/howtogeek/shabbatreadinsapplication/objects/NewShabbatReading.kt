package za.co.howtogeek.shabbatreadingsapplication.objects

/*class Person(private var _age: Int) {
    // Custom getter for age property
    var age: Int
        get() {
            println("Getting age")
            return if (_age >= 0) _age else 0 // Conditional logic in getter
        }
        set(value) {
            println("Setting age to $value")
            if (value >= 0) {
                _age = value
            }
        }
}
 */

/*
fun main() {
    val person = Person(-5)

    println("Initial age: ${person.age}") // Output: Getting age, Initial age: 0

    person.age = 30 // Output: Setting age to 30
    println("Updated age: ${person.age}") // Output: Getting age, Updated age: 30

    person.age = -10 // Output: Setting age to -10 (but age will remain 30 due to conditional setter)
    println("Final age: ${person.age}") // Output: Getting age, Final age: 30
}
*/

class NewShabbatReading(
    //var date: String? = null,
    var readingSet: Int = 0,
    var parashaName: String? = null,
    var gregorianDate: String? = null,
    var hebrewDate: String? = null,
    var torahPortion: String? = null,
    var haftarahPortion: String? = null,
    var gospelPortion: String? = null)

/* Different class where you modify the string property
class AnotherClass {
    fun modifyShabbatReadingName(newShabbatReading: NewShabbatReading, newName: String) {
        newShabbatReading.parashaName = newName
    }
}
 */

/*fun main() {
    val john = NewShabbatReading("John Doe")
    println("Before modification: ${john.parashaName}") // Output: Before modification: John Doe

    val modifier = AnotherClass()
    modifier.modifyShabbatReadingName(john, "John Smith")

    println("After modification: ${john.parashaName}") // Output: After modification: John Smith
}
 */