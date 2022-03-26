import random 
import sys

def user_input():
    print("0-1 Knapsack GA.\nMode 0 is random parameters no user input needed.\nMode 1 allows user to choose parameters.")
    mode = input("Enter Mode: ")
    if int(mode) == 1:
        popSize = input("Enter population size: ")
        numItems = input("Enter Number of items: ")
        knapLimit = input("Input max weight for knapsack: ")
        weightLimit = input("Input max weight per item: ")
        valueLimit = input("Input max value per item: ")
    else:
        popSize = random.randrange(100,300)
        numItems = random.randrange(5,10)
        knapLimit = random.randrange(50,100)
        weightLimit = random.randrange(5,30)
        valueLimit = random.randrange(5,50)
    paramList = [int(popSize),int(numItems),int(knapLimit),int(weightLimit),int(valueLimit)]
    return paramList

def gen_weight_array(population, itemWeights):
    weightArray = []
    for row in population:
        rowCost = 0
        for col in row:
            rowCost += itemWeights[row.index(col)]
        weightArray.append(rowCost)

    return weightArray

def gen_value_array(population, itemValues):
    valueArray = []
    for row in population:
        rowValue = 0
        for col in row:
            rowValue += itemValues[row.index(col)]
        valueArray.append(rowValue)

    return valueArray

def gen_fitness_array(valueArray, weightArray, knapLimit):
    fittnessArray = []
    for i in weightArray:
        if (i != 0) and (i <= knapLimit):
            idx = weightArray.index(i)
            fittnessArray.append(valueArray[idx])

    return fittnessArray

def get_best(fittnessArray, pop): 
    val = max(fittnessArray)
    idx = fittnessArray.index(val)
    gene = pop[idx]
    return val, idx, gene


def crossover(r1, r2):
    xpoint = random.randrange(1,len(r1)-1)
    r3 = []

    for i in range(len(r1)):
        if i < xpoint:
            r3.append(r1[i])
        else:
            r3.append(r2[i])
    assert len(r3) == len(r2),"xover length does not match"
    return r3

def mutate(row):
    res = []
    for i in row:
        num = random.randrange(0,2)
        
        if num == 1:
            if i == 0:
                res.append(0)
            else: 
                res.append(1)
        else:
            res.append(i)
   

    return res

def create_new_pop(fittnessArray, population, numItems, best):
    new_pop = []

    for i in fittnessArray:
        if i >= 0.75 * best:
            idx = fittnessArray.index(i)
            res = mutate(population[idx])
            new_pop.append(res)
        elif i < 0.75 * best and i > 0.50*best:
            if fittnessArray.index(i) != len(fittnessArray)-1:
                idx = fittnessArray.index(i)
                idx2 = idx+1
                res = crossover(population[idx], population[idx2])
                new_pop.append(res)
            else:
                idx = fittnessArray.index(i)
                new_pop.append(population[idx])
    diff = len(population) - len(new_pop)

    # we need to add rows
    if diff != 0:
        for i in range(diff):
            row = []
            for j in range(numItems):
                row.append(random.randrange(0,2))
            new_pop.append(row)
    assert len(new_pop) == len(population), "population sizes do not match!"
    return new_pop


def main():
    paramList = user_input()
    knapLimit = paramList[2]
    #Generate an 2 item arrays both of length numItems that declare each items weight and value
    itemWeights = []
    itemValues = []
    numItems = paramList[1]
    for i in range(numItems):
        itemWeights.append(random.randrange(1,paramList[3]+1)) # append paramList[1] random numbers
        itemValues.append(random.randrange(0,paramList[4]+1))

    #generate a 2D list of dimension paramList[0]-by-paramList[1] (rows-by-cols) with random 1 or 0 as elements
    population = []
    
    for i in range(paramList[0]):
        row = []
        for j in range(paramList[1]):
            row.append(random.randrange(0,2))
        population.append(row)
    
    valArray = gen_value_array(population,itemValues)
    weightArray = gen_weight_array(population, itemWeights)
    fitArray = gen_fitness_array(valArray,weightArray,knapLimit)
    bestValcur, bestIdxCur, bestGeneCur = get_best(fitArray,population)
    bestFit2 = 0
    bestFit1 = 1
    newMax = 0
    safety = 0
    while( abs(bestFit1 - bestFit2 > 0.001)):

        # Create new population 
        population = create_new_pop(fitArray, population, numItems, bestValcur)
        valArray = gen_value_array(population,itemValues)
        weightArray = gen_weight_array(population, itemWeights)
        fitArray = gen_fitness_array(valArray,weightArray,knapLimit)
        bestValcur,bestIdxCur,bestGeneCur = get_best(fitArray,population)
        if bestValcur > bestFit1:
            bestFit2 = bestFit1
            bestFit1 = bestValcur
        elif bestValcur < bestFit1 and bestValcur > bestFit2:
            bestFit2 = bestValcur
        elif bestValcur < bestFit2:
            for j in range(1000):
                population = create_new_pop(fitArray, population, numItems, bestValcur)
                valArray = gen_value_array(population,itemValues)
                weightArray = gen_weight_array(population, itemWeights)
                fitArray = gen_fitness_array(valArray,weightArray,knapLimit)
                bestValcur,bestIdxCur,bestGeneCur = get_best(fitArray,population)
                if bestValcur > bestFit1:
                    bestFit2 = bestFit1
                    bestFit1 = bestValcur
                i = i + 1
            
        if bestValcur == bestFit1:
            safety = safety + 1
        if safety == 5:
            break
        i = i + 1
        if i > 1000000:
            print("Exiting due to infinite loop possibility!")
            sys.exit()
    
    print("Best gene found in ",i," iterations")
    print("Value: ", bestFit1)
    print("Gene: ", bestGeneCur)
    print("index: ", bestIdxCur) 


if __name__ == "main":
    main()