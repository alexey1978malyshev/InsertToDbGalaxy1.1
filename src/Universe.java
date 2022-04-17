package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Universe
{
    public List<LevelOfUniverse> listLevels  = new ArrayList<>();

    public void addLevel(LevelOfUniverse level)
    {
        listLevels.add(level);
    }

    public Universe(String fname) {
        LevelOfUniverse zero = new LevelOfUniverse(0,1);
        LevelOfUniverse prev = zero;

        System.out.println("грузим Вселенную из файла "+fname);

        Path path = Paths.get(fname);

        try (Stream stream = Files.lines(path, StandardCharsets.UTF_8)) {
            ArrayList<String> text= (ArrayList<String>) stream.collect(Collectors.toList());
            System.out.println(text);

            int index=0;
            int kLevels = Integer.parseInt(text.get(index++));
            listLevels = new ArrayList<>(kLevels+1);
            listLevels.add(zero);
            for (int i = 0; i < kLevels; i++) {
                int kPlanets = Integer.parseInt(text.get(index++));
                LevelOfUniverse level = new LevelOfUniverse(i+1, kPlanets);
                listLevels.add(level);
                for (int j = 1; j <= kPlanets; j++) {
                    String ss = text.get(index++);
                    level.setTonnels(j-1, ss, prev);
                }
                index++;
                prev = level;
            }

        } catch (IOException ex) {
            System.out.println("что-то не то с этим файлом "+fname);
        }


    }

    public SpacePath findBestPath()
    {
        ArrayList<SpacePath> spacePaths=generatePathList();
        /*int minCost=spacePaths.get(0).getTotalCost();
        for (int i = 1; i < spacePaths.size(); i++) {
            if(spacePaths.get(i).getTotalCost()< minCost)
                minCost = spacePaths.get(i).getTotalCost();
        }*/
        SpacePath theBestPath = spacePaths.stream()
                                            .min(Comparator.comparing(SpacePath::getTotalCost))
                                            .get();
        Collections.reverse(theBestPath.steps);
        return theBestPath;
    }

    public void printAllTonnels()
    {
        for (LevelOfUniverse l:listLevels ) {
            l.printIncomeTonnels();
        }
    }

    public ArrayList<Tonnel> getAllTonnel(){
        ArrayList<Tonnel> res = new ArrayList<>();
        for (LevelOfUniverse l:listLevels ) {
            for (Planet p: l.getPlanets()) {
                for (Tonnel t: p.tonnels ) {
                    res.add(t);
                }
            }
        }
        return res;
    }


    public ArrayList<SpacePath> generatePathList() {
        ArrayList<SpacePath> pathList = new ArrayList<>();
        SpacePath path = new SpacePath();
        //Находясь на планете, мы имеем список входящих тоннелей
        //для каждой планеты верхнего уровня {
        Planet[] paradiseWorlds = this.listLevels.get(listLevels.size()-1).getPlanets();
        for (Planet p: paradiseWorlds)
            findPath(p, this.listLevels.get(0).getPlanets()[0], path, pathList);

        return pathList;
    }

    public void findPath(Planet current, Planet finish, SpacePath previousPath, ArrayList<SpacePath> pathList)
    {
        SpacePath currentPath;
        if(current.equals(finish))  {   pathList.add(previousPath);   //System.out.println(previousPath);
                    }
        else { //перебрать все тоннели, которые ведут на планету start с предыдущего уровня
            for (Tonnel t: current.tonnels) {
                //System.out.println("находясь на "+current+" идем по "+t);
                //то есть, добавить тоннель в Путь,
                currentPath = new SpacePath(previousPath);
                currentPath.steps.add(t);
                //выполнить поискПути для другого конца тонееля t
                findPath(t.from, finish, currentPath, pathList);
            }
        }
    }
}
