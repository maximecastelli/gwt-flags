# Flag Designer
# Copyright (c) 2011 Lars Ruoff - see license.txt
# http://code.google.com/p/flag-designer/
# Python 2.7

import random

designs = {
    'solid':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n',
        },
    'bicolor-horizontal':
        {
            'svg': '  <rect width="360" height="120" x="0" y="0" fill="%(color1)s" />\n  <rect width="360" height="120" x="0" y="120" fill="%(color2)s" />\n',
        },
    'bicolor-vertical':
        {
            'svg': '  <rect width="180" height="240" x="0" y="0" fill="%(color1)s" />\n  <rect width="180" height="240" x="180" y="0" fill="%(color2)s" />\n',
        },
    'tricolor-horizontal':
        {
            'svg': '  <rect width="360" height="80" x="0" y="0" fill="%(color1)s" />\n  <rect width="360" height="80" x="0" y="80" fill="%(color2)s" />\n  <rect width="360" height="80" x="0" y="160" fill="%(color3)s" />\n',
        },
    'tricolor-vertical':
        {
            'svg': '  <rect width="120" height="240" x="0" y="0" fill="%(color1)s" />\n  <rect width="120" height="240" x="120" y="0" fill="%(color2)s" />\n  <rect width="120" height="240" x="240" y="0" fill="%(color3)s" />\n',
        },
    'bicolor-2x2':
        {
            'svg': '  <rect width="180" height="120" x="0" y="0" fill="%(color1)s" />\n  <rect width="180" height="120" x="180" y="0" fill="%(color2)s" />\n  <rect width="180" height="120" x="0" y="120" fill="%(color2)s" />\n  <rect width="180" height="120" x="180" y="120" fill="%(color1)s" />\n',
        },
    'bicolor-stripes-horizontal':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n  <rect width="360" height="26" x="0" y="26" fill="%(color2)s" />\n  <rect width="360" height="26" x="0" y="80" fill="%(color2)s" />\n  <rect width="360" height="26" x="0" y="133" fill="%(color2)s" />\n  <rect width="360" height="26" x="0" y="187" fill="%(color2)s" />\n',
        },
    'bicolor-stripes-vertical':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n  <rect width="40" height="240" x="40" y="0" fill="%(color2)s" />\n  <rect width="40" height="240" x="120" y="0" fill="%(color2)s" />\n  <rect width="40" height="240" x="200" y="0" fill="%(color2)s" />\n  <rect width="40" height="240" x="280" y="0" fill="%(color2)s" />\n',
        },
    'bicolor-checkered':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n  <rect width="40" height="40" x="40" y="0" fill="%(color2)s" />\n  <rect width="40" height="40" x="120" y="0" fill="%(color2)s" />\n  <rect width="40" height="40" x="200" y="0" fill="%(color2)s" />\n  <rect width="40" height="40" x="280" y="0" fill="%(color2)s" />\n  <rect width="40" height="40" x="0" y="40" fill="%(color2)s" />\n  <rect width="40" height="40" x="80" y="40" fill="%(color2)s" />\n  <rect width="40" height="40" x="160" y="40" fill="%(color2)s" />\n  <rect width="40" height="40" x="240" y="40" fill="%(color2)s" />\n  <rect width="40" height="40" x="320" y="40" fill="%(color2)s" />\n  <rect width="40" height="40" x="40" y="80" fill="%(color2)s" />\n  <rect width="40" height="40" x="120" y="80" fill="%(color2)s" />\n  <rect width="40" height="40" x="200" y="80" fill="%(color2)s" />\n  <rect width="40" height="40" x="280" y="80" fill="%(color2)s" />\n  <rect width="40" height="40" x="0" y="120" fill="%(color2)s" />\n  <rect width="40" height="40" x="80" y="120" fill="%(color2)s" />\n  <rect width="40" height="40" x="160" y="120" fill="%(color2)s" />\n  <rect width="40" height="40" x="240" y="120" fill="%(color2)s" />\n  <rect width="40" height="40" x="320" y="120" fill="%(color2)s" />\n  <rect width="40" height="40" x="40" y="160" fill="%(color2)s" />\n  <rect width="40" height="40" x="120" y="160" fill="%(color2)s" />\n  <rect width="40" height="40" x="200" y="160" fill="%(color2)s" />\n  <rect width="40" height="40" x="280" y="160" fill="%(color2)s" />\n  <rect width="40" height="40" x="0" y="200" fill="%(color2)s" />\n  <rect width="40" height="40" x="80" y="200" fill="%(color2)s" />\n  <rect width="40" height="40" x="160" y="200" fill="%(color2)s" />\n  <rect width="40" height="40" x="240" y="200" fill="%(color2)s" />\n  <rect width="40" height="40" x="320" y="200" fill="%(color2)s" />\n',
        },
    'bicolor-diagonal-down':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n  <polygon points="0,0 0,240 360,240" fill="%(color2)s" />\n',
        },
    'bicolor-diagonal-up':
        {
            'svg': '  <rect width="360" height="240" x="0" y="0" fill="%(color1)s" />\n  <polygon points="360,0 0,240 360,240" fill="%(color2)s" />\n',
        },
}

overlays = {
    'none':
        {
            'svg': '',
            'weight': 1.0,
            'symbol-x': 180,
            'symbol-y': 120,
        },
    'bar-cross':
        {
            'svg': '  <rect width="360" height="60" x="0" y="90" fill="%(color4)s" />\n  <rect width="60" height="240" x="150" y="0" fill="%(color4)s" />\n',
            'incompatible': ['tricolor-horizontal', 'tricolor-vertical', 'bicolor-diagonal-down', 'bicolor-diagonal-up', 'bicolor-checkered'],
            'weight': 1.0,
        },
    'diagonal-bar-down':
        {
            'svg': '  <polygon points="0,0 40,0 360,200, 360,240, 320,240, 0,40" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-diagonal-up', 'bicolor-checkered'],
            'weight': 1.0,
        },
    'diagonal-bar-up':
        {
            'svg': '  <polygon points="360,0 320,0 0,200, 0,240, 40,240, 360,40" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-diagonal-down', 'bicolor-checkered'],
            'weight': 1.0,
        },
    'diagonal-cross':
        {
            'svg': '  <polygon points="0,0 40,0 360,200, 360,240, 320,240, 0,40" fill="%(color4)s" />\n    <polygon points="360,0 320,0 0,200, 0,240, 40,240, 360,40" fill="%(color4)s" />\n',
            'incompatible': ['tricolor-horizontal', 'tricolor-vertical', 'bicolor-checkered'],
            'weight': 1.0,
        },
    'triangle-left':
        {
            'svg': '  <polygon points="0,0 180,120 0,240" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-diagonal-down', 'bicolor-diagonal-up', 'bicolor-stripes-vertical'],
            'weight': 1.0,
        },
    'circle-center':
        {
            'svg': '  <circle cx="180" cy="120" r="80" fill="%(color4)s" />\n',
            'weight': 1.0,
        },
    'diamond-center':
        {
            'svg': '  <polygon points="80,120 180,20 280,120 180,220" fill="%(color4)s" />\n',
            'weight': 1.0,
            'incompatible': ['bicolor-diagonal-up', 'bicolor-diagonal-down'],
        },
    'square-canton':
        {
            'svg': '  <rect width="120" height="120" x="0" y="0" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-vertical', 'bicolor-diagonal-down', 'bicolor-diagonal-up', 'bicolor-2x2'],
            'weight': 1.0,
        },
    'rectangle-canton':
        {
            'svg': '  <rect width="180" height="120" x="0" y="0" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-diagonal-down', 'bicolor-diagonal-up', 'bicolor-stripes-vertical', 'bicolor-checkered'],
            'weight': 1.0,
        },
    'scandinavian':
        {
            'svg': '  <rect width="360" height="40" x="0" y="100" fill="%(color4)s" />\n  <rect width="40" height="240" x="100" y="0" fill="%(color4)s" />\n',
            'incompatible': ['bicolor-diagonal-down', 'bicolor-diagonal-up', 'bicolor-2x2'],
            'weight': 1.0,
        },
}

colors = {
    'black': '#000000',
    'white': '#ffffff',
    'red': '#ce1126',
    'blue': '#00335b',
    'green': '#006b3f',
    'yellow': '#fcd116',
    'orange': '#ff8400',
    'lightblue': '#3a75c4',
}

colors_list = [
    '#000000',
    '#ffffff',
    '#ce1126',
    '#00335b',
    '#006b3f',
    '#fcd116',
    '#ff8400',
    '#3a75c4',
]

# Test
total_weight = 0.0
for key, val in designs.items():
    if 'weight' in val:
        total_weight += val['weight']
    else:
        total_weight += 1.0
    designs[key]['weight-upper-bound'] = total_weight
x = random.random() * total_weight
for key, val in designs.items():
    if x<val['weight-upper-bound']:
        break
the_design = key

the_overlay = 'none'
total_weight = 0.0
for key, val in overlays.items():
    if 'weight' in val:
        total_weight += val['weight']
    else:
        total_weight += 1.0
    overlays[key]['weight-upper-bound'] = total_weight
x = random.random() * total_weight
for key, val in overlays.items():
    if x<val['weight-upper-bound'] and (('incompatible' not in val)
        or ('incompatible' in val and the_design not in val['incompatible'])):
        the_overlay = key
        break

print('<?xml version="1.0" encoding="UTF-8" standalone="no"?>')
print('<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.0//EN" "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd">')
print('<svg xmlns="http://www.w3.org/2000/svg" version="1.0" x="0" y="0" width="360" height="240">')
##print(designs['tricolor-horizontal']['svg']
##    % {'color1':colors['black'], 'color2':colors['red'], 'color3':colors['yellow']})
print(designs[the_design]['svg']
    % {'color1':colors_list[random.randint(0, len(colors_list)-1)],
    'color2':colors_list[random.randint(0, len(colors_list)-1)],
    'color3':colors_list[random.randint(0, len(colors_list)-1)],})
print(overlays[the_overlay]['svg']
    % {'color4':colors_list[random.randint(0, len(colors_list)-1)],})
print('</svg>')

