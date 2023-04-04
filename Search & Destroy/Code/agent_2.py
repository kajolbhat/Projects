#agent2
import numpy as np
import queue
import sys
import matplotlib.pyplot as mpl
import math as math
import matplotlib as m
import random
from collections import deque
from sklearn import preprocessing
import random

n = 50

## creating the map based on the following colors
## green is for forest, white is for flat, gray is for hilly, and dark gray is for cave
colors = [mpl.cm.ocean(1),mpl.cm.binary(0),mpl.cm.binary(0.4),
              mpl.cm.binary(0.7)]
cmap = m.colors.ListedColormap(colors)
def create():
    # create matrix
    p = 0.25

    ### making the grid so that there are 25% of each cell
    greycounter = 0
    lightgreycounter = 0
    greencounter = 0
    blackcounter = 0

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


    ## randomly shuffling so that the distribution is random each time we run it
    random.shuffle(num_arr)


    
    ### creating "newmatrix" which refers to all of the terrains, and each terrain is represented using numerical values:
    ### flat =2.0
    ### hilly = 1.0
    ### forest = 3.0
    ### cave = 4.0
    counter = 0
    l=0
    x = n*n
    while l < x:
        for i in range(n):
            for j in range(n):
                newmatrix[i,j] = num_arr[l]
                l+=1

    return newmatrix

newmatrix = create()

## randomly assigning the target
x = np.random.randint(0,n-1)
y = np.random.randint(0,n-1)
print("This is the target:",(x,y))



## go through the array and set probability to 1/(total number of cells)
prob_matrix = np.zeros((n,n))
## inital probability when all have equal chance
init_prob = 1/(n*n)
for i in range(n):
    for j in range(n):
        prob_matrix[i,j] = 1/(n*n)




def neighbors(arr, d, coordinate):
    neighborList= []
#The following method is to check the neighbors of a given path
#The possible neighbors are right,left,up,down
### used when deciding whether to search current cell or any of its 4 neighbors
    x = int(coordinate[0])
    y = int(coordinate[1])
    counter = int(0)


    if ((x+1 < d and ((y)<d))):
        neighborList.append((x+1,y))
        
    if ((x-1>=0 and y<d)):
        neighborList.append((x-1,y))

    if ((x<d) and (y+1)<d):
        neighborList.append((x,y+1))

    if ((x < d) and (y-1)>=0):
        neighborList.append((x,y-1))

    return neighborList


## calculate manhattan distance between 2 points
def ManhattanDistance(a1,b1,a2,b2):
    total =  abs(a1 - a2) + abs(b1 - b2) 
    return total



## list containing all manhattan distances calculated based on cells searched
## we output the total sum of this
manList = []
def open_cell(newmatrofix, prob_matrix,Coord1,Coord2):
    tup_list = [] 
    
    for a in range(n):
        for b in range(n):
            tup_list.append(((a,b), prob_matrix[a,b]))
    tup_list.sort(key=lambda x: x[1], reverse= True)
        
                #print("already visited")
    #tup_list.sort(key=lambda x: x[1], reverse = True)  
    ## find maximum value in the mrix and open that 
    i = tup_list[0][0]
    ## calculate Manhattan
    #print("current value", i)
    #j = tup_list[0][0]
    
    ## check neighbors
    neighbor = neighbors(prob_matrix, n, i)
    
    check_n = []
    for neigh in neighbor:
        check_n.append((neigh, prob_matrix[neigh], ManhattanDistance(neigh[0], neigh[1], Coord1, Coord2)))
    check_n.append((i, prob_matrix[i], ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    check_n.sort(key=lambda x: (-x[1], x[2]))
    
    i= check_n[0][0]
    manList.append(ManhattanDistance(i[0], i[1], Coord1, Coord2))

   ## print(tup_list)
    
    #visited[i] = 1
    #print(visited)
    #print("before method", prob_matrix)
    #print(tup_list)
    #print("this is new", newmatrix[i,j])
    #print("original,  = ", prob_matrix[i])
    #print("this is new matrix " , newmatrix[i])
    if (newmatrix[i] == 2):
        #changed to 1-p
        #prob_matrix[i] *= .1
        prob_matrix[i] *= .9
        #print("flat,  = ", prob_matrix[i])
        curr = prob_matrix[i]
        tup_list.append(((i[0], i[1]), curr, ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    #forest
    elif (newmatrix[i] == 1):
        #changed to 1-p
        #prob_matrix[i]  *= .7
        prob_matrix[i] *= .3
        #print("firest,  = ", prob_matrix[i])
        curr = prob_matrix[i]
        tup_list.append(((i[0], i[1]), curr, ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    #hilly 
    elif (newmatrix[i] == 3):
        #changed to 1-p
        #prob_matrix[i] *=  .3
        prob_matrix[i] *= .7
        #print("hill,  = ", prob_matrix[i])
        curr = prob_matrix[i]
        tup_list.append(((i[0], i[1]), curr, ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    #cave 
    ## the zeros are a temp fix
    elif (newmatrix[i] == 4):
        #changed to 1-p
        #prob_matrix[i] *= .9
        prob_matrix[i] *= .1
        #print("cave,  = ", prob_matrix[i])
        curr = prob_matrix[i]
        tup_list.append(((i[0], i[1]), curr, ManhattanDistance(i[0], i[1], Coord1, Coord2)))
    #print("after method", prob_matrix)
    
    
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
                if tot!=0:
                    prob_matrix[a,b] = float(prob_matrix[a,b]) / float(tot)
        
                
    #print(tup_list)
    tup_list.clear()
    return i

found = False
x_val = 0
y_val = 0
#print("this is found", found)
Coord1 = np.random.randint(n-1)
Coord2 = np.random.randint(n-1)
count =1
 ## 1= green -> forest
    ## 0 = white -> flat
    ## .4 = gray -> hilly
    ## .7 = dark gray -> cave
while found == False:  
    x_val, y_val = open_cell(newmatrix, prob_matrix,Coord1, Coord2)
    #open_cell(newmatrix, prob_matrix)
    ## if equals target
    false_neg = {}
    false_neg[1] = .7
    false_neg[2] = .1
    false_neg[3] = .3
    false_neg[4] = .9
    
    chance = random.uniform(0,1)
    terrain = newmatrix[x_val, y_val]
    if (x_val == x and y_val ==y):
        if (chance > false_neg[terrain]):
            found = True
            count+=1
            print("true found")
    else:
        Coord1 = x_val
        Coord2 = y_val
        count+=1
        continue

print(found, x_val, y_val, "manhattan" + str(sum(manList) + count), str(newmatrix[x,y]))

       
## if target not found, normalize
## The idea is that if you search a flat cell and its not the 
## target, then finding the target becomes harder because the remaining cells 
## have a higher false negative rate. 


## find highest probability:
print(manList)
mpl.imshow(newmatrix,cmap = cmap,interpolation='nearest')
mpl.show()
