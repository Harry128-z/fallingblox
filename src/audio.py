# src.audio.py

import pygame

class AudioManager:
    def __init__(self, music_volume=0.5, effects_volume=0.5):
        pygame.mixer.init()
        self.music_volume = music_volume
        self.effects_volume = effects_volume
        self.mute = True

        # Charger les sons
        self.background_music = pygame.mixer.Sound("./resources/audios/play.wav")
        self.toggle_pacman_sound = pygame.mixer.Sound("./resources/audios/activated_button.wav")
        self.pause_sound = pygame.mixer.Sound("./resources/audios/pause.wav")
        self.reset_sound = pygame.mixer.Sound("./resources/audios/reset.wav")

        # Ajuster le volume des sons
        self.background_music.set_volume(self.music_volume)
        self.toggle_pacman_sound.set_volume(self.effects_volume)
        self.pause_sound.set_volume(self.effects_volume)
        self.reset_sound.set_volume(self.effects_volume)

    def play_background_music(self):
        self.background_music.play(loops=-1)  # Boucle infinie pour la musique de fond
        self.mute = False

    def stop_background_music(self):
        self.background_music.stop()
        self.mute = True

    def play_sound(self, sound_type):
        if sound_type == "toggle_pacman":
            self.toggle_pacman_sound.play()
        elif sound_type == "pause":
            self.pause_sound.play()
        elif sound_type == "reset":
            self.reset_sound.play()

    def adjust_music_volume(self, volume_change):
        self.music_volume = max(0, min(1, self.music_volume + volume_change))
        self.background_music.set_volume(self.music_volume)

    def adjust_effects_volume(self, volume_change):
        self.effects_volume = max(0, min(1, self.effects_volume + volume_change))
        self.toggle_pacman_sound.set_volume(self.effects_volume)
