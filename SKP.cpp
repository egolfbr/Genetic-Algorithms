#include <stdlib.h>
#include <vector>
#include <random>
#include <stdio.h>
#include <iostream>
#include <type_traits>

static std::vector< std::vector <int> > genPop(int popSize, int numItems){
    std::vector<std::vector<int> > population;
    for (int i = 0; i < popSize; i++) {
        std::vector <int> row;
        for (int j = 0 ; j <numItems; j++) {
            row.push_back(rand() % 2);
        }
        population.push_back(row);
    }
    return population;
}

static int geneWeight(std::vector <int> gene, std::vector <int> weights, int numItems){
    int gene_weight = 0;
    for (int i = 0; i < numItems; i++) {
        if (gene[i] == 1) {
            gene_weight += weights[i];
        }
    }
    return gene_weight;
}
static int geneValue(std::vector <int> gene, std::vector <int> values, int numItems){
    int gene_value = 0;
    for (int i = 0; i < numItems; i++) {
        if (gene[i] == 1) {
            gene_value += values[i];
        }
    }
    return gene_value;
}
static std::vector <int> gen_itemWeights(int numItems, int itemWeightLim){
    std::vector <int> weights;
    for (int i = 0; i < numItems; i++) {
        weights.push_back(rand() % itemWeightLim + 1);
    }
    return weights;
}


static std::vector <int> gen_itemValues(int numItems, int itemValLimit){
    std::vector <int> vals;
    for (int i = 0; i < numItems; i++) {
        vals.push_back(rand() % itemValLimit + 1);
    }
    return vals;
}
static std::vector <int> genFitArray(std::vector <int> geneWeight, std::vector <int> geneVals, int knapLimit){
    std::vector <int> fittnessArray;
    for (int i = 0; i < geneVals.size(); i++) {
        if ((geneWeight[i] != 0) && (geneWeight[i] <= knapLimit)){
            fittnessArray.push_back(geneVals[i]);
        }
    }
}
// static int fittestVal(){

// }
// static int fittestIdx(){

// }
// static int fittestGene(){

// }
// static int** gen_new_pop(){
    //temp change
    //hello

// }
static void printMat(std::vector <std::vector <int> > mat) {
    int rows = mat.size();
    int cols = mat[0].size();
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::cout << mat[i][j];
        }
        std::cout <<"\n";

    }
}
static void printVec (std::vector <int> vec) {
    int elements = vec.size();
    for (int i = 0; i< elements; i++) {
        std::cout << vec[i] << " ";
    }
    std::cout << "\n";
}
int main(int argc, char* argv[]){

    std::vector <std::vector <int> > pop;
    // Declare all variables used
    int popSize = 10;
    int numItems = 5;
    int knapLimit = 50;
    int itemWeightLim = 10;
    int itemValueLim = 20;
    int genCount = 0;
    int safety = 0; 
    int newMax = 0;
    srand(time(NULL));
    // declare array's to be used
    std::vector <int> itemWeights, itemValues, geneWeight, geneValue, bestGene, fitnessArray;
    pop = genPop(popSize,numItems);
    itemValues = gen_itemValues(numItems,itemValueLim);
    
    printVec(itemValues);
}