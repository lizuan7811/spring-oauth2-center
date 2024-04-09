USE [lizdb]
GO
/****** Object:  Table [dbo].[DATABASECHANGELOG]    Script Date: 2023/11/28 上午 06:51:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DATABASECHANGELOG](
	[ID] [nvarchar](255) NOT NULL,
	[AUTHOR] [nvarchar](255) NOT NULL,
	[FILENAME] [nvarchar](255) NOT NULL,
	[DATEEXECUTED] [datetime2](3) NOT NULL,
	[ORDEREXECUTED] [int] NOT NULL,
	[EXECTYPE] [nvarchar](10) NOT NULL,
	[MD5SUM] [nvarchar](35) NULL,
	[DESCRIPTION] [nvarchar](255) NULL,
	[COMMENTS] [nvarchar](255) NULL,
	[TAG] [nvarchar](255) NULL,
	[LIQUIBASE] [nvarchar](20) NULL,
	[CONTEXTS] [nvarchar](255) NULL,
	[LABELS] [nvarchar](255) NULL,
	[DEPLOYMENT_ID] [nvarchar](10) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[oauthclientdetails]    Script Date: 2023/11/28 上午 06:51:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[oauthclientdetails](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[clientid] [varchar](255) NOT NULL,
	[username] [varchar](255) NULL,
	[resourcesids] [varchar](255) NULL,
	[clientsecret] [varchar](255) NOT NULL,
	[scope] [varchar](255) NOT NULL,
	[authorizedgranttypes] [varchar](255) NOT NULL,
	[webserverredirecturis] [varchar](255) NOT NULL,
	[authorities] [varchar](255) NULL,
	[accesstokenvalidity] [int] NULL,
	[refreshtokenvalidity] [int] NULL,
	[additionalinformation] [varchar](255) NULL,
	[autoapprove] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 2023/11/28 上午 06:51:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](32) NULL,
	[name_zh] [nvarchar](32) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_role]    Script Date: 2023/11/28 上午 06:51:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[uid] [int] NOT NULL,
	[rid] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usertb]    Script Date: 2023/11/28 上午 06:51:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usertb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password] [varchar](200) NOT NULL,
	[enabled] [tinyint] NOT NULL,
	[accountnonexpired] [tinyint] NULL,
	[accountnonlocked] [tinyint] NULL,
	[credentialsnonexpired] [tinyint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'empty', N'database/db.changelog-master.xml', CAST(N'2023-04-01T15:03:14.1800000' AS DateTime2), 1, N'EXECUTED', N'8:d41d8cd98f00b204e9800998ecf8427e', N'empty', N'', NULL, N'4.5.0', NULL, NULL, N'0361393308')
GO
SET IDENTITY_INSERT [dbo].[oauthclientdetails] ON 

INSERT [dbo].[oauthclientdetails] ([id], [clientid], [username], [resourcesids], [clientsecret], [scope], [authorizedgranttypes], [webserverredirecturis], [authorities], [accesstokenvalidity], [refreshtokenvalidity], [additionalinformation], [autoapprove]) VALUES (1, N'lizuan', N'lizuan', N'lizuan', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', N'code', N'authorization_code', N'http://localhost:9999/index', N'authorities', 3600, 3600, N'nothing only test', N'autoapprove')
SET IDENTITY_INSERT [dbo].[oauthclientdetails] OFF
GO
SET IDENTITY_INSERT [dbo].[role] ON 

INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (1, N'ROLE_product', N'商品管理員')
INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (2, N'USER', N'系統管理員')
INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (3, N'ROLE_product', N'用戶管理員')
SET IDENTITY_INSERT [dbo].[role] OFF
GO
SET IDENTITY_INSERT [dbo].[user_role] ON 

INSERT [dbo].[user_role] ([id], [uid], [rid]) VALUES (1, 1, 1)
INSERT [dbo].[user_role] ([id], [uid], [rid]) VALUES (2, 1, 2)
INSERT [dbo].[user_role] ([id], [uid], [rid]) VALUES (3, 2, 2)
INSERT [dbo].[user_role] ([id], [uid], [rid]) VALUES (4, 3, 3)
SET IDENTITY_INSERT [dbo].[user_role] OFF
GO
SET IDENTITY_INSERT [dbo].[usertb] ON 

INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (1, N'user', N'$2a$10$LDqK0ms6BbW3/9nGcGHTi.AnROGIl2GBUlwGQ8jufBM8Er1IpfTGi', 1, 1, 1, 1)
INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (2, N'lizuan', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', 1, 1, 1, 1)
INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (5, N'admin', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', 1, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[usertb] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__oauthcli__819DC768D42186AB]    Script Date: 2023/11/28 上午 06:51:51 ******/
ALTER TABLE [dbo].[oauthclientdetails] ADD UNIQUE NONCLUSTERED 
(
	[clientid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [PK_USERROLE]    Script Date: 2023/11/28 上午 06:51:51 ******/
ALTER TABLE [dbo].[user_role] ADD  CONSTRAINT [PK_USERROLE] PRIMARY KEY NONCLUSTERED 
(
	[id] ASC,
	[uid] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[oauthclientdetails] ADD  DEFAULT ((0)) FOR [accesstokenvalidity]
GO
ALTER TABLE [dbo].[oauthclientdetails] ADD  DEFAULT ((0)) FOR [refreshtokenvalidity]
GO
ALTER TABLE [dbo].[role] ADD  DEFAULT (NULL) FOR [name]
GO
ALTER TABLE [dbo].[role] ADD  DEFAULT (NULL) FOR [name_zh]
GO
ALTER TABLE [dbo].[user_role] ADD  DEFAULT (NULL) FOR [uid]
GO
ALTER TABLE [dbo].[user_role] ADD  DEFAULT (NULL) FOR [rid]
GO
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [accountnonexpired]
GO
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [accountnonlocked]
GO
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [credentialsnonexpired]
GO
