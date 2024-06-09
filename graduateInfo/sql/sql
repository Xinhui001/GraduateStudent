use graduateStudent
go

create table dbo.Majors
(
    MajorID   int identity
        primary key,
    majorName nvarchar(50) not null
)
go

exec sp_addextendedproperty 'MS_Description', '专业信息表', 'SCHEMA', 'dbo', 'TABLE', 'Majors'
go

exec sp_addextendedproperty 'MS_Description', '专业id', 'SCHEMA', 'dbo', 'TABLE', 'Majors', 'COLUMN', 'MajorID'
go

exec sp_addextendedproperty 'MS_Description', '专业名称', 'SCHEMA', 'dbo', 'TABLE', 'Majors', 'COLUMN', 'majorName'
go

------------------------------------------------------------------------------------------------------------------------

use graduateStudent
go

create table dbo.StudentClasses
(
    ClassID   int identity
        primary key,
    className nvarchar(50) not null,
    MajorID   int
        references dbo.Majors,
    majorName nvarchar(50)
)
go

exec sp_addextendedproperty 'MS_Description', '班级信息表', 'SCHEMA', 'dbo', 'TABLE', 'StudentClasses'
go

exec sp_addextendedproperty 'MS_Description', '班级id', 'SCHEMA', 'dbo', 'TABLE', 'StudentClasses', 'COLUMN', 'ClassID'
go

exec sp_addextendedproperty 'MS_Description', '班级名称', 'SCHEMA', 'dbo', 'TABLE', 'StudentClasses', 'COLUMN',
     'className'
go

exec sp_addextendedproperty 'MS_Description', '专业id', 'SCHEMA', 'dbo', 'TABLE', 'StudentClasses', 'COLUMN', 'MajorID'
go

exec sp_addextendedproperty 'MS_Description', '专业名称', 'SCHEMA', 'dbo', 'TABLE', 'StudentClasses', 'COLUMN',
     'majorName'
go

------------------------------------------------------------------------------------------------------------------------

use graduateStudent
go

create table dbo.Graduates
(
    GraduateID       int identity
        primary key,
    studentName      nvarchar(50) not null,
    gender           nvarchar(10) not null,
    birthDate        nvarchar(50) not null,
    MajorID          int
        references dbo.Majors,
    ClassID          int
        references dbo.StudentClasses,
    graduationYear   nvarchar(50) not null,
    employmentStatus nvarchar(50),
    majorName        nvarchar(50),
    className        nvarchar(50),
    isDeleted        tinyint default 0
)
go

exec sp_addextendedproperty 'MS_Description', '毕业生信息表', 'SCHEMA', 'dbo', 'TABLE', 'Graduates'
go

exec sp_addextendedproperty 'MS_Description', '毕业生信息表id', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN',
     'GraduateID'
go

exec sp_addextendedproperty 'MS_Description', '毕业生姓名', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN',
     'studentName'
go

exec sp_addextendedproperty 'MS_Description', '毕业生性别', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'gender'
go

exec sp_addextendedproperty 'MS_Description', '毕业生生日', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'birthDate'
go

exec sp_addextendedproperty 'MS_Description', '专业id', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'MajorID'
go

exec sp_addextendedproperty 'MS_Description', '班级id', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'ClassID'
go

exec sp_addextendedproperty 'MS_Description', '毕业年份', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN',
     'graduationYear'
go

exec sp_addextendedproperty 'MS_Description', '就业状态', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN',
     'employmentStatus'
go

exec sp_addextendedproperty 'MS_Description', '专业名称', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'majorName'
go

exec sp_addextendedproperty 'MS_Description', '班级名称', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN', 'className'
go

exec sp_addextendedproperty 'MS_Description', '逻辑删除 0-正常 1-删除', 'SCHEMA', 'dbo', 'TABLE', 'Graduates', 'COLUMN',
     'isDeleted'
go

------------------------------------------------------------------------------------------------------------------------

use graduateStudent
go

create table dbo.UserInfo
(
    userId       int identity
        primary key,
    userName     nvarchar(50)       not null,
    userPassword nvarchar(50)       not null,
    userRole     int      default 0 not null,
    userStatus   int      default 0,
    createTime   datetime default getdate(),
    updateTime   datetime default getdate(),
    isDeleted    tinyint  default 0
)
go

exec sp_addextendedproperty 'MS_Description', '用户信息表', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo'
go

exec sp_addextendedproperty 'MS_Description', '用户账户', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN', 'userName'
go

exec sp_addextendedproperty 'MS_Description', '密码', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN', 'userPassword'
go

exec sp_addextendedproperty 'MS_Description', '用户角色   0-普通 1-管理员', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo',
     'COLUMN', 'userRole'
go

exec sp_addextendedproperty 'MS_Description', '帐号状态  0-正常 1-封号', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN',
     'userStatus'
go

exec sp_addextendedproperty 'MS_Description', '创建时间', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN', 'createTime'
go

exec sp_addextendedproperty 'MS_Description', '更新时间', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN', 'updateTime'
go

exec sp_addextendedproperty 'MS_Description', '逻辑删除  0-正常 1-删除', 'SCHEMA', 'dbo', 'TABLE', 'UserInfo', 'COLUMN',
     'isDeleted'
go

------------------------------------------------------------------------------------------------------------------------
