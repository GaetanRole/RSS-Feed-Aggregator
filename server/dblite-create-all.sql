create table feed_item (
  id                            integer not null,
  flux_id                       integer,
  title                         TEXT,
  description                   TEXT,
  link                          varchar(255),
  pub_date                      timestamp,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_feed_item primary key (id),
  foreign key (flux_id) references flux (id) on delete restrict on update restrict
);

create table flux (
  id                            integer not null,
  user_id                       integer,
  name                          varchar(255),
  url                           varchar(255),
  tag                           varchar(255),
  title                         TEXT,
  description                   TEXT,
  language                      varchar(255),
  copyright                     varchar(255),
  image                         varchar(255),
  lastsync                      timestamp,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint pk_flux primary key (id),
  foreign key (user_id) references user (id) on delete restrict on update restrict
);

create table user (
  id                            integer not null,
  username                      varchar(255),
  password                      varchar(255),
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id)
);

