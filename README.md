# University-Management-System
Simple university management system
Main functions:
login to the software using credentials stored in the admin table of the DB schema,
generate unique IDs for student and professor
add a new student,
add new professor,
add new course,
add new module,
add new academic year,
a pie chart which shows stat about the number of students, professors, courses and modules

a scene for students with a student tableView with a search function (with filters) which displays main info of a student (studentID, name, surname),
next to it there is a listView with modules, the list displays the modules of a selected student,
can add or remove modules of a selected student,
the modules options are filtered based on the selected student's course,

a scene for professors with the the same layout and functionalities of the student scene but instead of mdoules you can add or remove courses of a selected professor

a scene for the academic year, it has 2 tableViews aand a listView, the first one displays the academic years, the second displays the students of a selected academic year,
the listView shows a list of students without an academic year which can be filtered by course and assign the selected one to an aademic year.

requires javaFX 16


