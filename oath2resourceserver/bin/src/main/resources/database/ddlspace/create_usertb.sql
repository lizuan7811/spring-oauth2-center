USE [lizdb]
GO
/****** Object:  Table [dbo].[usertb]    Script Date: 2023/7/22 下午 02:51:18 ******/
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
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [accountnonexpired]
GO
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [accountnonlocked]
GO
ALTER TABLE [dbo].[usertb] ADD  DEFAULT (NULL) FOR [credentialsnonexpired]
GO
