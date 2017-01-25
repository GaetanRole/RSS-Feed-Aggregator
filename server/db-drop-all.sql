alter table feed_item drop foreign key fk_feed_item_flux_id;
drop index ix_feed_item_flux_id on feed_item;

alter table flux drop foreign key fk_flux_user_id;
drop index ix_flux_user_id on flux;

drop table if exists feed_item;

drop table if exists flux;

drop table if exists user;

