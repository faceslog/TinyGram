# Tinygram

*_Application deployment:_ [`https://tinyinsta-366314.nw.r.appspot.com`](https://tinyinsta-366314.nw.r.appspot.com)*

![Tinygram logo](src/main/webapp/public/images/tinygram-login.png)

<!-- TODO: Basic project presentation -->

<!-- TODO: Add other links? -->




## Front-End

<!-- TODO: I dunno, whether there's something to talk about in particular or not, here ya go... -->



## Back-End

The general back-end problem has been split into two distinct problems, with each its own package:

- [`tinygram.api`](src/main/java/tinygram/api): Managing the resource availability with the public REST API;
- [`tinygram.datastore`](src/main/java/tinygram/datastore): Managing the resource availability with the public REST API.

After several refactorings, we went through different data representations and chose to distinguish the data seen from the front-end, manageable using the API, which we call *resources*, and the data stored internally within the datastore, which we call entities. E.g. the front-end may access and alter [*User*](src/main/java/tinygram/api/UserResource.java) resources using the API, resources mapped to [*User*](src/main/java/tinygram/datastore/UserEntity.java) and [*Follow*](src/main/java/tinygram/datastore/FollowEntity.java) entities within the datastore management.

### API

<!-- TODO -->

### Datastore

<!-- TODO -->



## Benchmark

<!-- TODO: Benchmark presentation and results. -->



## Legacy

A short list of things we planned to do, but could not implement of finish designing with the given project timeline. Thanks to the additional project week, we managed to implement some improvements initially in this list, especially the logarithmic counter allocation and the introduction of a type-safe interface for entity properties ([`tinygram.datastore.Property`](src/main/java/tinygram/datastore/Property.java)).

- Improve API resource updaters with use of JSON serializers (`com.google.api.server.spi.config.Transformer`) to hide public fields, and maybe find another solution to run transactions from APIs and not from resource updaters.
- Improve the datastore interface to manage parallel entity updates, i.e. allow multiple entities to update the same one within the same transaction. Maybe replace all TypedEntity instances with, for each one:
    - an `EntityView` with read-only operations and
    - an `EntityUpdater` with write-only operations, using delayed transaction operations with more functional interfaces.
- Consider using entity groups to improve performance, mainly with counters and Like/Follow entities.
- Find a way to make the benchmark API require specific identifiers, to prevent any logged-in user from using it.

<!-- TODO: Front-end ideas? -->
