CREATE TABLE tfs_rs_processor_key
(
  id                    NUMBER(19, 0) NOT NULL,
  rs_class              VARCHAR2(200) NOT NULL,
  scenario_id           VARCHAR2(200),
  business_process_name VARCHAR2(200),
  CONSTRAINT tfs_rs_processor_key_id PRIMARY KEY (ID),
  UNIQUE (rs_class, scenario_id, business_process_name)
);

CREATE SEQUENCE tfs_rs_processor_key_seq;

CREATE TABLE tfs_operation
(
  id                      NUMBER(19, 0) NOT NULL,
  creation_date           TIMESTAMP(6)  NOT NULL,
  update_date             TIMESTAMP(6),
  status                  VARCHAR2(20)  NOT NULL,
  rq_uid                  VARCHAR2(200) NOT NULL,
  file_uid                VARCHAR2(200) NOT NULL,
  tfs_rs_processor_key_id NUMBER(19, 0),
  CONSTRAINT tfs_operation_id PRIMARY KEY (id),
  CONSTRAINT scenario_id_fk FOREIGN KEY (tfs_rs_processor_key_id) REFERENCES tfs_rs_processor_key (id)
);

CREATE SEQUENCE tfs_operation_seq;