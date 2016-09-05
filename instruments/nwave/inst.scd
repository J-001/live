//single synth definition

//modulator variations using inst . notation
(
var fltArr = (
	'lpf' : ['Lpf', LPF],
	'hpf' : ['Hpf', HPF]
	);

var modArr = (
	'uni' : ['Uni', {1}],
	'sin' : ['Sin', {|lfospd|SinOsc.kr(lfospd).range(0,1)}],
 		);

i['nwave'] = List();

fltArr.do{|filter|
	modArr.do{|mod|

		i.nwave.add('nwave'++filter[0]++mod[0]);

		Instr('nwave'++filter[0]++mod[0], {|
			gate = 1,
			noise,
			dur = 4.0, legato = 1.0,
			amp = 0.1,
			panlvl = 0.5, panspd = 0.1, direction = -1,
			locut = 500, hicut = 19000,
			lfospd = 0.1|
			var sig;
			var src = ();
			var flt = ();
			var pan = ();
			//SOURCE
			sig = noise * amp;
			src[\lvl] = [0, 1, rrand(0.2, 0.5), 0];
			src[\asrpeak] = exprand(0.1, 0.5).rinvert;
			src[\asr] = [src.asrpeak, rrand(0.01, 0.05), 1 - src.asrpeak];
			src.asr　=　src.asr.normalizeSum * dur * legato;
			src[\crv] = [7, -2, -7];
			src[\env] = EnvGen.ar(Env(src.lvl, src.asr, src.crv), gate, doneAction: 2);
			sig = sig * src.env;
			//FILTER
			flt[\lvl]=　[0, rrand(0.1, 0.5), rrand(0.5, 0.8), 1] * rrand(500, 19000);
			flt[\env]=　EnvGen.kr(Env(flt.lvl, src.asr, [1, 1, 1]), doneAction:2);
			flt[\env]=　Line.kr(locut, hicut, dur * legato);
			sig 　= filter[1].ar(sig, flt.env);
			//PANNING
			pan[\lvl]　=　[-1, rrand(-0.8,-0.3), rrand(0.3,0.8),1];
			pan[\env] = EnvGen.ar(Env(pan.lvl * [-1,1].choose, src.asr, src.crv), doneAction:2);
			//LFO
			sig = sig * mod[1].(lfospd);
			sig = Pan2.ar(sig, pan.env);
		}, [
		ScalarSpec(),
		ObjectSpec({WhiteNoise.ar}),
		\dur.asSpec, \legato.asSpec, 
		nil,
		[-1,1], [0.001, 20, \linear, 0], [-48, 48, \linear, 1], 
		[20, 20000, \exp, 0, 200], [20, 20000, \exp, 0, 15000],
		[0.001, 20,\linear,0,0.1], 
		]).add;
	}
};
)
