# Variables #
JC = javac
JCFLAGS = -cp "./mariaDB/mariadb-java-client-3.4.1.jar:build" -d build -classpath build -sourcepath src -implicit:none

JVM = java
JVMFLAGS = -cp "./mariaDB/mariadb-java-client-3.4.1.jar:build"

JAR_FILE = DEV31.jar
MAIN_CLASS = fr.iutfbleau.Main

## Chemins vers les fichiers sources

#chemin main
BUILD_DIR = build/fr/iutfbleau
SRC_DIR = src/fr/iutfbleau

#chemin vues
SRC_DIR_VUE = src/fr/iutfbleau/Vues
BUILD_DIR_VUE = build/fr/iutfbleau/Vues

#chemin models
SRC_DIR_MODEL = src/fr/iutfbleau/Models
BUILD_DIR_MODEL = build/fr/iutfbleau/Models

#chemin vues
SRC_DIR_CONTROLLER = src/fr/iutfbleau/Controllers
BUILD_DIR_CONTROLLER = build/fr/iutfbleau/Controllers


# chemin ressources #
RES_DIR = res
BUILD_DIR_RES = build/fr/iutfbleau/res


# dépendances #
$(BUILD_DIR_RES)/New_Game.jpg: $(RES_DIR)/New_Game.jpg
	@mkdir -p $(BUILD_DIR_RES)
	cp $(RES_DIR)/New_Game.jpg $(BUILD_DIR_RES)


$(BUILD_DIR_VUE)/Poche.class: $(SRC_DIR_VUE)/Poche.java
	@mkdir -p $(BUILD_DIR_VUE)
	$(JC) $(JCFLAGS) $(SRC_DIR_VUE)/Poche.java

$(BUILD_DIR_VUE)/Tuile.class: $(SRC_DIR_VUE)/Tuile.java $(BUILD_DIR_VUE)/Poche.class
	@mkdir -p $(BUILD_DIR_VUE)
	$(JC) $(JCFLAGS) $(SRC_DIR_VUE)/Tuile.java

$(BUILD_DIR_CONTROLLER)/TerrainController.class: $(SRC_DIR_CONTROLLER)/TerrainController.java $(BUILD_DIR_VUE)/Tuile.class
	@mkdir -p $(BUILD_DIR_CONTROLLER)
	$(JC) $(JCFLAGS) $(SRC_DIR_CONTROLLER)/TerrainController.java

$(BUILD_DIR_VUE)/Terrain.class: $(SRC_DIR_VUE)/Terrain.java $(BUILD_DIR_VUE)/Tuile.class $(BUILD_DIR_CONTROLLER)/TerrainController.class $(BUILD_DIR_VUE)/Fin.class $(BUILD_DIR_VUE)/Poche.class
	@mkdir -p $(BUILD_DIR_VUE)
	$(JC) $(JCFLAGS) $(SRC_DIR_VUE)/Terrain.java
	
$(BUILD_DIR_VUE)/Fin.class: $(SRC_DIR_VUE)/Fin.java
	@mkdir -p $(BUILD_DIR_VUE)
	$(JC) $(JCFLAGS) $(SRC_DIR_VUE)/Fin.java

$(BUILD_DIR)/Main.class: $(SRC_DIR)/Main.java $(BUILD_DIR_MODEL)/Modele.class $(BUILD_DIR_VUE)/MenuJeu.class $(BUILD_DIR_CONTROLLER)/Ferme.class
	@mkdir -p $(BUILD_DIR)
	$(JC) $(JCFLAGS) $(SRC_DIR)/Main.java

# SI ça foire c'est ici #
$(BUILD_DIR_MODEL)/Modele.class: $(SRC_DIR_MODEL)/Modele.java $(BUILD_DIR_MODEL)/DatabaseConnection.class
	@mkdir -p $(BUILD_DIR_MODEL)
	$(JC) $(JCFLAGS) $(SRC_DIR_MODEL)/Modele.java

$(BUILD_DIR_MODEL)/DatabaseConnection.class: $(SRC_DIR_MODEL)/DatabaseConnection.java
	@mkdir -p $(BUILD_DIR_MODEL)
	$(JC) $(JCFLAGS) $(SRC_DIR_MODEL)/DatabaseConnection.java

$(BUILD_DIR_VUE)/MenuJeu.class: $(SRC_DIR_VUE)/MenuJeu.java $(BUILD_DIR_MODEL)/Modele.class $(BUILD_DIR_CONTROLLER)/SerieSelectionListener.class $(BUILD_DIR_CONTROLLER)/JouerButtonListener.class  $(BUILD_DIR_VUE)/Terrain.class $(BUILD_DIR_RES)/New_Game.jpg
	mkdir -p $(BUILD_DIR_VUE)
	$(JC) $(JCFLAGS) $(SRC_DIR_VUE)/MenuJeu.java 

$(BUILD_DIR_CONTROLLER)/SerieSelectionListener.class: $(SRC_DIR_CONTROLLER)/SerieSelectionListener.java
	mkdir -p $(BUILD_DIR_CONTROLLER)
	$(JC) $(JCFLAGS) $(SRC_DIR_CONTROLLER)/SerieSelectionListener.java

$(BUILD_DIR_CONTROLLER)/JouerButtonListener.class: $(SRC_DIR_CONTROLLER)/JouerButtonListener.java $(BUILD_DIR_MODEL)/Modele.class
	mkdir -p $(BUILD_DIR_CONTROLLER)
	$(JC) $(JCFLAGS) $(SRC_DIR_CONTROLLER)/JouerButtonListener.java

$(BUILD_DIR_CONTROLLER)/Ferme.class: $(SRC_DIR_CONTROLLER)/Ferme.java $(BUILD_DIR_MODEL)/Modele.class
	mkdir -p $(BUILD_DIR_CONTROLLER)
	$(JC) $(JCFLAGS) $(SRC_DIR_CONTROLLER)/Ferme.java



jar: $(BUILD_DIR)/Main.class $(BUILD_DIR_MODEL)/Modele.class $(BUILD_DIR_VUE)/MenuJeu.class
	jar -cvfe $(JAR_FILE) $(MAIN_CLASS) -C build fr -C mariaDB .


# Lancement #

run:       
	$(JVM) $(JVMFLAGS) -jar $(JAR_FILE)

clean:
	-rm -rf build
	-rm -f $(JAR_FILE)

mrproper: clean

### BUTS FACTICES ###
.PHONY: run clean mrproper copy-resources
