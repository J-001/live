
n = NK2();

w = 32;
h = 32;

d = Array.fill(w,{|x|
	Array.fill(h,{|y|
		var xVal,yVal;
		var val;
		var n = (h*x)+y;

		xVal = x/w;
		yVal = y/h;

		val = (xVal-yVal)*(xVal-1)*(xVal+1)*(yVal-1)*(yVal+1);
		val.postln;
		})
	}).flatten;


y=0;
x=0;
m=0;

n[\knob0].cc_({|val| 
	x = val.linlin(0,127,0,w-1).round;
	m = (y*h)+x;
	},normalize:false);

n[\knob1].cc_({|val| 

	y = val.linlin(0,127,0,h-1).round;
	m = (y*h)+x;
	},normalize:false);


m.postln;

WaveTerrain