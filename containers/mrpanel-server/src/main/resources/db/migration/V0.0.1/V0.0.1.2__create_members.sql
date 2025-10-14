CREATE SEQUENCE seq_districts_id INCREMENT 50;
CREATE SEQUENCE seq_members_id INCREMENT 1;
CREATE SEQUENCE seq_members_districts_id INCREMENT 50;
CREATE SEQUENCE seq_district_board_members_id INCREMENT 50;
CREATE SEQUENCE seq_member_social_media_id INCREMENT 50;

CREATE TABLE districts
(
    id                BIGINT PRIMARY KEY    DEFAULT nextval('seq_districts_id'),
    version           BIGINT,
    added_at          TIMESTAMP    NOT NULL DEFAULT now(),
    name              VARCHAR(255) NOT NULL,
    group_email       VARCHAR(512) NOT NULL,
    board_group_email VARCHAR(512) NOT NULL
);

CREATE TABLE members
(
    id                BIGINT PRIMARY KEY    DEFAULT nextval('seq_members_id'),
    added_at          TIMESTAMP    NOT NULL DEFAULT now(),
    version           BIGINT,
    preferred_name    VARCHAR(511) NOT NULL,
    formal_name       VARCHAR(511) NOT NULL,
    email             VARCHAR(511) NOT NULL,
    formal_identifier VARCHAR(127) NOT NULL,
    private_email     VARCHAR(511) NOT NULL,
    phone_number      VARCHAR(127) NOT NULL,
    country_code      CHAR(3)      NOT NULL,
    state             VARCHAR(511) NOT NULL,
    province          VARCHAR(511) NOT NULL,
    city              VARCHAR(511) NOT NULL,
    street            VARCHAR(767),
    building_number   VARCHAR(127) NOT NULL,
    apartment_number  VARCHAR(127),
    postal_code       VARCHAR(127) NOT NULL
);

CREATE TABLE member_social_media
(
    id         BIGINT PRIMARY KEY     DEFAULT nextval('seq_member_social_media_id'),
    added_at   TIMESTAMP     NOT NULL DEFAULT now(),
    version    BIGINT,
    member_id  BIGINT        NOT NULL REFERENCES members (id),
    media_name VARCHAR(512)  NOT NULL,
    url        VARCHAR(2048) NOT NULL,
    CONSTRAINT member_social_media_unique UNIQUE (member_id, media_name)
);

CREATE TABLE members_districts
(
    id               BIGINT PRIMARY KEY   DEFAULT nextval('seq_members_districts_id'),
    added_at         TIMESTAMP   NOT NULL DEFAULT now(),
    version          BIGINT,
    member_id        BIGINT      NOT NULL REFERENCES members (id),
    district_id      BIGINT      NOT NULL REFERENCES districts (id),
    membership_level VARCHAR(31) NOT NULL,
    CONSTRAINT members_districts_membership_levels CHECK (membership_level IN ('MAIN', 'SUPPORT'))
);

CREATE TABLE district_board_members
(
    id          BIGINT PRIMARY KEY   DEFAULT nextval('seq_district_board_members_id'),
    added_at    TIMESTAMP   NOT NULL DEFAULT now(),
    version     BIGINT,
    district_id BIGINT      NOT NULL REFERENCES districts (id),
    member_id   BIGINT      NOT NULL REFERENCES members (id),
    role        VARCHAR(31) NOT NULL,
    CONSTRAINT district_board_members_district_unique UNIQUE (district_id, member_id),
    CONSTRAINT district_board_members_role_levels CHECK (role IN ('PRESIDENT', 'BOARD_MEMBER', 'COMMISSIONER'))
);
