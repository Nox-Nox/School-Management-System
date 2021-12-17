create table course(
courseID int primary key not null unique auto_increment,
courseName varchar(50),
module varchar(50),
type varchar(35),
classroom varchar(4),
date date,
grade int,
professor_ID int,
student_ID int,
foreign key(professor_ID) references professor(professor_ID),
foreign key(student_ID) references student(student_ID)
)