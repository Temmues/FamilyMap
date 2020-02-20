create table user
(
	username varchar(255) not null,
	password varchar(255) not null,
	email varchar(255) not null,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	gender char(1) CHECK (gender='f' OR gender='m'),
	personID varchar(255) not null,
	primary key (personID, userName)
);

create table person
(
	personID varchar(255) not null,
	username varchar(255) not null,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	gender varchar(255) not null,
	fatherID varchar(255) not null,
	motherID varchar(255) not null,
	spouseID varchar(255) not null,
	foreign key(userName) references user(username),
	foreign key(fatherID) references person(personID),
	foreign key(motherID) references person(personID),
	foreign key(spouseID) references person(personID),
);

create table event
(
	eventID varchar(255) not null,
	username varchar(255) not null,
	personID varchar(255) not null,
	latitude double not null,
	longitude double not null,
	country varchar(255) not null,
	city varchar(255) not null,
	currentYear int not null,
	eventType varchar(255),
	foreign key(username) references user(username)
	foreign key(personID) references person(personID)
);

create table authtoken
(
	token varchar(255) not null,
	personID varchar(255) not null,
	foreign key(personID) references person(personID)
);