CREATE TABLE Securities (
                            SeqNo INT IDENTITY(1,1) NOT NULL,                       -- 自增頁面編號
                            ISIN VARCHAR(12) NOT NULL,                               -- 國際證券編碼 (ISIN 通常是12位字符)
                            SecurityCode VARCHAR(10) NOT NULL,                       -- 有價證券代號
                            SecurityName NVARCHAR(100),                             -- 有價證券名稱
                            MarketType NVARCHAR(50),                                -- 市場別
                            SecurityType NVARCHAR(50),                              -- 有價證券別
                            IndustryType NVARCHAR(50),                              -- 產業別
                            PublicOfferingDate DATE,                                -- 公開發行/上市(櫃)/發行日
                            CFICode VARCHAR(6),                                     -- CFICode (通常是6位字符)
                            Remarks NVARCHAR(255),                                   -- 備註
                            CONSTRAINT PK_Securities PRIMARY KEY (SeqNo, ISIN, SecurityCode)  -- 定義複合主鍵
);

-- Adding comments to the table and columns
EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Contains information about securities.',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'SeqNo',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'SeqNo';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'International Securities Identification Number',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'ISIN';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Security Code',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'SecurityCode';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Security Name',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'SecurityName';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Market Type',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'MarketType';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Security Type',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'SecurityType';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Industry Type',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'IndustryType';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Public Offering Date',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'PublicOfferingDate';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Classification of Financial Instruments Code',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'CFICode';

EXEC sp_addextendedproperty
    @name = N'MS_Description',
    @value = N'Remarks',
    @level0type = N'SCHEMA', @level0name = 'dbo',
    @level1type = N'TABLE',  @level1name = 'Securities',
    @level2type = N'COLUMN', @level2name = 'Remarks';