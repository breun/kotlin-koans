package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {

        var current = start

        override fun hasNext(): Boolean = current <= endInclusive

        override fun next(): MyDate {
            if (!hasNext()) {
                throw RuntimeException("$current was the last element of the range")
            }
            val result = current
            current = current.nextDay()
            return result
        }
    }

    operator fun contains(d: MyDate): Boolean = start <= d && d <= endInclusive
}
