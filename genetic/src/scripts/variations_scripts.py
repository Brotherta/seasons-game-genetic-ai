import matplotlib.pyplot as plt
import seaborn as sb
import json

IT = [ 0.01,  0.02,  0.03,  0.04,  0.05,  0.06,0.07] #,0.08,0.09,0.1

PATH_LOAD = "genetics_data\\tournaments\\_MutRate_"
PATH_SAVE = "genetic/src/scripts/outputs/variation_MutRate_.png"

TITLE = 'Average score over generations'
X_LABEL = "Generations"
Y_LABEL = "Average score"
LEGEND_TITLE = 'Mutation Rate'


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

    return score_acc / nb_subjects


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
        for i in range(1, max_gen):
            scores_by_gen.append(mean(data, i))
        # print(scores_by_gen)
        all_var.append(scores_by_gen)

    plt.title(TITLE)
    plt.xlabel(X_LABEL)
    plt.ylabel(Y_LABEL)

    
    for i in range(len(IT)):
        plt.plot([i for i in range(1, max_gen)], all_var[i], label=IT[i])
    plt.legend(loc="upper right", title=LEGEND_TITLE)
    
    plt.savefig(PATH_SAVE)
    plt.show()