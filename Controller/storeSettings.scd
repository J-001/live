n['knob0'].cc_({|val| });
n['fader0'].cc_({|val|});
n['s0'].on_({  });
n['s0'].off_({  });
n['m0'].on_({  });
n['m0'].off_({  });
n['r0'].on_({  });
n['r0'].off_({  });

n['knob1'].cc_({|val| });
n['fader1'].cc_({|val|});
n['s1'].on_({ });
n['s1'].off_({ });
n['m1'].on_({ });
n['m1'].off_({ });
n['r1'].on_({ });
n['r1'].off_({ });

n['knob2'].cc_({|val| });
n['fader2'].cc_({|val|});
n['s2'].on_({ });
n['s2'].off_({ });
n['m2'].on_({ });
n['m2'].off_({ });
n['r2'].on_({ });
n['r2'].off_({ });

n['knob3'].cc_({|val| });
n['fader3'].cc_({|val|});
n['s3'].on_({ Ndef(\name).play});
n['s3'].off_({ Ndef(\name).play});
n['m3'].on_({ });
n['m3'].off_({ });
n['r3'].on_({ });
n['r3'].off_({ });

n['knob4'].cc_({|val| });
n['fader4'].cc_({|val|});
n['s4'].on_({ Ndef(\radioNoise).trig;});
n['s4'].off_({ Ndef(\radioNoise).rel;});
n['m4'].on_({ });
n['m4'].off_({ });
n['r4'].on_({ });
n['r4'].off_({ });

n['knob5'].cc_({|val| });
n['fader5'].cc_({|val|});
n['s5'].on_({ });
n['s5'].off_({ });
n['m5'].on_({ });
n['m5'].off_({ });
n['r5'].on_({ });
n['r5'].off_({ });

n['knob6'].cc_({|val| });
n['fader6'].cc_({|val|});
n['s6'].on_({ });
n['s6'].off_({ });
n['m6'].on_({ });
n['m6'].off_({ });
n['r6'].on_({ });
n['r6'].off_({ });

n['knob7'].cc_({|val| });
n['fader7'].cc_({|val|});
n['s7'].on_({ });
n['s7'].off_({ });
n['m7'].on_({ });
n['m7'].off_({ });
n['r7'].on_({ });
n['r7'].off_({ });

n['play'].on_({ })
n['play'].off_({ })
n['stop'].on_({ })
n['stop'].off_({ })
n['rec'].on_({ })
n['rec'].off_({ })
n['rewind'].on_({ })
n['rewind'].off_({ })
n['ffw'].on_({ })
n['ffw'].off_({ })

n['fwdTrack'].on_({ })
n['fwdTrack'].off_({ })
n['bkTrack'].on_({ })
n['bkTrack'].off_({ })
n['cycle'].on_({ NKConf.clear; n.clearSettings});
n['cycle'].off_({ })
n['setMarker'].on_({ NKConf.set});
n['setMarker'].off_({ })
n['bkMarker'].on_({ NKConf.back});
n['bkMarker'].off_({ })
n['fwdMarker'].on_({ NKConf.fwd});
n['fwdMarker'].off_({ })