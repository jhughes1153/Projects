'''
Created on Sep 29, 2017

This class is based around manipulating the pile as removing
things from a pile is basically the whole game

@author: jack
'''

class Game:
    
    #constructor for the class, it makes sure the pilesize is greater than 3
    def __init__(self, size):
        self.size = size
        if self.size<3:
            print("that doesnt work")
        else:
            self.pile = self.size
            self.limitMove = self.pile - 1
    
    #this method removes chips from the pile  
    def removeChips(self, remove):
        self.remove = remove
        if self.remove > self.limitMove:
            print("That doesnt work either") 
        else:
            self.pile = self.pile - self.remove  
            if self.remove > self.limitMove:
                self.limitMove = self.pile - 1
            else:
                self.limitMove = 2 * self.remove
    
    #returns the pile size            
    def getPile(self):
        return self.pile
    
    #returns the max move size
    def getLimitMove(self):
        return self.limitMove



    
    
    
    