import matplotlib.pyplot as plt
import seaborn as sb
import json

NB_GEN = 101

# colors =["black","red","coral","gold","chartreuse","seagreen","teal","aqua","steelblue","pink","navy"]

f = open("genetics_data\\tournaments\\HOPE2_t.json")
data = json.load(f)


if __name__ == '__main__':
    tournaments = list(data.keys())
    NB_TOURNAMENT = len(tournaments)
    genDict = [{} for _ in range(NB_GEN)] # nb de generation 

    
    for t in tournaments : 
        subjects = []
        for sub in data[t].keys():
            subjects.append(data[t][sub]) 

        print(t)
        maxbirthGen = 0
        for s in subjects: 
            if s["birthGen"] > maxbirthGen:
                maxbirthGen = s["birthGen"]

        meanScoreGen = [None for _ in range(maxbirthGen)]
        for i in range(maxbirthGen+1):
            mean = 0
            nbs  = 0
            for s in subjects: 
                if s["birthGen"] == i:
                    mean += s["score"]
                    nbs += 1 
                
            if nbs != 0 :
                genDict[i][t] = mean / nbs

    tournaments = sorted(tournaments)
    tournaments_int = sorted([int(tour.split(" ")[1]) for tour in tournaments])
    
    tournaments_int.remove(tournaments_int[0])
    tournaments_int.remove(tournaments_int[len(tournaments_int)-1])

    tournamentsAVG = [[] for _ in range(len(tournaments))]
    i = 0
    for t in tournaments_int : 
        for s in genDict : 
            if f"tournament {t}" in s.keys():
                tournamentsAVG[i].append(s[f"tournament {t}"])
            else: 
                tournamentsAVG[i].append(None)
        i += 1
    

    data_plt = []

    for i in range(NB_GEN): #gen
        tmp = []
        for j in range(NB_TOURNAMENT -2):
            tmp.append(tournamentsAVG[j][i])
        
        data_plt.append(tmp)


    
    
    generations = [i for i in range(NB_GEN)]
    for i in range(NB_GEN):
        print(data_plt[i])
        plt.plot(tournaments_int[1:], data_plt[i][1:], marker='o', label=generations[i])

    plt.legend(loc="best", title='generations')

    plt.show()
    