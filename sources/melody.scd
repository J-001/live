/*
Functions which return 
arrays of frequencies and durations to be used with Pseqs

arguments - length


TO FIGURE OUT ??

1. How to work into phrase structure.
*/

~melody = {|length=4,pitchCollection|

var firstNote;
var melodicStream;
var melodicPhrase;
var durStream;
var phraseDur;

firstNote = 0;

melodicStream = Pfunc{
	~arp[firstNote];
	firstNote=firstNote+[-1,1].choose
	}.asStream;

melodicPhrase =melodicStream.nextN(length);

durStream= Prand((1..4),inf).asStream;
phraseDur= durStream.nextN(length)*0.125;

	[melodicPhrase,phraseDur]
}

