# Vancalc3
This is an app for calculating vancomycin peak and trough concentration by patient's clinical data.

## Download apk.
Cick [HERE](https://drive.google.com/file/d/1ljjtdPIW8FZG13WIulXnkqvkETJ0D7ux/view?usp=sharing) to download apk.

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

## Formula
### IBW
**Male** <br/>
<img src="http://chart.googleapis.com/chart?cht=tx&chl= IBW=(Height-80) \times 0.7" style="border:none;"><img/> <br/>
**Female** <br/>
<img src="http://chart.googleapis.com/chart?cht=tx&chl= IBW=(Height-70) \times 0.6" style="border:none;"><img/> <br/>
### Adjusted Body Weight(ABW)
<img src="http://chart.googleapis.com/chart?cht=tx&chl= ABW=IBW+0.4 \times (Weight-IBW)" style="border:none;"><img/>

### Def of overweight
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Weight>(IBW \times 1.2)" style="border:none;"><img/>

### Def of underweight
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Weight<IBW" style="border:none;"><img/>

### Ccr
**Male** <br/>
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Ccr=\frac{(140-Age)*Weight}{(72*Crea)}" style="border:none;"><img/><br/>
**Female** <br/>
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Ccr=\frac{(140-Age)*Weight*0.85}{(72*Crea)}" style="border:none;"><img/><br/>

### Vancomycin Vd
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Vd_{vanco}=0.7*Weight" style="border:none;"><img/>

### k
<img src="http://chart.googleapis.com/chart?cht=tx&chl= k_{vanco}=\frac{Ccr}{Vd_{vanco}}" style="border:none;"><img/>

### Half-life
<img src="http://chart.googleapis.com/chart?cht=tx&chl= t_{\frac{1}{2}}=\frac{\ln{2}}{k_{vanco}}" style="border:none;"><img/>

### Css peak (t=0)
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Css_{t=0}=\frac{Dose}{Vd_{vanco}}*\frac{1}{(1-e^{-k_{vanco}*Freq})}" style="border:none;"><img/>

### Css peak (t=2)
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Css_{t=2}=Css_{peak} * e^{-k_{vanco}*2}" style="border:none;"><img/>

### Css trough
<img src="http://chart.googleapis.com/chart?cht=tx&chl= Css_{trough}=Css_{peak} * e^{-k_{vanco}*Freq}" style="border:none;"><img/>

