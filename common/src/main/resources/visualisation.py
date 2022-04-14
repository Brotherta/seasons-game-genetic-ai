import json
import matplotlib.pyplot as plt

f = open("../../../../testTournament.json")

data = json.load(f)
figure, axis = plt.subplots(2, 1)
subjects = []
for s in data["tournament 1"]:
    subjects.append(s)

score = [data["tournament 1"][s]["score"] for s in subjects]
age = [data["tournament 1"][s]["age"] for s in subjects]
print(score)
print(age)
axis[0].bar(subjects,score)

axis[0].set_xlabel("n° subject")
axis[0].set_ylabel("score")
axis[0].set_title('visualisation des scores des ia')

axis[1].bar(subjects,age)
axis[1].set_xlabel("n° subject")
axis[1].set_title('age des IA')


plt.show()
