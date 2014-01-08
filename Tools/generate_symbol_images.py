#!/usr/bin/env python
import glob, string
from subprocess import call

for infile in glob.glob('sym*.svg'):
	outfile = "../GwtFlags/war/images/" + string.replace(infile, '.svg', '.png')
	if call(["rsvg-convert.exe", "--zoom=0.1", "--output", outfile, infile])==0:
		print(outfile + " OK.")
