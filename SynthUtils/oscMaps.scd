o=();

o.s01 = NodeProxy.control(s);
o.s02.postln;

OSCdef(\syn01, {|msg, time, addr, recvPort|
	msg[1].postln;
	o.s01.source=msg[1];
}, '/jd/syn/1/flt/1');

OSCdef(\syn02, {|msg, time, addr, recvPort|
	o.s02= msg[1];
}, '/jd/syn/1/flt/2');

OSCdef(\syn03, {|msg, time, addr, recvPort|
	o.s03 = msg[1];
}, '/jd/syn/0/flt/3');



o.s11 = NodeProxy.control(s);
o.s12 = NodeProxy.control(s);
o.s13 = NodeProxy.control(s);


OSCdef(\syn11, {|msg, time, addr, recvPort|
	msg[1].postln;
	o.s11[0]=msg[1];
}, '/jd/syn/2/flt/1');

OSCdef(\syn12, {|msg, time, addr, recvPort|
	o.s12[0]=msg[1];
}, '/jd/syn/2/flt/2');

OSCdef(\syn13, {|msg, time, addr, recvPort|
	o.s13[0]=msg[1];
}, '/jd/syn/2/flt/3');
o.s11
o.s12


o.s13.postln;