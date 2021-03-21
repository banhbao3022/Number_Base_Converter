package converter

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*

fun main() {
    menu()
}
fun menu() {
    print("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
    var scanner = Scanner(System.`in`)
    var sourceBase = scanner.next()
    if (sourceBase.equals("/exit")) System.exit(0)
    var targetBase = scanner.next()
    convert(sourceBase.toInt(),targetBase.toInt())
}
fun convert(sourceBase: Int,targetBase: Int) {
    print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
    var num = readLine()!!.toString()
    var result = ""
    if (!num.equals("/back")) {
        val numberSplit  = num.split(".")
        if (numberSplit.size == 1 || sourceBase == 1) {
            result = convertIntegerPart(numberSplit[0],sourceBase, targetBase)
        } else {
            result = convertIntegerPart(numberSplit[0],sourceBase, targetBase) + "." +convertFractionalPart(numberSplit[1],sourceBase, targetBase)
        }
        println("Conversion result:" + result)
        convert(sourceBase,targetBase)
    } else menu()
}
fun convertIntegerPart(sourceInteger: String, sourceBase: Int, targetBase: Int): String {
    val number: BigInteger
    number = if (sourceBase != 10) {
        if (sourceBase == 1) {
            sourceInteger.length.toBigInteger()
        } else {
            sourceInteger.toBigInteger(sourceBase)
        }
    } else {
        sourceInteger.toBigInteger()
    }
    return number.toString(targetBase)
}

fun convertFractionalPart(sourceFractional: String, sourceBase: Int, targetBase: Int): String? {
    var decimalValue = 0.0
    if (sourceBase == 10) {
        decimalValue = "0.$sourceFractional".toDouble()
    } else {
        var c: Char
        for (i in 0 until sourceFractional.length) {
            c = sourceFractional[i]
            decimalValue += Character.digit(c, sourceBase) / Math.pow(sourceBase.toDouble(), (i + 1).toDouble())
        }
    }
    val result = StringBuilder()
    var decimal: Int
    for (i in 0..4) {
        val aux = decimalValue * targetBase
        decimal = aux.toInt()
        result.append(java.lang.Long.toString(decimal.toLong(), targetBase))
        decimalValue = aux - decimal
    }
    return result.toString()
}