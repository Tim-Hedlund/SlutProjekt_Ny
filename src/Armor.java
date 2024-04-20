public class Armor {

    String name;
    int flatDamageReduction;
    float percentDamageReduction;
    float reductionChance;
    float critReductionChance;

    public Armor(String name, int flatDamageReduction, float percentDamageReduction, float reductionChance, float critReductionChance) {
        this.name = name;
        this.flatDamageReduction = flatDamageReduction;
        this.percentDamageReduction = percentDamageReduction;
        this.reductionChance = reductionChance;
        this.critReductionChance = critReductionChance;


    }
}
