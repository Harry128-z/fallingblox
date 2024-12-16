from typing import Dict, List, Tuple, Set
import numpy as np

class ReglesPersonnalisees:
    def __init__(self, limInfVivant:int=2, limSuppVivant:int=3, limInfMort:int=3, limSuppMort:int=3):
        self.limInfVivant = limInfVivant
        self.limSuppVivant = limSuppVivant
        self.limInfMort = limInfMort
        self.limSuppMort = limSuppMort

class Cellule:
    def __init__(self, y:int, x:int):
        self.vivant = False
        self.y = y
        self.x = x
        self.voisins: Set[Cellule] = set()

    def set_state(self, state: bool):
        self.vivant = state

    def flip_state(self):
        self.vivant = not self.vivant

    def removeVoisin(self, voisin):
        self.voisins.remove(voisin)

    def addVoisin(self, voisin):
        self.voisins.add(voisin)

class Grill:
    def __init__(self, nombrelignes:int, nombrecolonnes:int, pacman:bool, regles:ReglesPersonnalisees = ReglesPersonnalisees()):
        self.nombrelignes = nombrelignes
        self.nombrecolonnes = nombrecolonnes
        self.Cellules: Dict[Tuple[int, int], Cellule] = {}
        self.Vivants: Set[Cellule] = set()
        self.pacman = pacman
        self.regles = regles
        self.init_grill()
        self.init_tous_voisins_cell()

    def flipVivant(self, y, x):
        self.Cellules[(y,x)].flip_state()
        self.updatelistvivants()

    def updatelistvivants(self):
        self.Vivants: Set[Cellule] = set()
        for (i, j), cell in self.Cellules.items():
            if cell.vivant:
                self.Vivants.add(cell)
        
    def get_voisins(self, cellule):
        """
        Retourne les voisins d'une cellule en prenant en compte l'effet Pac-Man si activé.
        """
        voisins = set()
        max_x, max_y = self.nombrecolonnes - 1, self.nombrelignes - 1  # Limites de la grille

        for dx in [-1, 0, 1]:     # Déplacements horizontaux : gauche, centre, droite
            for dy in [-1, 0, 1]: # Déplacements verticaux : haut, centre, bas
                if dx == 0 and dy == 0:
                    continue      # Ignore la cellule elle-même
                # Coordonnées potentielles du voisin
                x, y = cellule.x + dx, cellule.y + dy

                # Si mode Pac-Man, enroule les coordonnées
                if self.pacman:
                    x = (x + self.nombrecolonnes) % self.nombrecolonnes
                    y = (y + self.nombrelignes) % self.nombrelignes
                else:
                    # Ignore les voisins en dehors de la grille si Pac-Man est désactivé
                    if not (0 <= x <= max_x and 0 <= y <= max_y):
                        continue
                
                # Ajoute le voisin valide
                voisins.add(self.Cellules[(y, x)])
        return voisins

    def init_grill(self):
        for i in range(self.nombrelignes):
            for j in range(self.nombrecolonnes):
                self.Cellules[(i, j)] = Cellule(i, j)

    def init_tous_voisins_cell(self):
        for (i,j), cell in self.Cellules.items():
            cell = self.Cellules[(i, j)]
            for voisin in self.get_voisins(cellule=cell):
                cell.addVoisin(self.Cellules[(voisin.y, voisin.x)])
    
    def initialisationAleatoire(self):
        init_grid = np.random.rand(self.nombrelignes, self.nombrecolonnes) > 0.5
        for (i, j), cell in self.Cellules.items():
            if init_grid[i, j]:
                cell.set_state(True)
        self.updatelistvivants()
            
    def mettreToutesCellulesaMorts(self):
        self.init_grill()
        self.init_tous_voisins_cell()
        self.updatelistvivants()

    def update(self):
        nextVivants: Set[Cellule] = set()
        morts_verifies: Set[Cellule] = set()

        for cell in self.Vivants:
            nb_voisins_vivants = sum(1 for voisin in cell.voisins if voisin.vivant)
            if self.regles.limInfVivant <= nb_voisins_vivants <= self.regles.limSuppVivant:
                nextVivants.add(self.Cellules[(cell.y, cell.x)])
            morts_verifies.update([self.Cellules[(voisin.y, voisin.x)] for voisin in cell.voisins if not voisin.vivant])

        for cell in morts_verifies:
            nb_voisins_vivants = sum(1 for voisin in cell.voisins if voisin.vivant)
            if self.regles.limInfMort <= nb_voisins_vivants <= self.regles.limSuppMort:
                nextVivants.add(self.Cellules[(cell.y, cell.x)])

        # Apply the updates
        new_vivants = nextVivants - self.Vivants
        new_morts = self.Vivants - nextVivants
        for cell in new_vivants:
            self.Cellules[(cell.y, cell.x)].set_state(True)
        for cell in new_morts:
            self.Cellules[(cell.y, cell.x)].set_state(False)

        # Update current set of live cells
        self.Vivants = nextVivants

        # print("\n\n\n ******* NEW UPDATE ****** \n\n\n")
        # for cell in self.Cellules.values():
        #     print(f"Cellule({cell.y, cell.x}) :  {[(voisin.y, voisin.x) for voisin in cell.voisins]} \n\n")



