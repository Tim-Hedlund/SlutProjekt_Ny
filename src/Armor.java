//the armor class, each character can equip an armor that makes them more resilient.
public class Armor {
    public String name;
    public int flatDamageReduction; //applied first, before percentage reduction
    public float percentDamageReduction; //applied last, after flat reduction
    public float reductionChance;
    public float critReductionChance;

    //Constructor för Armor
    public Armor(String name, int flatDamageReduction, float percentDamageReduction, float reductionChance, float critReductionChance) {
        this.name = name;
        this.flatDamageReduction = flatDamageReduction;
        this.percentDamageReduction = percentDamageReduction;
        this.reductionChance = reductionChance;
        this.critReductionChance = critReductionChance;
    }
}
