'''
Created on Oct 9, 2017

This is basically a test class, this player only removes 1 chips from
the pile ever

@author: jack
'''

class OnesPlayer:
    
    #sets the name
    def __init__(self, name):
        self.name = name
    
    #removes 1 chip from the pile    
    def getMove(self, g):
        return 1
    
    #returns the name given to the player
    def getName(self):
        return self.name
