DROP TABLE IF EXISTS `cbe_account_record`;
CREATE TABLE "cbe_account_record" (
  "id" bigint(20) NOT NULL COMMENT '主键id',
  "user_id" varchar(128) NOT NULL COMMENT '用户id',
  "user_type" tinyint(5) NOT NULL COMMENT '账号类型，常量编码account_type【1=公司、2=患者、3、医生、4=医院、5=代理、7=停诊险、10=商户】',
  "account_type" int(11) DEFAULT NULL COMMENT '账户类型（1中信）',
  "account_status" tinyint(5) DEFAULT 0 NULL COMMENT '账户状态 0未开户 1已开户 2销户 ',
  "account_code" varchar(128) DEFAULT NULL COMMENT '账户编码',
  "result_code" varchar(16) DEFAULT NULL COMMENT '开户结果code',
  "result_msg" varchar(32) DEFAULT NULL COMMENT '开户结果描述',
  "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
  "db_status" tinyint(2) DEFAULT 1 NULL COMMENT '记录状态 1正常 -1删除',
  PRIMARY KEY ("id"),
  UNIQUE KEY "idx_user_id_user_type_account_type" ("user_id","user_type","account_type")
);