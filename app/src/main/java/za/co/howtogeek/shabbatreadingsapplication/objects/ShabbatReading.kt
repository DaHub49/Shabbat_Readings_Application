package za.co.howtogeek.shabbatreadingsapplication.objects



data class ShabbatReading(
    val id: Int?, //0
    var readingSet: Int? = 0, //1
    var parashaName: String? = null, //2
    var gregorianDate: String? = null, //3
    var hebrewDate: String? = null, //4
    var torahPortion: String? = null, //5
    var haftarahPortion: String? = null, //6
    var gospelPortion: String? = null, //7
    var youVersion: String? = null, //8
    var mySword: String? = null, //9

    var additionalScripture1: String? = null, //10
    var additionalScripture2: String? = null, //11
    var additionalScripture3: String? = null, //12
    var additionalScripture4: String? = null, //13
    var xhosa: String? = null) { //14

    val readingSetString: String
        get() = when (readingSet) {
            0 -> "Lion and Lamb Ministries"
            1 -> "First Fruits of Zion"
            else -> "Error loading reading set name"
        }

    /*val youVersionEdited: String
        get() {

        }
     */

    /*fun setDate(date: String?) {
        mDate = date
    }

    fun setReadingSet(readingSet: Int) {
        mReadingSet = readingSet
    }
     */
}
