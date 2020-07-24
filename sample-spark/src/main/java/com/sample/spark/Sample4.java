package com.sample.spark;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.sample.spark.models.Battle;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Objects;

public class Sample4 {

    private static final String KEY_SPACE = "gameofthrones";
    private static final String TABLE = "battles";

    private static JavaSparkContext sc;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("Java API demo");
        conf.setMaster("local[*]");
        conf.set("spark.cassandra.connection.host", "127.0.0.1");
        sc = new JavaSparkContext(conf);

        printAttackerKingsName();
        printBattles();
        printDefendedBattle();

        sc.close();
    }

    private static void printAttackerKingsName() {
        JavaRDD<String> cassandraRdd = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable(KEY_SPACE, TABLE, CassandraJavaUtil.mapColumnTo(String.class))
                .select("attacker_king")
                .filter(Objects::nonNull)
                .sortBy(name -> name, true, 1);

        System.out.println("################# Attacker King's Name #################");
        System.out.println("#########################################################");
        // Print attacker king's name
        for (String attackerKing : cassandraRdd.collect()) {
            System.out.println(" - " + attackerKing);
        }
    }

    private static void printBattles() {
        JavaRDD<Battle> battles = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable(KEY_SPACE, TABLE, CassandraJavaUtil.mapRowTo(Battle.class))
                .select("battle_number", "year", "attacker_king", "defender_king")
                .sortBy(battle -> battle.getBattle_number(), true, 1);

        System.out.println("################# Battles #################");
        System.out.println("###########################################");
        for (Battle battle : battles.collect()) {
            System.out.println(battle);
        }
    }

    private static void printDefendedBattle() {
        JavaRDD<Battle> battles = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable(KEY_SPACE, TABLE, CassandraJavaUtil.mapRowTo(Battle.class))
                .select("battle_number", "year", "attacker_king", "defender_king")
                .sortBy(battle -> battle.getBattle_number(), true, 1);

        System.out.println("################# Defended Battles #################");
        System.out.println("####################################################");
        battles.foreach(battle ->
                System.out.println(String.format("Battle Number %d was defended by %s.",
                        battle.getBattle_number(),
                        battle.getDefender_king())));
    }

}