create table feed_item (
  id                            bigint auto_increment not null,
  flux_id                       bigint,
  title                         TEXT,
  description                   TEXT,
  link                          varchar(255),
  pub_date                      datetime(6),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint pk_feed_item primary key (id)
);

create table flux (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  name                          varchar(255),
  url                           varchar(255),
  tag                           varchar(255),
  title                         TEXT,
  description                   TEXT,
  language                      varchar(255),
  copyright                     varchar(255),
  image                         varchar(255),
  lastsync                      datetime(6),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint pk_flux primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_updated                  datetime(6) not null,
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id)
);

alter table feed_item add constraint fk_feed_item_flux_id foreign key (flux_id) references flux (id) on delete restrict on update restrict;
create index ix_feed_item_flux_id on feed_item (flux_id);

alter table flux add constraint fk_flux_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_flux_user_id on flux (user_id);

