package br.com.alexromanelli.android.experimentodialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OnRemoveItemListener implements OnClickListener {

	private int indiceItem;
	private MainActivity refPai;

	public OnRemoveItemListener(MainActivity refPai, int indiceItem) {
		this.refPai = refPai;
		this.indiceItem = indiceItem;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		refPai.removerItem(indiceItem);
	}

}
