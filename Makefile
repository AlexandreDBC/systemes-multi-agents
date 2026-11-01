# Le répertoire où les fichiers .class seront placés
BINDIR = bin

# Le chemin vers la librairie graphique gui.jar
GUI_JAR = lib/gui.jar

# Le chemin vers la librairie Junit
JUNIT_JAR=lib/junit-platform-console-standalone-6.0.1.jar

# Le classpath pour la compilation et l'exécution
CLASSPATH = $(BINDIR):$(GUI_JAR)

SRC_MAIN=$(shell find src/main -name '*.java')
SRC_TEST=$(shell find src/test -name '*.java')

all: compile

# Compiler tous les fichiers .java du projet
compile:
	@mkdir -p $(BINDIR)
	javac -d $(BINDIR) -classpath $(GUI_JAR) -sourcepath src $(SRC_MAIN)

# Lancer la simulation des boids
run_boids: compile
	java -classpath $(CLASSPATH) main.boids.TestBoids

# Lancer la simulation Conway
run_conway: compile
	java -classpath $(CLASSPATH) main.automatons.conway.TestConway

# Lancer la simulation Immigration
run_immigration: compile
	java -classpath $(CLASSPATH) main.automatons.immigration.TestImmigration

# Lancer la simulation Schelling
run_schelling: compile
	java -classpath $(CLASSPATH) main.automatons.schelling.TestSchelling

# Lancer la simulation des Balles
run_balls: compile
	java -classpath $(CLASSPATH) main.balls.TestBalls

# Lancer l'exemple Invader
run_invader: compile
	java -classpath $(CLASSPATH) main.invader.TestInvader

# Lancer la simulation des boids
run_boids: compile
	java -classpath $(CLASSPATH) main.boids.TestBoids

compileTests:
	mkdir -p $(BINDIR)
	rm -rf $(BINDIR)/test
	javac -d $(BINDIR) -classpath $(CLASSPATH):$(JUNIT_JAR) -sourcepath src $(SRC_MAIN) $(SRC_TEST)

runTests: compileTests
	java -jar $(JUNIT_JAR) execute --class-path bin:$(GUI_JAR) --scan-class-path

tests: runTests


# Nettoyer le projet (supprimer les fichiers compilés)
clean:
	rm -rf $(BINDIR)

# documentation
doc:
	doxygen doxyfile

.PHONY: all compile clean doc run_boids run_conway run_immigration run_schelling run_balls run_invader
