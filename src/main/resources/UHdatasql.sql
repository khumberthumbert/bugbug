SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'moneybug';


ALTER TABLE tb_member
    ADD COLUMN roles VARCHAR(30) NULL;

commit;