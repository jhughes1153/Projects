'''
Created on Oct 9, 2017

This is also mostly a testing class, it always takes the max possible
move that it can make

@author: jack
'''

class MaxPlayer:
    
    #sets the name
    def __init__(self, name):
        self.name = name
     
    #takes the most that it can from the pile    
    def getMove(self, g):
        self.Game = g
        return self.Game.getLimitMove()
    
    #returns its given name
    def getName(self):
        return self.name
