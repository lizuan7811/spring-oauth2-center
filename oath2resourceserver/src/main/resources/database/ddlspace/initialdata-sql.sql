USE [lizdb]
GO
/****** Object:  Table [dbo].[OAUTHCLIENTDETAILS]    Script Date: 2024/4/9 下午 02:47:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OAUTHCLIENTDETAILS](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[CLIENTID] [varchar](255) NOT NULL,
	[USERNAME] [varchar](255) NULL,
	[RESOURCESIDS] [varchar](255) NULL,
	[CLIENTSECRET] [varchar](255) NOT NULL,
	[SCOPE] [varchar](255) NOT NULL,
	[AUTHORIZEDGRANTTYPES] [varchar](255) NOT NULL,
	[WEBSERVERREDIRECTURIS] [varchar](255) NOT NULL,
	[AUTHORITIES] [varchar](255) NULL,
	[ACCESSTOKENVALIDITY] [int] NULL,
	[REFRESHTOKENVALIDITY] [int] NULL,
	[ADDITIONALINFORMATION] [varchar](255) NULL,
	[AUTOAPPROVE] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 2024/4/9 下午 02:47:02 ******/
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
/****** Object:  Table [dbo].[user_role]    Script Date: 2024/4/9 下午 02:47:02 ******/
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
/****** Object:  Table [dbo].[usertb]    Script Date: 2024/4/9 下午 02:47:02 ******/
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
SET IDENTITY_INSERT [dbo].[OAUTHCLIENTDETAILS] ON 

INSERT [dbo].[OAUTHCLIENTDETAILS] ([id], [CLIENTID], [USERNAME], [RESOURCESIDS], [CLIENTSECRET], [SCOPE], [AUTHORIZEDGRANTTYPES], [WEBSERVERREDIRECTURIS], [AUTHORITIES], [ACCESSTOKENVALIDITY], [REFRESHTOKENVALIDITY], [ADDITIONALINFORMATION], [AUTOAPPROVE]) VALUES (1, N'lizuan', N'lizuan', N'lizuan', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', N'code', N'authorization_code', N'http://localhost:8082/oauth/callback', N'authorities', 3600, 3600, N'nothing only test', N'autoapprove')
SET IDENTITY_INSERT [dbo].[OAUTHCLIENTDETAILS] OFF
GO
SET IDENTITY_INSERT [dbo].[role] ON 

INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (1, N'ROLE_product', N'商品管理員')
INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (2, N'USER', N'系統管理員')
INSERT [dbo].[role] ([id], [name], [name_zh]) VALUES (3, N'ROLE_product', N'用戶管理員')
SET IDENTITY_INSERT [dbo].[role] OFF
GO
SET IDENTITY_INSERT [dbo].[usertb] ON 

INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (1, N'lizuan', N'{bcrypt}$2a$10$LDqK0ms6BbW3/9nGcGHTi.AnROGIl2GBUlwGQ8jufBM8Er1IpfTGi', 1, 1, 1, 1)
INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (2, N'lizuan', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', 1, 1, 1, 1)
INSERT [dbo].[usertb] ([id], [username], [password], [enabled], [accountnonexpired], [accountnonlocked], [credentialsnonexpired]) VALUES (5, N'admin', N'{bcrypt}$2a$10$yDjvM42ZmjPESdgdcKGNieAvMZqxC/Br.dkgbgiHqtEkfIsdKweoK', 1, 1, 1, 1)
SET IDENTITY_INSERT [dbo].[usertb] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__OAUTHCLI__8E7CE2072A673EFD]    Script Date: 2024/4/9 下午 02:47:02 ******/
ALTER TABLE [dbo].[OAUTHCLIENTDETAILS] ADD UNIQUE NONCLUSTERED 
(
	[CLIENTID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__OAUTHCLI__8E7CE207D0E13C9F]    Script Date: 2024/4/9 下午 02:47:02 ******/
ALTER TABLE [dbo].[OAUTHCLIENTDETAILS] ADD UNIQUE NONCLUSTERED 
(
	[CLIENTID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [PK_USERROLE]    Script Date: 2024/4/9 下午 02:47:02 ******/
ALTER TABLE [dbo].[user_role] ADD  CONSTRAINT [PK_USERROLE] PRIMARY KEY NONCLUSTERED 
(
	[id] ASC,
	[uid] ASC,
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[OAUTHCLIENTDETAILS] ADD  DEFAULT ((0)) FOR [ACCESSTOKENVALIDITY]
GO
ALTER TABLE [dbo].[OAUTHCLIENTDETAILS] ADD  DEFAULT ((0)) FOR [REFRESHTOKENVALIDITY]
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
