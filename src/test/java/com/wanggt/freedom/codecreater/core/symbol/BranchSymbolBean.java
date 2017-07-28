package com.wanggt.freedom.codecreater.core.symbol;

import com.wanggt.freedom.codecreater.annotation.CodeCreaterIgnore;
import com.wanggt.freedom.codecreater.annotation.CodeCreaterType;

@CodeCreaterType
public class BranchSymbolBean {

	@CodeCreaterIgnore
	private boolean haha;

	public boolean isHaha() {
		return haha;
	}

	public void setHaha(boolean haha) {
		this.haha = haha;
	}

}
