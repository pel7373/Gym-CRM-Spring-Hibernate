DROP TABLE IF EXISTS trainee_trainer;
DROP TABLE IF EXISTS trainings;
DROP TABLE IF EXISTS trainees;
DROP TABLE IF EXISTS trainers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS training_types;

CREATE TABLE training_types (
    id bigint NOT NULL PRIMARY KEY,
    training_type_name character varying(255) NOT NULL UNIQUE,
    CONSTRAINT check_training_type_name CHECK (training_type_name IN
                                               ('fitness', 'yoga', 'Zumba', 'stretching', 'resistance'))
);

CREATE TABLE users (
   id bigint NOT NULL PRIMARY KEY,
   first_name character varying(255) NOT NULL,
   last_name character varying(255) NOT NULL,
   username character varying(255) NOT NULL UNIQUE,
   password character varying(255) NOT NULL,
   is_active boolean NOT NULL
);

CREATE TABLE trainees (
  id bigint NOT NULL PRIMARY KEY,
  date_of_birth date,
  address character varying(255),
  user_id bigint NOT NULL,
  CONSTRAINT fk_trainee_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE trainers (
  id bigint NOT NULL PRIMARY KEY,
  specialization bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT fk_trainer_user FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_trainer_specialization FOREIGN KEY (specialization) REFERENCES training_types (id)
);

CREATE TABLE IF NOT EXISTS trainee_trainer (
   trainee_id bigint NOT NULL,
   trainer_id bigint NOT NULL,
   CONSTRAINT fk_trainee_trainer_trainee FOREIGN KEY (trainee_id) REFERENCES trainees (id),
   CONSTRAINT fk_trainee_trainer_trainer FOREIGN KEY (trainer_id) REFERENCES trainers (id)
);

CREATE TABLE trainings (
    id bigint NOT NULL,
    trainee_id bigint,
    trainer_id bigint,
    training_name character varying(255) NOT NULL,
    training_type_id bigint,
    date date NOT NULL,
    duration integer NOT NULL,
    CONSTRAINT fk_training_trainee FOREIGN KEY (trainee_id) REFERENCES trainees (id),
    CONSTRAINT fk_training_trainer FOREIGN KEY (trainer_id) REFERENCES trainers (id),
    CONSTRAINT fk_training_type FOREIGN KEY (training_type_id) REFERENCES training_types (id)
);