# Work Time Calculator

A simple library to calculate working minutes or days between two given dates unlike simple difference between two 
dates. See the [BusinessTimeCalculator.java](https://bitbucket.org/causecode/work-time-calculator/src/f5562b0a6fb7492228d1406f6a5ec0d6a4cd61b2/src/main/java/com/cc/BusinessTimeCalculator.java?at=master)

See [BusinessTimeCalculatorTest.java](https://bitbucket.org/causecode/work-time-calculator/src/dab4b7b676d3b9450a6f16a067d22100361ca0a2/src/test/java/com/cc/BusinessTimeCalculatorTest.java)
for various usage.

## Defaults

1. The default working hours is set from 09:30 AM to 06:30 PM and can be changed via the `setWorkingTime()` method.
2. The default weekends are set to Saturday & Sunday and be changed via the `setWeekends()` method.