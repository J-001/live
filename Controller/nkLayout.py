import os
import sys

inPath = '/Users/JDMuschett/Live/Controller/storeSettings.scd'
storePath = '/Users/JDMuschett/Live/Controller/storeSettings.scd'
outPath = '/Users/JDMuschett/Live/Controller/settings.scd'

fsKey = sys.argv[1]
cs = sys.argv[2] + '\n'

def clear(path):
	file = open(path, "w")
	file.truncate()
	file.close()
	return

def keyAt(dictionary, match):
	for (key, value) in dictionary.items():
		if (value==match):
			index = key
			return indexs
	return 

def nkKeyLineArray():
	uid = 0
	nGroups = 8
	array = [None] * 9 * nGroups
	# knobs, faders, s, m  r
	for i in range(0, nGroups):
		groupNum = 9 * i
		array[groupNum + 0] = 'knob' + str(i);
		array[groupNum + 1] = 'fader' + str(i);
		array[groupNum + 2] = 's' + str(i) + "'].on";
		array[groupNum + 3] = 's' + str(i) + "'].off";
		array[groupNum + 4] = 'm' + str(i) + "'].on";
		array[groupNum + 5] = 'm' + str(i) + "'].off";
		array[groupNum + 6] = 'r' + str(i) + "'].on";
		array[groupNum + 7] = 'r' + str(i) + "'].off";
		array[groupNum + 8] = 'line_break_' + str(i);
	# transport
	array.append("play'].on")
	array.append("play'].off")
	array.append("stop'].on")
	array.append("stop'].off")
	array.append("rec'].on")
	array.append("rec'].off")
	array.append("rewind'].on")
	array.append("rewind'].off")
	array.append("ffw'].on")
	array.append("ffw'].off")
	array.append("line_break_8")
	array.append("fwdTrack'].on")
	array.append("fwdTrack'].off")
	array.append("bkTrack'].on")
	array.append("bkTrack'].off")
	array.append("cycle'].on")
	array.append("cycle'].off")
	array.append("setMarker'].on")
	array.append("setMarker'].off")
	array.append("bkMarker'].on")
	array.append("bkMarker'].off")
	array.append("fwdMarker'].on")
	array.append("fwdMarker'].off")
	return array

def dictFromArrays(array, array2):
	dct = {}
	for i in range(0, len(array)):
		key = array[i]
		dct[key] = i 
	return dct

# KEYS
keys = nkKeyLineArray()

# READ IN STRINGS
storeStrings = [];
#formatted to remove enpty functions
funcStrings = [];

uidCounter = 0
inFile = open(inPath, 'r')
for (i, line) in enumerate(inFile,1):
	correctKey = keys[uidCounter]
		
	if "{}" in line.replace(" ",""):
		funcStrings.append("")
		storeStrings.append(line)
		uidCounter += 1
	elif "{|val|}" in line.replace(" ",""):
		funcStrings.append("")
		storeStrings.append(line)
		uidCounter += 1
	elif correctKey in line:
		funcStrings.append(line)
		storeStrings.append(line)
		uidCounter += 1
	elif 'line_break' in correctKey:

		funcStrings.append(line)
		storeStrings.append(line)
		uidCounter += 1
	else:
		funcStrings[uidCounter - 1] += line
		storeStrings[uidCounter - 1] += line
inFile.close()

# UPDATE STRING
dct = dictFromArrays(keys, funcStrings)
fsIndex = dct[fsKey]

#STORE UNFORMATTED
storeStrings[fsIndex] = cs 
#FORMAT CURRENT STRING
if "{}" in cs.replace(" ",""):
	cs = ""
elif "{|val|}" in line.replace(" ",""):
	cs = ""
funcStrings[fsIndex] = cs 

# SET STORE STRING FILE
storeFile = open(storePath, 'w')
storeFile.truncate(0)
for ss in storeStrings:
	storeFile.write(ss)
storeFile.close()

# SET OUT STRING FILE
newFile = open(outPath, 'w')
newFile.truncate(0)
for fs in funcStrings:
	newFile.write(fs)
newFile.close()


