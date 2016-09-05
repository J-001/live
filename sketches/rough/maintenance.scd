//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
/*
LOAD SOUNDS
*/
"~/Desktop/SuperCollider/Synths/SynthUtils/sounds.scd".loadPaths;
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------

//Persistent Functions to add to ServerTree

//EFFECTS
/*
Allocate and free busses for synths

//CREATE NEW INSTANCE OF SYNTH AND AUTOMATICALLY ALLOCATE NEW BUS?
*/
f = ();

f[\delay] = Bus.audio(s,2);


