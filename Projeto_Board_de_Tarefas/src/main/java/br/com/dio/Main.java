package br.com.dio;

//Classe Main é a classe principal do projeto, responsável por iniciar a aplicação. Ela contém o método main, que é o ponto de entrada do programa.

import br.com.dio.persistence.migration.MigrationStrategy;
import br.com.dio.ui.MainMenu;

import java.sql.SQLException;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;


public class Main {

    public static void main(String[] args) {

    try(var connection = getConnection()) {
        new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }

}
