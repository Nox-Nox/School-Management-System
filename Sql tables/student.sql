create table student(
student_ID int primary key not null unique auto_increment,
studentID int,
name varchar(30),
surname varchar(30),
age int,
DoB date,
gender varchar(6),
lectureID int,
foreign key(lectureID) references lecture(lectureID)
)