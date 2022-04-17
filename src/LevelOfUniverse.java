package src;

import java.util.Arrays;

public class LevelOfUniverse {
    int num;
     private Planet[] planets;
     public LevelOfUniverse(int n, int kolvoPlanet){
         num = n;
         if(kolvoPlanet>0) {
             planets = new Planet[kolvoPlanet];
             for (int i = 0; i < kolvoPlanet; i++) {
                 planets[i] = new Planet(num+"."+String.valueOf(i+1));
             }
         }
     }

    public Planet[] getPlanets() {
        return planets;
    }

    @Override
    public String toString() {
        return "LevelOfUniverse{" +
                "planets=" + Arrays.toString(planets) +
                '}';
    }

    public void setTonnels(int planetNumber, String tonnelString, LevelOfUniverse previousLevel)
    {
        System.out.println("Planeta "+(planetNumber+1)+": "+tonnelString);

        Planet to = this.planets[planetNumber];
        Planet from;
        String[] words = tonnelString.split(" ");
        to.tonnels = new Tonnel[words.length/2];

        for(int i =0, k=0; i<words.length-1; i+=2, k++)
        {
            int nPfrom = Integer.parseInt(words[i]) -1;
            int cost = Integer.parseInt(words[i+1]);
            //System.out.println("путь на планету "+to+"с "+nPfrom+" стоит "+cost);
            from = previousLevel.getPlanets()[nPfrom];
            //System.out.println("путь на планету "+to+"с "+from+" стоит "+cost);
            Tonnel tonnel = new Tonnel(from, to, cost);
            to.tonnels[k] = tonnel;
        }

    }

    public void printIncomeTonnels()
    {
        System.out.println("Тоннели, ведущие на уровень "+num);
        for (Planet p: planets)
            p.printIncomeTonnels();
    }
}

