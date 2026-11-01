# 🤖 Object-Oriented Simulation of Multi-Agent Systems

<p>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit" />
  <img src="https://img.shields.io/badge/Doxygen-Documentation-4285F4?style=for-the-badge" alt="Doxygen" />
</p>

> *Java simulation engine exploring emergent behaviors through cellular automata (Conway, Immigration) and multi-agent systems (Schelling, Boids), supported by comprehensive JUnit tests and Doxygen documentation.*

> 📚 **Further Reading:** For more detailed insights, two PDF documents are available at the root of the project:
> * `Documentation des choix.pdf` : Detailed explanations of the architectural and design choices.
> * `Documentation utilisateur.pdf` : Complete user manual for running and interacting with the simulations.

## 1. Project Overview

Developed as part of the Object-Oriented Programming (OOP) course at Ensimag, this Java application provides a graphical simulation of various multi-agent systems. The focus is on a modular and extensible design, applying fundamental OOP principles such as inheritance, abstraction, and encapsulation.

The application simulates two main families of systems:
* **Cellular Automata:** Systems where agents are positioned on a fixed grid and evolve based on the state of their neighbors (Conway's Game of Life, Immigration Game, Schelling's Segregation Model).
* **Swarm Systems (Boids):** Systems where agents move freely in a 2D space, with their behavior governed by local interaction rules (cohesion, alignment, separation).

## 2. Architecture and Design

The architecture was designed to be as generic as possible, making it easy to add new simulations with minimal code duplication.

#### **Simulation Core: `GUISimulator` and `Simulable`**
The interaction between the simulation logic and the provided graphical interface (`gui.jar`) is handled by the `Simulable` interface. Any simulation class implements this interface, providing `next()` (to advance by one time step) and `restart()` (to reset the initial state) methods. This allows the `GUISimulator` to drive any simulation in a completely agnostic manner.

#### **Cellular Automata Management (Package `main.automatons.grid`)**
* **`Grid` and `Cell`:** Manage the grid data structure and the state of each cell.
* **`EvolutionRules` (Abstract Class):** Defines the contract that any game rule must follow, specifically the `computeNextCellState()` method.
* **`GridSimulator`:** This simulator takes an `EvolutionRules` instance as a parameter. It orchestrates the simulation (cell updates, rendering) without knowing the specific rules of the game it is running.

#### **Boids System (Package `main.boids`)**
* Boids behave based on three classic rules: **cohesion, alignment, and separation**.
* **`Boid`:** Represents an individual agent. Its `update()` method calculates the forces based on the three behavioral rules.
* **`Boids`:** Represents a group of boids sharing the same parameters (color, speed, perception range, etc.).
* **`BoidsGroupEvent`:** At regular intervals, this event asks each boid in a group to update its state, then reschedules itself for the next time step.

## 3. File and Folder Structure

The source code is organized into packages reflecting the architecture described above.

```text
.
├── lib/
│   └── gui.jar                   # Provided graphical library
├── Makefile                      # To compile, run, and clean the project
├── readme.md                     # This file
└── src/
    ├── main.balls/               # Simple bouncing balls simulation
    ├── main.automatons.conway/   # Specific rules for Conway's Game of Life
    ├── main.automatons.grid/     # Generic framework for cellular automata
    ├── main.automatons.immigration/ # Specific rules for the Immigration Game
    ├── main.invader/             # Provided example code
    ├── main.automatons.schelling/# Specific rules for Schelling's segregation model
    ├── main.boids/               # Implementation of the Boids model
    ├── main.events/              # Package defining the management of scheduled actions
    ├── main.utils/               # Utility classes (color generation, etc.)
    └── test/                     # JUnit tests
```

## 4. Compilation and Execution

#### **Prerequisites**
* Java Development Kit (JDK) version 17 or higher.
* GNU Make.
* [junit-platform-console-standalone-6.0.1.jar](https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.0.1/junit-platform-console-standalone-6.0.1.jar)

#### **Compile the Project**
To compile all source files, run:
```bash
make
```
This command creates a `bin/` directory containing all `.class` files.

#### **Run a Simulation**

```bash
make run_invader
```
or
```bash
make run_balls
```
or
```bash
make run_conway
```
or
```bash
make run_immigration
```
or
```bash
make run_schelling
```
or
```bash
make run_boids
```

#### **Run Unit Tests**
```bash
make tests
```

#### **Other Makefile Commands**

* **Generate Documentation (Doxyfile):**
  ```bash
  make doc
  ```
  The documentation will be generated in the `doc/` directory and can be accessed by opening `doc/html/index.html`.

* **Clean the Project:**
  ```bash
  make clean
  ```
  This command removes the `bin/` and `doc/` directories.
