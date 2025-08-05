CREATE SEQUENCE seq_audit_log_id INCREMENT BY 50 MINVALUE 0;

-- this table is filled automatically using triggers defined in create_members_trace_triggers.sql
-- It stores the most recent data subset from the members table.
CREATE TABLE members_trace
(
    id             BIGINT PRIMARY KEY,
    added_at       TIMESTAMP    NOT NULL DEFAULT now(),
    version        BIGINT DEFAULT 0,
    preferred_name VARCHAR(511) NOT NULL,
    email          VARCHAR(511) NOT NULL
);

CREATE TABLE audit_log
(
    id           bigint       not null default nextval('seq_audit_log_id'),
    added_at     TIMESTAMP    NOT NULL DEFAULT now(),
    member_id    bigint references members_trace (id),
    operation_id varchar(255) not null,
    payload      jsonb,
    url          VARCHAR(4095),
    exception    varchar(255)
);