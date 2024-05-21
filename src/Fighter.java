//Fighter class. Takes in any value and gives it an integer position, used in fights.
public class Fighter<T>{

    public T obj;
    public int position;

    Fighter(T obj, int position) {
        this.obj = obj;
        this.position = position;
    }


}
