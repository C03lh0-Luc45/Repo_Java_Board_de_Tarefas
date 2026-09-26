package br.com.dio.persistence.migration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MigrationStrategy {

    private final Connection connection;


    private void executeMigration(){
        var originalOut : PrintStream = System.out;
        var originalErr : PrintStream = System.err;
      
        try(var fos = new FileOutputStream("liquibase.log")){
            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));
            try(
                    var connection = getConnection();
                    var jdbcConnection = new JdbcConnection(connection);
            ){
                var liquibase = new Liquibase(
                        "/db/changelog/db.changelog-master.yml",
                        new ClassLoaderResourceAccessor(),
                        jdbcConnection);
                liquibase.update();
            } catch (SQLException | LiquibaseException e) {
                e.printStackTrace();
                System.setErr(originalErr);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}
