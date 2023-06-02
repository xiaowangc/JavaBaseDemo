DROP TABLE IF EXISTS `cbe_account_info`;
CREATE TABLE "cbe_account_info" (
    "account_id" char(32) NOT NULL COMMENT '账户id',
    "user_id" varchar(128) NOT NULL COMMENT '用户id',
    "user_type" int(11) NOT NULL COMMENT '账号类型，常量编码account_type【1=公司、2=患者、3、医生、4=医院、5=代理、7=停诊险、10=商户】',
    "user_name" varchar(128) NOT NULL COMMENT '申请姓名',
    "user_id_type" char(8) DEFAULT NULL COMMENT '用户证件类型',
    "user_id_no" varchar(64) DEFAULT NULL COMMENT '用户证件',
    "user_phone" varchar(11) DEFAULT NULL COMMENT '申请注册的用户的手机号',
    "legal_name" varchar(128) DEFAULT NULL COMMENT '申请的企业法人姓名',
    "legal_card_no" varchar(64) DEFAULT NULL COMMENT '企业法人身份证号码',
    "legal_card_no_date" datetime DEFAULT NULL COMMENT '企业法人身份证件有效期',
    "company_score" varchar(255) DEFAULT NULL COMMENT '企业经营范围',
    "company_card_date" datetime DEFAULT NULL COMMENT '企业证件有效期',
    "user_address" varchar(255) DEFAULT NULL COMMENT '用户地址',
    "protocol_no" varchar(64) DEFAULT NULL COMMENT '协议编号',
    "person_nationality" char(16) DEFAULT NULL COMMENT '自然人国籍 56-中国大陆 158-中国台湾 344-中国香港 446-中国澳门 999-其他',
    "person_occupation" char(16) DEFAULT NULL COMMENT '自然人职业',
    "person_sex" char(8) DEFAULT NULL COMMENT '自然人性别 0-女 1-男',
    "person_id_date" datetime DEFAULT NULL COMMENT '自然人身份证件有效期',
    "bnf_name" varchar(128) DEFAULT NULL COMMENT '受益人姓名',
    "bnf_address" varchar(255) DEFAULT NULL COMMENT '受益人地址',
    "bnf_card_type" char(8) DEFAULT NULL COMMENT '受益人证件类型 仅支持身份证-01',
    "bnf_card_no" varchar(32) DEFAULT NULL COMMENT '受益人证件号',
    "bnf_card_date" datetime DEFAULT NULL COMMENT '受益人证件有效期',
    `company_credit_code` varchar(32) NULL COMMENT '企业统一社会信用代码',
    `company_org_code` varchar(32) NULL COMMENT '企业组织机构代码',
    `company_business_license` varchar(32) NULL COMMENT '企业营业执照',
    "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    "update_time" datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    "db_status" tinyint(2) DEFAULT 1 NULL COMMENT '记录状态 1正常 -1删除',
    PRIMARY KEY ("account_id"),
    UNIQUE KEY "idx_pay_account_user_id_user_type" ("user_id","user_type")
);