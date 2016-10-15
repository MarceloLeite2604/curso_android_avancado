package br.exemplocontentprovider.domain;

import br.exemplocontentprovider.database.AlunoDB;
import android.net.Uri;

public class AlunosContract {
	public static final String AUTHORITY = "br.exemplocontentprovider.aluno.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/alunos");
	public static final int ALUNOS = 1;
	public static final int ALUNOS_ID = 2;
	
	public static final String ALUNO = AlunoDB.DB_TABLE_ALUNO;
	public static final String ID = AlunoDB.DB_COL_ID;
	public static final String DATA = AlunoDB.DB_COL_DATA;
	public static final String NOME = AlunoDB.DB_COL_NOME;
	public static final String MATRICULA = AlunoDB.DB_COL_MATRICULA;
	public static final String IDADE = AlunoDB.DB_COL_IDADE;
	public static final String TURMA = AlunoDB.DB_COL_TURMA;
	public static final String FALTAS = AlunoDB.DB_COL_FALTAS;
	
	private AlunosContract(){}
}
