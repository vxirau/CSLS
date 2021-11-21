# CSLS

**2019/2020**

Graph + Trees | University Project

## Introduction

CS:LS is a game created by La Salle students, based on the game Counter Strike. This project will consist of different phases. Each phase will take care of a small part of the game.
The game consists of a first person shooter where there are two teams, the terrorists who must plant the bomb at one of the available points, and the anti-terrorist team, who must prevent it from planting. Terrorists win if they detonate the bomb, and anti-terrorists if they deactivate it. In any case, the team that manages to eliminate all the enemy team also wins.
During the second semester we will carry out several phases related to the project, culminating in a final delivery that will be where the result obtained is evaluated. In this phase we will have to program the search of the game store, and the detection of objects on the map.

## Functionalities

GRAPHS

To create this video game, we must implement a graph that best represents the data provided, and use Dijkstra's algorithm to find the path with the least chance of encountering an enemy.
Keep in mind that working with probabilities is not necessarily the same as working with distances, and adjust your algorithms accordingly.
Thus, we must be able to choose the point from which artificial intelligence will begin, and the destination point.

TREES

For this video game it is necessary to implement a store, where we will look for the objects for the price. We must also implement a search by position, as for the movement of the character we must know if it is colliding with any object in the environment.
So, when we look for an item, it will return an item for that price and delete it from the store, as it will be purchased.
When an item is searched for its location on the map (X, Y), it will be returned if there is an item in that position (Hit) or if that location is empty (Miss), an object on the map must be able to be removed from quick and easy way.
These data structures need to be able to be easily visualized at any time.
