package oath2authorizeserver.scrabdatas.persistent;

/**
 * 搜尋的類型
 */
public enum SelectType {
	STKCODE_ONEDAY("stkcode_oneday"),
	STKCODE_MOREDAY("stkcode_moreday"),
	STKCODE_ALL("stkcode_all"),
	ALLCODE_ONEDAY("nocode_oneday");
	
	private String selectType;
	SelectType(String selectType) {
		this.selectType=selectType;
	}
	
	/**
	 * 取Enum名使用
	 */
	public static SelectType getSelectType(String selectType) {
		SelectType resultType=SelectType.STKCODE_ONEDAY;
		for(SelectType sel:values()) {
			if(sel.selectType.equals(selectType))
			{
				resultType=sel;
				break;
			}
		}
		return resultType;
	}
	
	/**
	 * 取Enum值使用
	 */
	public String getSelectType() {
		return this.selectType;
	}
}
