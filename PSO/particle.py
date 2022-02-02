class Particle:
    def __init__(self,x,y,i,Xvel,Yvel):
        self.xcoord = x
        self.ycoord = y
        self.init_pos = (x,y)
        self.position = (x,y)
        self.bestPosition = (x,y)
        self.Xvelocity = Xvel
        self.Yvelocity = Yvel
        self.ID = i


    def updateBestPosition(self,x,y):
        self.bestPosition = (x,y)
    
    def updatePosition(self,x,y):
        self.position = (x,y)

    def updateXvelocity(self,vel):

    
    def printPos(self):
        print("Particle Num: " + str(self.ID) + " particle position: (" + str(self.xcoord) + ", " + str(self.ycoord)+")\n")
