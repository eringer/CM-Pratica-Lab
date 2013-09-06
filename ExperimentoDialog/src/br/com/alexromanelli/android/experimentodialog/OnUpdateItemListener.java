package br.com.alexromanelli.android.experimentodialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OnUpdateItemListener implements OnClickListener {
	
	private MainActivity refPai;
	private int indiceItem;
	
	public OnUpdateItemListener(MainActivity refPai, int indiceItem) {
		this.refPai = refPai;
		this.indiceItem = indiceItem;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		refPai.alterarItem(indiceItem);
	}

}
