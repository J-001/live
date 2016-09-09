n['knob0'].cc_({|val| ~romance.sm(\rate, val,\lfo)});
n['s0'].on_({ ~romance.play});
n['s0'].off_({ ~romance.stop});









n['cycle'].on_({ NKConf.clear; n.clearSettings});
n['setMarker'].on_({ NKConf.set});
n['bkMarker'].on_({ NKConf.back});
n['fwdMarker'].on_({ NKConf.fwd});
