#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import urllib2
import time

from xml.dom.minidom import parseString

def ox(resultText):
    return u'○' if resultText == 'Passed System Test' else u'×'

def getData(row, tagName, text=''):
    cnode = row.getElementsByTagName(tagName)[0].childNodes
    return cnode[0].data if len(cnode) > 0 else text

def computeMaxPoint(point, time):
    time /= 1000.0
    TT = 75 * 60.0
    # TT = total time
    # MP * (0.3 + (0.7 * TT ^ 2) / (10 * time^2+ TT ^2) = point
    return point / (0.3 + 0.7 * TT ** 2 / (10 * time ** 2 + TT ** 2))

argv = sys.argv
argc = len(argv)

nameToAdvance = {
        "2015 TCO 2": 40,
        "2015 TCO 3": 6,
        "TCO14 Round 2": 50,
        "TCO14 Round 3": 12,
        "TCO13 Round 2": 50,
        "TCO13 Round 3": 12,
        "TCO12 Round 2": 50,
        "TCO12 Round 3": 12,
        "TCO11 Round 3": 150,
        "TCO11 Round 4": 60,
        "TCO11 Round 5": 24,
        "TCO10 Round 3": 150,
        "TCO10 Round 4": 60,
        "TCO10 Round 5": 24,
        }

f = urllib2.urlopen("http://www.topcoder.com/tc?module=BasicData&c=dd_round_list")
dom = parseString(f.read())
print u"\t".join((u'Round', u'目標順位', u'目標得点', u'初期得点', u'実施日時', u'アーカイブ')).encode('utf-8').strip()
for row in dom.getElementsByTagName('row'):
    roundName = getData(row, 'short_name')
    if "Parallel" in roundName:
        continue
    roundId = getData(row, 'round_id')
    roundType = getData(row, 'round_type_desc')
    dateStr = getData(row, 'date')
    date = time.strptime(dateStr, "%Y-%m-%d %H:%M")
    if (date.tm_year < 2010) or (date.tm_year < 2014 and (roundType == 'Single Round Match')):
        continue

    roundDataUrl = 'http://www.topcoder.com/tc?module=BasicData&c=dd_round_results&rd='+roundId 

    # print roundDataUrl

    f = urllib2.urlopen(roundDataUrl)
    dom = parseString(f.read())

    points = []
    times = []
    for i in range(0,3):
        points.append([])
        times.append([])
    for userResult in dom.getElementsByTagName('row'):
        num = ['one', 'two', 'three']
        for i in range(0,3):
            p = getData(userResult, 'level_' + num[i] + "_final_points")
            t = getData(userResult, 'level_' + num[i] + "_time_elapsed")
            if p == "":
                continue
            points[i].append(float(p))
            times[i].append(t)

    maxPoints = []
    for i in range(0,3):
        maxPoints.append(0)
        for j in range(0, len(points[i])):
            maxPoints[i] = max(maxPoints[i], computeMaxPoint(points[i][j], float(times[i][j])))
    for i in range(0,3):
        points[i].sort(reverse=True)

    obj = 20
    for key in nameToAdvance:
        if key in roundName:
            obj = nameToAdvance[key] / 2
    
    for i in range(0,3):
        maxPoints[i] = int(maxPoints[i])
        maxPointStr = str(maxPoints[i])
        if maxPoints[i] == 0 or maxPoints[i] % 5 != 0:
            maxPointStr = ["250?", "500?", "1000?"][i]
        url = 'http://community.topcoder.com/stat?c=round_overview&er=%d&rd=%s' % (obj, roundId)
        o = obj if obj <= len(points[i]) else len(points[i])
        print "\t".join((roundName, str(o), str(points[i][o - 1]), maxPointStr, dateStr, url))
    
    sys.stdout.flush()
