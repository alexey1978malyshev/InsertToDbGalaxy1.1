package src;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:postgresql://127.0.0.1:5432/myGalaxy", "postgres", "123")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
            //runInsert(conn,8,  2);
           // runSelect(conn);
            //runInsert(conn, "Vasya");
            //runSelect(conn);
            Universe newUniverse = new Universe("INPUT5.txt");
            runInsertPlanetToDB(newUniverse,conn);
            runInsertTonnelToDB(newUniverse,conn);
            //runSelect(conn);


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void runSelect(Connection conn) {
        try {
            ArrayList<A> massiv = new ArrayList<>();
            String q = "SELECT * FROM public.planet";
            PreparedStatement statement = conn.prepareStatement(q);
            ResultSet rs = statement.executeQuery();
            System.out.println("по запросу " + q + " получено: ");
            while (rs.next()) {
                /*int i = rs.getInt("id");
                String s = rs.getString("x");
                System.out.println(i + " " + s);
                A a = new A(i, s);
                massiv.add(a);*/
                massiv.add(new A(rs.getInt("level_num"), rs.getInt("num")));

            }

            System.out.println(massiv);
        } catch (SQLException e) {
            System.out.println("select ERROR: " + e.getMessage());
        }
    }

    public static void runInsertPlanetToDB(Universe x,Connection conn)  throws SQLException{

            String q = "DELETE FROM planet;";//"insert into public.planet(level_num,num) values ("+x+","+y+");";
            PreparedStatement statement = conn.prepareStatement(q);
            statement.execute();

            for (LevelOfUniverse l : x.listLevels) {
                int lnum = l.num;
                for (Planet p : l.getPlanets()) {
                    String pNum = p.name.substring(p.name.indexOf('.') + 1);
                    q = "insert INTO public.planet(level_num, num) VALUES (" + lnum + ", " + pNum + ");";
                    // System.out.println(q);
                    statement = conn.prepareStatement(q);
                    statement.execute();
                }
            }
    }
    public static void runInsertTonnelToDB(Universe x, Connection conn) throws SQLException{
        String q = "DELETE FROM tonel;";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.execute();

        for(LevelOfUniverse l: x.listLevels){
            //int lnum = l.num;
            int flevel = l.num-1;
            int tolevel = l.num;
            for(Planet p: l.getPlanets()){
                String pTo = p.name.substring(p.name.indexOf('.') + 1);

                for(Tonnel t: p.tonnels){
                    String pFrom = t.from.name.substring(p.name.indexOf('.')+1);

                    //String  pto = t.to.name;
                    int pcost = t.cost;
                   q = "insert INTO public.tonel(from_level_num,from_planet_num,to_level_num,to_planet_num,price) VALUES (" + flevel + ", " + pFrom + ", " + tolevel + "," + pTo + ", " + pcost + ")";

                    ps = conn.prepareStatement(q);
                    ps.execute();
                }


            }
        }

    }
}

class A {
    int level;
    int num;

    public A(int level, int num) {
        this.level = level;
        this.num = num;
    }

    @Override
    public String toString() {
        return "A{" +
                "level=" + level +
                ", num='" + num + '\'' +
                '}';
    }
}