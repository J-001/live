//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
"~/Live/setup/init.scd".loadPaths;
"~/Live/setup/loadJDSounds.scd".standardizePath.loadPaths;
//-----------------------------------------------------------------------
i.load(\smp);
i.mat(\smp);

i.load(\fracDust,\xplode);
i.mat(\fracDust);

f.load(\dream);

i.load(\bass)

i.load(\dolph);
i.mat(\dolph);


f.load(\basic);
f.load(\cutmix);


a.begin
a.clear

c.refresh;