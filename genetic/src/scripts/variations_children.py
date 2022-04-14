import matplotlib.pyplot as plt
import seaborn as sb
import json

IT = [0.05, 0.15, 0.25,0.35,0.45]

PATH_LOAD = "genetics_data/tournaments/variationChildrate/_nbChild_"
PATH_SAVE = "genetic/src/scripts/avg_score_over_child_rate_variation.png"

TITLE = 'Average score over children rate variation'
X_LABEL = "Generations"
Y_LABEL = "Average score"
LEGEND_TITLE = 'children rate'


def get_older_gen(data):
    max_gen = 0
    for tournament in data:
        for subject in data[tournament]:
            max_gen = max(max_gen, data[tournament][subject]["birthGen"])

    return max_gen

def mean(data, gen):
    score_acc = 0
    nb_subjects = 0
    for tournament in data:
        for subject in data[tournament]:
            if data[tournament][subject]["birthGen"] == gen:
                score_acc += data[tournament][subject]["score"] / data[tournament][subject]["nbMatch"]
                nb_subjects += 1

    return score_acc/ nb_subjects


if __name__ == "__main__":

    max_gen = 0

    all_var = []
    for it in IT:
        
        data = []
        with open(f"{PATH_LOAD}{it}_t.json", 'r') as f:
            data = json.load(f)
            f.flush()
            f.close()

        max_gen = get_older_gen(data)

        scores_by_gen = []
        for i in range(max_gen):
            scores_by_gen.append(mean(data, i))

        all_var.append(scores_by_gen)

    plt.title(TITLE)
    plt.xlabel(X_LABEL)
    plt.ylabel(Y_LABEL)

    for i in range(len(IT)):
            print(all_var[i])
    print(max_gen)

    for i in range(len(IT)):
        plt.plot([i for i in range(max_gen)], all_var[i], label=IT[i])
    plt.legend(loc="best", title=LEGEND_TITLE)
    
    plt.savefig(PATH_SAVE)
    plt.show()