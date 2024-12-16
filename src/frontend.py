import time

from src.backend import Grill, ReglesPersonnalisees
from src.audio import AudioManager
from collections import deque


import pygame
from typing import List, Tuple, Dict
import json


def draw_navbar(screen, font, largeur):
    # Couleur de la navbar et du texte
    navbar_color = (30, 30, 30)  # Gris foncé
    text_color = (255, 255, 255)  # Blanc

    # Créer une surface pour la navbar
    navbar_height = 30
    navbar_surface = pygame.Surface((largeur, navbar_height))
    navbar_surface.fill(navbar_color)

    navbar_surface_1 = pygame.Surface((largeur, navbar_height))
    navbar_surface_1.fill(navbar_color)

    # Définir les raccourcis clavier et leurs descriptions
    shortcuts = [
        "(R) : Remplir aléatoirement",
        "(C) : Vider la grille",
        "(SPACE) : Mettre en Pause",
        "(P) : Activer/désactiver Pacman",
        "(M) : Arrêter la musique",
        "(+) : Zoom In",
        "(-) : Zoom out",
        "(Z) : + nombre Sup voisins Vivant",
        "(S) : - nombre Sup voisins Vivant",
        "(A) : + nombre Inf voisins Vivant",
        "(Q) : nombre Inf voisins Vivant",
        "(E) : + nombre Inf voisins Mort",
        "(D) : - nombre Inf voisins Mort",
        "(R) : + nombre Sup voisins Mort",
        "(F) : - nombre Sup voisins Mort",
        "(H) : Affiche le graphique",
        "(N) : Fait une itération",
        "(CTRL+S) : Enregistre l'état de la grille",
        "(CTRL+O) : charger une grille enregistré",

    ]
    # Calculate width and max number of shortcuts per line
    max_width = largeur - 20  # Padding on each side
    current_line = ""
    lines = []
    padding = 10
    space_between_lines = 5

    for shortcut in shortcuts:
        # Measure width of the current line with the next shortcut added
        test_line = current_line + shortcut
        text_width, _ = font.size(test_line)

        # If adding this shortcut exceeds the max width, finalize current line
        if text_width > max_width:
            lines.append(current_line)
            current_line = shortcut
        else:
            current_line = test_line

    # Add the last line if it has remaining shortcuts
    if current_line:
        lines.append(current_line)

    # Draw each line in the navbar area at the top
    y = padding
    for line in lines:
        text_surface = font.render(line, True, (255, 255, 255))
        screen.blit(text_surface, (padding, y))
        y += font.get_height() + space_between_lines
    return screen

# Fonction pour afficher les regles actuelles
def afficher_regles(screen, regles:ReglesPersonnalisees, width:int , height:int, pacman_mode:bool):
    font = pygame.font.Font(None, 24)
    pacman_mode_text = "ACTIVATED " if pacman_mode else "DEACTIVATED"
    texte = f"Survie: [{regles.limInfVivant}, {regles.limSuppVivant}], Naissance: [{regles.limInfMort}, {regles.limSuppMort}], Pacman mode : {pacman_mode_text}"
    texte_surface = font.render(texte, True, (255, 255, 255))
    screen.blit(texte_surface, ((width // 2)+265 - texte_surface.get_width() // 2, height-50))

# Fonction pour dessiner la grille
def draw_grid(grille:Grill, screen:pygame, CELL_SIZE:int, 
              VIVANT_COLOR:Tuple[int], MORT_COLOR:Tuple[int], GRID_COLOR:Tuple[int],
              offset_x=0, offset_y=0, zoom_level=1):
    """_summary_

    Args:
        grille (Grill): _description_
    """
    for position, cellule in grille.Cellules.items():
        color = VIVANT_COLOR if grille.Cellules[(cellule.y, cellule.x)].vivant else MORT_COLOR
        pygame.draw.rect(screen, color, (cellule.x * CELL_SIZE + offset_x,
                                         cellule.y * CELL_SIZE + offset_y, 
                                         CELL_SIZE*zoom_level, 
                                         CELL_SIZE*zoom_level))
        pygame.draw.rect(screen, GRID_COLOR, (cellule.x * CELL_SIZE + offset_x, 
                                              cellule.y * CELL_SIZE + offset_y, 
                                              CELL_SIZE*zoom_level, 
                                              CELL_SIZE*zoom_level), 1)

# Fonction pour afficher le texte au centre de l'écran
def display_text(screen, text:str, size:int, color:Tuple[int], width:int, height:int, y_offset=0):
    font = pygame.font.Font(None, size)
    text_surface = font.render(text, True, color)
    text_rect = text_surface.get_rect(center=((width // 2)+265, height // 2 + y_offset))
    screen.blit(text_surface, text_rect)


def draw_graph(screen, historique, width:int , height:int, y, title = ""):
    x = screen.get_width() - 300 + 20
    pygame.draw.rect(screen, (0, 0, 0), (x, y, width, height), 2)

    if title:
        font = pygame.font.Font(None, 24)
        text = font.render(title, True, (0, 0, 0))
        screen.blit(text, (x + 20, y - 50))

    # Calcule les valeurs min et max de l'historique pour normaliser les données
    min_val = int(min(historique))
    max_val = int(max(historique))

    # Dessine chaque point du graphique
    points = []
    if len(historique) > 1:
        for i, value in enumerate(historique):
            # Calcul des positions x et y pour les points
            x_pos = x + (i * (width / (len(historique) - 1)))
            y_pos = y + height - ((value - min_val) / (max_val - min_val)) * height
            points.append((x_pos, y_pos))

        # Dessine les lignes reliant les points
        pygame.draw.lines(screen, (0, 0, 255), False, points, 2)

    # Affiche le nombre d'itérations sous le graphique
    font = pygame.font.Font(None, 24)
    text = font.render(str(len(historique)), True, (0, 0, 0))
    screen.blit(text, (x, y + height + 10))

    # Affiche la valeur maximale en haut à droite du graphique
    text = font.render(f"Max: {max_val}", True, (0, 0, 0))
    screen.blit(text, (x + width - text.get_width() - 10, y - 30))

    # Affiche la valeur minimale en bas à droite du graphique
    text = font.render(f"Min: {min_val}", True, (0, 0, 0))
    screen.blit(text, (x + width - text.get_width() - 10, y + height + 10))

def handle_pan(offset_x, offset_y, direction):
    step = 10  # La distance de déplacement pour chaque action
    if direction == "left":
        offset_x += step
    elif direction == "right":
        offset_x -= step
    elif direction == "up":
        offset_y += step
    elif direction == "down":
        offset_y -= step
    return offset_x, offset_y
    

def handle_zoom(zoom_level, zoom_in):
    if zoom_in:
        zoom_level = min(zoom_level + 0.1, 2)  # Limite du zoom à x2
    else:
        zoom_level = max(zoom_level - 0.1, 0.5)  # Dézoom limité à x0.5
    cell_size = max(1, int(10 * zoom_level))  # Ajuste la taille des cellules
    return zoom_level, cell_size


# Structures complexes
GLIDER = [(0, 1), (1, 2), (2, 0), (2, 1), (2, 2)]  # Ex: structure planeur

def place_structure(grille:Grill, structure, position):
    base_x, base_y = position
    for dx, dy in structure:
        x, y = base_x + dx, base_y + dy
        if 0 <= x < grille.nombrecolonnes and 0 <= y < grille.nombrelignes:
            grille.Cellules[(x, y)].set_state(True)
    return grille


def display(GRID_SIZE_X = 50, GRID_SIZE_Y = 50,
            VIVANT_COLOR = (0, 255, 0), MORT_COLOR = (0, 0, 0), 
            GRID_COLOR = (50, 50, 50)):
    """Display the Grill and the animation

    Args:
        GRID_SIZE_X (int, optional): Nombre de cellules suivant la colonne ou la largeur. Defaults to 50.
        GRID_SIZE_Y (int, optional): Nombre de cellules suivant la ligne ou la hauteur. Defaults to 50.
        CELL_SIZE (int, optional): Taille d'une cellule en pixels. Defaults to 10.
        VIVANT_COLOR (tuple, optional):  Couleur pour les cellules vivantes. Defaults to (0, 255, 0).
        MORT_COLOR (tuple, optional): Couleur pour les cellules mortes. Defaults to (0, 0, 0).
        GRID_COLOR (tuple, optional): Couleur des lignes de la grille. Defaults to (50, 50, 50).
    """
    WHITE = (255, 255, 255)

    # Initialisation de Pygame
    pygame.init()
    # Paramètres de l'interface
    max_screen_WIDTH, max_screen_HEIGHT = 800, 800
    max_cell_width = max_screen_WIDTH // GRID_SIZE_X
    max_cell_height = max_screen_HEIGHT // GRID_SIZE_Y

    CELL_SIZE = min(max_cell_width, max_cell_height)
    WIDTH, HEIGHT = CELL_SIZE * GRID_SIZE_X, CELL_SIZE * GRID_SIZE_Y

    screen = pygame.display.set_mode((WIDTH+265, HEIGHT))
    
    pygame.display.set_caption("Game of Life - Interface")

    # Création de la grille initiale (toutes les cellules mortes)
    pacman_mode = True
    regles = ReglesPersonnalisees(limInfVivant=2, limSuppVivant=3, limInfMort=3, limSuppMort=3)
    grid = Grill(nombrecolonnes=GRID_SIZE_X, 
                 nombrelignes=GRID_SIZE_Y, 
                 pacman=pacman_mode,
                 regles=regles)
    
    grid.initialisationAleatoire()

    # Boucle principale
    running = True

    iteration_count = 0
    paused = False

    graphDisplay = False

    historique = deque(maxlen=100)

    tempsL = deque(maxlen=100)

    clock = pygame.time.Clock()

    audio_manager = AudioManager()

    font = pygame.font.SysFont("Arial", 16)
    audio_manager.play_background_music()

    offset_x = 0 
    offset_y = 0
    zoom_level = 1
    while running:
        screen.fill(GRID_COLOR)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    paused = not paused
                    display_text(screen=screen, text="PAUSE" if paused else "START", 
                                 size=50, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=0)
                    audio_manager.play_sound("pause")
                    if paused : 
                        audio_manager.stop_background_music()
                    else:
                        audio_manager.play_background_music()
                    pygame.display.flip()
                    clock.tick(1)

                elif event.key == pygame.K_c:
                    iteration_count = 0
                    grid.mettreToutesCellulesaMorts()
                    audio_manager.play_sound("reset")
                    display_text(screen=screen, text="CLEANING", 
                                 size=50, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=0)
                    pygame.display.flip()
                    clock.tick(1)

                elif event.key == pygame.K_r:
                    grid.initialisationAleatoire()
                    paused = False
                    iteration_count = 0
                    audio_manager.play_sound("reset")
                    display_text(screen=screen, text="RESET", 
                                 size=50, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=0)
                    pygame.display.flip()
                    clock.tick(1)

                elif event.key == pygame.K_p:
                    pacman_mode = not pacman_mode
                    grid.pacman = pacman_mode
                    grid.init_tous_voisins_cell()
                    audio_manager.play_sound("toggle_pacman")
                    clock.tick(1)

                elif event.key == pygame.K_m:
                    if audio_manager.mute and not paused:
                        audio_manager.play_background_music()
                    else:
                        audio_manager.stop_background_music()

                elif event.key == pygame.K_PLUS:      # Zoom avant
                    zoom_level, CELL_SIZE = handle_zoom(zoom_level=zoom_level, zoom_in=True)

                elif event.key == pygame.K_MINUS:   # Zoom arrière
                    zoom_level, CELL_SIZE = handle_zoom(zoom_level=zoom_level, zoom_in=False)

                elif event.key == pygame.K_LEFT:    # Déplacement gauche
                    offset_x, offset_y = handle_pan(offset_x, offset_y, "left")

                elif event.key == pygame.K_RIGHT:   # Déplacement droite
                    offset_x, offset_y = handle_pan(offset_x, offset_y,"right")

                elif event.key == pygame.K_UP:      # Déplacement haut
                    offset_x, offset_y = handle_pan(offset_x, offset_y,"up")

                elif event.key == pygame.K_DOWN:    # Déplacement bas
                    offset_x, offset_y = handle_pan(offset_x, offset_y,"down")

                elif event.key == pygame.K_g:       # Place un glider à une position donnée
                    grid = place_structure(grille=grid, structure=GLIDER, position=(10, 10))  # Exemple de position fixe

                elif event.key == pygame.K_n:
                    time_start = time.time_ns()
                    grid.update()
                    time_end = time.time_ns()
                    tempsL.append((time_end - time_start)/1000000)
                    historique.append(len(grid.Vivants))
                    iteration_count += 1

                # Raccourcis pour ajuster les règles de survie
                elif event.key == pygame.K_q:
                    regles.limInfVivant = max(0, regles.limInfVivant - 1)
                    grid.regles = regles

                elif event.key == pygame.K_a:
                    regles.limInfVivant += 1
                    grid.regles = regles

                elif event.key == pygame.K_s:
                    regles.limSuppVivant = max(regles.limInfVivant, regles.limSuppVivant - 1)
                    grid.regles = regles

                elif event.key == pygame.K_z:
                    regles.limSuppVivant += 1
                    grid.regles = regles
                # Raccourcis pour ajuster les regles de naissance

                elif event.key == pygame.K_d:
                    regles.limInfMort = max(0, regles.limInfMort - 1)
                    grid.regles = regles

                elif event.key == pygame.K_e:
                    regles.limInfMort += 1
                    grid.regles = regles

                elif event.key == pygame.K_f:
                    regles.limSuppMort = max(regles.limInfMort, regles.limSuppMort - 1)
                    grid.regles = regles

                elif event.key == pygame.K_r:
                    regles.limSuppMort += 1
                    grid.regles = regles

                elif event.key == pygame.K_h:
                    graphDisplay = not graphDisplay
                    if graphDisplay :
                        screen = pygame.display.set_mode((WIDTH+300+265, HEIGHT))
                    else :
                        screen = pygame.display.set_mode((WIDTH+265, HEIGHT))

                if event.key == pygame.K_s and pygame.key.get_mods() & pygame.KMOD_CTRL:
                    sauvegarder_grille(grille=grid, filename="./logs/log.json")
                    paused = True if not paused else paused
                    display_text(screen=screen, text="GRID SAVED", 
                                 size=50, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=0)
                    audio_manager.play_sound("pause")
                    pygame.display.flip()
                    clock.tick(1)
                    
                if event.key == pygame.K_o and pygame.key.get_mods() & pygame.KMOD_CTRL:
                    charger_grille(grille=grid, filename="./logs/log.json")
                    paused = True if not paused else paused
                    display_text(screen=screen, text="GRID LOAD", 
                                 size=50, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=0)
                    audio_manager.play_sound("pause")
                    pygame.display.flip()
                    clock.tick(1)

            elif event.type == pygame.MOUSEBUTTONDOWN and paused:
                # Permet de modifier l'état d'une cellule en cliquant
                x, y = event.pos
                x -= 265
                grid_x, grid_y = (x // CELL_SIZE) , (y // CELL_SIZE)
                grid.flipVivant(grid_y, grid_x)
                pygame.display.flip()

        if not paused:
            time_start = time.time_ns()
            grid.update()
            time_end = time.time_ns()
            tempsL.append((time_end - time_start) / 1000000)
            historique.append(len(grid.Vivants))
            iteration_count += 1

            
        draw_grid(grille=grid, screen=screen, 
                  CELL_SIZE=CELL_SIZE, VIVANT_COLOR=VIVANT_COLOR, 
                  MORT_COLOR=MORT_COLOR, GRID_COLOR=GRID_COLOR,
                  offset_x=offset_x+265, offset_y=offset_y, zoom_level=zoom_level)

        if graphDisplay :
            draw_graph(screen, historique, WIDTH / 3, HEIGHT / 3, 70, "Nombre de cellule vivants")
            draw_graph(screen, tempsL, WIDTH / 3, HEIGHT / 3, (HEIGHT / 3) + 190, "Temps en MS")

        pygame.display.flip()

        # Affichage du compteur d'itérations
        display_text(screen=screen, text=f"Iterations: {iteration_count}", 
                     size=30, color=WHITE, width=WIDTH, height=HEIGHT, y_offset=-HEIGHT // 2 + HEIGHT - 80)
        afficher_regles(screen, regles, WIDTH, HEIGHT, pacman_mode)
        
        draw_navbar(screen, font, GRID_SIZE_X * CELL_SIZE/100)

        pygame.display.flip()

        clock.tick(1)

    pygame.quit()

def sauvegarder_grille(grille:Grill, filename):
    """
    Sauvegarde la grille actuelle dans un fichier JSON.
    """
    data = [[int(grille.Cellules[(row, col)].vivant) for col in range(grille.nombrecolonnes)] for row in range(grille.nombrelignes)]
    with open(filename, 'w') as file:
        json.dump(data, file)
    print(f"Grille sauvegardée dans {filename}")

def charger_grille(grille:Grill, filename):
    """
    Charge une grille depuis un fichier JSON et met à jour l’état de la grille.
    """
    try:
        with open(filename, 'r') as file:
            data = json.load(file)
            for x, row in enumerate(data):
                for y, cell_state in enumerate(row):
                    if cell_state == 1:
                        grille.Cellules[(x, y)].set_state(True)
                    else:
                        grille.Cellules[(x, y)].set_state(False)
        print(f"Grille chargée depuis {filename}")
    except FileNotFoundError:
        print(f"Fichier {filename} introuvable")
    except json.JSONDecodeError:
        print("Erreur de lecture du fichier JSON")