create table timetable(
tTableID int primary key not null unique auto_increment,
date date,
startTime time,
endTime time,
type varchar(50),
moduleID int,
foreign key(moduleID) references module(moduleID)
)