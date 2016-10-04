package com;

/**
 * 1) file
 * 2) mem
 * 3) tcp - http
 * <p>
 * <p>
 * <p>
 * для того, чтобы подключиться к нашей базе -- нам необходимо
 * 1) создать коннект -- на базе урл, пароля и логина
 * 2) для запросов - юзать стейтмент - preparestatement -- то-есть специальн. представители пакета sql = которые позволяют делать запросы - обращение к базе
 * 3) execute - процесс коммита(транзакция)
 * <p>
 * <p>
 * развернуть в h2 -- базу данных, содерж табличку empl
 * вставку, селект -- сравнение записей, addbatch
 * <p>
 * CREATE TABLE EMPL(
 * ID INT,
 * NAME VARCHAR(25)
 * );
 * <p>
 * INSERT INTO EMPL(ID, NAME)
 * VALUES(1, 'EMPL1');
 * <p>
 * INSERT INTO EMPL(ID, NAME)
 * VALUES(2, 'EMPL1');
 * <p>
 * INSERT INTO EMPL(ID, NAME)
 * VALUES(3, 'EMPL1');
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1) регистрация нашего драйвера -- осуществляется с помощью рефлексии
 * 2) получение коннекта к базе данных
 */
public class JDBCTest {

    public static final String EMPL_1 = "\n" +
            "INSERT INTO EMPL(ID, NAME)\n" +
            " VALUES(1, 'EMPL1')";

    public static final String EMPL_2 = " \n" +
            " INSERT INTO EMPL(ID, NAME)\n" +
            " VALUES(2, 'EMPL2')";

    public static final String EMPL_3 = " \n" +
            " INSERT INTO EMPL(ID, NAME)\n" +
            " VALUES(3, 'EMPL3')";

    private Connection connection;
    private List<EmplModel> emplDB = new ArrayList();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JDBCTest jdbcTest = new JDBCTest();
        jdbcTest.createTable();
        jdbcTest.insertEmpl();
        /*
        for (int i = 0; i < 3; i++)
            jdbcTest.insertNewEmpl();
        */
        jdbcTest.selectEmpls();
        jdbcTest.printDbData();
    }

    //org.h2.Driver
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (this.connection == null) {
            Class.forName("org.h2.Driver"); //1-ый пункт
//            connection.setAutoCommit(false);
//            connection.commit();
//            connection.rollback();
//            this.connection = DriverManager.getConnection("jdbc:h2:mem:empl", "", "");//2-й пункт
            this.connection = DriverManager.getConnection("jdbc:h2:file:/home/silverod/empl_test", "", "");//2-й пункт
        }
        return connection;
    }

    public void createTable() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        String createTableSQL = "\n" +
                "CREATE TABLE EMPL(\n" +
                " ID INT,\n" +
                " NAME VARCHAR(25)\n" +
                " )";

        statement.execute(createTableSQL);

    }


    public void insertEmpl() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch(EMPL_1);
        statement.addBatch(EMPL_2);
        statement.addBatch(EMPL_3);
        statement.executeBatch();
//        statement.execute(EMPL_1);
//        statement.execute(EMPL_2);
//        statement.execute(EMPL_3);

    }


    public void insertNewEmpl() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        Scanner scanner = new Scanner(System.in);
        System.out.println("id = ");
        int id = scanner.nextInt();
        System.out.println("name = ");
        String name = scanner.next();

        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO EMPL(ID, NAME)" +
                        "VALUES(?, ?)");

        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();

    }


    public void selectEmpls() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPL");

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            EmplModel emplModel = new EmplModel(id, name);//создание сотрудника на базе 2 полей, взятых из нашей таблички -- "сотрудники"
            emplDB.add(emplModel);
        }
        // boolean execute = statement.execute("SELECT * FROM EMPL");
    }


    public void printDbData() {
        System.out.println("[==empl db===] ");
        System.out.println(emplDB);
        //упрощенный вариант вывода всех сотрудников существующих на уровне таблицы EMPL
        //[EmplModel{id=1, name='EMPL1'}, EmplModel{id=2, name='EMPL2'}, EmplModel{id=3, name='EMPL3'}]


        for (EmplModel emp : emplDB) {
            System.out.println(emp);
        }
        /**
         * EmplModel{id=1, name='EMPL1'}
         * EmplModel{id=2, name='EMPL2'}
         * EmplModel{id=3, name='EMPL3'}
         */
    }

}
