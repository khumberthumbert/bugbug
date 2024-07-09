SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'moneybug';


ALTER TABLE tb_member
    ADD COLUMN roles VARCHAR(30) NULL;

commit;

ALTER TABLE tb_member MODIFY COLUMN password VARCHAR(255) NOT NULL;


ALTER TABLE tb_member MODIFY COLUMN mem_no INT AUTO_INCREMENT;

select * from tb_member;

ALTER TABLE tb_alert DROP FOREIGN KEY fk_tb_alert_tb_member1;
ALTER TABLE tb_attachment DROP FOREIGN KEY fk_tb_attachment_tb_member1;
ALTER TABLE tb_biz_info DROP FOREIGN KEY fk_tb_biz_info_tb_member1;
ALTER TABLE tb_biz_mem DROP FOREIGN KEY fk_tb_biz_mem_tb_member1;
ALTER TABLE tb_board DROP FOREIGN KEY fk_tb_board_tb_member1;
ALTER TABLE tb_chat_mem DROP FOREIGN KEY fk_tb_chat_mem_tb_member1;
ALTER TABLE tb_coupon DROP FOREIGN KEY fk_tb_coupon_tb_member1;
ALTER TABLE tb_emp_mem DROP FOREIGN KEY fk_tb_emp_mem_tb_member1;
ALTER TABLE tb_mileage DROP FOREIGN KEY fk_tb_mileage_tb_member1;
ALTER TABLE tb_nego DROP FOREIGN KEY fk_tb_nego_tb_member1;
ALTER TABLE tb_pay_method DROP FOREIGN KEY fk_tb_pay_method_tb_member1;
ALTER TABLE tb_resume DROP FOREIGN KEY fk_tb_resume_tb_member1;
ALTER TABLE tb_review DROP FOREIGN KEY fk_tb_review_tb_member1;
ALTER TABLE tb_schedule DROP FOREIGN KEY fk_tb_schedule_tb_member1;
ALTER TABLE tb_set_payment DROP FOREIGN KEY fk_tb_set_payment_tb_member1;
ALTER TABLE tb_social_login DROP FOREIGN KEY fk_tb_social_login_tb_member;
ALTER TABLE tb_wanted DROP FOREIGN KEY fk_tb_wanted_tb_member1;

ALTER TABLE tb_member MODIFY COLUMN mem_no INT AUTO_INCREMENT;

ALTER TABLE tb_alert
    ADD CONSTRAINT fk_tb_alert_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_attachment
    ADD CONSTRAINT fk_tb_attachment_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_biz_info
    ADD CONSTRAINT fk_tb_biz_info_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_biz_mem
    ADD CONSTRAINT fk_tb_biz_mem_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_board
    ADD CONSTRAINT fk_tb_board_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_chat_mem
    ADD CONSTRAINT fk_tb_chat_mem_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_coupon
    ADD CONSTRAINT fk_tb_coupon_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_emp_mem
    ADD CONSTRAINT fk_tb_emp_mem_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_mileage
    ADD CONSTRAINT fk_tb_mileage_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_nego
    ADD CONSTRAINT fk_tb_nego_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_pay_method
    ADD CONSTRAINT fk_tb_pay_method_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_resume
    ADD CONSTRAINT fk_tb_resume_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_review
    ADD CONSTRAINT fk_tb_review_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_schedule
    ADD CONSTRAINT fk_tb_schedule_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_set_payment
    ADD CONSTRAINT fk_tb_set_payment_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_social_login
    ADD CONSTRAINT fk_tb_social_login_tb_member
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);

ALTER TABLE tb_wanted
    ADD CONSTRAINT fk_tb_wanted_tb_member1
        FOREIGN KEY (mem_no) REFERENCES tb_member(mem_no);


