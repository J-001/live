


n['s3'].on_({ Ndef(\name).play});
n['s3'].off_({ Ndef(\name).play});

n['s4'].on_({ Ndef(\radioNoise).trig;});
n['s4'].off_({ Ndef(\radioNoise).rel;});





n['cycle'].on_({ NKConf.clear; n.clearSettings});
n['setMarker'].on_({ NKConf.set});
n['bkMarker'].on_({ NKConf.back});
n['fwdMarker'].on_({ NKConf.fwd});
