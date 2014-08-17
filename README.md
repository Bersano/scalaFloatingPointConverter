FloatingPointConverter
======================

Converting value in IEEE754 format to IBM32bit format

For exmaple, number **-177.62376**.

    val input: Float = -177.62376f
    println(FloatingPointConverter.SinglePrecision.getIBM32(input))
    
    //for check this code
    println(FloatingPointConverter.SinglePrecision.getIEEE754(input))

You can see this.

    -793.9802
    -177.62376
    

Also, I do code for convert value from IBM32bit format to IEEE754 format. And of course, I'll do version with double precision, but today I need code for float-data only.
