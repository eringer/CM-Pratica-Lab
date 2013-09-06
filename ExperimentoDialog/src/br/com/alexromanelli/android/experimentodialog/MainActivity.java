package br.com.alexromanelli.android.experimentodialog;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int MENU_ITEM_ALTERAR = 1;
	public static final int MENU_ITEM_EXCLUIR = 2;

	private static final int OPCAO_EXIBIR_CURSO = 1;
	private static final int OPCAO_EXIBIR_DISCIPLINA = 2;
	private static final int OPCAO_EXIBIR_ALUNO = 3;

	private int opcaoExibir;

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
	private AlertDialog dialogInserir;
	private AlertDialog dialogAlterar;
	
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
		opcaoExibir = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			
			AdapterView.AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
					.getMenuInfo();
			int indiceItem = menuInfo.position;
			
			switch (item.getItemId()) {
			case MENU_ITEM_EXCLUIR:
				acaoRemoverItem(indiceItem);
				return true;
			case MENU_ITEM_ALTERAR:
				acaoAlterarItem(indiceItem);
				return true;
			}
			
		} else if (featureId == Window.FEATURE_OPTIONS_PANEL) {
			
			switch (item.getItemId()) {
			case R.id.menuitemExibirCursos:
				exibirDados(listCurso, aaCurso, R.string.titulo_cursos);
				opcaoExibir = OPCAO_EXIBIR_CURSO;
				return true;

			case R.id.menuitemExibirDisciplinas:
				exibirDados(listDisciplina, aaDisciplina,
						R.string.titulo_disciplinas);
				opcaoExibir = OPCAO_EXIBIR_DISCIPLINA;
				return true;

			case R.id.menuitemExibirAlunos:
				exibirDados(listAluno, aaAluno, R.string.titulo_alunos);
				opcaoExibir = OPCAO_EXIBIR_ALUNO;
				return true;

			case R.id.menuitemIncluirCurso:
				exibirDialogoIncluirCurso();
				return true;

			case R.id.menuitemIncluirDisciplina:
				exibirDialogoIncluirDisciplina();
				return true;

			case R.id.menuitemIncluirAluno:
				exibirDialogoIncluirAluno();
				return true;
			}
		}
		return false;
	}

	private void exibirDados(ArrayList<String> dados,
			ArrayAdapter<String> adapter, int titulo) {
		listDados = dados;
		baDados = adapter;
		listviewDados.setAdapter(adapter);
		textviewTitulo.setText(titulo);
	}

	private void acaoRemoverItem(int indiceItem) {
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
		adBuilder
				.setMessage(R.string.mensagem_confirmacao_excluir)

				.setPositiveButton(R.string.botao_confirmar_exclusao,
						new OnRemoveItemListener(this, indiceItem))

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

	public void removerItem(int indiceItem) {
		listDados.remove(indiceItem);
		baDados.notifyDataSetChanged();
		exibirMensagem(R.string.mensagem_exclusao_realizada);
	}

	private void acaoAlterarItem(int indiceItem) {
		switch (opcaoExibir) {
		case OPCAO_EXIBIR_CURSO:
			exibirDialogoAlterarCurso(indiceItem);
			break;
		case OPCAO_EXIBIR_DISCIPLINA:
			exibirDialogoAlterarDisciplina(indiceItem);
			break;
		case OPCAO_EXIBIR_ALUNO:
			exibirDialogoAlterarAluno(indiceItem);
			break;
		}
	}

	private void exibirDialogoAlterarCurso(final int indiceItem) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();

		builder
			.setView(inflater.inflate(R.layout.formulario_curso, null))
			
			.setPositiveButton(R.string.botao_salvar, 
					new OnUpdateItemListener(this, indiceItem))
						
			.setNegativeButton(R.string.botao_cancelar,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							exibirMensagem(R.string.mensagem_alteracao_cancelada);
						}
					});

		dialogAlterar = builder.create();
		dialogAlterar.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				EditText edittextNomeCurso = (EditText) dialogAlterar
						.findViewById(R.id.edittextNomeCurso);
				// EditText edittextDuracaoCurso =
				// (EditText)this.findViewById(R.id.edittextDuracaoCurso);

				edittextNomeCurso.setText(listCurso.get(indiceItem));
			}
		});
		dialogAlterar.show();
	}

	private void exibirDialogoAlterarDisciplina(int indiceItem) {
		// TODO Auto-generated method stub
		
	}

	private void exibirDialogoAlterarAluno(int indiceItem) {
		// TODO Auto-generated method stub
		
	}

	private void exibirDialogoIncluirCurso() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();

		builder
			.setView(inflater.inflate(R.layout.formulario_curso, null))
			
			.setPositiveButton(R.string.botao_salvar, 
					new OnInsertItemListener(this))
						
			.setNegativeButton(R.string.botao_cancelar,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							exibirMensagem(R.string.mensagem_inclusao_cancelada);
						}
					});

		dialogInserir = builder.create();
		dialogInserir.show();
	}

	private void exibirDialogoIncluirDisciplina() {
		// TODO Auto-generated method stub

	}

	private void exibirDialogoIncluirAluno() {
		// TODO Auto-generated method stub

	}

	public void inserirItem() {
		switch (opcaoExibir) {
		
		case OPCAO_EXIBIR_CURSO:
			inserirItemCurso();
			break;
			
		case OPCAO_EXIBIR_DISCIPLINA:
			break;
			
		case OPCAO_EXIBIR_ALUNO:
			break;
		}
		
		exibirMensagem(R.string.mensagem_inclusao_realizada);
		baDados.notifyDataSetChanged();
	}

	public void alterarItem(int indiceItem) {
		switch (opcaoExibir) {
		
		case OPCAO_EXIBIR_CURSO:
			alterarItemCurso(indiceItem);
			break;
			
		case OPCAO_EXIBIR_DISCIPLINA:
			break;
			
		case OPCAO_EXIBIR_ALUNO:
			break;
		}
		
		exibirMensagem(R.string.mensagem_alteracao_realizada);
		baDados.notifyDataSetChanged();
	}
	
	private void inserirItemCurso() {
		EditText edittextNomeCurso = (EditText) dialogInserir
				.findViewById(R.id.edittextNomeCurso);
		EditText edittextDuracaoCurso = (EditText) dialogInserir
				.findViewById(R.id.edittextDuracaoCurso);

		String nomeCurso = edittextNomeCurso.getText().toString();
		int duracaoCurso = Integer.parseInt(edittextDuracaoCurso.getText()
				.toString());

		/*
		 * apenas para este exemplo. na atividade, cada valor deve estar em
		 * um campo do objeto Curso
		 */
		String curso = nomeCurso + " - [" + duracaoCurso + " períodos]";
		
		listCurso.add(curso);
	}
	
	private void alterarItemCurso(int indiceItem) {
		EditText edittextNomeCurso = (EditText) dialogAlterar
				.findViewById(R.id.edittextNomeCurso);
		EditText edittextDuracaoCurso = (EditText) dialogAlterar
				.findViewById(R.id.edittextDuracaoCurso);

		String nomeCurso = edittextNomeCurso.getText().toString();
		int duracaoCurso = Integer.parseInt(edittextDuracaoCurso.getText()
				.toString());

		/*
		 * apenas para este exemplo. na atividade, cada valor deve estar em
		 * um campo do objeto Curso
		 */
		String curso = nomeCurso + " - [" + duracaoCurso + " períodos]";
		
		listCurso.set(indiceItem, curso);
	}

}
