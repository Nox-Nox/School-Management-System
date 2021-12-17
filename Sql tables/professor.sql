create table professor (
professorID int primary key not null unique auto_increment,
name varchar(30),
surname varchar(30),
gender varchar(6),
age int,
Dob date
)