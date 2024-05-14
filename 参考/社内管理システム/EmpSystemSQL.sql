create database empsystem;

USE empsystem;

CREATE TABLE `t_login` (
  `account_id` varchar(255) NOT NULL COMMENT '�A�J�E���g',
  `role_id` int(11) NOT NULL COMMENT '���[��ID',
  `password` varchar(64) NOT NULL COMMENT '�p�X���[�h',
  `del_flg` int(11) DEFAULT NULL,
  `ins_date` datetime DEFAULT NULL,
  `ins_emp` char(6) DEFAULT NULL,
  `upd_date` datetime DEFAULT NULL,
  `upd_emp` char(6) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
);

CREATE TABLE `t_empinfo` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `email` varchar(255) DEFAULT NULL COMMENT '�l���[���A�h���X',
  `companyEmail` varchar(255) DEFAULT NULL COMMENT '��Ѓ��[���A�h���X',
  `nameKanji` varchar(100) DEFAULT NULL COMMENT '�����i�����j',
  `nameKana` varchar(100) DEFAULT NULL COMMENT '�����i�J�i�j',
  `sex` varchar(4) DEFAULT NULL COMMENT '����',
  `birthday` date DEFAULT NULL COMMENT '���N����',
  `country` varchar(100) DEFAULT NULL COMMENT '����',
  `postcode` varchar(8) DEFAULT NULL COMMENT '�X�֔ԍ�',
  `address` varchar(300) DEFAULT NULL COMMENT '�Z��',
  `telNo` varchar(16) DEFAULT NULL COMMENT '�d�b�ԍ�',
  `homeStation` varchar(100) DEFAULT NULL COMMENT '�Ŋ�w',
  `enteringDate` date DEFAULT NULL COMMENT '���Г��t',
  `dimissionDate` date DEFAULT NULL COMMENT '�ސE���t',
  `company` varchar(100) DEFAULT NULL COMMENT '�������',
  `employeeType` varchar(100) DEFAULT NULL COMMENT '�Ј��敪',
  `comment` varchar(300) DEFAULT NULL COMMENT '���l',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`)
) ;

CREATE TABLE `t_engineerlist` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `workingDate` date DEFAULT NULL COMMENT '�ғ��J�n��',
  `experience` varchar(4) DEFAULT NULL COMMENT '�����N��',
  `japanese` varchar(100) DEFAULT NULL COMMENT '���{��',
  `techlanguage` varchar(100) DEFAULT NULL COMMENT '���C������',
  `basicDesign` varchar(4) DEFAULT NULL COMMENT '��{�݌v',
  `detailDesign` varchar(4) DEFAULT NULL COMMENT '�ڍא݌v',
  `produce` varchar(4) DEFAULT NULL COMMENT '����',
  `test` varchar(4) DEFAULT NULL COMMENT '�e�X�g',
  `project` varchar(100) DEFAULT NULL COMMENT '���݌���',
  `level` varchar(4) DEFAULT NULL COMMENT '���x��',
  `newEmployee` varchar(45) DEFAULT NULL COMMENT '�V�l',
  `engineerListComment` varchar(255) DEFAULT NULL COMMENT '���l',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_humanresource` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `basicSalary` char(10) DEFAULT NULL COMMENT '��{��',
  `salaryTimes` varchar(2) DEFAULT NULL COMMENT '���d�N��',
  `insuranceLine` varchar(10) DEFAULT NULL COMMENT '�ی��W���@��V���z',
  `healthInsurance` varchar(2) DEFAULT NULL COMMENT '���N�ی�',
  `retirementPay` varchar(2) DEFAULT NULL COMMENT '�����N��',
  `diseaseInturance` varchar(2) DEFAULT NULL COMMENT '���ی�',
  `humanResourceComment` varchar(100) DEFAULT NULL COMMENT '���l',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_retirementpayandhealthinsurance` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `State` char(4) DEFAULT NULL COMMENT '������',
  `insuranceLine` varchar(100) DEFAULT NULL COMMENT '�����',
  `applicationDate` date DEFAULT NULL COMMENT '�\����',
  `startingDate` date DEFAULT NULL COMMENT '�擾�N����',
  `quitDate` date DEFAULT NULL COMMENT '�r���N����',
  `InturanceNote` varchar(100) DEFAULT NULL COMMENT '�N���蒠',
  `HealthInsurance` varchar(100) DEFAULT NULL COMMENT '���N�ی�',
  `retirementPayAndHealthInsuranceComment` varchar(100) DEFAULT NULL COMMENT '���l/���R',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_workingstatus` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `projectName` varchar(255) DEFAULT NULL COMMENT '�Č���',
  `projectAddress` varchar(255) DEFAULT NULL COMMENT '�Ζ��n',
  `projectStation` varchar(100) DEFAULT NULL COMMENT '�Ŋ��w',
  `transportationFee` varchar(45) DEFAULT NULL COMMENT '��ʔ�',
  `startingDay` date DEFAULT NULL COMMENT '�J�n��',
  `endingDay` date DEFAULT NULL COMMENT '�I���\���',
  `maxWorkTime` varchar(4) DEFAULT NULL COMMENT '�����Ǝ���',
  `minWorkTime` varchar(4) DEFAULT NULL COMMENT '������Ǝ���',
  `workTime` varchar(4) DEFAULT NULL COMMENT '��Ǝ���',
  `actuarial` varchar(4) DEFAULT NULL COMMENT '���Z�L��',
  `requestAmount` varchar(100) DEFAULT NULL COMMENT '�������z',
  `contractCompanyAddress` varchar(255) DEFAULT NULL COMMENT '�_���',
  `contractCompanyName` varchar(100) DEFAULT NULL COMMENT '�_���Ж�',
  `projectDepartment` varchar(100) DEFAULT NULL COMMENT '�Ɩ��S������',
  `projecetLeader` varchar(100) DEFAULT NULL COMMENT '�A����S����',
  `projectTelNo` varchar(45) DEFAULT NULL COMMENT '�A����ԍ�',
  `projectEmail` varchar(100) DEFAULT NULL COMMENT '�A���惁�[���A�h���X',
  `contractType` varchar(100) DEFAULT NULL COMMENT '�_�����',
  `paymentTerm` varchar(100) DEFAULT NULL COMMENT '�x������',
  `companyLeader` varchar(100) DEFAULT NULL COMMENT '�{�ВS����',
  `workingStatusComment` varchar(255) DEFAULT NULL COMMENT '���l',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


CREATE TABLE `t_workinsurance` (
  `empCd` char(6) NOT NULL COMMENT '�Ј��ԍ�',
  `workInsuranceState` char(4) DEFAULT NULL COMMENT '������',
  `Salary` varchar(10) DEFAULT NULL COMMENT '���^',
  `applicationDate` date DEFAULT NULL COMMENT '�\����',
  `startingDate` date DEFAULT NULL COMMENT '�擾�N����',
  `quitDate` date DEFAULT NULL COMMENT '�r���N����',
  `workInsuranceNo` varchar(100) DEFAULT NULL COMMENT '��ی��ԍ�',
  `reason` varchar(100) DEFAULT NULL COMMENT '���R',
  `workInsuranceComment` varchar(100) DEFAULT NULL COMMENT '���l',
  `delFlg` int(11) DEFAULT NULL COMMENT '�폜�t���O',
  `insDate` datetime DEFAULT NULL COMMENT '�쐬���t',
  `insEmpCode` char(6) DEFAULT NULL COMMENT '�쐬��',
  `updDate` datetime DEFAULT NULL COMMENT '�X�V���t',
  `updEmpCode` char(6) DEFAULT NULL COMMENT '�X�V��',
  PRIMARY KEY (`empCd`),
  constraint foreign key(empCd) references t_empinfo(empCd) on delete cascade);


