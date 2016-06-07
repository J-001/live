//----------------------------------------------------------------------------
//----------------------------------------------------------------------------
/*
LOAD SOUNDS FROM LIBRARY
*/
(
var mainFolder;
var dir;
b=Dictionary();

dir = "/Users/JDMuschett/Desktop/LEXICON/";

mainFolder = PathName(dir++"recity/cityfrags");

mainFolder.entries.postln;
mainFolder.entries.do{|subfolder|
	var name;
	var subDictionary;
	subDictionary=[];
	name = subfolder.folderName;

	b.add(name.asSymbol -> subDictionary);
	subfolder.entries.do{|soundFile|
		var sound;
		// soundFile.fullPath.postln;
		sound = Buffer.read(s,soundFile.fullPath);
		b[name.asSymbol] = b[name.asSymbol].add(sound);
	}
};

)
//----------------------------------------------------------------------------
//----------------------------------------------------------------------------


//FREE
// b.do{|dictionaries|
// 	dictionaries.do{|buffer|
// 		buffer.free
// 	}
// }

