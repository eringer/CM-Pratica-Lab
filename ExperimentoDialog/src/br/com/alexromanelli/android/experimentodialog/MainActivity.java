package br.com.alexromanelli.android.experimentodialog;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int MENU_ITEM_ALTERAR = 1;
	public static final int MENU_ITEM_EXCLUIR = 2;

	private ArrayList<String> listCurso;
	private ArrayList<String> listDisciplina;
	private ArrayList<String> listAluno;

	@SuppressWarnings("rawtypes")
	private List listDados;
	private BaseAdapter baDados;

	private ArrayAdapter<String> aaCurso;
	private ArrayAdapter<String> aaDisciplina;
	private ArrayAdapter<String> aaAluno;

	private ListView listviewDados;
	private TextView textviewTitulo;

	private void popularListas() {
		listCurso.add("Sistemas de Informação");
		listCurso.add("Direito");
		listCurso.add("Administração");

		listDisciplina.add("Computação Móvel I");
		listDisciplina.add("Estrutura de Dados I");
		listDisciplina.add("Compiladores");

		listAluno.add("João");
		listAluno.add("Maria");
		listAluno.add("Derp");
		listAluno.add("Derpete");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listCurso = new ArrayList<String>();
		listDisciplina = new ArrayList<String>();
		listAluno = new ArrayList<String>();

		popularListas();

		aaCurso = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listCurso);
		aaDisciplina = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listDisciplina);
		aaAluno = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listAluno);

		listviewDados = (ListView) findViewById(R.id.listviewDados);
		listviewDados.setOnCreateContextMenuListener(this);
		registerForContextMenu(listviewDados);
		textviewTitulo = (TextView) findViewById(R.id.textviewTitulo);
		listDados = null;
		baDados = null;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, MENU_ITEM_EXCLUIR, 0, "Remover");
		menu.add(0, MENU_ITEM_ALTERAR, 1, "Alterar");
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (featureId == Window.FEATURE_CONTEXT_MENU) {
			switch (item.getItemId()) {
			case MENU_ITEM_EXCLUIR:
				AdapterView.AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
						.getMenuInfo();
				int indiceItem = menuInfo.position;
				acaoRemoverItem(indiceItem);
				return true;
			case MENU_ITEM_ALTERAR:
				return true;
			}
		} else if (featureId == Window.FEATURE_OPTIONS_PANEL) {
			switch (item.getItemId()) {
			case R.id.menuitemExibirCursos:
				listDados = listCurso;
				baDados = aaCurso;
				listviewDados.setAdapter(aaCurso);
				textviewTitulo.setText("Cursos:");
				return true;
			case R.id.menuitemExibirDisciplinas:
				listDados = listDisciplina;
				baDados = aaDisciplina;
				listviewDados.setAdapter(aaDisciplina);
				textviewTitulo.setText("Disciplinas:");
				return true;
			case R.id.menuitemExibirAlunos:
				listDados = listAluno;
				baDados = aaAluno;
				listviewDados.setAdapter(aaAluno);
				textviewTitulo.setText("Alunos:");
				return true;
			case R.id.menuitemIncluirCurso:
				exibeDialogoIncluirCurso();
				return true;
			case R.id.menuitemIncluirDisciplina:
				exibeDialogoIncluirDisciplina();
				return true;
			case R.id.menuitemIncluirAluno:
				exibeDialogoIncluirAluno();
				return true;
			}
		}
		return false;
	}

	private void acaoRemoverItem(int indiceItem) {
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
		adBuilder
				.setMessage(R.string.mensagem_confirmacao_excluir)

				.setPositiveButton(R.string.botao_confirmar_exclusao,
						new OnRemoverListener(indiceItem))

				.setNegativeButton(R.string.botao_cancelar_exclusao,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								exibirMensagem(R.string.mensagem_exclusao_cancelada);
							}
						});

		Dialog dialog = adBuilder.create();
		dialog.show();
	}

	protected void exibirMensagem(int mensagem) {
		Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
	}

	private class OnRemoverListener implements DialogInterface.OnClickListener {

		private int indiceItem;

		public OnRemoverListener(int indiceItem) {
			this.indiceItem = indiceItem;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			removerItem(indiceItem);
		}

	}

	public void removerItem(int indiceItem) {
		listDados.remove(indiceItem);
		baDados.notifyDataSetChanged();
		exibirMensagem(R.string.mensagem_exclusao_realizada);
	}

	private void exibeDialogoIncluirAluno() {
		// TODO Auto-generated method stub

	}

	private void exibeDialogoIncluirDisciplina() {
		// TODO Auto-generated method stub

	}

	private void exibeDialogoIncluirCurso() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
