# Vancalc3
This is an app for calculating vancomycin peak and trough concentration by patient's clinical data.

## User guide
Different Ccr is calculated based on if height is given or not. <br/>
<br/>
If patient's height is available, Ideal Body Weight(IBW) would be calculated first then compare with patient's real weight.<br/>
Then, according to the comparison, Adjusted Body Weight, Ideal Body Weight, or original given weight used to calculated for Ccr would be showed.<br/>
<br/>
Otherwise, CCr would be calculated by given weight directly.

### For Ccr calculation
Ccr could be calculated by Age, Weight, Gender, Crea, (Height) and showed directly with or without data of vancomycin.

### To range pt's weight
After adding height data, whether patient is overweight, underweight, or in normal range would be showed.

### For Vancomycin peak and trough concentration
As vancomycin dosage and frequency are added, peak and trough concentration would be calculcated. <br/>
**css p0:** real peak concentration <br/>
**css peak:** concentration 2 hr after peak one <br/>
**css trough:** real trough concentration <br/>
