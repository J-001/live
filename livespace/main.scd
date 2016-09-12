"~/Live/setup/init.scd".loadPaths
//BeginClockGui
Pbind(
	\type, Pseq([\beginConductor]),
).play(quant:4)

//-----------------------------------------------------------------------
//-----------------------------------------------------------------------

i.load(\bass,\natsu)
i.mat(\bass,\natsu)

\natsu_P.q_(8).p;

\bassDrive.asP_(
	Pseq([
	\bass_drive
	],inf)
).q_(8).p;

\natsuTrill.asP_(
	\natsu
	>< (\natsu_trill |? [
			\midinote, _ + [0,3,7,10,14,17],
			'amp', 0.5
		])
	// >< 'natsu_ride'
).p

a = PbindProxy()
(

(a <> \natsu_trill).play


 b  |? [\midinote, _ + [0,3,7,10]] ).p















