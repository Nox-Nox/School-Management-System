create table result (
resultID int not null primary key unique auto_increment,
mark float,
tTableID int,
student_ID int,
moduleID int,
foreign key (tTableID) references timetable (tTableID),
foreign key (student_ID) references student (student_ID),
foreign key (moduleID) references module (moduleID)
)