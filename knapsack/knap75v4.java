import java.util.*;
import java.lang.*;
public class knap75v4{
   static Random r = new Random();
   public static void main(String[] args){
      
      //declare all vars, arrays 
      int popSize,items,weightLim, valueLim, totalWeightLimit,fittestIndex,genCount, safety;
      int[][] generation, newGen;
      int[] weight,dollars, dollarSum, weightSum;
      double  bestFitIndex, bestFit1,bestFit2, bestFitValueCurrent;
      int[] bestGene;
      int[] costWeightArray,costDollarArray; 
      double[] fitnessArray;      
      //give values to essential vars
      final double EPSILON = .0000001;
      bestFit1 = Integer.MIN_VALUE;
      bestFit2 = Integer.MIN_VALUE+1;
      totalWeightLimit = 15;
      popSize = 30;
      items = 5;
      weightLim = 15;
      valueLim = 15;  
      genCount = 0;
       safety = 0;
      double newMax = 0;
      
      
      //generate a random array of weights and values 
 
      costWeightArray = costWeight(items,weightLim);  
      costDollarArray = costDollars(items,valueLim);  
      //generate and evaluate the initial population 
      generation = populationGeneration(popSize, items);
      dollars = dollarTotal(generation, costDollarArray); 
      weight =  weigthTotal(generation, costWeightArray); 
      fitnessArray = genFittnessArray(dollars, weight, totalWeightLimit);  
      fittestIndex =  fittestIn(fitnessArray);
      bestFitValueCurrent = fittestVal( fitnessArray,  fittestIndex);
      System.out.println(Arrays.toString(costWeightArray));
      System.out.println(Arrays.toString(costDollarArray));
      
      //genetic portion of the algorithm
      while(Math.abs(bestFit1 - bestFit2)> EPSILON){  //exit condition 
         generation = mutation(fitnessArray, generation,  items,  bestFitValueCurrent);
         dollars = dollarTotal(generation, costDollarArray); 
         weight =  weigthTotal(generation, costWeightArray);
         fitnessArray = genFittnessArray(dollars, weight, totalWeightLimit);
         fittestIndex =  fittestIn(fitnessArray);
         bestFitValueCurrent = fitnessArray[fittestIndex];
         if(bestFitValueCurrent > bestFit1){//calculating the first and second max 
            bestFit2 = bestFit1;
            bestFit1 = bestFitValueCurrent;
         }
         else if(bestFitValueCurrent > bestFit2 && bestFitValueCurrent != bestFit1){//calculating the first and second max 
            bestFit2 = bestFitValueCurrent;  
         } 
         else if(bestFitValueCurrent < bestFit2){//begin exit condition
            
            for(int i =0; i< 1000; i++){
               generation = mutation(fitnessArray, generation,  items,  bestFitValueCurrent);
               dollars = dollarTotal(generation, costDollarArray); 
               weight =  weigthTotal(generation, costWeightArray);
               fitnessArray = genFittnessArray(dollars, weight, totalWeightLimit);
               fittestIndex =  fittestIn(fitnessArray);
               bestFitValueCurrent = fitnessArray[fittestIndex];
               if(bestFitValueCurrent>newMax){
                  newMax = bestFitValueCurrent;
                  
               }        
            }
            if(newMax > bestFit1){
               bestFit2 = bestFit1;
               bestFit1 = newMax;
               
               
            }
            if(newMax<bestFit1 && newMax > bestFit2){
               bestFit2 = newMax;
            }
            if(bestFitValueCurrent == bestFit1){  //exit condition
               ++safety;            
               break;
            }
               if(safety > 5){ //saftey to allow for more accuracy
                  break;
               }
         }
         
         genCount++;  
      }
      System.out.print("Solution");
      System.out.printf(" found in %d\n", genCount);
      System.out.printf("%.2f\n", bestFitValueCurrent);
      bestGene = grabbedArray(generation, fittestIndex);
      System.out.print("Best Gene: "); 
      System.out.println(Arrays.toString(bestGene));
   }
   
   
   //INITIALIZE POPULATION-----------------------------------------------------------------------------------------
   public static int[][] populationGeneration(int pop, int num_items){
      int[][] generation = new int[pop][num_items];
      
      for(int i = 0; i< pop; i++){
         for(int j = 0; j < num_items; j++){
            generation[i][j] = (int)(Math.random() * 2); //creates random values for the genes 
         }
      }
      return generation;
   }
   //COST FUNCTION---------------------------------------------------------------------------------------------//comment out for trivial demonstration
   public static int[] costWeight(int num_items,int weightLimit){
      int[] weightArray = new int[num_items];
      
      for(int i = 0; i<weightArray.length;i++){
         weightArray[i] = r.nextInt(weightLimit-1)+1;  //assigns a random weight value for an item within a range
      }
      return weightArray;
   }
//cost array part two
   public static int[] costDollars(int num_items,int valueLimit){
      int[] dollarsArray = new int[num_items];
      
      for(int i = 0; i<dollarsArray.length;i++){
         dollarsArray[i] = r.nextInt(valueLimit-1)+1;   //assigns a random number within the range of the value limit
      }
      return dollarsArray;
   }
   // CALCULATION OF FITNESS---------------------------------------------------------------------------------------
   public static int[] dollarTotal(int[][]genome, int[] costD){
      int dollarsTotal;
      int[] arrayDtotal = new int[genome.length];
      for(int i = 0; i < genome.length; i++){
         dollarsTotal = 0;
         for(int j = 0; j < genome[i].length; j++){
            if(genome[i][j] == 1){
               dollarsTotal = dollarsTotal + costD[j];  
            }
            
         }
         
         arrayDtotal[i]= dollarsTotal;
      }
      return arrayDtotal;
   } 
//above calculates an array whose indicies are filled with the value total
   public static int[] weigthTotal(int[][]genome, int[] costW){
      int weightTotal;
      int[] arrayWtotal = new int[genome.length];
      for(int i = 0; i < genome.length; i++){
         weightTotal = 0;
         for(int j = 0; j < genome[i].length; j++){
            if(genome[i][j] == 1){
               weightTotal = weightTotal + costW[j];  
            }
            else{
               weightTotal = weightTotal + 0;  
            }
         }
         
         arrayWtotal[i]= weightTotal;
      }
      return arrayWtotal;
   }
   
   
   //above calculates an array whose indicies are filled with the weight total
   public static double[] genFittnessArray(int[] arrayDtotal, int[] arrayWtotal, int totalWeightLim){
      double[] fitnessArray = new double[arrayDtotal.length];
      for(int i = 0; i <arrayDtotal.length; i++){
         if((arrayWtotal[i] != 0) && (arrayWtotal[i] <= totalWeightLim)){//if the weight total is not over the weight limit calculate a cost to weight ratio
            fitnessArray[i] = ((double)arrayDtotal[i]); // this gives us a cost to density ratio  
         }
         
      }
      return fitnessArray;
   }
   
   
   //obtain best value
   public static double fittestVal(double[] fitArray, int index){
      double fittestValue= 0;
      for(int i = 0; i<fitArray.length;i++){
         if(i == index){
            fittestValue = fitArray[i];
            
         }
      }
      return fittestValue; 
   }
   
   
   //obtain best index
   public static int fittestIn(double[] fitArray){
      double fittestValue = 0;
      int fittestIndex = 0;
      for(int i =0; i<fitArray.length;i++){
         if(fitArray[i]> fittestValue){
            fittestValue = fitArray[i];
            fittestIndex = i;
         }
      }
      return fittestIndex; 
      
   }
   
   
   //grab the best row
   public static int[] grabbedArray(int[][]genome, int index){
      return genome[index];
   }
   //do mutation and crossover
   public static int[][] mutation(double[] fitArray, int[][]genome, int items, double fittestValue){
      int[][] newGeneration  = new int[genome.length][items];
      
      
      
      
      
      
      
      for(int i = 0; i<fitArray.length; i++){
         //MUTATION PORTION OF THE METHOD
         if(fitArray[i] >= .75*fittestValue){
            if(fitArray[i]%2 == 0){  
               for(int j  = 0; j<genome[i].length; j++){
                  newGeneration[i][j] = (int)(Math.random() * 2);
               }
            }
         }
         
         //CROSSOVER PORTION OF THE METHOD
         else if( fitArray[i] >=.75*fittestValue){ 
            if(i%2 == 0){
               if(i != fitArray.length){
                  for(int k = i+1; k<fitArray.length;k++){
                     if(fitArray[k] > .25*fittestValue && fitArray[k] <= .75*fittestValue){
                        int crossPoint = r.nextInt(genome[i].length);
                        for(int m = 0; m<genome[0].length; m++){
                           if(m<crossPoint){
                              newGeneration[i][m] = genome[i][m]; 
                           }
                           else if(m > crossPoint){
                              newGeneration[i][m] = genome[k][m];
                           }
                           else{
                              newGeneration[i][m] = 0;  
                           }
                        }
                     }
                  }
                  
               }
               
               else if( i == fitArray.length){
                  //take first row in crossover range and perform cross over with it 
                  for(int n= 0; n<fitArray.length;n++){
                     if(n != i){
                        if(fitArray[n] > .25*fittestValue && fitArray[n] <= .75*fittestValue){
                           int crossPoint1  = r.nextInt(genome[i].length);
                           for(int p = 0; p<genome[0].length; p++){
                              if(p<crossPoint1){
                                 newGeneration[i][p] = genome[i][p]; 
                              }
                              else if(p > crossPoint1){
                                 newGeneration[i][p] = genome[n][p];
                              }
                              else{
                                 newGeneration[i][p] = 0;  
                              }
                           }
                        }
                     }
                  }
               }
               
            }
         }
         //CARRY THROUGH PORTION OF THE METHOD
         else if(fitArray[i] >= .75*fittestValue){
            if(i%2 != 0){
               for(int l =0; l<genome[i].length; l++){
                  newGeneration[i][l] = genome[i][l]; 
               }
            }
         }
      }
//      for (int i = 0; i < genome.length; i++) {
//         for (int j = 0; j < genome[i].length; j++) {
//            System.out.print(genome[i][j] + ", ");
//         }
//         System.out.println();
//      }
//      System.out.println("----------");
//      for (int i = 0; i < newGeneration.length; i++) {
//         for (int j = 0; j < newGeneration[i].length; j++) {
//            System.out.print(newGeneration[i][j] + ", ");
//         }
//         System.out.println();
//      }
      
      return newGeneration;
   }   
}