
import json

import matplotlib.pyplot as plt
import seaborn as sns

f = open("genetics_data/tournaments/test_from_compose_t.json")
data = json.load(f)


def extract_point_tournament(tournament, subj_list):
    score = [data[tournament][s]["score"] for s in subj_list]
    # display_score_tournament(subj_list, score, tournament)
    mean = sum(score) / len(score)
    return mean

def display_score_tournament(x, y, tournament):
    fig, ax = plt.subplots(figsize=(12,10))
    ax.set_title(f"score of {tournament}")
    ax.set_xlabel("n° subj")
    ax.set_ylabel("score")
    sns.barplot(x=x, y=y)
    plt.show()

def extract_subject_list(tournament):
    subjects_str = list(data[tournament].keys())
    subjects_map = map(int, subjects_str)
    subjects = sorted(list(subjects_map))
    subjects = map(str, subjects)
    subjects = list(subjects)
    return subjects

def display_mean_scores(x, y):
    fig, ax = plt.subplots(figsize=(12,10))
    ax.set_title("mean score of all tournaments")
    ax.set_xlabel("tournament")
    ax.set_ylabel("scores")
    sns.barplot(x=x, y=y)
    plt.savefig("genetic/src/scripts/outputs/average_score_by_tournament.png")
    plt.show()

def pprint_tournament_list(t_list):
    str_list = list(map (lambda s : s.replace("tournament ", ""), t_list))
    int_list = list(map(int, str_list))
    return int_list

def mean_score_tournaments():
    mean_list = []
    tournament_list = list(data.keys())
    for trnmt in tournament_list:
        subjects = extract_subject_list(trnmt)
        mean_list.append(extract_point_tournament(trnmt, subjects))

    tournament_list = pprint_tournament_list(tournament_list)

    display_mean_scores(tournament_list, mean_list)

def extract_age_tournament(tournament, subj_list):
    age = [data[tournament][s]["birthGen"] for s in subj_list]
    #display_age_tournament(subj_list, age, tournament)
    mean = sum(age) / len(age)
    return mean

def display_age_tournament(x, y, tournament):
    fig, ax = plt.subplots(figsize=(12,10))
    ax.set_title(f"birthGen of {tournament}")
    ax.set_xlabel("n° subj")
    ax.set_ylabel("birthGen")
    sns.barplot(x=x, y=y)
    plt.show()

def display_mean_age(x, y):
    fig, ax = plt.subplots(figsize=(12,10))
    ax.set_title("mean birthGen of all tournaments")
    ax.set_xlabel("tournament")
    ax.set_ylabel("birthGens")
    sns.barplot(x=x, y=y)
    plt.savefig("genetic/src/scripts/outputs/average_birth_gen_by_tournament.png")
    plt.show()

def mean_age_tournaments():
    mean_list = []
    tournament_list = list(data.keys())
    for trnmt in tournament_list:
        subjects = extract_subject_list(trnmt)
        mean_list.append(extract_age_tournament(trnmt, subjects))

    tournament_list = pprint_tournament_list(tournament_list)

    display_mean_age(tournament_list, mean_list)

if __name__ == '__main__':

    mean_score_tournaments()
    mean_age_tournaments()




