'''
Created on Dec 31, 2017

This is a object that compares the values of a dataframe to an array and looks for certain indexes
if it finds a match it adds that row of a dataframe to an excel file to view later

The idea behind this is about the ex dividend anomaly and we are trying to find trends, this is to
help visualize the data, we usually look at cover rate and divi yeilds in this set and average
volume to look at volatility as volume needs to be at a certain hieght for the stock to be considered

@author: Jack Hughes and Tien Ha, Tien wrote the getDiviDateList function so I wanted to give him credit
'''

import requests
from bs4 import BeautifulSoup
import pandas_datareader.data as web
import datetime
import time
import xlsxwriter

class GetDiviDatesObject:
    
    """constructor for the object, it creates a n excel workbook that can be used by the object
    it also has a global recursive variable because well, I couldnt think of a better way 
    to end a recursive loop
    """
    def __init__(self,date):
        self.date = date
        self.stock_workbook = xlsxwriter.Workbook('/home/jack/Documents/FebWebScrap/Feb-' + str(self.date) + '.xlsx')
        self.recurse = 0
    
    """This method scraps for a webpage and gets the table with the stocks paying dividends on a certain day
    """
    def getDiviDateList(self):
        response = requests.get("http://www.nasdaq.com/dividend-stocks/dividend-calendar.aspx?date=2018-Feb-" + str(self.date))
        txt = response.text
    
        soup = BeautifulSoup(txt, 'lxml')
    
        count = 0
        table = soup.find('table', attrs={'class':'DividendCalendar'})
    
        todaysDivi = list()
        for row in table.find_all('td'):
            for cell in row.find_all('a'):
                getPar = cell.text.index('(')
                subster = cell.text[getPar+1:-1]
                todaysDivi.append(subster)
                count=count+1
        print('There are ' + str(len(todaysDivi)) + ' stocks with ex-dividend date tomorrow')
        return todaysDivi
    
    """"A method that grabs the values of the index columns of a dataframe to help later   
    """    
    def testSingleStock(self):
        start = datetime.datetime(2017,1,1)
        end = datetime.datetime(2017,11,8)
        df_daily = web.DataReader('F', 'yahoo', start, end)
        print(df_daily)
        print(df_daily.iloc[0][1])
        print(df_daily.iloc[1][2])
        print(df_daily.iloc[2][3])
    
    """This method gets passed a list of stocks, grabs info from online about the
    stock such as dates its payed a dividend and daily prices for it, it thttp://www.nasdaq.com/dividend-stocks/dividend-calendar.aspx?date=2017-Dec-18hen
    finds if the stock covered the dividend gap, so it has to check if on the EX
    divi day if the high of that day was above the close minus the dividend of 
    the previous day
    """
    def getCoverRates(self, date_list):
        #these set the dates that it looks for with the pandas frame
        start = datetime.datetime(2013,1,1)
        end = datetime.datetime(2018,1,26)
        
        #iterate through the passed list of tickers
        for ticker in date_list:
            if('(' in ticker):
                ticker = ticker[ticker.index('(')+1:len(ticker)]
            #sleep to help with not overloading yahoos server
            time.sleep(5)
            print('Getting data for', ticker)
            #reset the counting values to zero for each new stock
            count = 0
            count_daily = 0
            try:
                #gets the daily data for the passed ticker, so date, open, close, high
                df_daily = web.DataReader(ticker, 'yahoo', start, end)
                time.sleep(5)
                #gets the dividend data for the passed ticker, date, and dividend value
                df_divis = web.DataReader(ticker, 'yahoo-dividends', start, end)
                print('Finished getting values')
                #sets up the values for the worksheet after the webscrap for the recursive call
                worksheet = self.stock_workbook.add_worksheet(ticker)
                worksheet.write(0,0, 'Ex-Date')
                worksheet.write(0,1, 'Dividend')
                worksheet.write(0,2, 'Close day before EX')
                worksheet.write(0,3, 'Adj-Close')
                worksheet.write(0,4, 'High On Ex')
                worksheet.write(0,5, '(High - Adj-Close')
                worksheet.write(0,6, 'Percent covered')
                worksheet.write(0,7, 'Volume')
                print('Finished setting up file')
                """iterate through the dataframe index columns and look for a math
                once a match is found find the adjusted close of the previous day
                and find the high of the next day, this is a warning because row is
                not used technically, but it is needed to run the section many times
                """
                while(count != len(df_divis.index)):
                    #checks if the dates are the same
                    if(df_daily.index[count_daily] == df_divis.index[count]):
                        #adds the date to the excel file
                        worksheet.write(count+1,0, str(df_divis.index[count]))
                        #adds the dividend to the excel file
                        worksheet.write(count+1, 1, df_divis.iloc[count][0])
                        #adds the close of the day before ex dividend to excel file
                        worksheet.write(count+1, 2, df_daily.iloc[count_daily-1][3])
                        #this gets the adjusted close by getting the close and subtracting the divi value
                        cur_date_close_adj = df_daily.iloc[count_daily-1][3] - df_divis.iloc[count][0]
                        #This adds the adjusted close to the excel file
                        worksheet.write(count+1, 3, cur_date_close_adj)
                        #this gets the value of the high of the EX date
                        ex_date_high = df_daily.iloc[count_daily][1]
                        #adds the high for the date before the ex dividend
                        worksheet.write(count+1, 4, ex_date_high)
                        #this figures out the possible profit
                        profit = ex_date_high - cur_date_close_adj
                        #adds profit to the excel file
                        worksheet.write(count+1, 5, profit)
                        #gets the percent covered
                        per_covered = profit/df_divis.iloc[count][0]
                        #adds the value to the excel file
                        worksheet.write(count+1, 6, per_covered)
                        #gets the volume for the day
                        worksheet.write(count+1, 7, df_daily.iloc[count_daily][5])
                        #print('ex dividend date:',df_daily.index[count_daily],',High of next day - adjusted close =', profit)
                        #print('Percent covered from above date: (adjusted close - dividend) ' + 
                        #      str(profit) + ' / ' + str(df_divis.iloc[count][0]) + ' = ' + 
                        #     str(per_covered))
                        count = count + 1
                        #print(len(df_divis.index), count)
                    count_daily = count_daily + 1
                print('Finished printing to excel')
            except:
                print('Could not get proper url') 
                self.recurseForTicker(ticker)
        self.stock_workbook.close()
    """This is a recursive method that does the same thing as above if program cannot 
    find the url or if the url blocks it, it waits 10 seconds though to help with
    not getting kicked for trying to webscrap too much
    """
    def recurseForTicker(self, ticker):
        print('attempting to get data again waiting 10 seconds')
        time.sleep(10)
        start = datetime.datetime(2013,1,1)
        end = datetime.datetime(2018,1,3)
        count = 0
        count_daily = 0
        try:
            #gets the daily data for the passed ticker, so date, open, close, high
            df_daily = web.DataReader(ticker, 'yahoo', start, end)
            time.sleep(5)
            #gets the dividend data for the passed ticker, date, and dividend value
            df_divis = web.DataReader(ticker, 'yahoo-dividends', start, end)
            print('Finished getting values')
            #set up worksheet for the stock in the recursive call after getting past the web scrap
            worksheet = self.stock_workbook.add_worksheet(ticker)
            print('Made the workbook in the recursive call')
            worksheet.write(0,0, 'Ex-Date')
            worksheet.write(0,1, 'Dividend')
            worksheet.write(0,2, 'Close day before EX')
            worksheet.write(0,3, 'Adj-Close')
            worksheet.write(0,4, 'High on EX')
            worksheet.write(0,5, '(High - Adj-Close')
            worksheet.write(0,6, 'Percent covered')
            worksheet.write(0,7, 'Volume')
            print('Finished setting up file')
            """iterate through the dataframe index columns and look for a math
            once a match is found find the adjusted close of the previous day
            and find the high of the next day
            """
            while(count != len(df_divis.index)):
                #checks if the dates are the same
                if(df_daily.index[count_daily] == df_divis.index[count]):
                    #adds the date to the excel file
                    worksheet.write(count+1,0, str(df_divis.index[count]))
                    #adds the dividend to the excel file
                    worksheet.write(count+1, 1, df_divis.iloc[count][0])
                    #adds the close of the day before ex dividend to excel file
                    worksheet.write(count+1, 2, df_daily.iloc[count_daily-1][3])
                    #this gets the adjusted close by getting the close and subtracting the divi value
                    cur_date_close_adj = df_daily.iloc[count_daily-1][3] - df_divis.iloc[count][0]
                    #This adds the adjusted close to the excel file
                    worksheet.write(count+1, 3, cur_date_close_adj)
                    #this gets the value of the high of the EX date
                    ex_date_high = df_daily.iloc[count_daily][1]
                    #adds the high for the date before the ex dividend
                    worksheet.write(count+1, 4, ex_date_high)
                    #this figures out the possible profit
                    profit = ex_date_high - cur_date_close_adj
                    #adds profit to the excel file
                    worksheet.write(count+1, 5, profit)
                    #gets the percent covered
                    per_covered = profit/df_divis.iloc[count][0]
                    #adds the value to the excel file
                    worksheet.write(count+1, 6, per_covered)
                    #gets the volume for the day
                    worksheet.write(count+1, 7, df_daily.iloc[count_daily][5])
                    count = count + 1
                    #print(len(df_divis.index), count)
                count_daily = count_daily + 1
            print('Finished getting the stuff in the recursive call')
        except: 
            self.recurse += 1
            if(self.recurse == 5):
                print('Breaking out of recursive infinite loop')
                self.recurse = 0
                return False
            self.recurseForTicker(ticker)
            
    """This is a copy of the recursive method but it is for just one ticker, and it does not wait
    but for 10 years, this is here if we find a good stock once we have the other excel files so
    that we can use this method to look through the best for the last 10 years 
    """     
    def justOneTicker(self, ticker):
        start = datetime.datetime(2007,1,1)
        end = datetime.datetime(2017,12,28)
        count = 0
        count_daily = 0
        try:
            #gets the daily data for the passed ticker, so date, open, close, high
            df_daily = web.DataReader(ticker, 'yahoo', start, end)
            time.sleep(5)
            #gets the dividend data for the passed ticker, date, and dividend value
            df_divis = web.DataReader(ticker, 'yahoo-dividends', start, end)
            print('Finished getting values')
            #set up worksheet for the stock in the recursive call after getting past the web scrap
            worksheet = self.stock_workbook.add_worksheet(ticker)
            print('Made the workbook in the recursive call')
            worksheet.write(0,0, 'Ex-Date')
            worksheet.write(0,1, 'Dividend')
            worksheet.write(0,2, 'Close day before EX')
            worksheet.write(0,3, 'Adj-Close')
            worksheet.write(0,4, 'High on EX')
            worksheet.write(0,5, '(High - Adj-Close')
            worksheet.write(0,6, 'Percent covered')
            worksheet.write(0,7, 'Volume')
            print('Finished setting up file')
            """iterate through the dataframe index columns and look for a math
            once a match is found find the adjusted close of the previous day
            and find the high of the next day
            """
            while(count != len(df_divis.index)):
            #for row in df_daily.iterrows():
                #checks if the dates are the same
                if(df_daily.index[count_daily] == df_divis.index[count]):
                    #adds the date to the excel file
                    worksheet.write(count+1,0, str(df_divis.index[count]))
                    #adds the dividend to the excel file
                    worksheet.write(count+1, 1, df_divis.iloc[count][0])
                    #adds the close of the day before ex dividend to excel file
                    worksheet.write(count+1, 2, df_daily.iloc[count_daily-1][3])
                    #this gets the adjusted close by getting the close and subtracting the divi value
                    cur_date_close_adj = df_daily.iloc[count_daily-1][3] - df_divis.iloc[count][0]
                    #This adds the adjusted close to the excel file
                    worksheet.write(count+1, 3, cur_date_close_adj)
                    #this gets the value of the high of the EX date
                    ex_date_high = df_daily.iloc[count_daily][1]
                    #adds the high for the date before the ex dividend
                    worksheet.write(count+1, 4, ex_date_high)
                    #this figures out the possible profit
                    profit = ex_date_high - cur_date_close_adj
                    #adds profit to the excel file
                    worksheet.write(count+1, 5, profit)
                    #gets the percent covered
                    per_covered = profit/df_divis.iloc[count][0]
                    #adds the value to the excel file
                    worksheet.write(count+1, 6, per_covered)
                    #gets the volume for the day
                    worksheet.write(count+1, 7, df_daily.iloc[count_daily][5])
                    count = count + 1
                    #print(len(df_divis.index), count)
                    #if(count == len(df_divis.index)):
                        #break
                count_daily = count_daily + 1
            print('Finished setting up the excel file')
        except: 
            print('Couldnt get the url in time, trying again')
            self.justOneTicker(ticker)
        self.stock_workbook.close()

#these are for testing the values            
getValues = GetDiviDatesObject('Ford')
#get the list of stocks
#getStocks = getValues.getDiviDateList()
#put the list through the function to grab the info
#excel_info = getValues.getCoverRates(getStocks)
#testSingleStock()
getF_value = getValues.justOneTicker('F')
#getValues.testSingleStock()

