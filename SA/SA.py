import random
import sys
from numpy.random import randn, rand
from numpy import exp

def userInput():
    n_steps = input("Enter number of steps: ")
    stp_sz = input("Input step size (i.e 0.1): ")
    tmp_rule = input("Enter temperature rule. Options: \n1. linear reduction\n2. Geometric reduction\n3. Slow-Decrease Reduction\nEnter 1, 2, 3: ")
    upper_bound = input("Enter upper bound of objective domain: ")
    lower_bound = input("Enter lower bound of objective domain: ")
    obj_type = input("Enter Objective type. Options: 1. Explicit Function\n2. Custom Function/array\n Enter Choice: ")
    bounds = [[lower_bound, upper_bound]]
    return bounds, tmp_rule, stp_sz, n_steps, obj_type


# Example of an objective function for x^2.
def objective_function(candidate_idx):
    return candidate_idx[0]**2

# Example of how to use the objective custom.
# Parameters: 
#           - function_array: array of y values for the custom function
#           - candidate_idx: idx of the y value to return
# You can have multidimensional array's as long as you give a tuple of indicies or multiple indicies 
def objective_custom(function_array, candidate_idx):
    return function_array[candidate_idx]

def SA_function(objective, bounds, tmp_rule, n_steps, stp_sz,max_min_opt, init_temp):
    # get initial value 
    best_idx = random.randint(bounds[0][0], bounds[0][1])
    best_val = objective(best_idx)
    

    # set to current value and best
    curr_idx, curr_val = best_idx, best_val
    # iterate
    for i in range(n_steps):
        candidate_idx = curr_idx + randn(len(bounds)) * stp_sz
        candidate_val = objective(candidate_idx)
        if max_min_opt == 1:
            if candidate_val > best_val:
                best_idx, best_val = candidate_idx, candidate_val
        elif max_min_opt == 2:
            if candidate_val < best_val:
                best_idx, best_val = candidate_idx, candidate_val
        else:
            print("Max_Min Error! Max_Min rule not supported!")
            sys.exit()

        difference = candidate_val - curr_val
        if tmp_rule == 1:
            alpha = 0.5
            t = init_temp - alpha
        elif tmp_rule == 2:
            alpha = 2
            t = init_temp * alpha
        elif tmp_rule == 3:
            beta = 5
            t = t /(1+beta*t)
        else:
            print("Temperature Error! Temp rule not supported!")
            sys.exit() 

        tmp = exp(-difference/t)
        if difference < 0 or rand() < tmp:
			# store the new current point
	        curr_idx, curr_val = candidate_idx, candidate_val
    #return 
    return [best_idx, best_val]

def main():
    bounds, tmp_rule, stp_sz, n_steps, obj_type = userInput()
    if (obj_type == 1):
        ans = SA_function(objective_function,bounds, tmp_rule, stp_sz, n_steps)
    if (obj_type == 2):
        ans = SA_function(objective_custom,bounds, tmp_rule, stp_sz, n_steps)