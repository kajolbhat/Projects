# moving target extra credit
#advanced

## this is the same code as the other advanced except with the small changes added for moving target
## please refer to the other file for more details about the implementation
import numpy as np
import queue
import sys
import matplotlib.pyplot as mpl
import math as math
import matplotlib as m
import random
import random

n =30
global ran_5
ran_5 = 0
colors = [mpl.cm.ocean(1),mpl.cm.binary(0),mpl.cm.binary(0.4),
              mpl.cm.binary(0.7)]
cmap = m.colors.ListedColormap(colors)


def create():
    # create matrix
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
    #print(numBlocks)
    matrix = np.zeros((n,n))

    cmap= m.colors.ListedColormap(colors)

    ## 6,12,13,14
    num_arr = []
    for i in range(numBlocks):
        num_arr.append(1)
        num_arr.append(2)
        num_arr.append(3)
        num_arr.append(4)

    #print(num_arr)
    random.shuffle(num_arr)
    #print(num_arr)

    counter = 0
    l=0
    x = n*n
    while l < x:
        for i in range(n):
            for j in range(n):
                #print("l value = ", l, "n value", i,j)
                newmatrix[i,j] = num_arr[l]
                l+=1

    #print(newmatrix)
    return newmatrix

newmatrix = create()
x = np.random.randint(0,n-1)
y = np.random.randint(0,n-1)

print("This is the target:",(x,y))

ran_first_time = True

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
#for i in range(n):
    #for j in range(n):
        #flat

#print(newmatrix) 

 
def ManhattanDistance(a1,b1,a2,b2):
    total =  abs(a1 - a2) + abs(b1 - b2) 
    #print("1= ", a1, b1, "2=", a2,b2, total)
    return total
## if coordinate is visited equals 1 else equals 0
##Manhattan = 0

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



def calc_fn(newmatrix, Coord1, Coord2):
    #flat
    if (newmatrix[Coord1, Coord2] == 2):
        #changed to 1-p
        #prob_matrix[i] *= .1
        prob_matrix[Coord1, Coord2] *= .9
        #print("flat,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #forest
    elif (newmatrix[Coord1, Coord2] == 1):
        #changed to 1-p
        #prob_matrix[i]  *= .7
        prob_matrix[Coord1, Coord2] *= .3
        #print("firest,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #hilly 
    elif (newmatrix[Coord1, Coord2] == 3):
        #changed to 1-p
        #prob_matrix[i] *=  .3
        prob_matrix[Coord1, Coord2] *= .7
        #print("hill,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #cave 
    ## the zeros are a temp fix
    elif (newmatrix[Coord1, Coord2] == 4):
        #changed to 1-p
        #prob_matrix[i] *= .9
        prob_matrix[Coord1, Coord2] *= .1
        #print("cave,  = ", prob_matrix[Coord1, Coord2])
        curr = prob_matrix[Coord1, Coord2]
        #tup_list.append(((a,b), curr, ManhattanDistance(a, b, Coord1, Coord2)))
    #print("after method", prob_matrix)
    
manList = []


## move the actual target
def move_target(neighbors_list):
    
    index = np.random.randint(0, len(neighbors_list))
    i = neighbors_list[index]
    
    ## update the probability based on false negative rates
    calc_fn(newmatrix, i[0], i[1])
    
    
    ## add everything together and check if needs to be normalized
    tot= 0
    for a in range(n):
        for b in range(n):
            tot += prob_matrix[a,b]
    
    ## normalize
    if tot != 1.0:
        for a in range(n):
            for b in range(n):
                if tot != 0:
                    prob_matrix[a,b] = float(prob_matrix[a,b]) / (float(tot))
    
    return i[0], i[1]

#open cell is a method to look a that the probability value and decides the current value to look at 
#based on the probability matrix, our agent will decide where to go 
def open_cell(newmatrofix, prob_matrix,Coord1,Coord2):
    tup_list = [] 
    ran_5 = 0
    
    #we append coordinates and probaility to a list 
    #this will later determine what has the highest chance of containing the target
    if within_5 == False and (ran_first_time == False):
        print("is not within 5")
        tup_list = []
        for a in range(n):
            for b in range(n):
                tup_list.append(((a,b), prob_matrix[a,b]))
        tup_list.sort(key=lambda x: x[1], reverse = True)
        i = tup_list[0][0]
    
    else:
        ran_5+=1
        print("is within 5")
        tup_list = within_5_list
        print("within 5 list inside the loop", tup_list)
    ## find maximum value in the mrix and open that 
        if (len(within_5_list) > 0):
            i = tup_list[0]
        else:
            sys.exit()
    print("i printed = ", i)
    
    ## check neighbors
    neighbor1 = neighbors(prob_matrix, n, i)
    
    #checking neighbors of neighbors to have a short distance 
    #this will...
    for q in neighbor1:
        neighbor = neighbors(prob_matrix, n, q)
    
    
    #...determine whether there is a better cell to visit, once going through the neighbors of neighbors
    check_n = []
    for neigh in neighbor:
        print("checking neighbors")
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
Coord1 = 0
Coord2 = 0
count = 1
within_5 = False



within_5_list = []
while found == False:  
    ran_first_time = False
    ## exit if more than 5 away too many times
    if (ran_5 > 10):
        print("program not likely to find target - EXIT")
        break
    coord_return = open_cell(newmatrix, prob_matrix,Coord1, Coord2)
    x_val = coord_return[0]
    print("this is the x val return", x_val)
    y_val = coord_return[1]
    print("this is the y val return", y_val)
    ## dictionary of the false negative rates for each
    false_neg = {}
    false_neg[1] = .7
    false_neg[2] = .1
    false_neg[3] = .3
    false_neg[4] = .9
    
    chance = random.uniform(0,1)
    terrain = newmatrix[x_val, y_val]
    
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
        neighbors_list = neighbors(newmatrix, n, (x,y))
        x,y = move_target(neighbors_list)
        print("moved target = ", x, y)
        Coord1 = x_val
        Coord2 = y_val
        distance = ManhattanDistance(x,y,Coord1,Coord2)
        print("distance = ", distance)
        if (distance <= 5):
            within_5 = True
            
            ## append to list if within 5 manhattan distance away
            for u in range(6):
                    if (Coord1+u < n-1):
                        within_5_list.append((Coord1+u,Coord2))
                    if (Coord1-u >= 0):
                        within_5_list.append((Coord1-u,Coord2))
                    if (Coord2+u < n-1):
                        within_5_list.append((Coord1,Coord2+u))
                    if (Coord2-u >= 0):
                        within_5_list.append((Coord1,Coord2-u))
            
            print(within_5_list)
        #print("coord = ", Coord1, Coord2)
            count+=1
            calc_fn(newmatrix, Coord1, Coord2)
        else:
            count+=1
            calc_fn(newmatrix, Coord1, Coord2)
        
 
    
print("gotten to this point")
print(found, x_val, y_val, "Manhattan = " + str(sum(manList) + count), newmatrix[x, y])

       
## if target not found, normalize
## The idea is that if you search a flat cell and its not the 
## target, then finding the target becomes harder because the remaining cells 
## have a higher false negative rate. 




#print(manL)
mpl.imshow(newmatrix,cmap = cmap,interpolation='nearest')
mpl.show()
