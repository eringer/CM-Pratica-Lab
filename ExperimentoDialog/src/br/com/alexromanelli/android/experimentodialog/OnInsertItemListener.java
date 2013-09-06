package br.com.alexromanelli.android.experimentodialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OnInsertItemListener implements OnClickListener {

	private MainActivity refPai;
	
	public OnInsertItemListener(MainActivity refPai) {
		this.refPai = refPai;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		refPai.inserirItem();
	}

}
