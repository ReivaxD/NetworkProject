# Projet-Reseau

Pour lancer ce programme, lancer le fichier simulateur/reso/examples/dv_routing/Demo.java
(ne pas oublier de compiler avant de lancer si modifications il y a eu)

-> main de Demo.java est la méthode principale :

  -> String filename= "reso/examples/dv_routing/demo-graph.txt" permet de choisir le modèle à instancier
  Pour revenir au fichier de base (demo-graph.txt étant l'exercice 1) remplacer reso/examples/dv_routing/demo-graph.txt par reso/data/topology.txt
  
  -> scheduler.run() lance l'algo avec le router A comme récepteur du 1er signal (via setupRoutingProtocol(network, "A") )
  -> scheduler.runUntil(0.100) lance l'algo avec un timing max avant arret (en cas de récursion infinie)
  
Exercice 2 presque fini, tout est la en commentaire dans main, seulement, il reste à trouver le changement d'arc provoquant une boucle infinie
Note : boucle infinie n'est pas vraiment infinie, c'est le concept vu en cours
