insert into account() values ();
insert into account() values ();

insert into activity(timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (now(), 1, null, 1, 10000);
insert into activity(timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (now(), 2, null, 2, 10000);