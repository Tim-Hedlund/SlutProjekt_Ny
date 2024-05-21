public class Armor {
//the armor class, each character can equip an armor that makes them more resilient.
    String name;
    int flatDamageReduction; //applied first, before percentage reduction
    float percentDamageReduction; //applied last, after flat reduction
    float reductionChance;
    float critReductionChance;

    //Constructor för Armor
    public Armor(String name, int flatDamageReduction, float percentDamageReduction, float reductionChance, float critReductionChance) {
        this.name = name;
        this.flatDamageReduction = flatDamageReduction;
        this.percentDamageReduction = percentDamageReduction;
        this.reductionChance = reductionChance;
        this.critReductionChance = critReductionChance;


    }
}
