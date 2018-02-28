'''
Created on Sep 29, 2017

This class is based around the idea that you never want to be down to 3 chips and
can only take away 2, if the pile is even it takes away 1 because it is trying to
get the other player to have 3 by the end, if it is odd it will take away 2
because again it is trying to make the other player have an odd number at the end
so that it will win 

@author: jack
'''

class JackPlayer:
    
    #sets the name for the class
    def __init__(self, name):
        self.name = name        
    
    """takes chips away from the pile based on if the pile is even
    or odd basically
    """    
    def getMove(self, g):
        self.Game = g
        if self.Game.getLimitMove() >= self.Game.getPile():
            return self.Game.getPile()
        elif self.Game.getPile() == self.Game.getLimitMove() + 1:
            return 2
        #elif y <= 100 and y%2==0:
        elif self.Game.getPile() <= 100 and self.Game.getPile()%2==0:
            return 1
        else:
            return 2
        
    #returns the set name    
    def getName(self):
            return self.name
    
    
    