#advanced
import numpy as np
import queue
import sys
import matplotlib.pyplot as mpl
import math as math
import matplotlib as m
import random
from collections import deque
import random

n =50
colors = [mpl.cm.ocean(1),mpl.cm.binary(0),mpl.cm.binary(0.4),
              mpl.cm.binary(0.7)]
cmap = m.colors.ListedColormap(colors)


## Create matrix
def create():
    # create matrix of equal proportion of all terrains
    p = 0.25
    #start = (0,0)
    #goal = (n-1, n-1)
    greycounter = 0
    lightgreycounter = 0
    greencounter = 0
    blackcounter = 0

    ## 1= green -> forest
    ## 0 = white -> flat
    ## .4 = gray -> hilly
    ## .7 = dark gray -> cave

    newmatrix = np.zeros((n,n))
    numBlocks = math.ceil(n*n*p)
    numBlocks = int(numBlocks)
    matrix = np.zeros((n,n))

    cmap= m.colors.ListedColormap(colors)


    num_arr = []
    for i in range(numBlocks):
        num_arr.append(1)
        num_arr.append(2)
        num_arr.append(3)
        num_arr.append(4)


    random.shuffle(num_arr)

    ## loading new_matrix, the matrix that contains all of the terrain types
    counter = 0
    l=0
    x = n*n
    while l < x:
        for i in range(n):
            for j in range(n):
                #print("l value = ", l, "n value", i,j)
                newmatrix[i,j] = num_arr[l]
                l+=1

    return newmatrix

newmatrix = create()

## pick random target value
x = np.random.randint(0,n-1)
y = np.random.randint(0,n-1)

print("This is the target:",(x,y))



#### using Bayes theorem

## go through the array and set probability to 1/(total number of cells)
prob_matrix = np.zeros((n,n))
## inital probability when all have equal chance
init_prob = 1/(n*n)
for i in range(n):
    for j in range(n):
        prob_matrix[i,j] = 1/(n*n)

    ## 1= green -> forest  =====> .7
    ## 2 = white -> flat ====> .1
    ## 3 = gray -> hilly =====> .3
    ## 4 = dark gray -> cave ===> .9




# calculate Manhattan Distance
def ManhattanDistance(a1,b1,a2,b2):
    total =  abs(a1 - a2) + abs(b1 - b2) 
    #print("1= ", a1, b1, "2=", a2,b2, total)
    return total


def neighbors(arr, d, coordinate):
    neighborList= []
#The following method is to check the neighbors of a given path
#The possible neighbors are right,left,up,down,upper right,upper left,lower right,lower,left
    x = int(coordinate[0])
    y = int(coordinate[1])
    counter = int(0)


    #in this neighbors list, we are expanding our reach and looking beyond the up,down,right left neighbors
    #we are looking at direct neighbors and look at the probability of those 
    if ((x+1 < d and ((y)<d))):
        neighborList.append((x+1,y))
        

    if ((x-1>=0 and y<d)):
        neighborList.append((x-1,y))

    if ((x<d) and (y+1)<d):
        neighborList.append((x,y+1))

    if ((x < d) and (y-1)>=0):
        neighborList.append((x,y-1))
            

    return neighborList



### update the probability matrix using the false negative rates
## value multiplied by (1-fnr)
def calc_fn(newmatrix, Coord1, Coord2):
    if (newmatrix[Coord1, Coord2] == 2):
        #changed to 1-p
        #hilly
        prob_matrix[Coord1, Coord2] *= .9
        #print("flat,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #forest
    elif (newmatrix[Coord1, Coord2] == 1):
        #prob_matrix[i]  *= .7
        prob_matrix[Coord1, Coord2] *= .3
        #print("firest,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #hilly 
    elif (newmatrix[Coord1, Coord2] == 3):
        #prob_matrix[i] *=  .3
        prob_matrix[Coord1, Coord2] *= .7
        #print("hill,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #cave 
    ## the zeros are a temp fix
    elif (newmatrix[Coord1, Coord2] == 4):
        #prob_matrix[i] *= .9
        prob_matrix[Coord1, Coord2] *= .1
        #print("cave,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #print("after method", prob_matrix)
    
manList = []

#open cell is a method to look a that the probability value and decides the current value to look at 
#based on the probability matrix, our agent will decide where to go 
def open_cell(newmatrofix, prob_matrix,Coord1,Coord2):
    tup_list = [] 
    
    #we append coordinates and probaility to a list 
    #this will later determine what has the highest chance of containing the target
    for a in range(n):
        for b in range(n):
                tup_list.append(((a,b), prob_matrix[a,b]))
                tup_list.sort(key=lambda x: x[1], reverse = True)
    

    ## find maximum value in the mrix and open that 
    i = tup_list[0][0]
    
    ## check neighbors
    neighbor1 = neighbors(prob_matrix, n, i)
    
    #checking neighbors of neighbors to have a short distance 
    #this will...
    for q in neighbor1:
        neighbor = neighbors(prob_matrix, n, q)
    
    
    #...determine whether there is a better cell to visit, once going through the neighbors of neighbors
    check_n = []
    for neigh in neighbor:
        check_n.append((neigh, prob_matrix[neigh], ManhattanDistance(neigh[0], neigh[1], Coord1, Coord2)))
    check_n.append((i, prob_matrix[i], ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    check_n.sort(key=lambda x: (-x[1], x[2]))
    
    i = check_n[0][0]
        
    
    
    #print(check_n)
    ## calculate Manhattan
    manList.append(ManhattanDistance(i[0], i[1], Coord1, Coord2))
    #print("current value", i)
    #j = tup_list[0][0]
    
    
    tot = 0
    ## add everything together
    for a in range(n):
        for b in range(n):
            tot += prob_matrix[a,b]
    
    ## normalize
    if tot != 1.0:
        #print("in here")
        for a in range(n):
            for b in range(n):
                if tot != 0:
                    prob_matrix[a,b] = float(prob_matrix[a,b]) / (float(tot))
                
    #print(tup_list)
    tup_list.clear()
    return i

found = False
x_val = 0
y_val = 0
#print("this is found", found)
Coord1 = 25
Coord2 = 25
count = 1
while found == False:  
    x_val, y_val = open_cell(newmatrix, prob_matrix,Coord1, Coord2)
    ## dictionary of the false negative rates for each
    false_neg = {}
    false_neg[1] = .7
    false_neg[2] = .1
    false_neg[3] = .3
    false_neg[4] = .9
    
    ## chance picks a random decimal value between 0 and 1 -> if greater than false negative rate, then the agent actually finds the target when looking in the correct target cell
    ## otherwise will be a false negative
    chance = random.uniform(0,1)
    terrain = newmatrix[x_val, y_val]
    
    ## if cave or forest we check twice
    if (terrain == .7) or (terrain == .9):
        if (x_val == x and y_val ==y):
            if (chance > false_neg[terrain]):
                count+=1
                found = True
                print("true found")

            else:
                count+=1
                chance = random.uniform(0,1)

    #print(x_val, y_val)
    if (x_val == x and y_val ==y):
        if (chance > false_neg[terrain]):
            count+=1
            found = True
            print("true found")
    else:
      ## set original value looked at to Coord1 and 2 and then continue to find next value
        Coord1 = x_val
        Coord2 = y_val
        #print("coord = ", Coord1, Coord2)
        count+=1
        calc_fn(newmatrix, Coord1, Coord2)
        continue
 
    
print(manList)
print(found, x_val, y_val, "Manhattan = " + str(sum(manList) + count), newmatrix[x, y])

       
## if target not found, normalize
## The idea is that if you search a flat cell and its not the 
## target, then finding the target becomes harder because the remaining cells 
## have a higher false negative rate. 


## find highest probability:


#mpl.imshow(matrix,cmap='Greys',interpolation='nearest')

#print(manL)
mpl.imshow(newmatrix,cmap = cmap,interpolation='nearest')
mpl.show()
