# Flag Designer
# Copyright (c) 2011 Lars Ruoff - see license.txt
# http://code.google.com/p/flag-designer/
# Python 2.7

width=40
height=40

for y in range(0, 240, height):
    for x in range(0, 360, width):
        if (x/width+y/height)%2!=0:
            print('  <rect width="%(width)d" height="%(height)d" x="%(x)d" y="%(y)d" fill="#fcd116" />'
                % {'width':width, 'height':height, 'x':x, 'y':y})
