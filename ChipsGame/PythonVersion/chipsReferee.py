'''
Created on Sep 29, 2017

This class is the referee for the game, so it makes sure that players
are making valid moves and that the next player is making the next 
move

@author: jack
'''

#this says error but it actually works
from game import Game

class ChipsReferee:
    
    #constructor for the class
    def __init__(self, a):
        self.verbosity=a
        self.playerList = []
    
    #set if printing should be allowed    
    def getVerbosity(self):
        return self.verbosity
    
    #add a player to the roster
    def addPlayer(self, Player):
        self.playerList.append(Player)
    
    #print the players in the roster and the number of players    
    def printPlayers(self):
        for i in self.playerList:
            print(i.getName())
        print(len(self.playerList))
    
    """this method is called to actually start the game and is the act of playing the game
    """    
    def playGame(self, pilesize):
        self.game = Game(pilesize)
        count = 0
        self.move = 0
        numPlayers = len(self.playerList)
        while self.game.getPile() > 0:
            print('Player: ' +str(self.playerList[count].getName()) + ' is taking ' + 
                  str(self.playerList[count].getMove(self.game)) + ' from Pile size: ' + 
                  str(self.game.getPile()))
            self.move = self.playerList[count].getMove(self.game) 
            self.game.removeChips(self.move) 
            count+=1
            if count==numPlayers:
                count=0
            print('The limit Move is: ' + str(self.game.getLimitMove()) + ', The Pile Size is: ' + str(self.game.getPile()))
            #print(self.game.getPile())
        return self.playerList[count-1].getName()    
        #if count == 0:
            #return self.playerList[len(self.playerList) - 1].getName()
        #else:
            #return self.playerList[count].getName()
    
    """this method here because I was helping a friend write a similar method to mine
    he was having trouble getting his to work so I wrote this in mine program to help
    and this one is a working version of the above method 
    """ 
    def playTest(self, chipValue):
        self.game = Game(chipValue)
        self.winner = " "
        self.move = 0
        while (self.game.getPile() > 0):
            for i in self.playerList:
                print(i.getName())
                x = self.move = i.getMove(self.game)
                self.game.removeChips(self.move)
                self.winner = i.getName()
                print(x)
                print(self.game.getLimitMove())
                print(self.game.getPile())
                if(self.game.getPile()==0):
                    return self.winner
        return self.winner
     
    """This method is in case someone passes a list instead of just adding players
    one by one
    """          
    def playGameList(self, pilesize, roster):
        self.game = Game(pilesize)
        self.roster = roster
        count = 0
        self.move = 0
        numPlayers = len(self.roster)
        while self.game.getPile() > 0:
            print('Player: ' +str(self.roster[count].getName()) + ' is taking ' + 
                  str(self.roster[count].getMove(self.game)) + ' from Pile size: ' + 
                  str(self.game.getPile()))
            self.move = self.roster[count].getMove(self.game) 
            self.game.removeChips(self.move) 
            count+=1
            if count==numPlayers:
                count=0
            print('The limit Move is: ' + str(self.game.getLimitMove()) + ', The Pile Size is: ' + str(self.game.getPile()))
            #print(self.game.getPile())
        return self.roster[count-1].getName() 
                
                
                