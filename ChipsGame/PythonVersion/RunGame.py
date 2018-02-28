'''
Created on Oct 9, 2017

This is the main method for the game, to actually run
it and see what the results are

@author: jack
'''

#these have errors but they actually work as well
from chipsReferee import ChipsReferee
from jackPlayer import JackPlayer
from onesPlayer import OnesPlayer
from MaxPlayer import MaxPlayer
    
ref = ChipsReferee(True)
ones = OnesPlayer("ones")
onesA = OnesPlayer("onesA")
onesB = OnesPlayer("onesB")
roster = [ones, onesA, onesB]
ref.addPlayer(ones)
ref.addPlayer(onesA)
ref.addPlayer(onesB)
print(ref.printPlayers())
winner = ref.playGameList(3, roster)
print(winner)

#print("The winner of the game is " + winner)