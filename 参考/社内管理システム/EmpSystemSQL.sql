create database empsystem;

USE empsystem;

CREATE TABLE `t_login` (
  `account_id` varchar(255) NOT NULL COMMENT 'アカウント',
  `role_id` int(11) NOT NULL COMMENT 'ロールID',
  `password` varchar(64) NOT NULL COMMENT 'パスワード',
  `del_flg` int(11) DEFAULT NULL,
  `ins_date` datetime DEFAULT NULL,
  `ins_emp` char(6) DEFAULT NULL,
  `upd_date` datetime DEFAULT NULL,
  `upd_emp` char(6) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
);

CREATE TABLE `t_empinfo` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `email` varchar(255) DEFAULT NULL COMMENT '個人メールアドレス',
  `companyEmail` varchar(255) DEFAULT NULL COMMENT '会社メールアドレス',
  `nameKanji` varchar(100) DEFAULT NULL COMMENT '姓名（漢字）',
  `nameKana` varchar(100) DEFAULT NULL COMMENT '姓名（カナ）',
  `sex` varchar(4) DEFAULT NULL COMMENT '性別',
  `birthday` date DEFAULT NULL COMMENT '生年月日',
  `country` varchar(100) DEFAULT NULL COMMENT '国籍',
  `postcode` varchar(8) DEFAULT NULL COMMENT '郵便番号',
  `address` varchar(300) DEFAULT NULL COMMENT '住所',
  `telNo` varchar(16) DEFAULT NULL COMMENT '電話番号',
  `homeStation` varchar(100) DEFAULT NULL COMMENT '最寄駅',
  `enteringDate` date DEFAULT NULL COMMENT '入社日付',
  `dimissionDate` date DEFAULT NULL COMMENT '退職日付',
  `company` varchar(100) DEFAULT NULL COMMENT '所属会社',
  `employeeType` varchar(100) DEFAULT NULL COMMENT '社員区分',
  `comment` varchar(300) DEFAULT NULL COMMENT '備考',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`)
) ;

CREATE TABLE `t_engineerlist` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `workingDate` date DEFAULT NULL COMMENT '稼働開始日',
  `experience` varchar(4) DEFAULT NULL COMMENT '実務年数',
  `japanese` varchar(100) DEFAULT NULL COMMENT '日本語',
  `techlanguage` varchar(100) DEFAULT NULL COMMENT 'メイン言語',
  `basicDesign` varchar(4) DEFAULT NULL COMMENT '基本設計',
  `detailDesign` varchar(4) DEFAULT NULL COMMENT '詳細設計',
  `produce` varchar(4) DEFAULT NULL COMMENT '製造',
  `test` varchar(4) DEFAULT NULL COMMENT 'テスト',
  `project` varchar(100) DEFAULT NULL COMMENT '現在現場',
  `level` varchar(4) DEFAULT NULL COMMENT 'レベル',
  `newEmployee` varchar(45) DEFAULT NULL COMMENT '新人',
  `engineerListComment` varchar(255) DEFAULT NULL COMMENT '備考',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_humanresource` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `basicSalary` char(10) DEFAULT NULL COMMENT '基本給',
  `salaryTimes` varchar(2) DEFAULT NULL COMMENT '月薪年回',
  `insuranceLine` varchar(10) DEFAULT NULL COMMENT '保険標準　報酬月額',
  `healthInsurance` varchar(2) DEFAULT NULL COMMENT '健康保険',
  `retirementPay` varchar(2) DEFAULT NULL COMMENT '厚生年金',
  `diseaseInturance` varchar(2) DEFAULT NULL COMMENT '介護保険',
  `humanResourceComment` varchar(100) DEFAULT NULL COMMENT '備考',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_retirementpayandhealthinsurance` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `State` char(4) DEFAULT NULL COMMENT '加入状況',
  `insuranceLine` varchar(100) DEFAULT NULL COMMENT '加入基準',
  `applicationDate` date DEFAULT NULL COMMENT '申請日',
  `startingDate` date DEFAULT NULL COMMENT '取得年月日',
  `quitDate` date DEFAULT NULL COMMENT '喪失年月日',
  `InturanceNote` varchar(100) DEFAULT NULL COMMENT '年金手帳',
  `HealthInsurance` varchar(100) DEFAULT NULL COMMENT '健康保険',
  `retirementPayAndHealthInsuranceComment` varchar(100) DEFAULT NULL COMMENT '備考/理由',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_workingstatus` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `projectName` varchar(255) DEFAULT NULL COMMENT '案件名',
  `projectAddress` varchar(255) DEFAULT NULL COMMENT '勤務地',
  `projectStation` varchar(100) DEFAULT NULL COMMENT '最寄り駅',
  `transportationFee` varchar(45) DEFAULT NULL COMMENT '交通費',
  `startingDay` date DEFAULT NULL COMMENT '開始日',
  `endingDay` date DEFAULT NULL COMMENT '終了予定日',
  `maxWorkTime` varchar(4) DEFAULT NULL COMMENT '上限作業時間',
  `minWorkTime` varchar(4) DEFAULT NULL COMMENT '下限作業時間',
  `workTime` varchar(4) DEFAULT NULL COMMENT '作業時間',
  `actuarial` varchar(4) DEFAULT NULL COMMENT '精算有無',
  `requestAmount` varchar(100) DEFAULT NULL COMMENT '請求金額',
  `contractCompanyAddress` varchar(255) DEFAULT NULL COMMENT '契約先',
  `contractCompanyName` varchar(100) DEFAULT NULL COMMENT '契約会社名',
  `projectDepartment` varchar(100) DEFAULT NULL COMMENT '業務担当部署',
  `projecetLeader` varchar(100) DEFAULT NULL COMMENT '連絡先担当者',
  `projectTelNo` varchar(45) DEFAULT NULL COMMENT '連絡先番号',
  `projectEmail` varchar(100) DEFAULT NULL COMMENT '連絡先メールアドレス',
  `contractType` varchar(100) DEFAULT NULL COMMENT '契約条件',
  `paymentTerm` varchar(100) DEFAULT NULL COMMENT '支払条件',
  `companyLeader` varchar(100) DEFAULT NULL COMMENT '本社担当者',
  `workingStatusComment` varchar(255) DEFAULT NULL COMMENT '備考',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_workinsurance` (
  `empCd` char(6) NOT NULL COMMENT '社員番号',
  `workInsuranceState` char(4) DEFAULT NULL COMMENT '加入状況',
  `Salary` varchar(10) DEFAULT NULL COMMENT '給与',
  `applicationDate` date DEFAULT NULL COMMENT '申請日',
  `startingDate` date DEFAULT NULL COMMENT '取得年月日',
  `quitDate` date DEFAULT NULL COMMENT '喪失年月日',
  `workInsuranceNo` varchar(100) DEFAULT NULL COMMENT '被保険番号',
  `reason` varchar(100) DEFAULT NULL COMMENT '理由',
  `workInsuranceComment` varchar(100) DEFAULT NULL COMMENT '備考',
  `delFlg` int(11) DEFAULT NULL COMMENT '削除フラグ',
  `insDate` datetime DEFAULT NULL COMMENT '作成日付',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '作成者',
  `updDate` datetime DEFAULT NULL COMMENT '更新日付',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


