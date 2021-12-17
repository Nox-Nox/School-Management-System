create table module (
moduleID int primary key not null unique auto_increment,
moduleCode varchar(20),
moduleName varchar(50),
courseID int,
foreign key(courseID) references course(courseID)
)