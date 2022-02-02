import random 
import math 
from particle import Particle


def main(): 
    numParticles = 100 
    lower_bound = 0
    upper_bound = 100000
    particles = []
    swarmBestPos = (0,0)
    for i in range(numParticles):
        initX = random.randrange(lower_bound,upper_bound)
        initY = random.randrange(lower_bound,upper_bound)
        initVel = random.randrange(-abs(upper_bound-lower_bound),abs(upper_bound-lower_bound))
        particles.append(Particle(initX,initY,i,initVel))
    

    




main()