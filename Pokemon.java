import java.util.*; //scanner, ArrayList, Map, HashMap
import java.io.*; //file, filenotfoundexception

public class Pokemon {

  public static void main(String[] args) {
    Pokemon bulb = new Pokemon("Bulbasaur");
    Pokemon ivy = new Pokemon("Ivysaur");
    // System.out.println("HEHEHEHHRE" + ivy.getEvolve());
    Pokemon weeze = new Pokemon("Weezing");
    System.out.println(weeze.getEvolve());
    Pokemon mewtwo = new Pokemon("Mewtwo");
    Pokemon chara = new Pokemon("Charizard");
    System.out.println(mewtwo.getName());

    System.out.println(weeze.getAttacks());

    System.out.println("Testing Bulbasaur properties");
    System.out.println(bulb.getHP());
    System.out.println(bulb.getAttack());
    System.out.println(bulb.getDefense());
    System.out.println(bulb.getSpeed());
    System.out.println(bulb.getType1());
    System.out.println(bulb.getTypeID1());
    System.out.println(bulb.getType2());
    System.out.println(bulb.getTypeID2());
    System.out.println(ivy.getTypeWeakness());
    System.out.println(ivy.getTypeResistance());

    // bulb.attack(weeze, "earthquake");
    System.out.println();
    chara.attack(ivy, chara.getAttacks().get(0));
      System.out.println(ivy.getHP());
    bulb.attack(ivy);
    System.out.println(ivy.getHP());

    Random rand = new Random();
    int x = rand.nextInt(3 - 0)+1;
    bulb.attack(ivy, bulb.getAttacks().get(x));
    // bulb.attack(ivy, "");
    // ivy.attack(bulb, "");
    System.out.println(Pokemon.evolutionID(3));
    System.out.println(Pokemon.idToName(Pokemon.evolutionID(bulb.getID())));

    bulb.attack(ivy, bulb.getAttacks().get(0));
    System.out.println(bulb.getAttacks());
    System.out.println(bulb.getPossibleAttacks());
  }

  private String name, type1, type2;
  private int attack, speed, defense, ID, typeID1, typeID2;
  private double hp;
  private ArrayList<String> attacks, possibleAttacks;
  private ArrayList<String> typeWeakness, typeResistance;
  private boolean evolve = true;

  //Have to convert this way because CSV file gives IDs for types, not names, so
  //we can match them up.
  private String[] types =
  {"Normal", "Fighting", "Flying", "Poison", "Ground",
   "Rock", "Bug", "Ghost", "Steel", "Fire", "Water",
   "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark", "Fairy"};

   private void create(String name1){
     name = name1;

     String[] data = organizeData(name1);

     ID = Integer.parseInt(data[0]);
     type1 = data[2];
     type2 = data[3];

     for (int x = 0; x < types.length; x++){
       if (types[x].equals(type1)) typeID1 = x+1;
       if (types[x].equals(type2)) typeID2 = x+1;
     }

     setWeakandRes();

     hp = Integer.parseInt(data[5]);
     attack = Integer.parseInt(data[6]);
     defense = Integer.parseInt(data[7]);
     speed = Integer.parseInt(data[10]);
   }

  public Pokemon(String name1, ArrayList<String> selectedAttacks){
    create(name1);
    possibleAttacks(name1);
    setAttacks(name1, selectedAttacks);
    haveEvolve(name1);

    // haveEvolve(name1);
    if (evolve){
      evolvedMoves(name1);
    }
  }

  public Pokemon(String name1){
    create(name1);
    possibleAttacks(name1);
    setAttacks(name1);
    haveEvolve(name1);

    // System.out.println(getEvolve());
    // haveEvolve(name1);
    if (evolve){
      evolvedMoves(name1);
    }

  }

  public String toString(){
    return name;
  }

  private void haveEvolve(String named){
    try{
      File f = new File("movesets.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");
        // System.out.println(stats[0] + ", " + named);
        if (stats[0].equals(named)){
          // System.out.println("We in");
          evolve = false;
          break;
        }
        // System.out.println(evolve);
      }
      // evolve = true;
    }
    catch(FileNotFoundException e){
      System.out.println("In haveEvolve");
      throw new Error();
    }
  }

  private static int evolutionID(int index){
    try{
      File f = new File("evolutions.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");

        if (String.valueOf(index).equals(stats[0])){
          return Integer.parseInt(stats[1]);
        }
      }
    }
    catch(FileNotFoundException e){
      System.out.println("ERROR evolutionID");
      throw new Error();
    }
    throw new Error();
  }

  private static String idToName(int index){
    if (index < 0 || index > 151){
      throw new Error();
    }
    try{
      File f = new File("Pokemon.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");

        // System.out.println(index + ", " + stats[0]);
        if (String.valueOf(index).equals(stats[0])){
          return stats[1];
        }
      }
    }
    catch(FileNotFoundException e){
      System.out.println("Error in idToName");
      throw new Error();
    }
    throw new Error();
  }

  private static String nameToID(String names){
    try{
      File f = new File("Pokemon.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");

        if (names.equals(stats[1])){
          return stats[0];
        }
      }
    }
    catch(FileNotFoundException e){
      System.out.println("Error in idToName");
      throw new Error();
    }
    throw new Error();
  }

  public String evolve(int index){
    // System.out.println("here" + idToName(evolutionID(index)));
    return idToName(evolutionID(index));
  }

  public ArrayList<String> possibleAttacks(String name1){
    possibleAttacks = new ArrayList<String>(50);
    try{
      File f = new File("movesets.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");
        // System.out.println(stats[0]);
        // System.out.println(name1);
        // System.out.println(stats[0].equals(name1));
        if (stats[0].equals(name1)){
          Move temp = new Move(name1);

          // System.out.println(temp.getPower());

          possibleAttacks.add(stats[1]);
          possibleAttacks.add(stats[2]);
        }
      }
    }
    catch(FileNotFoundException e){
      System.out.println("Error in possibleAttacks");
      throw new Error();
    }

    return possibleAttacks;
    // if (possibleAttacks.isEmpty()){
    //   // System.out.println("HERE");
    //   String name = evolve(ID);
    //   possibleAttacks(name);
    // }

    // Pokemon poke;
    // System.out.println(evolve(ID));
    // while (possibleAttacks.isEmpty()){
    //   // System.out.println("HERE");
    //   String name2 = evolve(poke.getID());
    //   poke = new Pokemon(name2);
    //   possibleAttacks(poke.getName());

}

    public void evolvedMoves(String name){
      String newName = evolve(Integer.parseInt(nameToID(name)));
      if (possibleAttacks(newName).isEmpty()) {
        String newerName = evolve(Integer.parseInt(nameToID(newName)));
        possibleAttacks(newerName);
        setAttacks(newerName);
      }
    }

    // String evo1 = idToName(evolutionID(ID));
    // Pokemon evoPoke = new Pokemon(evo1);
    //
    //
    // // Can just run this process twice, evolving each time because no Pokemon
    // // has more than 2 evolutions.
    // if (possibleAttacks.isEmpty()){
    //   possibleAttacks(evo1);
    // }
    //
    // String evo2 = idToName(evolutionID(evoPoke.getID()));
    // if (possibleAttacks.isEmpty()){
    //   possibleAttacks(evo2);
    // }

    // while (possibleAttacks.isEmpty()){
    //   System.out.println(idToName(evolutionID(ID))) ;
    //   Pokemon next = new Pokemon(idToName(evolutionID(ID)));
    //   possibleAttacks(next.getName());
    // }

    // if (possibleAttacks.isEmpty()){
      // possibleAttacks(idToName(evolutionID(ID)));
    // }
    // System.out.println(possibleAttacks);

  public void setAttacks(String name1){
    ArrayList<String> temp = new ArrayList<String>();
    possibleAttacks(name1);
    attacks = new ArrayList<String>(4);
    for (int x = 0; x < possibleAttacks.size() && attacks.size() < 5; x++){
      attacks.add(possibleAttacks.get(x));
    }
    for (String element: attacks){
      if (!temp.contains(element)){
        temp.add(element);
      }
    }

    // while (attacks.size() != 4){
    //   Random temp1 = new Random();
    //   // System.out.println(temp1);
    //   int x = temp1.nextInt(4);
    //   System.out.println(x);
    // }
    // for (int x = possibleAttacks.size(); x > 0  && attacks.size() < 5; x--){
    //   attacks.add(possibleAttacks.get(x));
    // }
    // if (!temp.isEmpty()){
    //   attacks = temp;
    // }
  }

  // public String attackstoString(int index){ // One for possibleAttacks, two for just attacks
  //   String output ="";
  //   if (index == 1){
  //     for (int x = 0; x < possibleAttacks.size(); x++){
  //       output += possibleAttacks.get(x).getName();
  //       // System.out.println(possibleAttacks.get(x).getName());
  //       if (x + 1 != possibleAttacks.size()) output += ", ";
  //     }
  //   }
  //   else{
  //     for (int x = 0; x < attacks.size(); x++){
  //       output += attacks.get(x).getName();
  //       if (x + 1 != attacks.size()) output += ", ";
  //     }
  //   }
  //   return output;
  // }

  public static String arrayToString(ArrayList<Move> input){
    String output = "";
    for (int x = 0; x < input.size(); x++){
      // System.out.println(input.get(x));
      // System.out.println(input.get(x).getName());
      output += input.get(x);
      if (x+1 != input.size()) output += ", ";
    }
    return output;
  }

  public void setAttacks(String name1, ArrayList<String> selectedAttacks){
    possibleAttacks(name1);
    attacks = new ArrayList<String>();
    for (int x = 0; x < selectedAttacks.size(); x++){
      if (possibleAttacks.contains(selectedAttacks.get(x))){
        attacks.add(selectedAttacks.get(x));
      }
      else{
        System.out.println("Error in setAttacks");
        throw new Error();
      }
    }
  }

  // organizeData reads through the moves.csv file and returns a String with all
  // the data from the corresponding move.
  private String[] organizeData(String name1){
    try{
      File f = new File("Pokemon.csv");
      Scanner in = new Scanner(f);

      while (in.hasNext()){
        String line = in.nextLine();
        String[] stats = line.split(",");

        if (stats[1].equals(name)) {
          return stats;
        }
      }
    }
    catch (FileNotFoundException e){
      System.out.println("Error");
    }
    throw new Error();
  }

  public boolean isDead(){
    return hp <= 0;
  }

  //Accessor Methods///////////////////
  //This essentially returns every property that a Pokemon has.
  public String getName(){
    return name;
  }

  public int getID(){
    return ID;
  }

  public String getType1(){
    return type1;
  }

  public String getType2(){
    return type2;
  }

  public double getHP(){
    return hp;
  }

  public int getAttack(){
    return attack;
  }

  public boolean getEvolve(){
    return evolve;
  }

  public int getDefense(){
    return defense;
  }

  public ArrayList<String> getAttacks(){
    return attacks;
  }

  public ArrayList<String> getTypeWeakness(){
    return typeWeakness;
  }

  public ArrayList<String> getTypeResistance(){
    return typeResistance;
  }

  public int getSpeed(){
    return speed;
  }

  public int getTypeID1(){
    return typeID1;
  }

  public int getTypeID2(){
    return typeID2;
  }

  public ArrayList<String> getPossibleAttacks(){
    return possibleAttacks;
  }

  /////////////////////////////////

  // Mutator  and Helper Methods

  private void setHP(double num){
    hp = num;
  }

  /////////////////////////////

  // Goes through type_efficacy.csv and checks to align the weaknesses and
  // resistances - easier to do this in one function.

  private void setWeakandRes(){
    typeWeakness = new ArrayList<String>(10);
    typeResistance = new ArrayList<String>(10);

    try{
      File f = new File("type_efficacy");
      Scanner in = new Scanner(f);
      String line = in.nextLine(); // To skip the first row that just has labels

      while (in.hasNext()){
        line = in.nextLine();
        String[] stats = line.split(",");


        if (typeID1 == Integer.parseInt(stats[1])){
          if (Integer.parseInt(stats[2]) == 200){
            typeWeakness.add(stats[0]);
          }
          if (Integer.parseInt(stats[2]) == 50){
            typeResistance.add(stats[0]);
          }
        }

        if (typeID2 == Integer.parseInt(stats[1])){
          if (Integer.parseInt(stats[2]) == 200){
            typeWeakness.add(stats[0]);
          }
          if (Integer.parseInt(stats[2]) == 50){
            typeResistance.add(stats[0]);
          }
        }
      }
    }
      catch(FileNotFoundException e){
        System.out.println("error");
      }
      removeRepeats();
    }


    // There can be a type in the typeWeakness array, and the same type in the
    // typeResistance array due to the fact there some Pokemon have more than
    // one type. This removes the type from both arrays if they exist.
    private void removeRepeats(){
      int count = 0;
      for (int x = 0; x < typeResistance.size(); x++){
        if (typeWeakness.contains(typeResistance.get(x))){
          typeWeakness.remove(typeResistance.get(x));
          typeResistance.remove(x);
          typeResistance.remove(typeResistance.get(x));
        }
      }
    }

  // public void attack(Pokemon enemy){
  //   Random rand = new Random();
  //
  //   ArrayList<String> twoTimes = new ArrayList<String>();
  //   ArrayList<String> fourTimes = new ArrayList<String>();
  //
  //   for (int x = 0; x < attacks.size(); x++){
  //     double mod = modifier(new Move(attacks.get(x)), enemy);
  //     // System.out.println("HERE" + mod);
  //     if (mod == 4){
  //       fourTimes.add(attacks.get(x));
  //     }
  //     if (mod == 2){
  //       twoTimes.add(attacks.get(x));
  //     }
  //   }
  //
  //   if (!fourTimes.isEmpty() && twoTimes.isEmpty()){
  //     int x = rand.nextInt(fourTimes.size());
  //
  //     attack(enemy, attacks.get(x));
  //   }
  //
  //   else if (fourTimes.isEmpty() && !twoTimes.isEmpty()){
  //     int x = rand.nextInt(fourTimes.size());
  //
  //     attack(enemy, attacks.get(x));
  //   }
  //
  //   else if (!fourTimes.isEmpty() && !twoTimes.isEmpty()){
  //     if (rand.nextInt(100) < 70){
  //       int x = rand.nextInt(fourTimes.size());
  //       attack(enemy, fourTimes.get(x));
  //     }
  //     else{
  //       int x = rand.nextInt(twoTimes.size());
  //       attack(enemy, twoTimes.get(x));
  //     }
  //   }

    // else if (fourTimes.isEmpty() && twoTimes.isEmpty()){
    //   int x = rand.nextInt(attacks.size());
    //   System.out.println(x + ", " + attacks.size());
    //   // System.out.println(attacks.size() + "HERE");
    //   attack(enemy, attacks.get(x));
    // }
  //   else{
  //     setAttacks(name);
  //     System.out.println(attacks.get(0));
  //     attack(enemy, attacks.get(0));
  //   }
  // }

    // This calculates the proper modifier, based on if the type exists in the
    // typeWeakness or typeResistance array, and returns .25, .5, 1, 2, or 4
    // based on which array it exists in, and how many times.

private double modifier(Move move, Pokemon enemy){
  int typeID = move.getTypeID();

  if (enemy.getTypeWeakness().contains(String.valueOf(typeID))) {
    int num = Collections.frequency(enemy.getTypeWeakness(), String.valueOf(typeID));
    if (num == 1) return 2;
    if (num == 2) return 4;
  }
  if (enemy.getTypeResistance().contains(String.valueOf(typeID))) {
    int num = Collections.frequency(enemy.getTypeResistance(), String.valueOf(typeID));
    if (num == 1) return .5;
    if (num == 2) return .25;
  }
  return 1;
}

public double attack(Pokemon enemy){
  Random rand = new Random();

  System.out.println(attacks);
  // int calc = rand.nextInt(attacks.size());
  int calc = rand.nextInt((3 - 0) + 1) + 0;

  Move move = new Move(attacks.get(calc));
  double mod = modifier(move, enemy);


  double dmg = ((42 * move.getPower()) *
         (attack / enemy.getDefense()+2)
         / 50 * mod);

  return attack(enemy, attacks.get(calc));
}


  public double attack(Pokemon enemy, String move1){

    // else{
      Move move = new Move(move1);
      double mod = modifier(move, enemy);

      if (!attacks.contains(move1)){
        throw new NumberFormatException();
      }

      // Formula found online - it's the actual formula used to calculate damage

      double dmg = ((42 * move.getPower()) *
             (attack / enemy.getDefense()+2)
             / 50 * mod);

             // System.out.println(dmg + "HEY");

      if (enemy.getHP() - dmg > 0) enemy.setHP(enemy.getHP() - dmg);
      else enemy.setHP(0);

      return dmg;

    // }


  }
}
