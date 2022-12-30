<div align="center"> <h1> POO-TV </h1></div>

----
#### About the project:

**POO-TV** is a streaming platform, similar to Netflix and HBO MAX. It allows
users to login to their account or register a new one. Movies on the platform, `for now` are
a given list and cannot appear any new one. A user can `purchase  | watch  | like | rate` any movie he pleases
as long as the movie it's available in his current country.

----
#### How it was implemented:
`Json files` which contain the input (from an open session on the platform) are parsed into
the classes of `input package`. <br/> From here the information is copied
into the necessary classes or worked with it as it is.

`Structure of the classes:` <br />
- Page class
    - Authenticated
    - NotAuthenticated
    - LoginPage
    - RegisterPage
    - LogoutPage
    - MoviesPage
    - SeeDetails
    - Upgrades

> Each of which being implemented using a `Singleton design pattern`, being necessary only one
instance of each page per session

- The connection between classes is a `has a` type, which infer that each page has a
  `hashMap` of the pages that can be accessed at any time from page X. Filling of these hashMaps were
  done using the method `setPages` contained in every type of page
- Accessing the input or output variable is done from the `IO` *abstract class*
- Writing an output is done with the `OutputCreater.addObject` which adds
  a new ObjectNode to the ArrayNode

> An `observer` pattern is used to notify all the users when there's a change to the movies' database
(a movie is added/deleted)

> A `command` pattern is used to initiate the 5 types of commands available: `Change page| On page| Database |
Subscribe | Back`. For creating the instances of these commands, a `factory` pattern is used. 

For the `back command`, the `Invoker` has a stack of pages accessed since the user has logged.
When back command is called, the invoker pops last page of the top of the stack when possible, else prints an error.

----
#### Feedback
- Luckily I started implementing the homework only when most of the bugs in the tests where already fixed,
but I can't imagine how pleasurable would've been for those who started early. I truly know that everyone has a lot 
on their shoulders, but a little more testing would've helped. Thank you for your time!