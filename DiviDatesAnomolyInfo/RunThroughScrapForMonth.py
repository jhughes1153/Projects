'''
Created on Jan 1, 2018

This is like a main to run through the values of the other object for a month
it is hard coded here to only get half a month because the data is usually not
very valueable further out than this due to not all companies announcing ex-dividend yet

@author: jack
'''

from GetDiviDatesObject import GetDiviDatesObject

def runThroughWebscrape():
    #set this to no lower than 1 because months dont count starting at 0
    count = 1
    #Set this count value to the day after the date you are looking for
    while(count != 14):
        try:
            print('got the date for feb ' + str(count))
            getDiviTemp = GetDiviDatesObject(count)
            tempDiviList = getDiviTemp.getDiviDateList()
            getDiviTemp.getCoverRates(tempDiviList)
            print('Finished creating excel file for feb ' + str(count))
            count += 1
        except:
            print('That is not a valid date')
            count += 1

runThroughWebscrape()

