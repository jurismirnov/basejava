CREATE TABLE resume
(
    uuid TEXT PRIMARY KEY NOT NULL,
    full_name TEXT NOT NULL
);

CREATE TABLE contact
(
    id SERIAL,
    resume_uuid TEXT NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    contact_type TEXT NOT NULL,
    contact_value TEXT NOT NULL
);

CREATE TABLE section
(
    id SERIAL,
    resume_uuid TEXT NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    section_type TEXT NOT NULL,
    section_value TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index ON public.contact (resume_uuid, type)

