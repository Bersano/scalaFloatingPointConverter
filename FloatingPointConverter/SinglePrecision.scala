package FloatingPointConverter

import scala.math._
import java.lang.{Float => JFloat}

object SinglePrecision {
  private def getDecimalValueOfMantissa(input: String): Double = {
    var decimalValueOMantissa: Double = 0.0
    for (k <- 0 until input.length()) if (input(k) == '1') decimalValueOMantissa += pow(2.0, -1.0*(k+1))
    decimalValueOMantissa
  }
  
  private def getStringBits(input: Float): String = {
    if (Integer.toBinaryString(JFloat.floatToIntBits(input)).length() != 32) {
      val normalisedNumber32length: Array[Char] = new Array[Char](32)
      val lackOf: Int = 32 - Integer.toBinaryString(JFloat.floatToIntBits(input)).length()
      for (k <- 0 until lackOf) normalisedNumber32length(k) = '0'
      for (k <- lackOf until 32) normalisedNumber32length(k) = Integer.toBinaryString(JFloat.floatToIntBits(input))(k-lackOf)
      val ssBuildString: StringBuilder = new StringBuilder()
      normalisedNumber32length foreach((x: Char) => ssBuildString.append(x))
      ssBuildString.toString()
    } else Integer.toBinaryString(JFloat.floatToIntBits(input))
  }
  
  def getIBM32(inputIBM32: Float): Float = {
    val signIBM32: Char = getStringBits(inputIBM32)(0)
    
    val sbExpIBM32: StringBuilder = new StringBuilder()
    for (k <- 1 to 7) sbExpIBM32.append(getStringBits(inputIBM32)(k))
    val expIBM32: String = sbExpIBM32.toString()
    
    val sbMantissaIBM32: StringBuilder = new StringBuilder()
    for (k <- 1 to 24) sbMantissaIBM32.append(getStringBits(inputIBM32)(k+7))
    val mantissaIBM32: String = sbMantissaIBM32.toString()
    
    (pow(-1.0, signIBM32.toDouble) * pow(16.0, (Integer.parseInt(expIBM32, 2) - 64)) * getDecimalValueOfMantissa(mantissaIBM32)).toFloat
  }
  
  def getIEEE754(inputIEEE754: Float): Float = {
    val signIEEE754: Char = getStringBits(inputIEEE754)(0)
    
    val sbExpIEEE754: StringBuilder = new StringBuilder()
    for (k <- 1 to 8) sbExpIEEE754.append(getStringBits(inputIEEE754)(k))
    val expIEEE754: String = sbExpIEEE754.toString()
    
    val sbMantissaIEEE754: StringBuilder = new StringBuilder()
    for (k <- 1 to 23) sbMantissaIEEE754.append(getStringBits(inputIEEE754)(k+8))
    val mantissaIEEE754: String = sbMantissaIEEE754.toString()
    
    (pow(-1.0, signIEEE754.toDouble) * pow(2.0, (Integer.parseInt(expIEEE754, 2) - 127) ) * (1.0 + getDecimalValueOfMantissa(mantissaIEEE754))).toFloat
  }
}
