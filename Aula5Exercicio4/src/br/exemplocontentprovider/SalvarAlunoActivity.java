package br.exemplocontentprovider;


import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import br.exemplocontentprovider.database.AlunoDB;
import br.exemplocontentprovider.domain.Aluno;
import br.exemplocontentprovider.util.ImageUtil;

public class SalvarAlunoActivity extends Activity {
	private static final int IMG_SDCARD_ACTIVITY = 4;
	
	private ImageView ivImg;
	private EditText etNome;
	private EditText etIdade;
	private EditText etMatricula;
	private EditText etTurma;
	private EditText etFaltas;
	
	private Aluno aluno;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvar_aluno);
		
		ivImg = (ImageView) findViewById(R.id.ivImg);
		
		etNome = (EditText) findViewById(R.id.etNome);
		etIdade = (EditText) findViewById(R.id.etIdade);
		etMatricula = (EditText) findViewById(R.id.etMatricula);
		etTurma = (EditText) findViewById(R.id.etTurma);
		etFaltas = (EditText) findViewById(R.id.etFaltas);
		
		if(getIntent() != null){
			aluno = (Aluno) getIntent().getParcelableExtra("aluno");
			if(aluno != null){
				if(aluno.getBitmap() == null){
					aluno.setBitmap(ImageUtil.setPic(Uri.parse(aluno.getData()), 90, 90));
					ivImg.setImageBitmap(BitmapFactory.decodeFile(aluno.getData()));
				}
				else{
					ivImg.setImageBitmap(aluno.getBitmap());
				}
				ivImg.setContentDescription(aluno.getData());
				
				etNome.setText(aluno.getNome());
				etIdade.setText(aluno.getIdade()+"");
				etMatricula.setText(aluno.getMatricula());
				etTurma.setText(aluno.getTurma());
				etFaltas.setText(aluno.getFaltas()+"");
			}
		}
	}
	
	
	public void salvarAluno(View view){
		ContentValues values = new ContentValues();
		
		values.put(AlunoDB.DB_COL_DATA, ivImg.getContentDescription().toString());
		values.put(AlunoDB.DB_COL_NOME, etNome.getText().toString());
		values.put(AlunoDB.DB_COL_IDADE, Integer.parseInt(etIdade.getText().toString()));
		values.put(AlunoDB.DB_COL_MATRICULA, etMatricula.getText().toString());
		values.put(AlunoDB.DB_COL_TURMA, etTurma.getText().toString());
		values.put(AlunoDB.DB_COL_FALTAS, Integer.parseInt(etFaltas.getText().toString()));
		
		
		AlunoDB alunoDB = new AlunoDB(SalvarAlunoActivity.this);
		long success = 0;
		if(aluno == null)
			success = alunoDB.insert(values);
		else
			success = alunoDB.update(values, AlunoDB.DB_COL_ID+" = "+aluno.getId(), null);
		alunoDB.close();
		
		
		if(success > 0){
			if(aluno == null)
				Toast.makeText(SalvarAlunoActivity.this, "Aluno adicionado com sucesso, id = "+success, Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(SalvarAlunoActivity.this, "Aluno "+aluno.getNome()+" atualizado com sucesso", Toast.LENGTH_SHORT).show();
			
			setResult(RESULT_OK);
			finish();
		}
		else{
			Toast.makeText(SalvarAlunoActivity.this, "Falhou!", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void carregarImagem(View view){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, IMG_SDCARD_ACTIVITY);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		File file;
		String pass = null;
		
		if(requestCode == IMG_SDCARD_ACTIVITY && resultCode == Activity.RESULT_OK){
			try{
				Uri img = data.getData();
				String[] cols = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(img, cols, null, null, null);
				cursor.moveToFirst();
				
				int indexCol = cursor.getColumnIndex(cols[0]);
				String imgString = cursor.getString(indexCol);
				cursor.close();
				
				file = new File(imgString);
				ivImg.setImageBitmap(ImageUtil.setPic(Uri.fromFile(file), 100, 100));
				ivImg.setContentDescription(file.getPath());
				pass = "passou";
			}
			catch(NullPointerException e){ e.printStackTrace(); }
			catch(Exception e){ e.printStackTrace(); }
			finally{
				if(pass == null){
					ivImg.setImageResource(R.drawable.person);
					Toast.makeText(SalvarAlunoActivity.this, "Falhou! Tente novamente", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
