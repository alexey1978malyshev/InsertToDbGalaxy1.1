package src;

public class Planet {
    String name;
    Tonnel[] tonnels;

    public Planet(){

    }
    public Planet(int tonnelVal) {  //параметром принимает количество переходов с n-1 го уровня

      tonnels  = new Tonnel[tonnelVal];

    }

    public Planet(String name) {
        this.name = name;
        tonnels= new Tonnel[0];
    }

    @Override
    public String toString() {
        return "P{" +
                " " + name +
                " }";
    }

    public void printIncomeTonnels()
    {
        System.out.println("Тоннели, ведущие на "+this.toString());
        for (Tonnel t: tonnels  ) {
            System.out.println(t);
        }
    }

}
