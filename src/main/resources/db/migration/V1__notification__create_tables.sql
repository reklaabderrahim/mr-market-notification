CREATE SCHEMA IF NOT EXISTS notification;
ALTER SCHEMA notification OWNER TO market_owner;
SET search_path = notification, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

CREATE TABLE if not exists mail
(
    id            BIGINT       NOT NULL,
    uuid          UUID         NOT NULL,
    create_date   TIMESTAMP    NOT NULL,
    update_date   TIMESTAMP    NULL,
    body          text         NOT NULL,
    emitter       VARCHAR(50)  NOT NULL,
    status        VARCHAR(50)  NOT NULL,
    subject       VARCHAR(255) NOT NULL,
    type          VARCHAR(50)  NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    content_type  VARCHAR(30)  NOT NULL,
    PRIMARY KEY (id),
    constraint mail_uuid_unique UNIQUE (uuid)
);

comment on table mail is 'mail main table';
comment on column mail.id is 'The mail ID';
comment on column mail.uuid is 'The mail unique identifier';
comment on column mail.create_date is 'The mail creation (insert) date';
comment on column mail.update_date is 'The mail update date';
comment on column mail.body is 'The mail body';
comment on column mail.emitter is 'The mail sender';
comment on column mail.status is 'The mail status';
comment on column mail.subject is 'The mail subject';
comment on column mail.type is 'The mail type';
comment on column mail.template_name is 'The mail used to create it';
comment on column mail.content_type is 'The mail content type';


ALTER TABLE if exists mail
    OWNER TO market_owner;

CREATE SEQUENCE if not exists mail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE if exists mail_id_seq
    OWNER TO market_owner;

/**************************************************************************************************/

CREATE TABLE if not exists mail_recipients
(
    mail_id    BIGINT NOT NULL,
    recipients CHARACTER VARYING(255),
    PRIMARY KEY (mail_id),
    CONSTRAINT recipients_mail_id_fk FOREIGN KEY (mail_id) REFERENCES mail (id)
);

ALTER TABLE if exists mail_recipients
    OWNER TO market_owner;



comment on table mail_recipients is 'mail_recipients table';
comment on column mail_recipients.mail_id is 'The mail_recipients ID';
comment on column mail_recipients.recipients is 'The recipients';


/***************************************************************************************/
SELECT pg_catalog.setval('mail_id_seq', 1, FALSE);


REVOKE ALL ON SCHEMA notification FROM PUBLIC;
REVOKE ALL ON SCHEMA notification FROM market_owner;
GRANT ALL ON SCHEMA notification TO market_owner;
GRANT USAGE ON SCHEMA notification TO market_select;
GRANT USAGE ON SCHEMA notification TO market_update;


REVOKE ALL ON TABLE mail FROM PUBLIC;
REVOKE ALL ON TABLE mail FROM market_owner;
GRANT ALL ON TABLE mail TO market_owner;
GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLE mail TO market_update;
GRANT SELECT ON TABLE mail TO market_select;

REVOKE ALL ON SEQUENCE mail_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE mail_id_seq FROM market_owner;
GRANT ALL ON SEQUENCE mail_id_seq TO market_owner;
GRANT USAGE ON SEQUENCE mail_id_seq TO market_select;


REVOKE ALL ON TABLE mail_recipients FROM PUBLIC;
REVOKE ALL ON TABLE mail_recipients FROM market_owner;
GRANT ALL ON TABLE mail_recipients TO market_owner;
GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLE mail_recipients TO market_update;
GRANT SELECT ON TABLE mail_recipients TO market_select;


ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON SEQUENCES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON SEQUENCES FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT ALL ON SEQUENCES TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT USAGE ON SEQUENCES TO market_select;


ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON FUNCTIONS FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON FUNCTIONS FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT ALL ON FUNCTIONS TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT ALL ON FUNCTIONS TO market_select;


ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON TABLES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification REVOKE ALL ON TABLES FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT ALL ON TABLES TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLES TO market_update;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA notification GRANT SELECT ON TABLES TO market_select;
