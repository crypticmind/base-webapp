base-webapp
===========

Base spray + angularjs webapp meant as a boilerplate.

## Features and design decisions:

### Backend

+ HTTP server implemented using [spray](http://spray.io).
+ Service component injection done using the [cake pattern](http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di).
+ Data access layer implemented using [mapperdao](https://code.google.com/p/mapperdao).
+ No actors (only the minimum required by spray to bootstrap).

### Frontend

+ _TBD_

## Roadmap

+ Transaction support (it's there, but I haven't validated it yet).
+ Security.
+ Sample CRUD.
+ Documentation.
+ More to be defined later.
