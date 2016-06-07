//OSC RECIEVEE COMMANDS



OSCdef(\wwv, {|msg, time, addr, recvPort|
msg.postln;
// Synth(\wwvlpf,[\dur,rrand(1.0,8.0),\legato,rrand(1.5,2.0),\lfoSpd,0.5]);
}, '/jd/wwv/lpf');

