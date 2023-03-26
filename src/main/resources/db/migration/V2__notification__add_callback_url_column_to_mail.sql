SET search_path = notification;

ALTER TABLE mail add column callback_url TEXT NULL;

comment on column mail.callback_url is 'The mail callback Url';