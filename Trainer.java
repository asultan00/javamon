import java.util.*; //scanner, ArrayList, Map
import java.io.*; //file, filenotfoundexception

public class Trainer extends Player{
  private ArrayList<Pokemon> party;
  private boolean catchable;
  private String name;

  public Trainer(String name1, ArrayList<Pokemon> party1){
    super(name1, party1);
    catchable = false;
  }

}
