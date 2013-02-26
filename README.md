ActivityPlanner
===============

Interaction programming project for the course [Interaction Programming at KTH](https://www.kth.se/social/course/DH2641/).

# Project description

The project is based on the following requirements (as specified [here](http://www.csc.kth.se/utbildning/kth/kurser/DH2641/iprog11/project.html)):

* enter task data (customer, duration in days, earliest start date, latest delivery date)
* view graphical representations of tasks on production lines in a "task planning chart", visualize at a glance which tasks are planned too early or too late, and which come from the same customer.
* view a table representation of the tasks and their planing, where they can be created and edited.
* drag-and-drop tasks on the graphical representation to re-plan them on another production line, or to another time point on the same line. No tasks should ever intersect on a production line.
* either see the non- planned tasks in a "parking line" and drag them from there to production lines, or be able to drag and drop them directly from the table to the production lines, or both
* scroll the production line view to earlier or later time

* **optional:** *resize the window to visualize the planning over a longer or shorter period. The table should grow/reduce in size proportionally on all columns (JTable usually does that), i.e. the table should take all the width it can take.*
* **optional:** *zoom the production line view on the time axis*
* **optional:** *mark weekends in the task planning chart and do not assume that a task is worked upon on a weeked (so if it reaches a Saturday, its duration is prolongued with at least two days)*
* **optional:** *sort the table by clicking on any of its headings*

# Video prototype

A video prototype showing the interaction with the software:

http://www.youtube.com/watch?v=jwXM0Qu8zoc
