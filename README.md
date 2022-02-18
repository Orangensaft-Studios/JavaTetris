<!-- README file mainly for GitHub -->
# JavaTetris 
#### ein SEW Projekt von 
### [Severin](https://github.com/einsev) und [Roman](https://github.com/Ixpiria)
#### im Jahr 2022 | 2CI

## ❓ Was ist JavaTetris ##
JavaTetris ist ein Klon des beliebten Retrospiels "[Tetris](https://de.wikipedia.org/wiki/Tetris)",
welches man auf [tetris.com](https://tetris.com) kostenlos spielen kann. Jedoch wird dieses Programm mit verschiedenen
Modi und Statistiküberscihten erweitert, reizt mit Leaderboards und wird von Hintergrundmusik musikalisch untermalt.

## 📔 Pflichtenheft
Das Pflichtenheft findet man hier

## 📄 GIT
Repository am Schul-Git-Server:&nbsp;`21_2ci/public/projekt02_JavaTetris` <br>
Repository auf GitHub: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;`https://github.com/EinSev/JavaTetris`
<br>
<br>
**in IntelliJ**
<br>
#### Klonen:
```
# IntelliJ > File > New > Project from Version Control
URL: git@netzwerktechnik.htl.rennweg.at:21_2ci/public/projekt02_JavaTetris
```

#### Commit
```
git add <Files die geändert wurden>
git commit -m "<Änderungsnachricht>"
```

#### Pull
```
git pull
```

#### Push (auf beide Repos): <br>
***Vorbereitung:***
```
git remote add all git@netzwerktechnik.htl.rennweg.at:21_2ci/public/projekt02_JavaTetris
git remote set-url --add --push all https://github.com/einsev/JavaTetris.git
git remote set-url --add --push all git@netzwerktechnik.htl.rennweg.at:21_2ci/public/projekt02_JavaTetris
```

***Push***
```
git push all master

# Alias erstellen:
git config --global alias.pall 'push all master'
# dann Pushen mit
git pall
```
